package com.cms.usecase;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cms.Course_Monitoring_System.CoursePlan;
import com.cms.Course_Monitoring_System.Faculty;
import com.cms.dao.CoursePlanDaoImp;
import com.cms.dao.FacultyDao;
import com.cms.dao.FacultyDaoImp;
import com.cms.exception.CourseException;
import com.cms.exception.CoursePlanException;
import com.cms.exception.FacultyException;
import com.cms.start.UserActivity;
import com.cms.util.HashingUtil;

public class FacultyUseCase {

	public static void facultyLogin() throws CourseException {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter faculty details -");
		System.out.println("Enter Username: ");
		String username = sc.next();

		System.out.println("Enter Password: ");
		String password = sc.next();

		try {
			new FacultyDaoImp().loginFaculty(username, password);

			System.out.println("\nWelcome! Login Successful...");
			UserActivity.facultyCanDo();

		} catch (FacultyException e) {

			System.out.println(e.getMessage());
			System.out.println("\nTry again...");
			UserActivity.selectUser();
		}

	}

	public static void facultyLogout() throws CourseException, FacultyException {

		System.out.println("Are you sure? y/n");

		Scanner sc = new Scanner(System.in);
		String choice = sc.next();

		if (choice.equalsIgnoreCase("y")) {
			try {
				new FacultyDaoImp().logoutFaculty();
			} catch (FacultyException e) {
				System.out.println(e.getMessage());
			}

		} else {

			System.out.println("\nInvalid choice");
			System.out.println("\nTry again...");

			UserActivity.facultyCanDo();
		}

	}

