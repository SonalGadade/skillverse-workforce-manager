package com.skillverse.manager.controller;

import java.util.ArrayList;
import java.util.List;

import com.skillverse.manager.model.LearningApprovalModel;
import com.skillverse.manager.view.LearningApproval;

import javafx.scene.Scene;

public class LearningApprovalController {

    private List<LearningApprovalModel> learningRequests;
    private int pendingRequests;
    private int approvedRequests;
    private int activeLearningPaths;
    private int completionRate;
    private int employeesLearning;

    private LearningApproval view;

    public LearningApprovalController() {

        learningRequests = new ArrayList<>();

        learningRequests.add(new LearningApprovalModel("Priya Sharma","Advanced React Development","2 days ago","Pending"));
        learningRequests.add(new LearningApprovalModel("Rahul Verma","Machine Learning Fundamentals","3 days ago","Pending"));
        learningRequests.add(new LearningApprovalModel("Sneha Joshi","UI/UX Design Masterclass","5 days ago","Pending"));

        pendingRequests = 8;
        approvedRequests = 24;
        activeLearningPaths = 12;
        completionRate = 87;
        employeesLearning = 18;

        view = new LearningApproval();
    }

    public Scene showLearningApproval(Runnable callBackActionDashboard,Runnable callBackActionFeed,Runnable callBackActionMyTeam,Runnable callBackActionPerformance,Runnable callBackActionGoalsKPIs,Runnable callBackActionFeedback,Runnable callBackActionInterview,Runnable callBackActionAnalytics,Runnable callBackActionAnonymous) {

        return view.getLearningApprovalScene(callBackActionDashboard,callBackActionFeed,callBackActionMyTeam,callBackActionPerformance,callBackActionGoalsKPIs,callBackActionFeedback,callBackActionInterview,callBackActionAnalytics,callBackActionAnonymous,this::approveRequest,this::rejectRequest);
    }

    public List<LearningApprovalModel> getLearningRequests() {
        return learningRequests;
    }

    public void addLearningRequest(LearningApprovalModel request) {
        learningRequests.add(request);
    }

    public void approveRequest(LearningApprovalModel request) {

        request.setStatus("Approved");

        if (pendingRequests > 0) {
            pendingRequests--;
        }

        approvedRequests++;

        System.out.println("Learning request approved.");
        System.out.println("Employee : " + request.getEmployeeName());
        System.out.println("Course : " + request.getCourseName());
    }

    public void rejectRequest(LearningApprovalModel request) {

        request.setStatus("Rejected");

        if (pendingRequests > 0) {
            pendingRequests--;
        }

        System.out.println("Learning request rejected.");
        System.out.println("Employee : " + request.getEmployeeName());
        System.out.println("Course : " + request.getCourseName());
    }

    public int getPendingRequests() {
        return pendingRequests;
    }

    public int getApprovedRequests() {
        return approvedRequests;
    }

    public int getActiveLearningPaths() {
        return activeLearningPaths;
    }

    public int getCompletionRate() {
        return completionRate;
    }

    public int getEmployeesLearning() {
        return employeesLearning;
    }

    public void removeRequest(LearningApprovalModel request) {
        learningRequests.remove(request);
    }
}