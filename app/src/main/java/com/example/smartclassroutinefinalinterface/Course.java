package com.example.smartclassroutinefinalinterface;

public class Course {
    private String title,code,credit,type,dept,year,semester;

    public Course() {
    }

    public Course(String title, String code, String credit, String type,String dept,String year,String semester) {
        this.title = title;
        this.code = code;
        this.credit = credit;
        this.type = type;
        this.dept = dept;
        this.year = year;
        this.semester = semester;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
