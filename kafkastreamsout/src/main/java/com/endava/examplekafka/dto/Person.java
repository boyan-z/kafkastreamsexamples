package com.endava.examplekafka.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Person {
    private String firstName;
    private String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
