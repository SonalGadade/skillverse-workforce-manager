package com.skillverse.hr.controller;

import com.skillverse.hr.view.Account;
import com.skillverse.hr.view.Feed;
import com.skillverse.hr.view.HR;

public class FeedController {

    private final Feed view;

    public FeedController() {

        view = new Feed();

        setupNavigation();
    }

    private void setupNavigation() {

        view.getBackButton().setOnAction(e -> {

            HR.HRstage.setScene(
                new Account(
                    "HR Manager"
                ).getScene()
            );

        });
    }

    public Feed getView() {

        return view;
    }
}