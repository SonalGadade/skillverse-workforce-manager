package com.skillverse.manager.controller;

import com.skillverse.manager.model.ManagerDashboardModel;
import com.skillverse.manager.view.ManagerDashboard;

public class ManagerDashboardController {

    private ManagerDashboardModel managerDashboardModel;

    public ManagerDashboardController() {
        managerDashboardModel = new ManagerDashboardModel();
    }

    public ManagerDashboardModel getDashboardData() {
        return managerDashboardModel;
    }

    public void openDashboard(ManagerDashboard managerDashboard) {
        managerDashboard.backToManagerDashboard();
    }

    public void logout(ManagerDashboard managerDashboard, Runnable callBackActionLogin) {
        if (callBackActionLogin != null) {
            callBackActionLogin.run();
        }
    }

    public void openFeed(ManagerDashboard managerDashboard) {

        FeedController feedController = new FeedController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        managerDashboard.getStage().setScene(feedController.getFeedScene(callBackActionDashboard));
    }

    public void openMyTeam(ManagerDashboard managerDashboard) {

        MyTeamController myTeamController = new MyTeamController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        managerDashboard.getStage().setScene(myTeamController.getMyTeamScene(callBackActionDashboard));
    }

    public void openPerformance(ManagerDashboard managerDashboard) {

        PerformanceController performanceController = new PerformanceController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        managerDashboard.getStage().setScene(performanceController.getPerformanceScene(callBackActionDashboard));
    }

    public void openGoals(ManagerDashboard managerDashboard) {

        GoalsKPIController goalsKPIController = new GoalsKPIController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        managerDashboard.getStage().setScene(goalsKPIController.showGoalsKPIs(callBackActionDashboard));
    }

    public void openFeedback(ManagerDashboard managerDashboard) {

        FeedbackController feedbackController = new FeedbackController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        managerDashboard.getStage().setScene(feedbackController.getFeedbackScene(callBackActionDashboard));
    }

    public void openInterviews(ManagerDashboard managerDashboard) {

        InterviewController interviewController = new InterviewController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        managerDashboard.getStage().setScene(interviewController.showInterviews(callBackActionDashboard));
    }

    public void openAnalytics(ManagerDashboard managerDashboard) {

        AnalyticsController analyticsController = new AnalyticsController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        managerDashboard.getStage().setScene(analyticsController.getAnalyticsScene(callBackActionDashboard));
    }

    public void openLearningApproval(ManagerDashboard managerDashboard) {

        LearningApprovalController learningApprovalController = new LearningApprovalController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        Runnable callBackActionFeed = new Runnable() {
            @Override
            public void run() {
                openFeed(managerDashboard);
            }
        };

        Runnable callBackActionMyTeam = new Runnable() {
            @Override
            public void run() {
                openMyTeam(managerDashboard);
            }
        };

        Runnable callBackActionPerformance = new Runnable() {
            @Override
            public void run() {
                openPerformance(managerDashboard);
            }
        };

        Runnable callBackActionGoalsKPIs = new Runnable() {
            @Override
            public void run() {
                openGoals(managerDashboard);
            }
        };

        Runnable callBackActionFeedback = new Runnable() {
            @Override
            public void run() {
                openFeedback(managerDashboard);
            }
        };

        Runnable callBackActionInterview = new Runnable() {
            @Override
            public void run() {
                openInterviews(managerDashboard);
            }
        };

        Runnable callBackActionAnalytics = new Runnable() {
            @Override
            public void run() {
                openAnalytics(managerDashboard);
            }
        };

        Runnable callBackActionAnonymous = new Runnable() {
            @Override
            public void run() {
                openAnonymousFeedback(managerDashboard);
            }
        };

        managerDashboard.getStage().setScene(learningApprovalController.showLearningApproval(callBackActionDashboard,callBackActionFeed,callBackActionMyTeam,callBackActionPerformance,callBackActionGoalsKPIs,callBackActionFeedback,callBackActionInterview,callBackActionAnalytics,callBackActionAnonymous));
    }

    public void openAnonymousFeedback(ManagerDashboard managerDashboard) {

        AnonymousController anonymousController = new AnonymousController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        managerDashboard.getStage().setScene(anonymousController.getAnonymousFeedbackScene(callBackActionDashboard));
    }

    public void openManagerProfile(ManagerDashboard managerDashboard) {

        ManagerProfileController managerProfileController = new ManagerProfileController();

        Runnable callBackActionDashboard = new Runnable() {
            @Override
            public void run() {
                managerDashboard.backToManagerDashboard();
            }
        };

        managerDashboard.getStage().setScene(managerProfileController.getManagerProfileScene(callBackActionDashboard));
    }

    public void openSettings(ManagerDashboard managerDashboard) {
        System.out.println("Opening Settings");
    }
}