package ru.alcereo.tests;

import org.apache.storm.shade.org.apache.commons.collections.list.LazyList;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by alcereo on 21.05.17.
 */
public class TestSchedulingSpout extends BaseRichSpout{

    private SpoutOutputCollector collector;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String text = "{\n" +
                "\t\"name\":\"Alcereo\",\n" +
                "\t\"age\":\"25\"\n" +
                "}";

        Arrays.asList(
                text.split(" ")).forEach(s -> collector.emit(new Values(s))
        );

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(
                "text"
        ));
    }
}
