package com.cms.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cms.Course_Monitoring_System.CoursePlan;
import com.cms.exception.CoursePlanException;
import com.cms.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class CoursePlanDaoImp implements CoursePlanDao {

    @Override
    public boolean isBatchIdAvailable(int id) throws CoursePlanException {
        boolean result = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT 1 FROM Batch WHERE batchId = :batchId");
            query.setParameter("batchId", id);
            result = query.uniqueResult() != null;
        } catch (Exception e) {
            throw new CoursePlanException(e.getMessage());
        }

        return result;
    }

    @Override
    public String createCoursePlan(CoursePlan coursePlan) throws CoursePlanException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(coursePlan);
            transaction.commit();
            message = "Course plan created successfully!";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new CoursePlanException(e.getMessage());
        }

        return message;
    }

    @Override
    public boolean isPlanIdAvailable(int id) throws CoursePlanException {
        boolean result = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT 1 FROM CoursePlan WHERE planId = :planId");
            query.setParameter("planId", id);
            result = query.uniqueResult() != null;
        } catch (Exception e) {
            throw new CoursePlanException(e.getMessage());
        }

        return result;
    }

    @Override
    public String upadteCoursePlanById(int id, CoursePlan coursePlan) throws CoursePlanException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Query<?> query = session.createQuery("UPDATE CoursePlan SET batchId = :batchId, dayNumber = :dayNumber, topic = :topic, status = :status WHERE planId = :planId");
            query.setParameter("batchId", coursePlan.getBatch());
            query.setParameter("dayNumber", coursePlan.getDayNumber());
            query.setParameter("topic", coursePlan.getTopic());
            query.setParameter("status", coursePlan.getStatus());
            query.setParameter("planId", id);
            
            int res = query.executeUpdate();
            if (res > 0) {
                message = "Course plan updated successfully!";
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new CoursePlanException(e.getMessage());
        }

        return message;
    }

    @Override
    public List<CoursePlan> viewAllCoursePlanDetails() throws CoursePlanException {
        List<CoursePlan> coursePlans = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CoursePlan> query = session.createQuery("FROM CoursePlan", CoursePlan.class);
            coursePlans = query.list();
        } catch (Exception e) {
            throw new CoursePlanException(e.getMessage());
        }

        if (coursePlans.isEmpty()) throw new CoursePlanException("Empty!");

        return coursePlans;
    }

    @Override
    public String coursePlanDeleteById(int coursePlanId) throws CoursePlanException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            
            // Retrieve the CoursePlan by ID
            CoursePlan coursePlan = session.get(CoursePlan.class, coursePlanId);
            
            if (coursePlan == null) {
                throw new CoursePlanException("Course Plan with ID " + coursePlanId + " does not exist.");
            }
            
            // Delete the CoursePlan
            session.delete(coursePlan);
            tx.commit();
            
            return "Course Plan with ID " + coursePlanId + " was successfully deleted.";
        } catch (Exception e) {
            throw new CoursePlanException("Error deleting course plan: " + e.getMessage());
        }
    }

   



    @Override
    public List<CoursePlan> pendingCoursePlan() throws CoursePlanException {
        List<CoursePlan> coursePlans = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CoursePlan> query = session.createQuery("FROM CoursePlan WHERE status = 'Pending'", CoursePlan.class);
            coursePlans = query.list();
        } catch (Exception e) {
            throw new CoursePlanException(e.getMessage());
        }

        if (coursePlans.isEmpty()) throw new CoursePlanException("Empty!");

        return coursePlans;
    }

    @Override
    public String updateCoursePlanStatus(int id) throws CoursePlanException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<?> query = session.createQuery("UPDATE CoursePlan SET status = 'Completed' WHERE planId = :planId");
            query.setParameter("planId", id);
            int res = query.executeUpdate();

            if (res > 0) {
                message = "Course plan updated successfully!";
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new CoursePlanException(e.getMessage());
        }

        return message;
    }

    @Override
    public boolean isIdAvaillableAndStatusPending(int id) throws CoursePlanException {
        boolean result = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT 1 FROM CoursePlan WHERE planId = :planId AND status = 'Pending'");
            query.setParameter("planId", id);
            result = query.uniqueResult() != null;
        } catch (Exception e) {
            throw new CoursePlanException(e.getMessage());
        }

        return result;
    }
    
    

	



}
