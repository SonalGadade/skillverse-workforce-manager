package com.skillverse.manager.controller;

import com.skillverse.manager.model.MyTeamModel;
import com.skillverse.manager.view.MyTeam;
import javafx.scene.Scene;

public class MyTeamController {

    private MyTeamModel myTeamModel;

    private MyTeam myTeamView;

    public MyTeamController() {
        myTeamModel = new MyTeamModel();
        myTeamView = new MyTeam();
    }

    public Scene getMyTeamScene(Runnable callBackActionDashboard) {

        Scene scene = myTeamView.getMyTeamScene(callBackActionDashboard);

        setupEmployeeNavigation();

        return scene;
    }

    private void setupEmployeeNavigation() {

        for (int i = 0; i < myTeamView.getEmployeeButtons().size(); i++) {

            int employeeIndex = i;

            myTeamView.getEmployeeButtons().get(i).setOnAction(event -> {

                String[] employeeNames = {
                        "Priya Sharma",
                        "Rahul Verma",
                        "Sneha Joshi",
                        "Amit Kumar",
                        "Neha Patil"
                };

                String memberName = employeeNames[employeeIndex];

                viewMemberProfile(memberName);
            });
        }
    }

    public MyTeamModel getMyTeamModel() {
        return myTeamModel;
    }

    public void viewMemberProfile(String memberName) {

        if (myTeamModel.isValidMember(memberName)) {

            myTeamModel.viewProfile(memberName);
        }
    }

    public void updateTeamStatistics(String teamMembers, String activeMembers, String onLeave, String averagePerformance) {

        myTeamModel.updateTeamStatistics(teamMembers, activeMembers, onLeave, averagePerformance);
    }

    public MyTeam getMyTeamView() {
        return myTeamView;
    }
}