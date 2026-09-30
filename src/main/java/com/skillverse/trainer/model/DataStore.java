package com.skillverse.trainer.model;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    
    private static final DataStore INSTANCE = new DataStore();

    public static DataStore getInstance() {
        return INSTANCE;
    }

    private final List<Trainer> trainers = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Learner> learners = new ArrayList<>();
    private final List<Seminar> seminars = new ArrayList<>();
    private final List<Assessment> assessments = new ArrayList<>();
    private final List<LearnerSkill> learnerSkills = new ArrayList<>();
    private final List<Announcement> announcements = new ArrayList<>();

    private PerformanceSummary performanceSummary;

    private DataStore() {
        loadDemoData();
    }

    private void loadDemoData() {

        trainers.add(
                new Trainer(
                        "Ayushi Kiran Kawade",
                        "ayushi@skillverse.com",
                        "1234",
                        "9876543210",
                        "SkillVerse Trainer Workspace",
                        "Female"
                )
        );

        courses.add(
                new Course(
                        1,
                        "Java Full Stack Development",
                        "Trainer workspace • Manage and update this item.",
                        "Advanced",
                        24,
                        "12 Weeks",
                        "Active"
                )
        );

        courses.add(
                new Course(
                        2,
                        "Python & Data Analytics",
                        "Trainer workspace • Manage and update this item.",
                        "Intermediate",
                        22,
                        "10 Weeks",
                        "Active"
                )
        );

        courses.add(
                new Course(
                        3,
                        "Web Development Fundamentals",
                        "Trainer workspace • Manage and update this item.",
                        "Beginner",
                        20,
                        "8 Weeks",
                        "Active"
                )
        );

        courses.add(
                new Course(
                        4,
                        "Professional Communication",
                        "Trainer workspace • Manage and update this item.",
                        "Advanced",
                        31,
                        "6 Weeks",
                        "Active"
                )
        );

        learners.add(
                new Learner(
                        1,
                        "Learner 1",
                        "learner1@skillverse.com",
                        "Java Full Stack Development",
                        92,
                        88,
                        "On Track"
                )
        );

        learners.add(
                new Learner(
                        2,
                        "Learner 2",
                        "learner2@skillverse.com",
                        "Python & Data Analytics",
                        84,
                        81,
                        "On Track"
                )
        );

        learners.add(
                new Learner(
                        3,
                        "Learner 3",
                        "learner3@skillverse.com",
                        "Web Development Fundamentals",
                        72,
                        76,
                        "Needs Support"
                )
        );

        learners.add(
                new Learner(
                        4,
                        "Learner 4",
                        "learner4@skillverse.com",
                        "Professional Communication",
                        96,
                        94,
                        "Advanced"
                )
        );

        seminars.add(
                new Seminar(
                        1,
                        "Java Workshop",
                        "Tomorrow",
                        "10:00 AM",
                        "Trainer workspace • Manage and update this item.",
                        24,
                        "Scheduled"
                )
        );

        seminars.add(
                new Seminar(
                        2,
                        "AI Tools Seminar",
                        "Friday",
                        "2:00 PM",
                        "Trainer workspace • Manage and update this item.",
                        30,
                        "Scheduled"
                )
        );

        seminars.add(
                new Seminar(
                        3,
                        "Interview Preparation",
                        "Saturday",
                        "11:00 AM",
                        "Trainer workspace • Manage and update this item.",
                        25,
                        "Scheduled"
                )
        );

        assessments.add(
                new Assessment(
                        1,
                        "Java Fundamentals Assessment",
                        "Java Full Stack Development",
                        24,
                        100,
                        "Trainer workspace • Manage and update this item.",
                        "Active"
                )
        );

        assessments.add(
                new Assessment(
                        2,
                        "SQL Skill Test",
                        "Java Full Stack Development",
                        18,
                        100,
                        "Trainer workspace • Manage and update this item.",
                        "Active"
                )
        );

        assessments.add(
                new Assessment(
                        3,
                        "Communication Assessment",
                        "Professional Communication",
                        30,
                        100,
                        "Trainer workspace • Manage and update this item.",
                        "Active"
                )
        );

        learnerSkills.add(
                new LearnerSkill(
                        1,
                        "Java",
                        "Advanced",
                        18,
                        "Trainer workspace • Manage and update this item."
                )
        );

        learnerSkills.add(
                new LearnerSkill(
                        2,
                        "Python",
                        "Intermediate",
                        22,
                        "Trainer workspace • Manage and update this item."
                )
        );

        learnerSkills.add(
                new LearnerSkill(
                        3,
                        "Communication",
                        "Advanced",
                        31,
                        "Trainer workspace • Manage and update this item."
                )
        );

        learnerSkills.add(
                new LearnerSkill(
                        4,
                        "Leadership",
                        "Developing",
                        15,
                        "Trainer workspace • Manage and update this item."
                )
        );

        performanceSummary =
                new PerformanceSummary(
                        92,
                        84,
                        18,
                        12
                );

        announcements.add(
                new Announcement(
                        1,
                        "Course Completion",
                        "3 learners completed a course",
                        "Today",
                        false
                )
        );

        announcements.add(
                new Announcement(
                        2,
                        "Assessment",
                        "New assessment submitted",
                        "Today",
                        false
                )
        );

        announcements.add(
                new Announcement(
                        3,
                        "Training Session",
                        "Training session at 2:00 PM",
                        "Today",
                        false
                )
        );
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public Trainer authenticate(
            String email,
            String password) {

        for (Trainer trainer : trainers) {

            if (trainer.login(email, password)) {
                return trainer;
            }
        }

        return null;
    }

    public boolean emailExists(String email) {

        for (Trainer trainer : trainers) {

            if (trainer.getEmail()
                    .equalsIgnoreCase(email)) {

                return true;
            }
        }

        return false;
    }

    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Learner> getLearners() {
        return learners;
    }

    public void addLearner(Learner learner) {
        learners.add(learner);
    }

    public List<Seminar> getSeminars() {
        return seminars;
    }

    public void addSeminar(Seminar seminar) {
        seminars.add(seminar);
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    public List<LearnerSkill> getLearnerSkills() {
        return learnerSkills;
    }

    public void addLearnerSkill(LearnerSkill skill) {
        learnerSkills.add(skill);
    }

    public PerformanceSummary getPerformanceSummary() {
        return performanceSummary;
    }

    public void setPerformanceSummary(
            PerformanceSummary performanceSummary) {

        this.performanceSummary = performanceSummary;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void addAnnouncement(
            Announcement announcement) {

        announcements.add(announcement);
    }

    public int getActiveCourseCount() {
        return courses.size();
    }

    public int getLearnerCount() {
        return learners.size();
    }

    public int getAssessmentCount() {
        return assessments.size();
    }

    public int getSeminarCount() {
        return seminars.size();
    }
}
