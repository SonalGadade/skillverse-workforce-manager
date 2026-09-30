package com.skillverse.hr.controller;

import com.skillverse.hr.view.Account;
import com.skillverse.hr.view.Feed;
import com.skillverse.hr.view.HR;

import javafx.scene.Scene;

public class PostController {

    private Feed view;

    public PostController(Feed view) {
        this.view = view;
    }

    public void goToDashboard() {

        HR.HRstage.setScene(
            new Account("HR Manager").getScene()
        );
    }

    public Scene getScene() {
        return view.getScene();
    }
}