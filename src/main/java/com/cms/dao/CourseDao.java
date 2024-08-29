package com.cms.dao;

import java.util.List;

import com.cms.Course_Monitoring_System.Course;
import com.cms.exception.CourseException;

public interface CourseDao {

    // Check if course name is unique
    boolean isNameUnique(String name) throws CourseException;

    // Create a new course
    String createCourse(Course course) throws CourseException;

    // Check if course name is available before updating
    boolean isCourseNameAvailable(String name) throws CourseException;

    // Update course by name
    String updateCourseByName(String oldName, Course course) throws CourseException;

    // View all course details
    List<Course> viewAllCourseDetails() throws CourseException;

    // Delete course by name
    String courseDeleteByName() throws CourseException;

    // Fetch a course by its ID
    Course getCourseById(int courseId) throws CourseException; // New method

	String courseDeleteByName(String name) throws CourseException;
}
