package com.skillverse.employee.controller;

import com.skillverse.employee.model.AttendanceModel;
import com.skillverse.employee.view.MyAttendanceView;

import javafx.stage.Stage;

public class AttendanceController {

    private MyAttendanceView view;

    public AttendanceController() {
        view = new MyAttendanceView();
    }

    public void showAttendance(Stage stage, String email) {

        if (stage == null) {
            return;
        }

        AttendanceModel attendance = getAttendanceData(email);

        view.show(
                stage,
                attendance.getEmail()
        );
    }

    private AttendanceModel getAttendanceData(String email) {

        return new AttendanceModel(
                email == null ? "Employee" : email,
                22,
                1,
                2,
                25,
                96.0
        );
    }
}