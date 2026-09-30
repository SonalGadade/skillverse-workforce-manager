package com.skillverse.hr.model;

public class HRModel {

    private String userName;
    private String role;
    private String workspaceName;
    private String loginButtonText;
    private String createAccountButtonText;

    public HRModel() {

        this.userName = "HR Manager";
        this.role = "HR";
        this.workspaceName = "HR PLATFORM";
        this.loginButtonText = "Login into your account";
        this.createAccountButtonText = "Create an HR account";
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

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public String getLoginButtonText() {
        return loginButtonText;
    }

    public void setLoginButtonText(String loginButtonText) {
        this.loginButtonText = loginButtonText;
    }

    public String getCreateAccountButtonText() {
        return createAccountButtonText;
    }

    public void setCreateAccountButtonText(String createAccountButtonText) {
        this.createAccountButtonText = createAccountButtonText;
    }
}