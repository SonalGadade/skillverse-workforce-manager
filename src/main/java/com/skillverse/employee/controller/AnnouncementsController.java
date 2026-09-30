package com.skillverse.employee.controller;

import com.skillverse.employee.model.AnnouncementModel;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsController {

    private final List<AnnouncementModel> announcements;

    public AnnouncementsController() {

        announcements = new ArrayList<>();

        loadAnnouncements();
    }

    private void loadAnnouncements() {

        announcements.add(
                new AnnouncementModel(null, null, null, null, null, false
                )
        );

        announcements.add(
                new AnnouncementModel(
                        "◇",
                        "UPDATE",
                        "22 Aug 2024",
                        "New Learning Program Available",
                        "The Q3 Advanced Leadership Workshop series is now open for employees.",
                        false
                )
        );

        announcements.add(
                new AnnouncementModel(
                        "✧",
                        "GENERAL",
                        "20 Aug 2024",
                        "Company Holiday Notice",
                        "A friendly reminder that all global offices will be closed on Monday.",
                        false
                )
        );

        announcements.add(
                new AnnouncementModel(
                        "♨",
                        "GENERAL",
                        "18 Aug 2024",
                        "Employee Wellness Program",
                        "Based on recent team feedback, we are launching a new comprehensive wellness program. This includes fitness and wellbeing activities.",
                        false
                )
        );
    }

    public List<AnnouncementModel> getAllAnnouncements() {

        return new ArrayList<>(announcements);
    }

    public List<AnnouncementModel> getHighPriorityAnnouncements() {

        List<AnnouncementModel> result = new ArrayList<>();

        for (AnnouncementModel announcement : announcements) {

            if (announcement.isImportant()) {

                result.add(announcement);
            }
        }

        return result;
    }

    public List<AnnouncementModel> getGeneralAnnouncements() {

        List<AnnouncementModel> result = new ArrayList<>();

        for (AnnouncementModel announcement : announcements) {

            if (announcement.getCategory().equalsIgnoreCase("GENERAL")) {

                result.add(announcement);
            }
        }

        return result;
    }

    public List<AnnouncementModel> getUpdateAnnouncements() {

        List<AnnouncementModel> result = new ArrayList<>();

        for (AnnouncementModel announcement : announcements) {

            if (announcement.getCategory().equalsIgnoreCase("UPDATE")) {

                result.add(announcement);
            }
        }

        return result;
    }

    public List<AnnouncementModel> searchAnnouncements(String keyword) {

        List<AnnouncementModel> result = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {

            return getAllAnnouncements();
        }

        String searchText = keyword.toLowerCase().trim();

        for (AnnouncementModel announcement : announcements) {

            if (announcement.getTitle()
                    .toLowerCase()
                    .contains(searchText)

                    || announcement.getDescription()
                            .toLowerCase()
                            .contains(searchText)

                    || announcement.getCategory()
                            .toLowerCase()
                            .contains(searchText)) {

                result.add(announcement);
            }
        }

        return result;
    }

    public void addAnnouncement(AnnouncementModel announcement) {

        if (announcement != null) {

            announcements.add(announcement);
        }
    }

    public int getAnnouncementCount() {

        return announcements.size();
    }
}
