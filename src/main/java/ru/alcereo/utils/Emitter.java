package ru.alcereo.utils;

import java.io.Serializable;

/**
 * Created by alcereo on 21.05.17.
 */
@FunctionalInterface
public interface Emitter<TYPE> extends Serializable{
    void emit(TYPE value);
}
