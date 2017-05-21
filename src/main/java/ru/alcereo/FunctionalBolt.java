package ru.alcereo;

import ru.alcereo.utils.BoltConsumer;
import ru.alcereo.utils.Emitter;

/**
 * Created by alcereo on 21.05.17.
 */
public abstract class FunctionalBolt<TYPE_IN extends TupledPojo, TYPE_OUT extends TupledPojo> extends TypeSafeBolt<TYPE_IN, TYPE_OUT> {

    private BoltConsumer<TYPE_IN, TYPE_OUT> consumer;

    public FunctionalBolt(Class<TYPE_IN> classIn, Class<TYPE_OUT> classOut, BoltConsumer<TYPE_IN, TYPE_OUT> consumer) {
        super(classIn, classOut);
        this.consumer = consumer;
    }

    @Override
    public void consume(TYPE_IN inputObject, Emitter<TYPE_OUT> emitter) {
        consumer.consume(inputObject, emitter);
    }
}
