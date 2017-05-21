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
import java.util.*;

/**
 * Created by alcereo on 21.05.17.
 */
public class TestSchedulingSpout extends BaseRichSpout{

    private SpoutOutputCollector collector;

    static final List<String> personsList = Arrays.asList(
            "{\"name\":\"Alexander\",\"age\":\"25\"}",
            "{\"name\":\"Michael\",\"age\":\"16\"}",
            "{\"name\":\"Ilya\",\"age\":\"28\"}",
            "{\"name\":\"Maxim\",\"age\":\"18\"}",
            "{\"name\":\"Dmitriy\",\"age\":\"29\"}",
            "{\"name\":\"Konstantin\",\"age\":\"23\"}",
            "{\"name\":\"Pavel\",\"age\":\"27\"}"
    );

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

        ArrayList<String> list = new ArrayList<>(personsList);
        Collections.shuffle(list);

        list.stream()
                .findAny()
                .map(
                        s -> collector.emit(new Values(s))
                );

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(
                "text"
        ));
    }
}
