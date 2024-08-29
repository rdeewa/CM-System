package com.cms.usecase;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.cms.Course_Monitoring_System.Batch;
import com.cms.Course_Monitoring_System.CoursePlan;
import com.cms.dao.BatchDaoImp;
import com.cms.dao.CoursePlanDao;
import com.cms.dao.CoursePlanDaoImp;
import com.cms.exception.BatchException;
import com.cms.exception.CourseException;
import com.cms.exception.CoursePlanException;
import com.cms.exception.FacultyException;
import com.cms.start.UserActivity;

public class CoursePlanUseCase {

	public static void createCoursePlan() {
        CoursePlanDao dao = new CoursePlanDaoImp();
        CoursePlan coursePlan = new CoursePlan();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter required course plan details - ");

        try {
            // Retrieve and display available batches
            List<Batch> batches = new BatchDaoImp().availableBatch();
            batches.forEach(b -> {
                System.out.print(b.getBatchId() + "(" + b.getBatchName() + ") ");
            });
            System.out.println("\n\nEnter batch id");
            int batchId = sc.nextInt();

            // Fetch the Batch object
            Batch batch = new BatchDaoImp().getBatchById(batchId);
            if (batch == null) {
                System.out.println("\nThis batch id doesn't exist!" + "\nPlease select from above");
                try {
					UserActivity.coursePlanOptions();
				} catch (CourseException | FacultyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return;
            }

            // Map to convert day names to numbers
            Map<String, Integer> dayMap = new HashMap<>();
            dayMap.put("monday", 1);
            dayMap.put("tuesday", 2);
            dayMap.put("wednesday", 3);
            dayMap.put("thursday", 4);
            dayMap.put("friday", 5);
            dayMap.put("saturday", 6);
            dayMap.put("sunday", 7);

            System.out.println("Enter day name (e.g., Monday, Tuesday)");
            String dayName = sc.next().toLowerCase();

            // Validate the day name input
            if (!dayMap.containsKey(dayName)) {
                System.out.println("\nInvalid day name. Please enter a valid day name.");
                try {
					UserActivity.coursePlanOptions();
				} catch (CourseException | FacultyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return;
            }

            // Get the day number corresponding to the day name
            int dayNumber = dayMap.get(dayName);

            // Get the topic for the course plan
            System.out.println("Enter course plan topic");
            String topic = sc.next();

            // Get the status for the course plan
            System.out.println("Enter status (e.g., Completed, Pending, In Progress)");
            String status = sc.next();

            // Set the details in the CoursePlan object
            coursePlan.setBatch(batch);
            coursePlan.setDayNumber(dayNumber);
            coursePlan.setTopic(topic);
            coursePlan.setStatus(status);

            // Create the course plan and display the result
            String result = dao.createCoursePlan(coursePlan);
            System.out.println("\n" + result);

        } catch (InputMismatchException e) {
            System.out.println("\nInvalid data-type");
            try {
				UserActivity.coursePlanOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } catch (CoursePlanException e) {
            System.out.println("\n" + e.getMessage());
            try {
				UserActivity.coursePlanOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } catch (BatchException e) {
            System.out.println(e.getMessage());
            try {
				UserActivity.coursePlanOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }

        // Display the course plan options again
        try {
			UserActivity.coursePlanOptions();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


	public static void coursePlanUpdateById() {
	    CoursePlanDao dao = new CoursePlanDaoImp();
	    CoursePlan coursePlan = new CoursePlan();
	    Scanner sc = new Scanner(System.in);
	    
	    System.out.println("\nEnter course plan id to update - ");
	    int planId = sc.nextInt();
	    
	    try {
	        if (!dao.isPlanIdAvailable(planId)) {
	            System.out.println("\nThis planId doesn't exist!");
	            try {
					UserActivity.coursePlanOptions();
				} catch (CourseException | FacultyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return;
	        }
	        
	        System.out.println("Enter batch id");
	        int batchId = sc.nextInt();
	        Batch batch = new BatchDaoImp().getBatchById(batchId); // Fetch the Batch object
	        if (batch == null) {
	            System.out.println("\nThis batch id doesn't exist!");
	            try {
					UserActivity.coursePlanOptions();
				} catch (CourseException | FacultyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return;
	        }
	        
	        System.out.println("Enter day number of week between (1 for Monday) to (7 for Sunday)");
	        int dayNumber = sc.nextInt();
	        if (dayNumber < 1 || dayNumber > 7) {
	            System.out.println("\nInvalid entry (choose between 1 to 7)");
	            try {
					UserActivity.coursePlanOptions();
				} catch (CourseException | FacultyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return;
	        }
	        
	        System.out.println("Enter course plan topic");
	        String topic = sc.next();
	        String status = "Pending";
	        
	        coursePlan.setBatch(batch); // Set the Batch object
	        coursePlan.setDayNumber(dayNumber);
	        coursePlan.setTopic(topic);
	        coursePlan.setStatus(status);
	        
	        String result = dao.upadteCoursePlanById(planId, coursePlan);
	        System.out.println("\n" + result);
	        
	    } catch (InputMismatchException e) {
	        System.out.println("\nInvalid data-type");
	        try {
				UserActivity.coursePlanOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    } catch (CoursePlanException e) {
	        System.out.println(e.getMessage());
	        try {
				UserActivity.coursePlanOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    } catch (BatchException e) {
	        System.out.println(e.getMessage());
	        try {
				UserActivity.coursePlanOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
	    
	    try {
			UserActivity.coursePlanOptions();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void viewAllCoursePlans() {

		try {

			List<CoursePlan> coursePlans = new CoursePlanDaoImp().viewAllCoursePlanDetails();

			coursePlans.forEach(cp -> {

				System.out.println("Course Plan ID : " + cp.getPlanId());
				System.out.println("Course Plan Batch ID : " + cp.getBatch());

				int day = cp.getDayNumber();

				switch (day) {
                case 1:
                    System.out.println("Course Plan Day Number : " + day +"(Monday)");
                    break;
                case 2:
                    System.out.println("Course Plan Day Number : " + day +"(Tuesday)");
                    break;
                case 3:
                    System.out.println("Course Plan Day Number : " + day +"(Wednesday)");
                    break;
                case 4:
                    System.out.println("Course Plan Day Number : " + day +"(Thursday)");
                    break;
                case 5:
                    System.out.println("Course Plan Day Number : " + day +"(Friday)");
                    break;
                case 6:
                    System.out.println("Course Plan Day Number : " + day +"(Saturday)");
                    break;
                case 7:
                    System.out.println("Course Plan Day Number : " + day + "(Sunday)");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid day: " + day);
            }
				
				System.out.println("Course Plan Topic : " + cp.getTopic());
				System.out.println("Course Plan Status : " + cp.getStatus());

				System.out.println("==================================");
			});

		} catch (CoursePlanException e) {
			System.out.println(e.getMessage());

			System.out.println();
			System.out.println("Try again...");
			try {
				UserActivity.coursePlanOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		try {
			UserActivity.coursePlanOptions();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void coursePlanDeleteById() {
	    Scanner sc = new Scanner(System.in);
	    
	    try {
	        System.out.println("Enter the Course Plan ID to delete: ");
	        int coursePlanId = sc.nextInt();
	        
	        String response = new CoursePlanDaoImp().coursePlanDeleteById(coursePlanId);
	        System.out.println("\n" + response);
	        
	    } catch (InputMismatchException e) {
	        System.out.println("Invalid input. Please enter a valid Course Plan ID.");
	        sc.next(); // Clear the invalid input
	        coursePlanDeleteById(); // Retry
	    } catch (CoursePlanException e) {
	        System.out.println(e.getMessage());
	        System.out.println("Try again...");
	        coursePlanDeleteById(); // Retry
	    }
	    
	    try {
	        UserActivity.coursePlanOptions();
	    } catch (CourseException | FacultyException e) {
	        e.printStackTrace();
	    }
	}

}
