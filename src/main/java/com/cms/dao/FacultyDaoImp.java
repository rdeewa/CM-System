package com.cms.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cms.Course_Monitoring_System.Faculty;
import com.cms.exception.FacultyException;
import com.cms.util.HashingUtil;
import com.cms.util.HibernateUtil;
import com.cms.start.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class FacultyDaoImp implements FacultyDao {

	@Override
	public Faculty loginFaculty(String username, String password) throws FacultyException {
	    Faculty faculty = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        // Hash the input password
	        String hashedPassword = HashingUtil.hashPassword(password);
	        
	        Query<Faculty> query = session.createQuery("FROM Faculty WHERE username = :username AND password = :password", Faculty.class);
	        query.setParameter("username", username);
	        query.setParameter("password", hashedPassword);
	        faculty = query.uniqueResult();

	        if (faculty == null) {
	            throw new FacultyException("Invalid username or password!");
	        }
	    } catch (Exception e) {
	        throw new FacultyException("Error during faculty login: " + e.getMessage());
	    }

	    return faculty;
	}


    @Override
    public void logoutFaculty() throws FacultyException {
        UserActivity.selectUser();
    }

    @Override
    public String registerFaculty(Faculty faculty) throws FacultyException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(faculty);
            transaction.commit();
            message = "Faculty registered successfully!";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new FacultyException("Error during faculty registration: " + e.getMessage());
        }

        return message;
    }

    @Override
    public String updateFacultyById(int id, Faculty faculty) throws FacultyException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Faculty existingFaculty = session.get(Faculty.class, id);

            if (existingFaculty != null) {
                existingFaculty.setFacultyName(faculty.getFacultyName());
                existingFaculty.setFacultyAddress(faculty.getFacultyAddress());
                existingFaculty.setMobile(faculty.getMobile());
                existingFaculty.setEmail(faculty.getEmail());
                existingFaculty.setUsername(faculty.getUsername());
                existingFaculty.setPassword(faculty.getPassword());
                session.update(existingFaculty);
                transaction.commit();
                message = "Faculty updated successfully!";
            } else {
                message = "Faculty does not exist with ID: " + id;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new FacultyException("Error during faculty update: " + e.getMessage());
        }

        return message;
    }

    @Override
    public List<Faculty> viewAllFaculty() throws FacultyException {
        List<Faculty> faculties = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Faculty> query = session.createQuery("FROM Faculty", Faculty.class);
            faculties = query.list();
        } catch (Exception e) {
            throw new FacultyException("Error viewing all faculty details: " + e.getMessage());
        }

        if (faculties.isEmpty()) throw new FacultyException("No faculty records found!");

        return faculties;
    }

    @Override
    public String deleteFacultyById(int id) throws FacultyException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Faculty faculty = session.get(Faculty.class, id);

            if (faculty != null) {
                session.delete(faculty);
                transaction.commit();
                message = "Faculty deleted successfully!";
            } else {
                message = "Faculty does not exist with ID: " + id;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new FacultyException("Error during faculty deletion: " + e.getMessage());
        }

        return message;
    }


    @Override
    public boolean checkUsernamePassword(String username, String oldPassword) throws FacultyException {
        boolean result = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Faculty WHERE username = :username AND password = :password", Long.class);
            query.setParameter("username", username);
            query.setParameter("password", oldPassword);
            result = query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new FacultyException("Error checking username and password: " + e.getMessage());
        }

        return result;
    }

    @Override
    public String updateFacultyPassword(String username, String newPassword) throws FacultyException {
        String result = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<?> query = session.createQuery("UPDATE Faculty SET password = :password WHERE username = :username");
            query.setParameter("password", newPassword);
            query.setParameter("username", username);
            int res = query.executeUpdate();

            if (res > 0) {
                result = "Password updated successfully!";
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new FacultyException("Error updating faculty password: " + e.getMessage());
        }

        return result;
    }

	@Override
	public Faculty getFacultyById(int id) throws FacultyException {
		// TODO Auto-generated method stub
		return null;
	}
}
