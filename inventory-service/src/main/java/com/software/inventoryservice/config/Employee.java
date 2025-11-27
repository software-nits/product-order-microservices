package com.software.inventoryservice.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;

    public Employee(String firstName, String lastName, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = mail;
    }
}
