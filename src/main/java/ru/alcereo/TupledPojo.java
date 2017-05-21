package ru.alcereo;

import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * Created by alcereo on 21.05.17.
 */
public interface TupledPojo {
    void fillFromTuple(Tuple input);
    Values mapToValues();
}
