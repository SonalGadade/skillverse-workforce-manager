package com.skillverse.admin.controller;



import com.skillverse.admin.view.DashboardHomeView;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class DashboardHomeController {

    private final Stage stage;
    private final DashboardHomeView view;

    public DashboardHomeController(
        Stage stage,
        DashboardHomeView view
    ) {
        this.stage = stage;
        this.view = view;
    }


    public ScrollPane initialize() {

        loadDashboardData();

        return view.createScrollPane();
    }



    private void loadDashboardData() {

        int employees =
            getTotalEmployees();

        int departments =
            getTotalDepartments();

        int jobs =
            getOpenJobs();

        int requests =
            getPendingRequests();

        view.setTotalEmployees(
            employees
        );

        view.setTotalDepartments(
            departments
        );

        view.setOpenJobs(
            jobs
        );

        view.setPendingRequests(
            requests
        );
    }


    private int getTotalEmployees() {

       

        return 245;
    }


    private int getTotalDepartments() {

      
        return 12;
    }

    

    private int getOpenJobs() {

      

        return 18;
    }


    private int getPendingRequests() {

    

        return 7;
    }

   

    public void loadEmployeeGrowthData() {

    }

    public void loadDepartmentDistributionData() {

    }

    public void loadRecentActivities() {

    }

    public void loadTopSkills() {

    }
}
