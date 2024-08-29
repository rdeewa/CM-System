package com.cms.dao;

import com.cms.Course_Monitoring_System.Admin;
import com.cms.exception.AdminException;

public interface AdminDao {

    // Login admin
    public Admin loginAdmin(String username, String password) throws AdminException;

    // Logout admin
    public void logoutAdmin() throws AdminException;
}
