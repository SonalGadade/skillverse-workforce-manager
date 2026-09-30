package com.skillverse.admin.controller;



import com.skillverse.admin.view.SkillView;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class SkillController {

    private final Stage stage;
    private final SkillView view;

    public SkillController(
        Stage stage,
        SkillView view
    ) {

        this.stage = stage;
        this.view = view;
    }



    public ScrollPane initialize() {

        loadSkillData();

        return view.createScrollPane();
    }

    

    private void loadSkillData() {


    }

   

    public void loadEmployeesBySkill(
        String skill
    ) {

        
    }

 

    public void verifySkill(
        int employeeId,
        String skill
    ) {

    }

    

    public void addSkill(
        String name,
        String category,
        String description,
        String level
    ) {

        
    }

 

    public void deleteSkill(
        int skillId
    ) {

       
    }



    public void refreshSkills() {

        
    }
}
