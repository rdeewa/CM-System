package com.cms.usecase;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cms.Course_Monitoring_System.Batch;
import com.cms.Course_Monitoring_System.Course;
import com.cms.Course_Monitoring_System.Faculty;
import com.cms.dao.BatchDao;
import com.cms.dao.BatchDaoImp;
import com.cms.dao.CourseDaoImp;
import com.cms.dao.FacultyDaoImp;
import com.cms.exception.BatchException;
import com.cms.exception.CourseException;
import com.cms.exception.FacultyException;
import com.cms.start.UserActivity;

public class BatchUseCase {

	public static void createBatch() throws CourseException, FacultyException {
	    BatchDao dao = new BatchDaoImp();
	    Batch batch = new Batch();
	    Scanner sc = new Scanner(System.in);

	    System.out.println("\nEnter batch details - ");

	    try {
	        // Handle batch name
	        String name;
	        System.out.println("Enter batch name:");
	        name = sc.next();
	        boolean nameExists = dao.isBatchNameUnique(name);
	        if (nameExists) {
	            System.out.println("This batch name already exists!");
	            UserActivity.batchOptions();
	            return;
	        }

	        // Handle course ID
	        int courseId;
	        Course course;
	        System.out.println("Enter course ID:");
	        courseId = sc.nextInt();
	        boolean courseExists = dao.isCourseIdPresent(courseId);
	        if (!courseExists) {
	            System.out.println("This course ID doesn't exist!");
	            UserActivity.batchOptions();
	            return;
	        }
	        course = new CourseDaoImp().getCourseById(courseId);

	        // Handle faculty ID
	        int facultyId;
	        Faculty faculty;
	        System.out.println("Enter faculty ID:");
	        facultyId = sc.nextInt();
	        boolean facultyExists = dao.isFacultyIdPresent(facultyId);
	        if (!facultyExists) {
	            System.out.println("This faculty ID doesn't exist!");
	            UserActivity.batchOptions();
	            return;
	        }
	        faculty = new FacultyDaoImp().getFacultyById(facultyId);

	        // Handle number of students
	        int numberOfStudents;
	        System.out.println("Enter number of students:");
	        numberOfStudents = sc.nextInt();

	        // Handle batch start date
	        System.out.println("Enter batch start date (yyyy-mm-dd):");
	        String batchStartDateStr = sc.next();
	        Date batchStartDate = java.sql.Date.valueOf(batchStartDateStr);

	        // Handle batch duration
	        String duration;
	        System.out.println("Enter batch duration:");
	        duration = sc.next();

	        // Set values to the batch object
	        batch.setBatchName(name);
	        batch.setCourse(course);
	        batch.setFaculty(faculty);
	        batch.setNumberOfStudents(numberOfStudents);
	        batch.setBatchStartDate(batchStartDate);
	        batch.setDuration(duration);

	        // Create the batch
	        String result = dao.createBatch(batch);
	        System.out.println(result);

	    } catch (InputMismatchException e) {
	        System.out.println("Invalid data type!");
	    } catch (BatchException | FacultyException e) {
	        System.out.println(e.getMessage());
	    } finally {
	        try {
	            UserActivity.batchOptions();
	        } catch (CourseException | FacultyException e) {
	            e.printStackTrace();
	        }
	    }
	}



