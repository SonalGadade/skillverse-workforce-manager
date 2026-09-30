package com.skillverse.manager.controller;

import com.skillverse.manager.view.ManagerDashboard;
import com.skillverse.manager.view.ManagerEditProfile;
import com.skillverse.manager.view.ManagerProfile;

import javafx.scene.Scene;

public class ManagerProfileController {

    private ManagerProfile managerProfileView;
    private Scene managerProfileScene;

    public ManagerProfileController() {

        managerProfileView = new ManagerProfile();

    }

    public Scene getManagerProfileScene(Runnable callBackActionDashboard) {

        managerProfileScene = managerProfileView.getManagerProfileScene();



        managerProfileView.getEditProfileButton().setOnAction(event -> {

            openEditProfile(callBackActionDashboard);

        });

        return managerProfileScene;
    }

    private void openEditProfile(Runnable callBackActionDashboard) {

        ManagerEditProfileController editProfileController = new ManagerEditProfileController();

        Runnable backToProfile = new Runnable() {

            @Override
            public void run() {

                showProfile(callBackActionDashboard);

            }

        };

        Runnable saveAndBack = new Runnable() {

            @Override
            public void run() {

                showProfile(callBackActionDashboard);

            }

        };

        Scene editProfileScene = editProfileController.getManagerEditProfileScene(backToProfile, saveAndBack);

        if (ManagerDashboard.managerStage != null) {

            ManagerDashboard.managerStage.setScene(editProfileScene);

        }

    }

    private void showProfile(Runnable callBackActionDashboard) {

        managerProfileScene = getManagerProfileScene(callBackActionDashboard);

        if (ManagerDashboard.managerStage != null) {

            ManagerDashboard.managerStage.setScene(managerProfileScene);

        }

    }

    public ManagerProfile getManagerProfileView() {

        return managerProfileView;

    }

}