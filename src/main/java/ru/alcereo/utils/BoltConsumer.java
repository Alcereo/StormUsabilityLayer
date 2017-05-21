package ru.alcereo.utils;

import java.io.Serializable;

/**
 * Created by alcereo on 21.05.17.
 */
@FunctionalInterface
public interface BoltConsumer<VALUE_TYPE, EMIT_TYPE> extends Serializable{
    void consume(VALUE_TYPE inputValue, Emitter<EMIT_TYPE> emitter);
}
