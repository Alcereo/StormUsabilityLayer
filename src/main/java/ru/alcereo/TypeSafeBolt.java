package ru.alcereo;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import ru.alcereo.utils.Consumer2WithFunction;
import ru.alcereo.utils.SerializableConsumer;

import java.util.Map;
import java.util.function.Function;


/**
 * Created by alcereo on 21.05.17.
 */
public abstract class TypeSafeBolt<TYPE_IN extends TupledPojo, TYPE_OUT extends TupledPojo> extends BaseRichBolt{

    private Class<TYPE_IN> classIn;
    private Class<TYPE_OUT> classOut;

    private OutputCollector collector;


    public TypeSafeBolt(Class<TYPE_IN> classIn, Class<TYPE_OUT> classOut) {
        this.classIn = classIn;
        this.classOut = classOut;
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        try {
            TYPE_IN objectIn = TypeSafeMapper.mapToPojo(input, classIn);
            consume(objectIn, this::emitFunction);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public void emitFunction(TYPE_OUT objectOut){
        Values values = TypeSafeMapper.mapToValues(objectOut);
        collector.emit(values);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(
                new Fields(
                        TypeSafeMapper.getFields(classOut)
                )
        );
    }

    public abstract void consume(TYPE_IN value1, SerializableConsumer<TYPE_OUT> consumer);

}
