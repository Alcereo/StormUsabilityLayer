package ru.alcereo.tests.bolttypes;

import ru.alcereo.FunctionalBolt;
import ru.alcereo.tests.valuetypes.TextValue;
import ru.alcereo.utils.BoltConsumer;

/**
 * Created by alcereo on 21.05.17.
 */
public class TextToTextFunctionalBolt extends FunctionalBolt<TextValue, TextValue> {

    public TextToTextFunctionalBolt(BoltConsumer<TextValue, TextValue> consumer) {
        super(TextValue.class, TextValue.class, consumer);
    }

}
