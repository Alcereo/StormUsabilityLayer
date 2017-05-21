package ru.alcereo.tests.valuetypes;

import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import ru.alcereo.TupledPojo;

/**
 * Created by alcereo on 21.05.17.
 */
public class TextValue implements TupledPojo{

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void fillFromTuple(Tuple input) {
        text = input.getString(0);
    }

    @Override
    public Values mapToValues() {
        return new Values(text);
    }

}
