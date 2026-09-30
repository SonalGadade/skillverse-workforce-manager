package com.skillverse.hr.controller;

import com.skillverse.hr.view.HR;
import com.skillverse.hr.view.HRLogin;
import com.skillverse.hr.view.HRSignin;

public class HRController {

    public void openLogin() {

        HRLogin login = new HRLogin();

        HR.HRstage.setScene(
                login.getScene()
        );
    }

    public void openSignin() {

        HRSignin signin = new HRSignin();

        HR.HRstage.setScene(
                signin.getScene()
        );
    }
}