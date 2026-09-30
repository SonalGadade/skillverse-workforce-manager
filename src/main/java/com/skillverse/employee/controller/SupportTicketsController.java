package com.skillverse.employee.controller;

import com.skillverse.employee.view.SupportTicketsView;
import javafx.stage.Stage;

public class SupportTicketsController {

    private Stage stage;

    public SupportTicketsController(Stage stage) {
        this.stage = stage;
    }

    public void openSupportTickets() {
        SupportTicketsView view =
            new SupportTicketsView();

        view.show(stage);
    }
}