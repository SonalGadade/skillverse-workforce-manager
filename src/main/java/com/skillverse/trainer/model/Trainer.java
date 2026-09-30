
package com.skillverse.trainer.model;

public class Trainer {

    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String gender;

    public Trainer(
            String fullName,
            String email,
            String password,
            String phone,
            String address,
            String gender) {

        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
    }

   
    public Trainer(
            String fullName,
            String email,
            String phone,
            String address,
            String gender) {

        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.password = "";
    }

    public boolean login(String email, String password) {

        if (this.email == null || this.password == null) {
            return false;
        }

        return this.email.equalsIgnoreCase(email)
                && this.password.equals(password);
    }

    public String getFullName() {
        return fullName;
    }

  
    public String getName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
