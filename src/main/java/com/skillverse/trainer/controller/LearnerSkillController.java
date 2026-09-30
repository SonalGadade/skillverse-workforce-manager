package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.LearnerSkill;
import com.skillverse.trainer.model.Trainer;

import java.util.List;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LearnerSkillController {

    private final Stage stage;
    private final Trainer trainer;
    private final DataStore dataStore;
    private final NavigationController navigation;

    public LearnerSkillController(
            Stage stage,
            Trainer trainer) {

        this.stage = stage;
        this.trainer = trainer;
        this.dataStore = DataStore.getInstance();
        this.navigation =
                new NavigationController(stage, trainer);
    }

    public List<LearnerSkill> getSkills() {
        return dataStore.getLearnerSkills();
    }

    public boolean addSkill(
            String skillName,
            String proficiencyLevel,
            int learnerCount,
            String description) {

        if (skillName == null || skillName.isBlank()) {
            showError("Skill name is required.");
            return false;
        }

        if (proficiencyLevel == null
                || proficiencyLevel.isBlank()) {

            showError("Proficiency level is required.");
            return false;
        }

        if (learnerCount < 0) {
            showError("Learner count cannot be negative.");
            return false;
        }

        LearnerSkill skill =
                new LearnerSkill(
                        getNextId(),
                        skillName.trim(),
                        proficiencyLevel.trim(),
                        learnerCount,
                        description == null
                                ? ""
                                : description.trim()
                );

        dataStore.addLearnerSkill(skill);

        return true;
    }

    public void goToDashboard() {
        navigation.goToDashboard();
    }

    private int getNextId() {

        int maxId = 0;

        for (LearnerSkill skill :
                dataStore.getLearnerSkills()) {

            if (skill.getId() > maxId) {
                maxId = skill.getId();
            }
        }

        return maxId + 1;
    }

    private void showError(String message) {

        new Alert(
                Alert.AlertType.WARNING,
                message
        ).showAndWait();
    }
}
