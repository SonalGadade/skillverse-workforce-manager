
package com.skillverse.manager.model;

public class CreateAccountModel {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;

    public CreateAccountModel(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public boolean validateName() {
        return name != null && !name.trim().isEmpty();
    }

    public boolean validateEmail() {
        return email != null && !email.trim().isEmpty() && email.contains("@");
    }

    public boolean validatePassword() {
        return password != null && !password.trim().isEmpty();
    }

    public boolean validateConfirmPassword() {
        return confirmPassword != null && confirmPassword.equals(password);
    }

    public boolean validateAccount() {
        return validateName() && validateEmail() && validatePassword() && validateConfirmPassword();
    }
}