package ru.alcereo.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.junit.Test;
import ru.alcereo.TypeSafeBolt;
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
                new TextToPersonBolt(textValue -> {

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

                    return result;
                });

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("string-reader", new TestSchedulingSpout());
        builder.setBolt("sout-bolt1", textToTextBolt1).shuffleGrouping("string-reader");

        cluster.submitTopology("test-topology", new HashMap(), builder.createTopology());

        Thread.sleep(20000);

        System.out.println("Exited");

        cluster.shutdown();

    }
}
