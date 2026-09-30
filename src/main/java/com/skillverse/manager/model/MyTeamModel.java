package com.skillverse.manager.model;

public class MyTeamModel {

    private String teamMembers;
    private String activeMembers;
    private String onLeave;
    private String averagePerformance;

    public MyTeamModel() {
        this.teamMembers = "14";
        this.activeMembers = "12";
        this.onLeave = "2";
        this.averagePerformance = "88%";
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public String getActiveMembers() {
        return activeMembers;
    }

    public String getOnLeave() {
        return onLeave;
    }

    public String getAveragePerformance() {
        return averagePerformance;
    }

    public boolean isValidMember(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public void viewProfile(String memberName) {
        System.out.println("Viewing profile of: " + memberName);
    }

    public void updateTeamStatistics(String teamMembers, String activeMembers, String onLeave, String averagePerformance) {
        this.teamMembers = teamMembers;
        this.activeMembers = activeMembers;
        this.onLeave = onLeave;
        this.averagePerformance = averagePerformance;
    }
}