package com.cms.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.cms.Course_Monitoring_System.Admin;
import com.cms.exception.AdminException;
import com.cms.start.UserActivity;
import com.cms.util.HashingUtil;
import com.cms.util.HibernateUtil;

public class AdminDaoImp implements AdminDao {

    @Override
    public Admin loginAdmin(String username, String password) throws AdminException {
        Admin admin = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Start a transaction
            transaction = session.beginTransaction();

            // Create HQL query to fetch the admin
            Query<Admin> query = session.createQuery("FROM Admin WHERE username = :username", Admin.class);
            query.setParameter("username", username);

            admin = query.uniqueResult();

            if (admin == null || !HashingUtil.verifyPassword(password, admin.getPassword())) {
                throw new AdminException("Invalid username or password!");
            }

            // Commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new AdminException("Login failed: " + e.getMessage(), e);
        }

        return admin;
    }

    @Override
    public void logoutAdmin() throws AdminException {
        // Redirect to the previous menu
        UserActivity.selectUser();
    }
}