	public static void facultyRegister() {

	    Scanner sc = new Scanner(System.in);
	    System.out.println("Enter faculty details - ");

	    System.out.println("Enter faculty name:");
	    String name = sc.next();

	    System.out.println("Enter faculty address:");
	    String address = sc.next();

	    System.out.println("Enter faculty mobile:");
	    String mobile = sc.next();

	    System.out.println("Enter faculty email:");
	    String email = sc.next();

	    System.out.println("Enter faculty username:");
	    String username = sc.next();

	    System.out.println("Enter faculty password:");
	    String password = sc.next();

	    // Hash the password before storing it
	    String hashedPassword = HashingUtil.hashPassword(password);

	    FacultyDao dao = new FacultyDaoImp();
	    Faculty faculty = new Faculty();

	    faculty.setFacultyName(name);
	    faculty.setFacultyAddress(address);
	    faculty.setMobile(mobile);
	    faculty.setEmail(email);
	    faculty.setUsername(username);
	    faculty.setPassword(hashedPassword);  // Store the hashed password

	    try {
	        String result = dao.registerFaculty(faculty);
	        System.out.println(result);

	    } catch (FacultyException e) {
	        System.out.println(e.getMessage());

	        System.out.println();
	        System.out.println("Try again...");
	        try {
	            UserActivity.facultyOptions();
	        } catch (CourseException | FacultyException e1) {
	            e1.printStackTrace();
	        }

	    }

	    try {
	        UserActivity.facultyOptions();
	    } catch (CourseException | FacultyException e) {
	        e.printStackTrace();
	    }
	}
	public static void facultyUpdateById() {

		Scanner sc = new Scanner(System.in);
		System.out.println("\nEnter faculty ID to update - ");

		int id = 0;
		try {

			System.out.println("Enter the faculty id");
			id = sc.nextInt();

		} catch (InputMismatchException e) {
			// TODO Auto-generated catch block

			System.out.println("Id is numeric!");

			System.out.println("\nTry again...");
			try {
				UserActivity.facultyOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		System.out.println("Enter new name");
		String name = sc.next();

		System.out.println("Enter new address");
		String address = sc.next();

		System.out.println("Enter new mobile");
		String mobile = sc.next();

		System.out.println("Enter new email");
		String email = sc.next();

		System.out.println("Enter new username");
		String username = sc.next();

		System.out.println("Enter new password");
		String password = sc.next();

		FacultyDao dao = new FacultyDaoImp();
		Faculty faculty = new Faculty();

		faculty.setFacultyName(name);
		faculty.setFacultyAddress(address);
		faculty.setMobile(mobile);
		faculty.setEmail(email);
		faculty.setUsername(username);
		faculty.setPassword(password);

		String result;
		try {
			result = dao.updateFacultyById(id, faculty);
			System.out.println("\n" + result);

		} catch (FacultyException e) {
			// TODO Auto-generated catch block

			System.out.println(e.getMessage());

			System.out.println();
			System.out.println("Try again...");
			try {
				UserActivity.facultyOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		try {
			UserActivity.facultyOptions();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void facultyView() {

		try {

			List<Faculty> faculties = new FacultyDaoImp().viewAllFaculty();

			faculties.forEach(f -> {

				System.out.println("Faculty ID : " + f.getFacultyId());
				System.out.println("Faculty Name : " + f.getFacultyName());
				System.out.println("Faculty Address : " + f.getFacultyAddress());
				System.out.println("Faculty Mobile : " + f.getMobile());
				System.out.println("Faculty Email : " + f.getEmail());
				System.out.println("Faculty Username : " + f.getUsername());
				System.out.println("Faculty Password : " + f.getPassword());

				System.out.println("==================================");
			});

		} catch (FacultyException e) {
			System.out.println(e.getMessage());

			System.out.println();
			System.out.println("Try again...");
			try {
				UserActivity.facultyOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		try {
			UserActivity.facultyOptions();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void facultyDeleteById() {
        Scanner sc = new Scanner(System.in);

        // Prompt the user for the Faculty ID
        System.out.print("Enter the Faculty ID to delete: ");
        int idToDelete = sc.nextInt();

        try {
            // Attempt to delete the faculty record
            String response = new FacultyDaoImp().deleteFacultyById(idToDelete);
            System.out.println("\n" + response);
        } catch (FacultyException e) {
            // Print the exception message if something goes wrong
            System.out.println(e.getMessage());
        }

        // Optionally, prompt the user to return to the faculty options
        try {
            UserActivity.facultyOptions();
        } catch (CourseException | FacultyException e) {
            e.printStackTrace();
        }
    }
	
	
	public static void viewCoursePlan() {

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

			System.out.println("\n" + e.getMessage());
			System.out.println("\nTry again...");
			try {
				UserActivity.facultyCanDo();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			UserActivity.facultyCanDo();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fillUpDayWisePlanner() {

		Scanner sc = new Scanner(System.in);
		System.out.println("\nPending Courses Id");

		try {
			List<CoursePlan> coursePlans = new CoursePlanDaoImp().pendingCoursePlan();

			coursePlans.forEach(pendingCP -> {
				System.out.print(pendingCP.getPlanId() + ", ");
			});

		} catch (CoursePlanException e) {

			System.out.println("\n" + e.getMessage());
			System.out.println("\nTry again...");
			try {
				UserActivity.facultyCanDo();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		int id = 0;
		try {

			try {

				System.out.println("\n\nEnter a course id to fill-up");
				id = sc.nextInt();

				Boolean result = new CoursePlanDaoImp().isIdAvaillableAndStatusPending(id);

				if (result == false) {
					System.out.println(
							"\nId doesn't exists Or the course is completed" + "\nTips - Select from above list");

					System.out.println("\nTry again... ");
					try {
						UserActivity.facultyCanDo();
					} catch (CourseException | FacultyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (CoursePlanException e) {

				System.out.println(e.getMessage());

				System.out.println("\nTry again...");
				try {
					UserActivity.facultyCanDo();
				} catch (CourseException | FacultyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		} catch (InputMismatchException e) {

			System.out.println("\n" + e.getMessage());
			System.out.println("\nTry again...");
			try {
				UserActivity.facultyCanDo();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		try {
			String result = new CoursePlanDaoImp().updateCoursePlanStatus(id);
			System.out.println("\n" + result);

		} catch (CoursePlanException e) {
			// TODO Auto-generated catch block

			System.out.println(e.getMessage());

			System.out.println("\nTry again...");
			try {
				UserActivity.facultyOptions();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			UserActivity.facultyCanDo();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void updateFacultyPassword() throws CourseException {

		Scanner sc = new Scanner(System.in);

		try {

			System.out.println("Enter your username");
			String username = sc.next();

			System.out.println("Enter your old password");
			String old_password = sc.next();

			Boolean result = new FacultyDaoImp().checkUsernamePassword(username, old_password);

			if (result == false) {
				System.out.println("\nInvalid Username Or Password");

				System.out.println("\nTry again... ");
				UserActivity.facultyCanDo();
			}

			System.out.println("Enter your new password");
			String new_password = sc.next();
			
			System.out.println("Re-enter your new password");
			String re_enter_new_password = sc.next();

			try {
				String res = new FacultyDaoImp().updateFacultyPassword(username, new_password);
				System.out.println("\n" + res);

			} catch (FacultyException e) {
				// TODO Auto-generated catch block

				System.out.println(e.getMessage());

				System.out.println("\nTry again...");
				UserActivity.facultyCanDo();
			}

			UserActivity.facultyCanDo();

		} catch (FacultyException e) {

			System.out.println(e.getMessage());

			System.out.println("\nTry again...");
			try {
				UserActivity.facultyCanDo();
			} catch (CourseException | FacultyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			UserActivity.facultyCanDo();
		} catch (CourseException | FacultyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
