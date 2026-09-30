package com.skillverse.hr.model;

public class AccountModel {

    private String userName;
    private String role;

    public AccountModel(String userName) {
        this.userName = userName;
        this.role = "HR Manager";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInitials() {

        if (userName == null || userName.trim().isEmpty()) {
            return "HR";
        }

        String[] parts
                = userName.trim().split("\\s+");

        if (parts.length == 1) {

            return parts[0]
                    .substring(0, 1)
                    .toUpperCase();
        }

        return (parts[0].substring(0, 1)
                + parts[parts.length - 1]
                        .substring(0, 1)).toUpperCase();
    }
}
