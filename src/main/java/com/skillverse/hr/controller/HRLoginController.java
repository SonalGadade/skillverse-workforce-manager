package com.skillverse.hr.controller;

import com.skillverse.hr.view.Account;
import com.skillverse.hr.view.HR;
import javafx.scene.Scene;

public class HRLoginController {

    public void openAccount(String password) {

        Account account = new Account(password);

        Scene accountScene = account.getScene();

        HR.HRstage.setScene(
            accountScene
        );
    }

    public void openHR() {

        HR hr = new HR();

        try {

            hr.start(
                HR.HRstage
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}