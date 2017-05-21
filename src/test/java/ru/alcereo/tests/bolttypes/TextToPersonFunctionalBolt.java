package ru.alcereo.tests.bolttypes;

import ru.alcereo.FunctionalBolt;
import ru.alcereo.TypeSafeBolt;
import ru.alcereo.utils.Consumer2WithFunction;
import ru.alcereo.utils.SerializableFunction;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.tests.valuetypes.TextValue;

import java.util.function.Function;

/**
 * Created by alcereo on 21.05.17.
 */
public class TextToPersonFunctionalBolt extends FunctionalBolt<TextValue, PersonValue> {

    public TextToPersonFunctionalBolt(Consumer2WithFunction<TextValue, PersonValue> mapFunction) {
        super(TextValue.class, PersonValue.class, mapFunction);
    }

}