package com.skillverse.employee.controller;

import com.skillverse.employee.view.MyEventsView;
import javafx.stage.Stage;

public class EventsController {

    private Stage stage;

    public EventsController(Stage stage) {
        this.stage = stage;
    }

    public void openEvents() {
        MyEventsView eventsView =
            new MyEventsView();

        eventsView.show(stage, null);
    }
}