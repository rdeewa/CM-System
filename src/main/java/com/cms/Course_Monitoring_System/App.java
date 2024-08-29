package com.cms.Course_Monitoring_System;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.cms.util.HashingUtil;

public class App {
    public static void main(String[] args) {
        System.out.println("Project Started...");

        // Create SessionFactory
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");  // Explicitly load the configuration
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Create and save entities
            Admin ad1 = new Admin();
            ad1.setUsername("deewanshu");
            ad1.setPassword(HashingUtil.hashPassword("deewa123"));
            session.save(ad1);

            Admin ad2 = new Admin();
            ad2.setUsername("diya");
            ad2.setPassword(HashingUtil.hashPassword("diya123"));
            session.save(ad2);

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        } finally {
            session.close();
            factory.close();
        }

        System.out.println("Application ended successfully!");
    }
}
