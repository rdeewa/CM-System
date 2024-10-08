// Updated CoursePlanDao Interface
package com.cms.dao;

import java.util.List;

import com.cms.Course_Monitoring_System.CoursePlan;
import com.cms.exception.CoursePlanException;

public interface CoursePlanDao {

    // check course plan id before checking
    public boolean isBatchIdAvailable(int id) throws CoursePlanException;

    // create course plan
    public String createCoursePlan(CoursePlan coursePlan) throws CoursePlanException;

    // before updating check the planId is available or not
    public boolean isPlanIdAvailable(int id) throws CoursePlanException;

    // update course plan by id
    public String upadteCoursePlanById(int id, CoursePlan coursePlan) throws CoursePlanException;

    // view all courses plan details
    public List<CoursePlan> viewAllCoursePlanDetails() throws CoursePlanException;

    // delete course plan by id
    public String coursePlanDeleteById(int id) throws CoursePlanException; // Updated method

    // pending course plan
    public List<CoursePlan> pendingCoursePlan() throws CoursePlanException;

    // update course plan status
    public String updateCoursePlanStatus(int id) throws CoursePlanException;

    // before updating checking id is available and the status is pending or not
    public boolean isIdAvaillableAndStatusPending(int id) throws CoursePlanException;
}
