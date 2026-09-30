package com.skillverse.hr.controller;

import com.skillverse.hr.view.Account;
import com.skillverse.hr.view.Profile;
import com.skillverse.hr.view.HR;

public class ProfileController {

    private final Profile profile;

    public ProfileController(Profile profile) {

        this.profile = profile;

        setupNavigation();
    }

    private void setupNavigation() {

        profile.getBackButton().setOnAction(e -> {

            HR.HRstage.setScene(
                    new Account(
                            profile.getUserName()
                    ).getScene()
            );

        });
    }
}