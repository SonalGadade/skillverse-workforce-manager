


package com.skillverse.admin.controller;

import com.skillverse.admin.view.DepartmentView;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class DepartmentController {

    private final Stage stage;
    private final DepartmentView view;

    public DepartmentController(
        Stage stage,
        DepartmentView view
    ) {

        this.stage = stage;
        this.view = view;
    }



    public ScrollPane initialize() {

        loadDepartmentData();

        return view.createScrollPane();
    }

  

    private void loadDepartmentData() {

    }


    public void loadEmployeesByDepartment(
        String department
    ) {

       
    }


    public void loadDepartmentStatistics(
        String department
    ) {

    }

  

    public void refreshDepartments() {

        
    }
}
