package com.skillverse.hr.controller;

import com.skillverse.hr.view.CreateAcc;
import com.skillverse.hr.view.HR;
import com.skillverse.hr.view.HRLogin;

public class HRSigninController {

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

    public void openCreateAccount() {

        CreateAcc createAccount =
            new CreateAcc();

        HR.HRstage.setScene(
            createAccount.getScene()
        );
    }

    public void openLogin() {

        HRLogin login =
            new HRLogin();

        HR.HRstage.setScene(
            login.getScene()
        );
    }
}