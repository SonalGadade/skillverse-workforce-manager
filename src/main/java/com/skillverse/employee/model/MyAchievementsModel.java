package com.skillverse.employee.model;

public class MyAchievementsModel {

   

    private int totalAchievements;
    private int certificates;
    private int badges;
    private int points;

   

    private String latestType;
    private String latestDate;
    private String latestTitle;
    private String latestDescription;

    public MyAchievementsModel() {

        totalAchievements = 12;
        certificates = 6;
        badges = 4;
        points = 1250;

        latestType = "CERTIFICATE";
        latestDate = "12 Aug 2026";
        latestTitle = "Java Professional Certification";

        latestDescription =
                "Achieved expert-level proficiency in Java SE 17 Developer " +
                "examination, demonstrating advanced skills in object-oriented " +
                "programming, modularity, and secure coding practices.";
    }

    

    public int getTotalAchievements() {
        return totalAchievements;
    }

    public int getCertificates() {
        return certificates;
    }

    public int getBadges() {
        return badges;
    }

    public int getPoints() {
        return points;
    }

    public String getLatestType() {
        return latestType;
    }

    public String getLatestDate() {
        return latestDate;
    }

    public String getLatestTitle() {
        return latestTitle;
    }

    public String getLatestDescription() {
        return latestDescription;
    }

   

    public void setTotalAchievements(int totalAchievements) {
        this.totalAchievements = totalAchievements;
    }

    public void setCertificates(int certificates) {
        this.certificates = certificates;
    }

    public void setBadges(int badges) {
        this.badges = badges;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setLatestType(String latestType) {
        this.latestType = latestType;
    }

    public void setLatestDate(String latestDate) {
        this.latestDate = latestDate;
    }

    public void setLatestTitle(String latestTitle) {
        this.latestTitle = latestTitle;
    }

    public void setLatestDescription(String latestDescription) {
        this.latestDescription = latestDescription;
    }
}