package ru.alcereo.tests.valuetypes;

import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import ru.alcereo.TupledPojo;

/**
 * Created by alcereo on 21.05.17.
 */
public class PersonValue implements TupledPojo {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void fillFromTuple(Tuple input) {
        name = input.getString(0);
        age = input.getInteger(1);
    }

    @Override
    public Values mapToValues() {
        return new Values(
                name,
                age
        );
    }


    @Override
    public String toString() {
        return "PersonValue{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
