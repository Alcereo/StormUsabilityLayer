package ru.alcereo.tests.bolttypes;

import ru.alcereo.FunctionalBolt;
import ru.alcereo.TypeSafeBolt;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.tests.valuetypes.TextValue;
import ru.alcereo.utils.Consumer2WithFunction;
import ru.alcereo.utils.SerializableConsumer;

/**
 * Created by alcereo on 21.05.17.
 */
public class PersonToTextFunctionalBolt extends FunctionalBolt<PersonValue, TextValue> {

    public PersonToTextFunctionalBolt(Consumer2WithFunction<PersonValue, TextValue> mapFunction) {
        super(PersonValue.class, TextValue.class, mapFunction);
    }

}