package com.example.gradeaverage;

public class Grade {

    private String subjectName;
    private int grade;

    public Grade(String subjectName, int grade) {
        this.subjectName = subjectName;
        this.grade = grade;
    }

    public String getName() {
        return subjectName;
    }

    public void setName(String name) {
        this.subjectName = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
