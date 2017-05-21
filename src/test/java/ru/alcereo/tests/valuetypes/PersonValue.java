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
    private City city;
    private String description;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void fillFromTuple(Tuple input) {
        name = input.getString(0);
        age = input.getInteger(1);
        city = (City) input.getValueByField("city");
        description = input.getStringByField("description");
    }

    @Override
    public Values mapToValues() {
        return new Values(
                name,
                age,
                city,
                description
        );
    }

    @Override
    public String toString() {
        return "PersonValue{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", city=" + city +
                ", description='" + description + '\'' +
                '}';
    }
}
