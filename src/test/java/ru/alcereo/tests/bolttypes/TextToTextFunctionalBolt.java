package ru.alcereo.tests.bolttypes;

import ru.alcereo.FunctionalBolt;
import ru.alcereo.TypeSafeBolt;
import ru.alcereo.tests.valuetypes.TextValue;
import ru.alcereo.utils.Consumer2WithFunction;
import ru.alcereo.utils.SerializableFunction;

import java.util.function.Function;

/**
 * Created by alcereo on 21.05.17.
 */
public class TextToTextFunctionalBolt extends FunctionalBolt<TextValue, TextValue> {

    public TextToTextFunctionalBolt(Consumer2WithFunction<TextValue, TextValue> mapFunction) {
        super(TextValue.class, TextValue.class, mapFunction);
    }

}
