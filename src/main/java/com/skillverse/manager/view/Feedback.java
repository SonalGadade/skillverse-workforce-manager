package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;
import com.skillverse.manager.model.FeedbackModel;
import javafx.scene.Scene;

public class Feedback {

    private Scene feedbackScene;
    private List<javafx.scene.control.Button> feedbackButtons = new ArrayList<>();

    public Scene getFeedbackScene(List<FeedbackModel> feedbackList, Runnable callBackActionDashboard) {
        ManagerFeedbackView managerFeedbackView = new ManagerFeedbackView();
        feedbackScene = new Scene(managerFeedbackView.createContent(callBackActionDashboard), 1280, 750);
        return feedbackScene;
    }

    public Scene getFeedbackScene(Runnable callBackActionDashboard) {
        return getFeedbackScene(null, callBackActionDashboard);
    }
}