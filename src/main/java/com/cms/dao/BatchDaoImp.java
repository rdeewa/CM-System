package com.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cms.Course_Monitoring_System.Batch;
import com.cms.exception.BatchException;
import com.cms.util.HibernateUtil;

public class BatchDaoImp implements BatchDao {

    @Override
    public boolean isCourseIdPresent(int courseId) throws BatchException {
        boolean result = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT 1 FROM Course WHERE courseId = :courseId");
            query.setParameter("courseId", courseId);
            result = query.uniqueResult() != null;
        } catch (Exception e) {
            throw new BatchException(e.getMessage());
        }

        return result;
    }

    @Override
    public boolean isFacultyIdPresent(int facultyId) throws BatchException {
        boolean result = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT 1 FROM Faculty WHERE facultyId = :facultyId");
            query.setParameter("facultyId", facultyId);
            result = query.uniqueResult() != null;
        } catch (Exception e) {
            throw new BatchException(e.getMessage());
        }

        return result;
    }

    @Override
    public boolean isBatchNameUnique(String name) throws BatchException {
        boolean result = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT 1 FROM Batch WHERE batchName = :batchName");
            query.setParameter("batchName", name);
            result = query.uniqueResult() != null;
        } catch (Exception e) {
            throw new BatchException(e.getMessage());
        }

        return result;
    }

    @Override
    public String createBatch(Batch batch) throws BatchException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(batch);
            transaction.commit();
            message = "Batch created successfully!";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new BatchException(e.getMessage());
        }

        return message;
    }

    @Override
    public String upadteBatchByName(String old_name, Batch batch) throws BatchException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<?> query = session.createQuery("UPDATE Batch SET courseId = :courseId, facultyId = :facultyId, numberOfStudents = :numberOfStudents, batchStartDate = :batchStartDate, duration = :duration, batchName = :batchName WHERE batchName = :oldName");
            query.setParameter("courseId", batch.getCourse());
            query.setParameter("facultyId", batch.getFaculty());
            query.setParameter("numberOfStudents", batch.getNumberOfStudents());
            query.setParameter("batchStartDate", batch.getBatchStartDate());
            query.setParameter("duration", batch.getDuration());
            query.setParameter("batchName", batch.getBatchName());
            query.setParameter("oldName", old_name);

            int res = query.executeUpdate();
            if (res > 0) {
                message = "Batch updated successfully!";
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new BatchException(e.getMessage());
        }

        return message;
    }

    @Override
    public List<Batch> viewAllBatchDetails() throws BatchException {
        List<Batch> batches;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Batch> query = session.createQuery(
                "FROM Batch b " +
                "LEFT JOIN FETCH b.course " +
                "LEFT JOIN FETCH b.faculty", 
                Batch.class
            );
            batches = query.list();

            if (batches == null || batches.isEmpty()) {
                throw new BatchException("No batches found.");
            }

            for (Batch b : batches) {
                System.out.println("Batch ID: " + b.getBatchId());
                System.out.println("Course: " + b.getCourse());
                System.out.println("Faculty: " + b.getFaculty());
            }
        } catch (Exception e) {
            throw new BatchException("Error retrieving batch details: " + e.getMessage());
        }

        return batches;
    }





    @Override
    public String batchDeleteByName(String name) throws BatchException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM Batch WHERE batchName = :batchName");
            query.setParameter("batchName", name);
            int result = query.executeUpdate();
            if (result > 0) {
                message = "Batch deleted successfully!";
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new BatchException(e.getMessage());
        }

        return message;
    }


   
    @Override
    public List<Batch> availableBatch() throws BatchException {
        List<Batch> batches = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Batch> query = session.createQuery("FROM Batch", Batch.class);
            batches = query.list();
        } catch (Exception e) {
            throw new BatchException(e.getMessage());
        }

        if (batches.isEmpty()) throw new BatchException("Empty!");

        return batches;
    }

    @Override
    public Batch getBatchById(int batchId) throws BatchException {
        Batch batch = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Batch> query = session.createQuery("FROM Batch WHERE batchId = :batchId", Batch.class);
            query.setParameter("batchId", batchId);
            batch = query.uniqueResult();
        } catch (Exception e) {
            throw new BatchException(e.getMessage());
        }

        if (batch == null) {
            throw new BatchException("Batch not found with ID: " + batchId);
        }

        return batch;
    }


	
}
