package com.skillverse.manager.controller;

import java.util.ArrayList;
import java.util.List;
import com.skillverse.manager.model.FeedbackModel;
import com.skillverse.manager.view.Feedback;
import javafx.scene.Scene;

public class FeedbackController {

    private Feedback feedbackView;

    private List<FeedbackModel> feedbackList;

    private Runnable callBackActionDashboard;

    public FeedbackController() {

        feedbackView = new Feedback();

        feedbackList = new ArrayList<>();

        loadFeedback();
    }

    public FeedbackController(List<FeedbackModel> feedbackList) {

        feedbackView = new Feedback();

        this.feedbackList = feedbackList;
    }

    private void loadFeedback() {

        feedbackList.add(new FeedbackModel("Priya Sharma", "Excellent work on the payment module. Keep improving documentation.", "Performance Feedback"));

        feedbackList.add(new FeedbackModel("Rahul Verma", "Strong analytical skills. Consider taking the leadership learning path.", "Development Feedback"));

        feedbackList.add(new FeedbackModel("Sneha Joshi", "Great contribution to the product redesign.", "Recognition"));
    }

    public Scene getFeedbackScene(Runnable callBackActionDashboard) {

        this.callBackActionDashboard = callBackActionDashboard;

        return feedbackView.getFeedbackScene(feedbackList, () -> navigateToDashboard());
    }

    private void navigateToDashboard() {

        if (callBackActionDashboard != null) {

            callBackActionDashboard.run();
        }
    }

    public List<FeedbackModel> getFeedbackList() {

        return feedbackList;
    }

    public void addFeedback(FeedbackModel feedback) {

        if (feedback != null) {

            feedbackList.add(feedback);
        }
    }

    public void respondToFeedback(FeedbackModel feedback) {

        if (feedback != null) {

            feedback.setResponded(true);
        }
    }

    public boolean isResponded(FeedbackModel feedback) {

        if (feedback == null) {

            return false;
        }

        return feedback.isResponded();
    }
}