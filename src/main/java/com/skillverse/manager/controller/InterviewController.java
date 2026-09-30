
package com.skillverse.manager.controller;

import java.util.ArrayList;

import java.util.List;

import com.skillverse.manager.model.InterviewModel;

import com.skillverse.manager.view.Interviews;

import javafx.scene.Scene;

public class InterviewController {

    private Interviews interviewsView;

    private List<InterviewModel> interviewList;

    private Runnable callBackActionDashboard;

    public InterviewController() {

        interviewsView = new Interviews();

        interviewList = new ArrayList<>();

        loadInterviews();
    }

    public InterviewController(List<InterviewModel> interviewList) {

        interviewsView = new Interviews();

        this.interviewList = interviewList;
    }

    private void loadInterviews() {

        interviewList.add(new InterviewModel("Aditya Shah", "Java Developer", "Today · 11:00 AM", "Technical Round"));

        interviewList.add(new InterviewModel("Riya Patil", "Frontend Developer", "Today · 2:30 PM", "Technical Round"));

        interviewList.add(new InterviewModel("Karan Mehta", "Data Scientist", "Tomorrow · 10:00 AM", "Manager Round"));
    }

    public Scene showInterviews(Runnable callBackActionDashboard) {

        this.callBackActionDashboard = callBackActionDashboard;

        return interviewsView.getInterviewsScene(interviewList, () -> navigateToDashboard());
    }

    private void navigateToDashboard() {

        if (callBackActionDashboard != null) {

            callBackActionDashboard.run();
        }
    }

    public List<InterviewModel> getInterviewList() {

        return interviewList;
    }

    public void addInterview(InterviewModel interview) {

        if (interview != null) {

            interviewList.add(interview);
        }
    }

    public void evaluateInterview(InterviewModel interview) {

        if (interview == null) {

            return;
        }

        System.out.println("Evaluating interview for: " + interview.getCandidate());

        System.out.println("Position: " + interview.getPosition());

        System.out.println("Interview Time: " + interview.getTime());

        System.out.println("Round: " + interview.getRound());
    }
}