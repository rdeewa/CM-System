package com.cms.Course_Monitoring_System;

import javax.persistence.*;

@Entity
@Table(name = "Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Course_ID")
    private int courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "Fee", nullable = false)
    private int fee;

    @Column(name = "CourseDescription", nullable = false)
    private String courseDescription;

    @Column(name = "CourseDuration", nullable = false)
    private String courseDuration; // Updated field

    public Course() {
        super();
    }

    public Course(String courseName, int fee, String courseDescription, String courseDuration) {
        super();
        this.courseName = courseName;
        this.fee = fee;
        this.courseDescription = courseDescription;
        this.courseDuration = courseDuration; // Updated constructor
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String duration) {
        this.courseDuration = duration;
    }

    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", courseName=" + courseName + ", fee=" + fee + ", courseDescription=" + courseDescription + ", courseDuration=" + courseDuration + "]";
    }
}
