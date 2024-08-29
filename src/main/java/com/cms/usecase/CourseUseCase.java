package com.cms.usecase;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cms.Course_Monitoring_System.Course;
import com.cms.dao.CourseDao;
import com.cms.dao.CourseDaoImp;
import com.cms.exception.CourseException;
import com.cms.exception.FacultyException;
import com.cms.start.UserActivity;

public class CourseUseCase {

	public static void courseCreate() {
	    CourseDao dao = new CourseDaoImp();
	    Course course = new Course();

	    Scanner sc = new Scanner(System.in);
	    System.out.println("Enter course details - ");

	    System.out.println("Enter course name");
	    String name = sc.next();

	    try {
	        boolean res = dao.isNameUnique(name);

	        if (res) {
	            System.out.println("\nThis course name already exists!");

	            System.out.println("\nTry again...");
	            try {
					UserActivity.courseOptions();
				} catch (FacultyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return;  // Exit if the course name is not unique
	        }
	    } catch (CourseException e1) {
	        System.out.println(e1.getMessage());
	    }

	    System.out.println("Enter course fee");
	    int fee = sc.nextInt();

	    System.out.println("Enter course description");
	    String description = sc.next();

	    System.out.println("Enter course duration (e.g., 6-month)");
	    String duration = sc.next();  // Capture duration as a String

	    // Set course properties
	    course.setCourseName(name);
	    course.setFee(fee);
	    course.setCourseDescription(description);
	    course.setCourseDuration(duration);  // Set duration as String

	    try {
	        String result = dao.createCourse(course);
	        System.out.println("\n" + result);
	    } catch (CourseException e) {
	        System.out.println("\n" + e.getMessage());
	        System.out.println("\nTry again...");
	        try {
				UserActivity.courseOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }

	    try {
			UserActivity.courseOptions();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void courseUpdateByName() {
	    CourseDao dao = new CourseDaoImp();
	    Course course = new Course();

	    Scanner sc = new Scanner(System.in);
	    System.out.println("\nEnter the course name you want to update:");

	    String oldname = sc.next();  // Capture the old course name

	    try {
	        // Check if the course exists
	        boolean courseExists = dao.isCourseNameAvailable(oldname);

	        if (!courseExists) {
	            System.out.println("\nThis course does not exist!");
	            System.out.println("\nTry again...");
	            try {
	                UserActivity.courseOptions();
	            } catch (FacultyException e) {
	                e.printStackTrace();
	            }
	            return;  // Exit if the course does not exist
	        }

	    } catch (CourseException e1) {
	        System.out.println(e1.getMessage());
	        return;
	    }

	    // Proceed with updating the course details
	    System.out.println("Enter new course name:");
	    String new_name = sc.next();

	    int fee = 0;
	    try {
	        System.out.println("Enter new fee:");
	        fee = sc.nextInt();
	    } catch (InputMismatchException e) {
	        System.out.println("\nFee requires a numeric value!");
	        System.out.println("\nTry again...");
	        try {
	            UserActivity.courseOptions();
	        } catch (CourseException | FacultyException e1) {
	            e1.printStackTrace();
	        }
	        return;  // Exit if input is not a valid integer
	    }

	    System.out.println("Enter new description:");
	    String description = sc.next();

	    System.out.println("Enter new course duration (e.g., 6-month):");
	    String duration = sc.next();  // Capture the duration as a string

	    // Set the updated values in the course object
	    course.setCourseName(new_name);
	    course.setFee(fee);
	    course.setCourseDescription(description);
	    course.setCourseDuration(duration);  // Set the duration correctly

	    try {
	        // Update the course using the old name
	        String result = dao.updateCourseByName(oldname, course);
	        System.out.println("\n" + result);
	    } catch (CourseException e) {
	        System.out.println(e.getMessage());
	        System.out.println("\nTry again...");
	        try {
	            UserActivity.courseOptions();
	        } catch (CourseException | FacultyException e1) {
	            e1.printStackTrace();
	        }
	    }

	    try {
	        UserActivity.courseOptions();
	    } catch (CourseException | FacultyException e) {
	        e.printStackTrace();
	    }
	}



	public static void courseViewAll() {
	    try {
	        List<Course> courses = new CourseDaoImp().viewAllCourseDetails();

	        courses.forEach(c -> {
	            System.out.println("Course ID : " + c.getCourseId());
	            System.out.println("Course Name : " + c.getCourseName());
	            System.out.println("Course Fee : " + c.getFee());
	            System.out.println("Course Description : " + c.getCourseDescription());
	            System.out.println("Course Duration : " + c.getCourseDuration()); // Show duration
	            System.out.println("==================================");
	        });
	    } catch (CourseException e) {
	        System.out.println(e.getMessage());
	        System.out.println("Try again...");
	        try {
				UserActivity.courseOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }

	    try {
			UserActivity.courseOptions();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void courseDeleteByName() {
	    Scanner sc = new Scanner(System.in);
	    System.out.println("\nEnter course name to delete - ");
	    String name = sc.next();

	    try {
	        CourseDao dao = new CourseDaoImp();
	        String response = dao.courseDeleteByName(name);
	        System.out.println("\n" + response);

	    } catch (CourseException e) {
	        System.out.println(e.getMessage());
	        System.out.println("\nTry again...");
	        try {
	            UserActivity.courseOptions();
	        } catch (CourseException | FacultyException e1) {
	            e1.printStackTrace();
	        }
	    }

	    try {
	        UserActivity.courseOptions();
	    } catch (CourseException | FacultyException e) {
	        e.printStackTrace();
	    }
	}
}