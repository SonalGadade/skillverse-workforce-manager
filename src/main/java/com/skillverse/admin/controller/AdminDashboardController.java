
package com.skillverse.admin.controller;

import com.skillverse.admin.view.AboutUsView;
import com.skillverse.admin.view.ActivityLogView;
import com.skillverse.admin.view.AdminDashboard;
import com.skillverse.admin.view.AdminProfileView;
import com.skillverse.admin.view.AnalyticsView;
import com.skillverse.admin.view.DashboardHomeView;
import com.skillverse.admin.view.DepartmentView;
import com.skillverse.admin.view.EmployeeView;
import com.skillverse.admin.view.JobView;
import com.skillverse.admin.view.SettingsView;
import com.skillverse.admin.view.TalentMatchingView;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class AdminDashboardController {

    private final Stage stage;
    private final AdminDashboard view;


    public AdminDashboardController(
        Stage stage,
        AdminDashboard view
    ) {
        this.stage = stage;
        this.view = view;
    }

    public void initialize() {
        view.setActiveButton(view.getDashboardButton());

        
        view.getDashboardButton().setOnAction(e -> openHome());


        
        view.getLogoutButton().setOnAction(e -> logout());

        
        view.getProfileButton().setOnAction(e -> openProfile());

        view.getEmployeesButton().setOnAction(e -> openEmployees());

        
        view.getDepartmentsButton().setOnAction(e -> openDepartments());

        view.getJobsButton().setOnAction(e -> openJobs());

        view.getTalentMatchingButton().setOnAction(e -> openTalentMatching());

        
        view.getAnalyticsButton().setOnAction(e -> openAnalytics());

        view.getActivityLogButton().setOnAction(e -> openActivityLog());

        view.getAboutUsButton().setOnAction(e -> openAboutUs());
    }



    private void openHome() {
        view.setActiveButton(view.getDashboardButton());
        DashboardHomeView homeView = new DashboardHomeView(stage);
        DashboardHomeController homeController = new DashboardHomeController(stage, homeView);
        view.getCenterContainer().setCenter(homeController.initialize());
    }

    private void logout() {
        com.skillverse.FirstScreen.SceneNavigator.confirmAndLogout("Admin");
    }

    private void openProfile() {
        view.setActiveButton(view.getProfileButton());
        AdminProfileView profileView = new AdminProfileView(stage);
        AdminProfileController profileController = new AdminProfileController(stage, profileView);
        view.getCenterContainer().setCenter(profileController.initialize());
    }

    private void openEmployees() {
        view.setActiveButton(view.getEmployeesButton());
        EmployeeView employeeView = new EmployeeView(stage);
        EmployeeController employeeController = new EmployeeController(stage, employeeView);
        view.getCenterContainer().setCenter(employeeController.initialize());
    }

    private void openDepartments() {
        view.setActiveButton(view.getDepartmentsButton());
        DepartmentView departmentView = new DepartmentView();
        DepartmentController departmentController = new DepartmentController(stage, departmentView);
        view.getCenterContainer().setCenter(departmentController.initialize());
    }

    private void openJobs() {
        view.setActiveButton(view.getJobsButton());
        JobView jobView = new JobView();
        JobController jobController = new JobController(jobView);
        ScrollPane jobPage = jobController.initialize();
        view.getCenterContainer().setCenter(jobPage);
    }

    private void openTalentMatching() {
        view.setActiveButton(view.getTalentMatchingButton());
        TalentMatchingView talentView = new TalentMatchingView();
        TalentMatchingController talentController = new TalentMatchingController(talentView);
        ScrollPane talentPage = talentController.initialize();
        view.getCenterContainer().setCenter(talentPage);
    }

    private void openAnalytics() {
        view.setActiveButton(view.getAnalyticsButton());
        AnalyticsView analyticsView = new AnalyticsView(stage);
        AnalyticsController analyticsController = new AnalyticsController(stage, analyticsView);
        view.getCenterContainer().setCenter(analyticsController.initialize());
    }


    private void openSettings() {
        SettingsView settingsView = new SettingsView(stage);
        SettingsController settingsController = new SettingsController(stage, settingsView);
        view.getCenterContainer().setCenter(settingsController.initialize());
    }

    private void openActivityLog() {
        view.setActiveButton(view.getActivityLogButton());
        ActivityLogView activityView = new ActivityLogView();
        ActivityLogController activityController = new ActivityLogController(activityView);
        view.getCenterContainer().setCenter(activityController.initialize());
    }

    private void openAboutUs() {
        view.setActiveButton(view.getAboutUsButton());
        AboutUsView aboutUsView = new AboutUsView(stage);
        AboutUsController aboutUsController = new AboutUsController(stage, aboutUsView);
        view.getCenterContainer().setCenter(aboutUsController.initialize());
    }





}