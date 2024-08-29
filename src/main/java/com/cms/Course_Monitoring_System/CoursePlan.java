package com.cms.Course_Monitoring_System;

import javax.persistence.*;

@Entity
@Table(name = "CoursePlan")
public class CoursePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Plan_ID")
    private int planId;

    @ManyToOne
    @JoinColumn(name = "Batch_ID")
    private Batch batch;

    @Column(name = "Day_Number")
    private int dayNumber;

    @Column(name = "Topic")
    private String topic;

    @Column(name = "Status")
    private String status;

    public CoursePlan() {
        super();
    }

    public CoursePlan(int planId, Batch batch, int dayNumber, String topic, String status) {
        super();
        this.planId = planId;
        this.batch = batch;
        this.dayNumber = dayNumber;
        this.topic = topic;
        this.status = status;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CoursePlan [planId=" + planId + ", batch=" + batch + ", dayNumber=" + dayNumber + ", topic=" + topic
                + ", status=" + status + "]";
    }
}
