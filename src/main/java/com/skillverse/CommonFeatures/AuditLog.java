package com.skillverse.CommonFeatures;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import java.util.Date;

@IgnoreExtraProperties
public class AuditLog {
    private String id;
    private String userEmail;
    private String userName;
    private String userRole;
    private String action;
    private String module; 
    private String status; 
    private Object timestamp;

    public AuditLog() {}

    public AuditLog(String userEmail, String userName, String userRole, String action, String module, String status) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userRole = userRole;
        this.action = action;
        this.module = module;
        this.status = status;
        this.timestamp = new Date();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Object getTimestamp() { return timestamp; }
    public void setTimestamp(Object timestamp) { this.timestamp = timestamp; }
}
