package com.software.courseservice.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeBean {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    public EmployeeBean(String phoneNumber, String firstName, String lastName, String mail, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = mail;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
