package com.skillverse.manager.controller;

import com.skillverse.manager.view.ManagerEditProfile;
import com.skillverse.manager.view.ManagerProfile;
import javafx.scene.Scene;

public class ManagerEditProfileController {

    private ManagerEditProfile managerEditProfileView;

    public ManagerEditProfileController() {

        managerEditProfileView = new ManagerEditProfile();
    }

    public Scene getManagerEditProfileScene(Runnable callBackActionProfile,Runnable callBackActionSave) {

        Scene scene = managerEditProfileView.getManagerEditProfileScene();

        managerEditProfileView.getCancelButton().setOnAction(event -> {

            if (callBackActionProfile != null) {

                callBackActionProfile.run();
            }

        });

        managerEditProfileView.getSaveButton().setOnAction(event -> {

            saveProfile();

            if (callBackActionSave != null) {

                callBackActionSave.run();
            }

        });

        return scene;
    }

    private void saveProfile() {

        ManagerProfile.profileName = managerEditProfileView.getNameField().getText();

        ManagerProfile.username = managerEditProfileView.getUsernameField().getText();

        ManagerProfile.bio = managerEditProfileView.getBioField().getText();

        ManagerProfile.email = managerEditProfileView.getEmailField().getText();

        if (managerEditProfileView.getSelectedImage() != null) {

            ManagerProfile.profileImage = managerEditProfileView.getSelectedImage();
        }
    }
}