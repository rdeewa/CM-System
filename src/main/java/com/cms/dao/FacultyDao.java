package com.cms.dao;

import java.util.List;

import com.cms.Course_Monitoring_System.Faculty;
import com.cms.exception.FacultyException;

public interface FacultyDao {

    // Login faculty
    Faculty loginFaculty(String username, String password) throws FacultyException;

    // Logout faculty
    void logoutFaculty() throws FacultyException;

    // Register faculty
    String registerFaculty(Faculty faculty) throws FacultyException;

    // Update faculty by ID
    String updateFacultyById(int id, Faculty faculty) throws FacultyException;

    // View all faculty details
    List<Faculty> viewAllFaculty() throws FacultyException;

    // Delete faculty by ID
    String deleteFacultyById(int id) throws FacultyException;

    // Check if username and password are valid
    boolean checkUsernamePassword(String username, String oldPassword) throws FacultyException;

    // Update faculty password
    String updateFacultyPassword(String username, String newPassword) throws FacultyException;
    
    Faculty getFacultyById(int id) throws FacultyException;
}
