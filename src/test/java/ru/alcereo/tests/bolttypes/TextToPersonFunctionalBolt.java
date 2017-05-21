package ru.alcereo.tests.bolttypes;

import ru.alcereo.FunctionalBolt;
import ru.alcereo.utils.BoltConsumer;
import ru.alcereo.tests.valuetypes.PersonValue;
import ru.alcereo.tests.valuetypes.TextValue;

/**
 * Created by alcereo on 21.05.17.
 */
public class TextToPersonFunctionalBolt extends FunctionalBolt<TextValue, PersonValue> {

    public TextToPersonFunctionalBolt(BoltConsumer<TextValue, PersonValue> consumer) {
        super(TextValue.class, PersonValue.class, consumer);
    }

}