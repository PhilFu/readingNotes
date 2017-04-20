package com.philfu.chapter3;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/20.
 */
public class EX9 implements Exercise {
    @Override
    @Test
    public void perform() {
        Person[] persons = {
                new Person("John", "Green"),
                new Person(null, "Black"),
                new Person("Adam", "White")
        };
        Arrays.sort(persons, lexographicComparator("lastname", "firstname"));
        assertEquals("Black", persons[0].lastname);
        Arrays.sort(persons, lexographicComparator("firstname", "lastname"));
        assertEquals("White", persons[0].lastname);
    }

    public Comparator lexographicComparator(String... args) {
        return (x, y) -> {
            try {
                for (String arg : args) {
                    Field field = x.getClass().getDeclaredField(arg);
                    field.setAccessible(true);
                    Object xValue = field.get(x);
                    Object yValue = field.get(y);

                    if (xValue == null && yValue == null) {
                        continue;
                    }
                    if (xValue == null || yValue == null) {
                        return xValue == null ? 1 : -1;
                    }
                    if (!xValue.equals(yValue)) {
                        return xValue.toString().compareTo(yValue.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        };
    }

    class Person {
        private String firstname;
        private String lastname;

        Person(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }
    }
}

