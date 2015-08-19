package com.entelgy.mediapro.spaininaday.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest implements Serializable {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String phone;
    private boolean legalTerm;


    public boolean isLegalTerm() {
        return legalTerm;
    }

    public void setLegalTerm(boolean legalTerm) {
        this.legalTerm = legalTerm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "{username: \"" + this.username + "\", " +
                "password: \"" + this.password + "\", " +
                "firstName: \"" + this.firstName + "\", " +
                "lastName: \"" + this.lastName + "\", " +
                "email: \"" + this.email + "\", " +
                "dateOfBirth: \"" + this.dateOfBirth + "\", " +
                "phone: \"" + this.phone + "\", " +
                "legalTerm: \"" + this.legalTerm + "\"}";
    }
}
