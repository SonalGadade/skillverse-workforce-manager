package com.skillverse.trainer.model;

public class PerformanceSummary {

    private double averageCourseCompletion;
    private double averageAssessmentScore;
    private int learnersNeedingSupport;
    private int advancedModuleLearners;

    public PerformanceSummary(
            double averageCourseCompletion,
            double averageAssessmentScore,
            int learnersNeedingSupport,
            int advancedModuleLearners) {

        this.averageCourseCompletion = averageCourseCompletion;
        this.averageAssessmentScore = averageAssessmentScore;
        this.learnersNeedingSupport = learnersNeedingSupport;
        this.advancedModuleLearners = advancedModuleLearners;
    }

    public double getAverageCourseCompletion() {
        return averageCourseCompletion;
    }

    public double getAverageAssessmentScore() {
        return averageAssessmentScore;
    }

    public int getLearnersNeedingSupport() {
        return learnersNeedingSupport;
    }

    public int getAdvancedModuleLearners() {
        return advancedModuleLearners;
    }

    public void setAverageCourseCompletion(double averageCourseCompletion) {
        this.averageCourseCompletion = averageCourseCompletion;
    }

    public void setAverageAssessmentScore(double averageAssessmentScore) {
        this.averageAssessmentScore = averageAssessmentScore;
    }

    public void setLearnersNeedingSupport(int learnersNeedingSupport) {
        this.learnersNeedingSupport = learnersNeedingSupport;
    }

    public void setAdvancedModuleLearners(int advancedModuleLearners) {
        this.advancedModuleLearners = advancedModuleLearners;
    }

    @Override
    public String toString() {
        return "PerformanceSummary{" +
                "averageCourseCompletion=" + averageCourseCompletion +
                ", averageAssessmentScore=" + averageAssessmentScore +
                ", learnersNeedingSupport=" + learnersNeedingSupport +
                ", advancedModuleLearners=" + advancedModuleLearners +
                '}';
    }
}
