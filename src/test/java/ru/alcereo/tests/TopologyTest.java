package ru.alcereo.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.junit.Assert;
import org.junit.Test;
import ru.alcereo.TypeSafeBolt;
import ru.alcereo.tests.bolttypes.PersonToPersonFunctionalBolt;
import ru.alcereo.tests.bolttypes.PersonToTextFunctionalBolt;
import ru.alcereo.tests.bolttypes.TextToPersonFunctionalBolt;
import ru.alcereo.tests.valuetypes.City;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.tests.valuetypes.TextValue;
import ru.alcereo.utils.Emitter;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by alcereo on 21.05.17.
 */
public class TopologyTest {


    @Test
    public void topologyWorkSample() throws InterruptedException {

        LocalCluster cluster = new LocalCluster();

        TypeSafeBolt<TextValue, PersonValue> textToTextBolt1 =
                new TextToPersonFunctionalBolt((textValue, emitter) -> {

                    PersonValue result = new PersonValue();

                    String text = textValue.getText();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode;
                    try {
                        jsonNode = objectMapper.readTree(text);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    result.setName(
                            jsonNode.get("name").asText()
                    );
                    result.setAge(
                            jsonNode.get("age").asInt()
                    );

                    System.out.println(result);

                    emitter.emit(result);

                });


        TypeSafeBolt<PersonValue, TextValue> bolt2 =
                new PersonToTextFunctionalBolt((value1, consumer) -> {

                    System.out.println("name: "+value1.getName());

                });

        TypeSafeBolt<PersonValue, TextValue> bolt3 =
                new PersonToTextFunctionalBolt((value1, consumer) -> {

                    System.out.println("age: "+value1.getAge());

                });


        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("string-reader", new TestSchedulingSpout());
        builder.setBolt("sout-bolt1", textToTextBolt1).shuffleGrouping("string-reader");
        builder.setBolt("sout-bolt2", bolt2).shuffleGrouping("sout-bolt1");
        builder.setBolt("sout-bolt3", bolt3).fieldsGrouping("sout-bolt1", new Fields("name"));

        cluster.submitTopology("test-topology", new HashMap(), builder.createTopology());

        Thread.sleep(20000);

        System.out.println("Exited");

        cluster.shutdown();

    }

    @Test
    public void fieldGroupingTopologyTest() throws InterruptedException {

        LocalCluster cluster = new LocalCluster();

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("string-reader", new TestSchedulingSpout());

        builder.setBolt("bolt1",
                new TextToPersonFunctionalBolt((inputValue, emitter) -> {
                    PersonValue result = new PersonValue();

                    String text = inputValue.getText();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode;
                    try {
                        jsonNode = objectMapper.readTree(text);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    result.setName(
                            jsonNode.get("name").asText()
                    );
                    result.setAge(
                            jsonNode.get("age").asInt()
                    );

                    emitter.emit(result);
                })
        ).shuffleGrouping("string-reader");

        builder.setBolt("city-adder",
                new PersonToPersonFunctionalBolt(
                        (inputValue, emitter) -> {

                            switch (inputValue.getName()){
                                case "Alexander": inputValue.setCity(City.VLAD); break;
                                case "Michael": inputValue.setCity(City.CHELYABA); break;
                                case "Ilya": inputValue.setCity(City.EKAT); break;
                                case "Maxim": inputValue.setCity(City.MOSKVA); break;
                                case "Dmitriy": inputValue.setCity(City.NOVOSIB); break;
                                case "Konstantin": inputValue.setCity(City.PITER); break;
                                case "Pavel": inputValue.setCity(City.TUMEN); break;
                            }

                            emitter.emit(inputValue);
                        }
        )).shuffleGrouping("bolt1");

        builder.setBolt("desc adder",
                new PersonToPersonFunctionalBolt(
                        (inputValue, emitter) -> {
                            switch (inputValue.getName()){
                                case "Alexander": inputValue.setDescription("Alexander in VLAD"); break;
                                case "Michael": inputValue.setDescription("Michael in CHELYABA"); break;
                                case "Ilya": inputValue.setDescription("Ilya in EKAT"); break;
                                case "Maxim": inputValue.setDescription("Maxim in MOSKVA"); break;
                                case "Dmitriy": inputValue.setDescription("Dmitriy in NOVOSIB"); break;
                                case "Konstantin": inputValue.setDescription("Konstantin in PITER"); break;
                                case "Pavel": inputValue.setDescription("Pavel in TUMEN"); break;
                            }

                            emitter.emit(inputValue);
                        }
                )
        ).shuffleGrouping("bolt1");

        builder.setBolt("aggregation-bolt", new PersonAgregationBolt(), 20)
                .fieldsGrouping("desc adder", new Fields("name"))
                .fieldsGrouping("city-adder", new Fields("name"));

        builder.setBolt("print-bolt", new PersonToPersonFunctionalBolt(
                (inputValue, emitter) -> System.out.println(inputValue)
        )).shuffleGrouping("aggregation-bolt");

        cluster.submitTopology("test-topology", new HashMap(), builder.createTopology());

        Thread.sleep(20000);

        System.out.println("Exited");

        cluster.shutdown();

    }

}

class PersonAgregationBolt extends TypeSafeBolt<PersonValue, PersonValue>{
    private PersonValue value;

    public PersonAgregationBolt() {
        super(PersonValue.class, PersonValue.class);
    }

    @Override
    public void consume(PersonValue inputObject, Emitter<PersonValue> emitter) {
        if (value==null) {
            value = inputObject;
        }else {
            if (inputObject.getDescription()!=null){
                value.setDescription(inputObject.getDescription());
            }else if (inputObject.getCity()!=null){
                value.setCity(inputObject.getCity());
            }

            if (value.getName()!=null && value.getDescription()!=null){
                emitter.emit(value);
                value=null;
            }
        }
    }
}
