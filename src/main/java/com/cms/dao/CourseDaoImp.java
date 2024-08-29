package com.cms.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.cms.Course_Monitoring_System.Course;
import com.cms.exception.CourseException;
import com.cms.util.HibernateUtil;

public class CourseDaoImp implements CourseDao {

    @Override
    public boolean isNameUnique(String name) throws CourseException {
        boolean result = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT 1 FROM Course WHERE courseName = :courseName");
            query.setParameter("courseName", name);
            result = query.uniqueResult() != null;
        } catch (Exception e) {
            throw new CourseException("Error checking course name uniqueness: " + e.getMessage());
        }

        return result;
    }

    @Override
    public String createCourse(Course course) throws CourseException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
            message = "Course created successfully!";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new CourseException("Error creating course: " + e.getMessage());
        }

        return message;
    }
    @Override
    public List<Course> viewAllCourseDetails() throws CourseException {
        List<Course> courses;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Course> query = session.createQuery("FROM Course", Course.class);
            courses = query.list();
        } catch (Exception e) {
            throw new CourseException("Error viewing all course details: " + e.getMessage());
        }

        if (courses.isEmpty()) throw new CourseException("No courses available!");

        return courses;
    }

    @Override
    public String updateCourseByName(String oldName, Course course) throws CourseException {
        String message = "Failed!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<?> query = session.createQuery("UPDATE Course SET courseName = :courseName, fee = :fee, courseDescription = :courseDescription, courseDuration = :courseDuration WHERE courseName = :oldName");
            query.setParameter("courseName", course.getCourseName());
            query.setParameter("fee", course.getFee());
            query.setParameter("courseDescription", course.getCourseDescription());
            query.setParameter("courseDuration", course.getCourseDuration());
            query.setParameter("oldName", oldName);

            int result = query.executeUpdate();
            if (result > 0) {
                message = "Course updated successfully!";
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new CourseException("Error updating course: " + e.getMessage());
        }

        return message;
    }

    @Override
    public String courseDeleteByName(String name) throws CourseException {
        String message = "Failed to delete course!";
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<?> query = session.createQuery("DELETE FROM Course WHERE courseName = :courseName");
            query.setParameter("courseName", name);
            int result = query.executeUpdate();
            if (result > 0) {
                message = "Course deleted successfully!";
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new CourseException("Error deleting course: " + e.getMessage());
        }

        return message;
    }


    @Override
    public boolean isCourseNameAvailable(String name) throws CourseException {
        boolean result = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT 1 FROM Course WHERE courseName = :courseName");
            query.setParameter("courseName", name);
            result = query.uniqueResult() != null;
        } catch (Exception e) {
            throw new CourseException("Error checking course name availability: " + e.getMessage());
        }

        return result;
    }


	@Override
	public String courseDeleteByName() throws CourseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Course getCourseById(int courseId) throws CourseException {
		// TODO Auto-generated method stub
		return null;
	}
}
