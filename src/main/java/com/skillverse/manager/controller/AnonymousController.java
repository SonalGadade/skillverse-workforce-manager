package com.skillverse.manager.controller;

import java.util.ArrayList;
import java.util.List;
import com.skillverse.manager.model.AnonymousModel;
import com.skillverse.manager.view.Anonymous;
import javafx.scene.Scene;

public class AnonymousController {

    private Anonymous anonymousView;
    private List<AnonymousModel> feedbackList;

    public AnonymousController() {

        anonymousView = new Anonymous();
        feedbackList = new ArrayList<>();
        loadFeedback();
    }

    private void loadFeedback() {

        feedbackList.add(new AnonymousModel("Workload", "The current workload is sometimes difficult to manage. Additional support during release periods would be helpful.", "2 days ago"));
        feedbackList.add(new AnonymousModel("Communication", "More regular team communication would help everyone understand project priorities better.", "4 days ago"));
        feedbackList.add(new AnonymousModel("Learning", "It would be useful to have more technical workshops and internal learning sessions.", "1 week ago"));
    }

    public Scene getAnonymousFeedbackScene(Runnable callBackActionDashboard) {
        return anonymousView.getAnonymousFeedbackScene(feedbackList, () -> navigateToDashboard(callBackActionDashboard));
    }

    private void navigateToDashboard(Runnable callBackActionDashboard) {
        if (callBackActionDashboard != null) {
            callBackActionDashboard.run();
        }
    }

    public List<AnonymousModel> getFeedbackList() {
        return feedbackList;
    }

    public void acknowledgeFeedback(AnonymousModel feedback) {
        if (feedback != null) {
            feedback.setAcknowledged(true);
        }
    }

    public boolean isAcknowledged(AnonymousModel feedback) {
        if (feedback == null) {
            return false;
        }
        return feedback.isAcknowledged();
    }
}