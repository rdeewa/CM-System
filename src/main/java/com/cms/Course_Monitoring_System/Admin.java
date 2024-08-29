package com.cms.Course_Monitoring_System;

import javax.persistence.*;

@Entity
@Table(name = "Admin")
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Admin_ID")
    private int id;
    
    @Column(name = "Username")
    private String username;
    
    @Column(name = "Password")
    private String password;
    
    public Admin() {
        super();
    }

    public Admin(int id, String username, String password) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Admin [id=" + id + ", username=" + username + ", password=" + password + "]";
    }
}
