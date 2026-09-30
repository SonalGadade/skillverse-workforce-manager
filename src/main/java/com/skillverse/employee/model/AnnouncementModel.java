package com.skillverse.employee.model;

public class AnnouncementModel {

    private String icon;
    private String category;
    private String date;
    private String title;
    private String description;
    private boolean important;

    public AnnouncementModel(
            String icon,
            String category,
            String date,
            String title,
            String description,
            boolean important
    ) {
        this.icon = icon;
        this.category = category;
        this.date = date;
        this.title = title;
        this.description = description;
        this.important = important;
    }

    

    public String getIcon() {
        return icon;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isImportant() {
        return important;
    }

    

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }
}