    public static void batchUpdateByName() throws CourseException, FacultyException {
        BatchDao dao = new BatchDaoImp();
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\nEnter batch name to update - ");

            String oldName;
            System.out.println("Enter old batch name");
            oldName = sc.next();
            if (!dao.isBatchNameUnique(oldName)) {
                System.out.println("\nThis batch name does not exist!");
                UserActivity.batchOptions();
                return;
            }

            String newName;
            System.out.println("Enter new batch name");
            newName = sc.next();
            if (dao.isBatchNameUnique(newName)) {
                System.out.println("\nThis batch name already exists!");
                UserActivity.batchOptions();
                return;
            }

            int courseId;
            Course course;
            System.out.println("Enter course Id");
            courseId = sc.nextInt();
            if (!dao.isCourseIdPresent(courseId)) {
                System.out.println("\nThis course Id doesn't exist!");
                UserActivity.batchOptions();
                return;
            }
            course = new CourseDaoImp().getCourseById(courseId);

            int facultyId;
            Faculty faculty;
            System.out.println("Enter faculty Id");
            facultyId = sc.nextInt();
            if (!dao.isFacultyIdPresent(facultyId)) {
                System.out.println("\nThis faculty Id doesn't exist!");
                UserActivity.batchOptions();
                return;
            }
            faculty = new FacultyDaoImp().getFacultyById(facultyId);

            System.out.println("Enter number of students");
            int numberOfStudents = sc.nextInt();

            System.out.println("Enter batch start-date (yyyy-mm-dd)");
            String batchStartDateStr = sc.next();
            Date batchStartDate = Date.valueOf(batchStartDateStr);

            System.out.println("Enter duration of the batch");
            String duration = sc.next();

            Batch batch = new Batch();
            batch.setBatchName(newName);
            batch.setCourse(course);
            batch.setFaculty(faculty);
            batch.setNumberOfStudents(numberOfStudents);
            batch.setBatchStartDate(batchStartDate);
            batch.setDuration(duration);

            String result = dao.upadteBatchByName(oldName, batch);
            System.out.println("\n" + result);

        } catch (InputMismatchException e) {
            System.out.println("\nInvalid data-type!");
        } catch (BatchException e) {
            System.out.println("\n" + e.getMessage());
        } finally {
            UserActivity.batchOptions();
        }
    }

    public static void viewAllBatch() throws CourseException, FacultyException {
        try {
            // Retrieve the list of all batches
            List<Batch> batches = new BatchDaoImp().viewAllBatchDetails();

            // Check if the list is empty
            if (batches.isEmpty()) {
                System.out.println("No batches available.");
            } else {
                // Display each batch's details
                for (Batch b : batches) {
                    System.out.println("Batch ID: " + b.getBatchId());
                    System.out.println("Batch Name: " + (b.getBatchName() != null ? b.getBatchName() : "N/A"));

                    // Handle potential null values for Course and Faculty
                    Course course = b.getCourse();
                    Faculty faculty = b.getFaculty();

                    String courseId = (course != null ? String.valueOf(course.getCourseId()) : "N/A");
                    String facultyId = (faculty != null ? String.valueOf(faculty.getFacultyId()) : "N/A");

                    //System.out.println("Allocated Course: " + (course != null ? course.getCourseName() : "N/A"));
                    //System.out.println("Allocated Faculty: " + (faculty != null ? faculty.getFacultyName() : "N/A"));
                    System.out.println("Allocated Course ID: " + courseId);
                    System.out.println("Allocated Faculty ID: " + facultyId);
                    System.out.println("Number of Students: " + b.getNumberOfStudents());
                    System.out.println("Batch Start Date: " + (b.getBatchStartDate() != null ? b.getBatchStartDate().toString() : "N/A"));
                    System.out.println("Batch Duration: " + (b.getDuration() != null ? b.getDuration() : "N/A"));
                    System.out.println("==================================");
                }
            }
        } catch (BatchException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Ensure the user options are displayed again
            try {
                UserActivity.batchOptions();
            } catch (CourseException | FacultyException e) {
                e.printStackTrace();
            }
        }
    }






    public static void batchDeleteByName() throws CourseException, FacultyException {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter batch name to delete");
            String batchName = sc.next();
            String result = new BatchDaoImp().batchDeleteByName(batchName);
            System.out.println("\n" + result);
        } catch (BatchException e) {
            System.out.println(e.getMessage());
            System.out.println("Try again...");
        } finally {
            UserActivity.batchOptions();
        }
    }

}
