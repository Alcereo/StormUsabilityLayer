package ru.alcereo.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.junit.Test;
import ru.alcereo.TypeSafeBolt;
import ru.alcereo.tests.bolttypes.PersonToTextBolt;
import ru.alcereo.tests.bolttypes.TextToPersonBolt;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.tests.valuetypes.TextValue;

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
                new TextToPersonBolt((textValue, emitter) -> {

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

                    emitter.consume(result);

                });


        TypeSafeBolt<PersonValue, TextValue> bolt2 =
                new PersonToTextBolt((value1, consumer) -> {

                    System.out.println("name: "+value1.getName());

                });

        TypeSafeBolt<PersonValue, TextValue> bolt3 =
                new PersonToTextBolt((value1, consumer) -> {

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
}
