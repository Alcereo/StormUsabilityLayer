package ru.alcereo;

import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alcereo on 21.05.17.
 */
class TypeSafeMapper {

    static <TYPE extends TupledPojo> TYPE mapToPojo(Tuple input, Class<TYPE> classIn) throws IllegalAccessException, InstantiationException {
        TYPE newObject = classIn.newInstance();
        newObject.fillFromTuple(input);
        return newObject;
    }

    static <TYPE extends TupledPojo> Values mapToValues(TYPE objectOut) {
        return objectOut.mapToValues();
    }

    static <TYPE extends TupledPojo> List<String> getFields(Class<TYPE> classOut) {
        return Arrays.stream(classOut.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }
}