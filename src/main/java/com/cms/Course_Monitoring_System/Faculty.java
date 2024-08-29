package com.cms.Course_Monitoring_System;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Faculty_ID")
    private int facultyId;

    @Column(name = "Faculty_Name")
    private String facultyName;

    @Column(name = "Faculty_Address")
    private String facultyAddress;

    @Column(name = "Mobile")
    private String mobile;

    @Column(name = "Email")
    private String email;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @OneToMany(mappedBy = "faculty")
    private List<Batch> batches;

    public Faculty() {
        super();
    }

    public Faculty(int facultyId, String facultyName, String facultyAddress, String mobile, String email,
                   String username, String password) {
        super();
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyAddress = facultyAddress;
        this.mobile = mobile;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyAddress() {
        return facultyAddress;
    }

    public void setFacultyAddress(String facultyAddress) {
        this.facultyAddress = facultyAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Faculty [facultyId=" + facultyId + ", facultyName=" + facultyName + ", facultyAddress=" + facultyAddress
                + ", mobile=" + mobile + ", email=" + email + ", username=" + username + ", password=" + password + "]";
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }
}
