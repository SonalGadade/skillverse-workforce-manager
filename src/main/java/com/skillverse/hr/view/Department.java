package com.skillverse.hr.view;

public class Department {
    private String name;
    private int headcount;

    public Department(String name, int headcount) {
        this.name = name;
        this.headcount = headcount;
    }

    public String getName() { return name; }
    public int getHeadcount() { return headcount; }
}
