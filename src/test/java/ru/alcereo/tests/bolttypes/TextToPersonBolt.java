package ru.alcereo.tests.bolttypes;

import ru.alcereo.TypeSafeBolt;
import ru.alcereo.utils.SerializableFunction;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.tests.valuetypes.TextValue;

import java.util.function.Function;

/**
 * Created by alcereo on 21.05.17.
 */
public class TextToPersonBolt extends TypeSafeBolt<TextValue, PersonValue> {

    private SerializableFunction<TextValue, PersonValue> mapFunction;

    public TextToPersonBolt(SerializableFunction<TextValue, PersonValue> mapFunction) {
        super(TextValue.class, PersonValue.class);
        this.mapFunction = mapFunction;
    }

    @Override
    public Function<TextValue, PersonValue> getMapFunction() {
        return mapFunction;
    }
}