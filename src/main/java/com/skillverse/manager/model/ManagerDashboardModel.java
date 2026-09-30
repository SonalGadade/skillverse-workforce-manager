package com.skillverse.manager.model;



import java.util.ArrayList;
import java.util.List;

public class ManagerDashboardModel {

    private int teamMembers;
    private String averagePerformance;
    private String goalsCompleted;
    private String learningProgress;

    private List<TeamMember> teamMembersList;
    private List<Activity> recentActivities;

    public ManagerDashboardModel() {

        teamMembers = 14;
        averagePerformance = "88%";
        goalsCompleted = "79%";
        learningProgress = "76%";

        teamMembersList = new ArrayList<>();

        teamMembersList.add(new TeamMember("Priya Sharma", "Software Engineer", "94%"));
        teamMembersList.add(new TeamMember("Rahul Verma", "Data Scientist", "87%"));
        teamMembersList.add(new TeamMember("Sneha Joshi", "Product Designer", "91%"));
        teamMembersList.add(new TeamMember("Amit Kumar", "Software Engineer", "82%"));

        recentActivities = new ArrayList<>();

        recentActivities.add(new Activity("Priya completed a goal", "2 hours ago"));
        recentActivities.add(new Activity("Rahul completed a course", "5 hours ago"));
        recentActivities.add(new Activity("New interview scheduled", "Yesterday"));
        recentActivities.add(new Activity("New feedback received", "Yesterday"));
    }

    public int getTeamMembers() {
        return teamMembers;
    }

    public String getAveragePerformance() {
        return averagePerformance;
    }

    public String getGoalsCompleted() {
        return goalsCompleted;
    }

    public String getLearningProgress() {
        return learningProgress;
    }

    public List<TeamMember> getTeamMembersList() {
        return teamMembersList;
    }

    public List<Activity> getRecentActivities() {
        return recentActivities;
    }

    public static class TeamMember {

        private String name;
        private String role;
        private String performance;

        public TeamMember(String name, String role, String performance) {
            this.name = name;
            this.role = role;
            this.performance = performance;
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        public String getPerformance() {
            return performance;
        }
    }

    public static class Activity {

        private String activity;
        private String time;

        public Activity(String activity, String time) {
            this.activity = activity;
            this.time = time;
        }

        public String getActivity() {
            return activity;
        }

        public String getTime() {
            return time;
        }
    }
}