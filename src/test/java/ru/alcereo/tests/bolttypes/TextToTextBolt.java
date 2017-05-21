package ru.alcereo.tests.bolttypes;

import ru.alcereo.TypeSafeBolt;
import ru.alcereo.tests.valuetypes.TextValue;
import ru.alcereo.utils.Consumer2WithFunction;
import ru.alcereo.utils.SerializableFunction;

import java.util.function.Function;

/**
 * Created by alcereo on 21.05.17.
 */
public class TextToTextBolt extends TypeSafeBolt<TextValue, TextValue> {

    private Consumer2WithFunction<TextValue, TextValue> mapFunction;

    public TextToTextBolt(Consumer2WithFunction<TextValue, TextValue> fullTextSpoutValueFullTextSpoutValueFunction) {
        super(TextValue.class, TextValue.class);
        this.mapFunction = fullTextSpoutValueFullTextSpoutValueFunction;
    }

    @Override
    public Consumer2WithFunction<TextValue, TextValue> getMapFunction() {
        return mapFunction;
    }
}
