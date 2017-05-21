package ru.alcereo.utils;

import java.io.Serializable;

/**
 * Created by alcereo on 21.05.17.
 */
@FunctionalInterface
public interface Consumer2WithFunction<TYPE1, TYPE2> extends Serializable{
    void consume(TYPE1 value1, SerializableConsumer<TYPE2> consumer);
}
