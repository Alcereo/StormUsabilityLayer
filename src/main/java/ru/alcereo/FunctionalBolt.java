package ru.alcereo;

import ru.alcereo.utils.Consumer2WithFunction;
import ru.alcereo.utils.SerializableConsumer;

/**
 * Created by alcereo on 21.05.17.
 */
public abstract class FunctionalBolt<TYPE_IN extends TupledPojo, TYPE_OUT extends TupledPojo> extends TypeSafeBolt<TYPE_IN, TYPE_OUT> {

    private Consumer2WithFunction<TYPE_IN, TYPE_OUT> mapFunction;

    public FunctionalBolt(Class<TYPE_IN> classIn, Class<TYPE_OUT> classOut, Consumer2WithFunction<TYPE_IN, TYPE_OUT> mapFunction) {
        super(classIn, classOut);
        this.mapFunction = mapFunction;
    }

    @Override
    public void consume(TYPE_IN value1, SerializableConsumer<TYPE_OUT> consumer) {
        mapFunction.consume(value1, consumer);
    }
}
