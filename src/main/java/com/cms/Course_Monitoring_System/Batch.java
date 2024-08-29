package com.cms.Course_Monitoring_System;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Batch")
public class Batch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Batch_ID")
    private int batchId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Course_ID")
    private Course course;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Faculty_ID")
    private Faculty faculty;
    
    @Column(name = "NumberOfStudents")
    private int numberOfStudents;
    
    @Column(name = "BatchStartDate")
    @Temporal(TemporalType.DATE)
    private Date batchStartDate;
    
    @Column(name = "Duration")
    private String duration;
    
    @Column(name = "BatchName")
    private String batchName;

    public Batch() {
        // Default constructor
    }

    // Getters and Setters

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Date getBatchStartDate() {
        return batchStartDate;
    }

    public void setBatchStartDate(Date batchStartDate) {
        this.batchStartDate = batchStartDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    @Override
    public String toString() {
        return "Batch [batchId=" + batchId + ", course=" + course + ", faculty=" + faculty
                + ", numberOfStudents=" + numberOfStudents + ", batchStartDate=" + batchStartDate + ", duration="
                + duration + ", batchName=" + batchName + "]";
    }
}
