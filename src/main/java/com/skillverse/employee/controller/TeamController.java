package com.skillverse.employee.controller;

import com.skillverse.employee.view.MyTeamView;
import javafx.stage.Stage;

public class TeamController {

    private Stage stage;

    public TeamController(Stage stage) {
        this.stage = stage;
    }

    public void openTeam() {
        MyTeamView teamView =
            new MyTeamView();

        teamView.show(stage, null);
    }
}