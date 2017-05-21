package ru.alcereo.tests.bolttypes;

import ru.alcereo.FunctionalBolt;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.utils.BoltConsumer;

/**
 * Created by alcereo on 21.05.17.
 */
public class PersonToPersonFunctionalBolt extends FunctionalBolt<PersonValue, PersonValue> {

    public PersonToPersonFunctionalBolt(BoltConsumer<PersonValue, PersonValue> consumer) {
        super(PersonValue.class, PersonValue.class, consumer);
    }

}