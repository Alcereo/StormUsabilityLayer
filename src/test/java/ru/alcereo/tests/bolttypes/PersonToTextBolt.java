package ru.alcereo.tests.bolttypes;

import ru.alcereo.TypeSafeBolt;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.tests.valuetypes.TextValue;
import ru.alcereo.utils.Consumer2WithFunction;

/**
 * Created by alcereo on 21.05.17.
 */
public class PersonToTextBolt extends TypeSafeBolt<PersonValue, TextValue> {

    private Consumer2WithFunction<PersonValue, TextValue> mapFunction;

    public PersonToTextBolt(Consumer2WithFunction<PersonValue, TextValue> mapFunction) {
        super(PersonValue.class, TextValue.class);
        this.mapFunction = mapFunction;
    }

    @Override
    public Consumer2WithFunction<PersonValue, TextValue> getMapFunction() {
        return mapFunction;
    }
}
