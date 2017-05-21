package ru.alcereo.tests.bolttypes;

import ru.alcereo.FunctionalBolt;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.tests.valuetypes.TextValue;
import ru.alcereo.utils.Consumer2WithFunction;

/**
 * Created by alcereo on 21.05.17.
 */
public class PersonToPersonFunctionalBolt extends FunctionalBolt<PersonValue, PersonValue> {

    public PersonToPersonFunctionalBolt(Consumer2WithFunction<PersonValue, PersonValue> mapFunction) {
        super(PersonValue.class, PersonValue.class, mapFunction);
    }

}