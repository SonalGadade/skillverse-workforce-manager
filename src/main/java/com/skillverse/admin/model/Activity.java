package com.skillverse.admin.model;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Activity {

    private final StringProperty time;
    private final StringProperty user;
    private final StringProperty activity;
    private final StringProperty module;
    private final StringProperty status;

    public Activity(
        String time,
        String user,
        String activity,
        String module,
        String status
    ) {

        this.time =
            new SimpleStringProperty(time);

        this.user =
            new SimpleStringProperty(user);

        this.activity =
            new SimpleStringProperty(activity);

        this.module =
            new SimpleStringProperty(module);

        this.status =
            new SimpleStringProperty(status);
    }

    public StringProperty timeProperty() {
        return time;
    }

    public StringProperty userProperty() {
        return user;
    }

    public StringProperty activityProperty() {
        return activity;
    }

    public StringProperty moduleProperty() {
        return module;
    }

    public StringProperty statusProperty() {
        return status;
    }
}
