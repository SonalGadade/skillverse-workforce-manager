package com.skillverse.hr.view;

public class Recruitment {
    private String jobTitle;
    private String department;
    private int vacancies;

    public Recruitment(String jobTitle, String department, int vacancies) {
        this.jobTitle = jobTitle;
        this.department = department;
        this.vacancies = vacancies;
    }

    public String getJobTitle() { return jobTitle; }
    public String getDepartment() { return department; }
    public int getVacancies() { return vacancies; }
}
