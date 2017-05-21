package ru.alcereo.tests.bolttypes;

import ru.alcereo.FunctionalBolt;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.tests.valuetypes.TextValue;
import ru.alcereo.utils.BoltConsumer;

/**
 * Created by alcereo on 21.05.17.
 */
public class PersonToTextFunctionalBolt extends FunctionalBolt<PersonValue, TextValue> {

    public PersonToTextFunctionalBolt(BoltConsumer<PersonValue, TextValue> consumer) {
        super(PersonValue.class, TextValue.class, consumer);
    }

}