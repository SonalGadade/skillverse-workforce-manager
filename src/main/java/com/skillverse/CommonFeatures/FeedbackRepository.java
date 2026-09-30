package com.skillverse.CommonFeatures;

import com.skillverse.Dao.FirebaseDAO;
import javafx.application.Platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FeedbackRepository {

    private static FeedbackRepository instance;
    private final List<Feedback> feedbackList = new ArrayList<>();
    private final List<Runnable> listeners = new ArrayList<>();

    private FeedbackRepository() {
        initSampleData();
        syncWithFirestoreAsync();
    }

    public static synchronized FeedbackRepository getInstance() {
        if (instance == null) {
            instance = new FeedbackRepository();
        }
        return instance;
    }

    private void initSampleData() {
        feedbackList.add(new Feedback(
                "FB-101",
                "Manager • Juig",
                "EMPLOYEE",
                "alex@skillverse.com",
                "Alex Morgan",
                "Sprint Performance",
                5.0,
                "Exceptional work on the microservices migration and API optimization during Sprint 4. Met all deliverables two days ahead of deadline with 98% test coverage.",
                "Today at 10:15 AM",
                false
        ));

        feedbackList.add(new Feedback(
                "FB-102",
                "Manager • Juig",
                "EMPLOYEE",
                "alex@skillverse.com",
                "Alex Morgan",
                "Leadership & Collaboration",
                4.8,
                "Outstanding initiative in mentoring junior developers during onboarding and spearheading the weekly architectural code review sessions.",
                "Yesterday at 04:30 PM",
                true
        ));

        feedbackList.add(new Feedback(
                "FB-103",
                "Manager • Juig",
                "EMPLOYEE",
                "alex@skillverse.com",
                "Alex Morgan",
                "Project Delivery",
                4.5,
                "Delivered the cross-platform UI components ahead of schedule with great attention to accessibility standards and dark mode aesthetics.",
                "Aug 24, 2026",
                true
        ));

        feedbackList.add(new Feedback(
                "FB-104",
                "Manager • Juig",
                "HR",
                "hr@skillverse.com",
                "HR Talent Acquisition Team",
                "Hiring Coordination",
                5.0,
                "Seamless coordination for the Senior Full-Stack Engineer interviews this week. Candidate communication and schedule management were top-notch.",
                "Today at 09:00 AM",
                false
        ));

        feedbackList.add(new Feedback(
                "FB-105",
                "Manager • Juig",
                "HR",
                "hr@skillverse.com",
                "HR Operations",
                "Process Optimization",
                4.7,
                "The revised automated onboarding workflow has significantly reduced ramp-up time for department new hires. Appreciate the proactive support!",
                "Yesterday at 02:15 PM",
                true
        ));
    }

    private void syncWithFirestoreAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                for (Feedback fb : feedbackList) {
                    FirebaseDAO.saveFeedback(fb);
                }

                List<Feedback> remoteFeedbacks = FirebaseDAO.getAllCommonFeedbacks();
                if (remoteFeedbacks != null && !remoteFeedbacks.isEmpty()) {
                    for (Feedback remote : remoteFeedbacks) {
                        boolean exists = feedbackList.stream().anyMatch(fb -> fb.getFeedbackId().equals(remote.getFeedbackId()));
                        if (!exists) {
                            feedbackList.add(0, remote);
                        }
                    }
                    notifyListeners();
                }
            } catch (Exception e) {
                System.err.println("⚠️ [FeedbackRepository] Async sync with Firestore failed: " + e.getMessage());
            }
        });
    }

    public synchronized void addFeedback(Feedback feedback) {
        if (feedback.getFeedbackId() == null || feedback.getFeedbackId().isEmpty()) {
            feedback.setFeedbackId("FB-" + (System.currentTimeMillis() % 10000));
        }
        if (feedback.getTimestamp() == null || feedback.getTimestamp().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy • hh:mm a");
            feedback.setTimestamp(LocalDateTime.now().format(formatter));
        }
        feedbackList.add(0, feedback);
        notifyListeners();

        CompletableFuture.runAsync(() -> FirebaseDAO.saveFeedback(feedback));
    }

    public synchronized boolean acknowledgeFeedback(String feedbackId) {
        for (Feedback fb : feedbackList) {
            if (fb.getFeedbackId().equals(feedbackId)) {
                fb.setAcknowledged(true);
                notifyListeners();

                CompletableFuture.runAsync(() -> FirebaseDAO.acknowledgeFeedback(feedbackId));
                return true;
            }
        }
        return false;
    }

    public synchronized List<Feedback> getAllFeedbacks() {
        return new ArrayList<>(feedbackList);
    }

    public synchronized List<Feedback> getFeedbacksForEmployee(String employeeEmailOrName) {
        List<Feedback> result = new ArrayList<>();
        for (Feedback fb : feedbackList) {
            if ("EMPLOYEE".equalsIgnoreCase(fb.getToUserRole())) {
                if (employeeEmailOrName == null || employeeEmailOrName.isEmpty()
                        || fb.getTargetUserId().equalsIgnoreCase(employeeEmailOrName)
                        || fb.getTargetUserName().toLowerCase().contains(employeeEmailOrName.toLowerCase())
                        || "alex@skillverse.com".equalsIgnoreCase(fb.getTargetUserId())) {
                    result.add(fb);
                }
            }
        }
        return result;
    }

    public synchronized List<Feedback> getFeedbacksForHR() {
        List<Feedback> result = new ArrayList<>();
        for (Feedback fb : feedbackList) {
            if ("HR".equalsIgnoreCase(fb.getToUserRole())) {
                result.add(fb);
            }
        }
        return result;
    }

    public synchronized double getAverageRatingForEmployee(String employeeEmailOrName) {
        List<Feedback> list = getFeedbacksForEmployee(employeeEmailOrName);
        if (list.isEmpty()) return 5.0;
        double sum = 0;
        for (Feedback fb : list) {
            sum += fb.getRating();
        }
        return Math.round((sum / list.size()) * 10.0) / 10.0;
    }

    public synchronized int getTotalCountForEmployee(String employeeEmailOrName) {
        return getFeedbacksForEmployee(employeeEmailOrName).size();
    }

    public synchronized void addListener(Runnable listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public synchronized void removeListener(Runnable listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : new ArrayList<>(listeners)) {
            Platform.runLater(listener);
        }
    }
}
