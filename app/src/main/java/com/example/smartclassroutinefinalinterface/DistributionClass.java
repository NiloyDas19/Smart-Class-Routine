package com.example.smartclassroutinefinalinterface;

public class DistributionClass {
    String courseCode,teacherId,type,credit,year;

    public DistributionClass(String courseCode, String teacherId, String type, String credit,String  year) {
        this.courseCode = courseCode;
        this.teacherId = teacherId;
        this.type = type;
        this.credit = credit;
        this.year = year;
    }

    public DistributionClass() {
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "DistributionClass{" +
                "courseCode='" + courseCode + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", type='" + type + '\'' +
                ", credit='" + credit + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
