package com.github.tsijercic1.auctionapi.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * class which gets populated on registration
 */
public class RegistrationRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return getEmail() + "\n" + getPassword() + "\n" + getName() + "\n" + getSurname() + "\n";
    }
}
