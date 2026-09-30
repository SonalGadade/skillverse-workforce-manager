package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.Trainer;
import com.skillverse.trainer.view.AssessmentPage;
import com.skillverse.trainer.view.CommunicationPage;
import com.skillverse.trainer.view.CoursePage;
import com.skillverse.trainer.view.DashboardPage;
import com.skillverse.trainer.view.LearnerSkillPage;
import com.skillverse.trainer.view.LearnersPage;
import com.skillverse.trainer.view.LoginPage;
import com.skillverse.trainer.view.PerformancePage;
import com.skillverse.trainer.view.ProfilePage;
import com.skillverse.trainer.view.SeminarPage;

import javafx.stage.Stage;

public class NavigationController {

    private final Stage stage;
    private final Trainer trainer;

    public NavigationController(Stage stage, Trainer trainer) {
        this.stage = stage;
        this.trainer = trainer;
    }

    public void goToDashboard() {
        DashboardPage.show(stage, trainer);
    }

    public void goToCourses() {
        CoursePage.show(stage, trainer);
    }

    public void goToLearners() {
        LearnersPage.show(stage, trainer);
    }

    public void goToSeminars() {
        SeminarPage.show(stage, trainer);
    }

    public void goToAssessments() {
        AssessmentPage.show(stage, trainer);
    }

    public void goToPerformance() {
        PerformancePage.show(stage, trainer);
    }

    public void goToCommunication() {
        CommunicationPage.show(stage, trainer);
    }

    public void goToLearnerSkills() {
        LearnerSkillPage.show(stage, trainer);
    }

    public void goToProfile() {
        ProfilePage.show(stage, trainer);
    }

    public void openCourses() {
        goToCourses();
    }

    public void openLearners() {
        goToLearners();
    }

    public void openSeminars() {
        goToSeminars();
    }

    public void openAssessments() {
        goToAssessments();
    }

    public void openPerformance() {
        goToPerformance();
    }

    public void openSkills() {
        goToLearnerSkills();
    }

    public void openCommunication() {
        goToCommunication();
    }

    public void openProfile() {
        goToProfile();
    }

    public void logout() {
        com.skillverse.FirstScreen.SceneNavigator.confirmAndLogout("Trainer");
    }
}
