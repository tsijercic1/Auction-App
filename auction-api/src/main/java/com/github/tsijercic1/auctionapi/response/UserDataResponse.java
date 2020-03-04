package com.github.tsijercic1.auctionapi.response;

public class UserDataResponse {
    private Long id;
    private String email, name, surname, token;

    public UserDataResponse() {
    }

    public UserDataResponse(Long id, String username, String email, String name, String surname, String token) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
