package com.skillverse.hr.model;

public class CreateAccModel {

    private String name;
    private String password;
    private String address;
    private String gender;

    public CreateAccModel(
            String name,
            String password,
            String address,
            String gender
    ) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}