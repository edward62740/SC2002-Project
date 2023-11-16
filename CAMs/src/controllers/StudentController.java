package controllers;

import java.util.ArrayList;
import java.util.Scanner;

import services.AuthStudentService;
import services.CampStudentService;
import interfaces.ICampStaffService;
import interfaces.ICampStudentService;
import interfaces.IRequestService;
import services.EnquiryRequestService;
import services.SuggestionRequestService;
import stores.AuthStore;
import utils.InputParser;
import views.CampView;
import views.RequestView;
import enums.UserGroup;
import models.Camp;
import models.EnquiryRequest;
import models.Request;


/**
 * The {@link StudentController} class handles user interactions for the Students.
 * It is responsible for application level tasks. It utilizes
 * services {@link ICampStudentService} and {@link EnquiryRequestService} to perform
 * lower level tasks for camp and requests respectively.
 * It extends {@link UserController}.
 */
public class StudentController extends UserController {

	/**
	 * Max attempts for {@link InputParser}.
	 */
	private static final int INPUT_MAX_ATTEMPTS = 1;
	/**
	 * Instance of {@link EnquiryRequestService}. Provides lower-level logic for handling enquires.
	 */
	private static EnquiryRequestService enquiryService = new EnquiryRequestService();

	/**
	 * Instance of {@link ICampStudentService}. Provides lower-level logic for handling camp-related functionality for students.
	 */
	private static ICampStudentService campStudentService = new CampStudentService();

	/**
	 * Runs the controller to take in user input and act accordingly.
	 * @return true to signal to calling function to exit, false otherwise.
	 */
	public static boolean run() {
		Integer c = 0;

		do {
			System.out.println("\033[1;34m--- STUDENT MENU ---\033[0m");
			if (AuthStore.getCurUser().isDefaultPassword())
			    System.out.println("\033[1;31mWarning! You are using the default password.\033[0m");
			System.out.println("\033[1mSelect option:\033[0m");
			System.out.println("\033[1;36m0. Exit");
			System.out.println("1. View open camps");
			System.out.println("2. View registered camps");
			System.out.println("3. Register for camp (as participant)");
			System.out.println("4. Register for camp (as committee)");
			System.out.println("5. Deregister from camp");
			System.out.println("6. Change password");
			System.out.println("7. Submit enquiry");
			System.out.println("8. View enquiry");
			System.out.println("9. Edit/delete enquiry\033[0m");
			System.out.println("\033[1;34m----------------------\033[0m");

			c = utils.InputParser.parseInInteger(sc, "", 0, 9, INPUT_MAX_ATTEMPTS, "C");
		} while (c == null);

		switch (c) {
		case 0:
			System.out.println("Logging out...");
			return true;
		case 1:
			viewOpenCamps();
			break;
		case 2:
			viewRegisteredCamps();
			break;
		case 3:
			registerForCamp();
			break;
		case 4:
			registerForCommittee();
			break;
		case 5:
			deregisterCamp();
			break;
		case 6:
			updatePassword();
			break;
		case 7:
			submitEnquiry();
			break;
		case 8:
			viewEnquiry();
			break;
		case 9:
			editDeleteEnquiry();
			break;
		}
		return false;

	}

	/**
	 * 
	 */
	private static void viewOpenCamps() {
		String input = "";
		UserGroup userGroup = null;
		do {
			System.out.println("Filter by faculty. Enter 'ALL' for all. Available options: ");
			for (UserGroup u : UserGroup.values())
				System.out.print(u + ", ");
			input = sc.nextLine();
			for (UserGroup u : UserGroup.values())
				if (input.strip().equals(u.toString())) {
					userGroup = u;
				}
			if (userGroup == null)
				System.out.println("Invalid user group. ");
		} while (userGroup == null);

		ArrayList<Camp> camps = campStudentService.getCamps(userGroup, false);
		for (Camp i : camps) {
			CampView.printCamp(i, AuthStore.getCurUser());
		}
		if (camps.size() == 0)
			System.out.println("There are no available camps. Modify your search or try again later. ");
	}

	/**
	 * 
	 */
	private static void viewRegisteredCamps() {
		ArrayList<Camp> camps = campStudentService.getRegisteredCamps();
		for (Camp i : camps) {
			CampView.printCamp(i, AuthStore.getCurUser());
			CampView.printPosition(i, AuthStore.getCurUser());

		}

		if (camps.size() == 0)
			System.out.println("You are not registered for any camps. ");
	}

	/**
	 * 
	 */
	private static void registerForCamp() {
		Integer id = null;
		do {
			id = utils.InputParser.parseInInteger(sc, "Enter the camp ID to register for. Enter 'C' to cancel. ", 0,
					Integer.MAX_VALUE, INPUT_MAX_ATTEMPTS, "C");
		} while (id == null);

		if (!campStudentService.existCamp(id)) {
			System.out.println("Camp does not exist.");
			return;
		}
		if (!campStudentService.isCampDateBeforeDeadline(id)) {
			System.out.println("Past deadline. Cannot register.");
			return;
		}
		if (!campStudentService.isCampNotClashWithExisting(id)) {
			System.out.println("Clashes with other camps. Cannot register.");
			return;
		}
		if (campStudentService.isPreviouslyRegistered(id)) {
			System.out.println("You previously deregistered from this camp. Not allowed to re-register.");
			return;
		}
		if (campStudentService.isAlreadyRegistered(id)) {
			System.out.println("You are already registered. Not allowed to re-register.");
			return;
		}
		if (!campStudentService.existVacancyMember(id)) {
			System.out.println("There are no more vacancies for normal members.");
			return;
		}
		if (campStudentService.registerForCamp(id))
			System.out.println("Registration successful.");
		else
			System.out.println("Camp ID does not exist.");
	}

	/**
	 * 
	 */
	private static void registerForCommittee() {
		Integer id = null;
		do {
			id = utils.InputParser.parseInInteger(sc,
					"Enter the camp ID to register as committee for. Enter 'C' to cancel. ", 0, Integer.MAX_VALUE,
					INPUT_MAX_ATTEMPTS, "C");
			
		} while (id == null);

		if (!campStudentService.existCamp(id)) {
			System.out.println("Camp does not exist.");
			return;
		}

		if (!campStudentService.isCampDateBeforeDeadline(id)) {
			System.out.println("Past deadline. Cannot register.");
			return;
		}

		if (!campStudentService.isCampNotClashWithExisting(id)) {
			System.out.println("Clashes with other camps. Cannot register.");
			return;
		}
		if (campStudentService.isPreviouslyRegistered(id)) {
			System.out.println("You previously deregistered from this camp. Not allowed to re-register.");
			return;
		}
		if (campStudentService.isAlreadyCCM(id)) {
			System.out.println("You are already CCM for another camp.");
			return;
		}
		if (!campStudentService.existVacancyCCM(id)) {
			System.out.println("There are no more vacancies for committee members.");
			return;
		}
		if (campStudentService.registerForCCM(id))
			System.out.println("Registration successful.");
		else
			System.out.println("Camp ID does not exist.");
	}

	/**
	 * 
	 */
	private static void deregisterCamp() {
		Integer id = null;
		do {
			id = utils.InputParser.parseInInteger(sc,
					"Enter the camp ID to deregister from. Enter 'C' to cancel. ", 0, Integer.MAX_VALUE,
					INPUT_MAX_ATTEMPTS, "C");
		} while (id == null);

		if (!campStudentService.existCamp(id)) {
			System.out.println("Camp does not exist.");
			return;
		}

		if (campStudentService.isPreviouslyRegistered(id)) {
			System.out.println("You previously deregistered from this camp. Not allowed to re-register.");
			return;
		}

		if (campStudentService.isAlreadyRegistered(id)) {
			if (campStudentService.isAlreadyCCM(id)) {
				System.out.println("CCMs may not deregister.");
				return;
			}
			if (campStudentService.deregisterFromCamp(id))
				System.out.println("Deregistration successful. You will not be eligible for re-registration.");
		} else
			System.out.println("You are not registered to this camp or does not exist.");
	}

	/**
	 * 
	 */
	private static void submitEnquiry() {
		String input = null;
		Integer id = null;
		do {
			id = utils.InputParser.parseInInteger(sc,
					"Enter the camp ID to enquire on. Enter 'C' to cancel. ", 0, Integer.MAX_VALUE,
					INPUT_MAX_ATTEMPTS, "C");
		} while (id == null);
		if (!campStudentService.existCamp(id)) {
			System.out.println("Camp does not exist.");
			return;
		}
		input = "";
		do {
			input = utils.InputParser.parseInString(sc,
					"Enter the enquiry. Enter 'C' to cancel. ", 
					INPUT_MAX_ATTEMPTS, "C");
		} while (input == null);
		if (enquiryService.isQueuedRequestForUser(id)) {
			System.out.println("You already have a pending enquiry!");
			return;
		}
		enquiryService.createNewRequest(input.strip(), id);
		System.out.println("Operation successful.");
	}

	/**
	 * 
	 */
	private static void viewEnquiry() {
		ArrayList<EnquiryRequest> req = enquiryService.getRequestByUser(AuthStore.getCurUser().getUserID());
		for (Request r : req) {
			RequestView.printReq(r);
		}
		if (req.size() == 0)
			System.out.println("You did not submit any enquiries. ");

	}

	/**
	 * 
	 */
	private static void editDeleteEnquiry() {
		String input = null;
		Integer id = null;
		Integer sel = null;
		do {
			sel = utils.InputParser.parseInInteger(sc,
					"Enter '0' to edit enquiries. Enter '1' to delete. Enter 'C' to cancel. ", 0, 1,
					INPUT_MAX_ATTEMPTS, "C");
		} while (sel == null);
		do {
			id = utils.InputParser.parseInInteger(sc,
					"Enter the camp ID of the request. Enter 'C' to cancel. ", 0, Integer.MAX_VALUE,
					INPUT_MAX_ATTEMPTS, "C");
		} while (id == null);
		if(sel == 0) //edit
		{
			String content;
			
			if (!campStudentService.existCamp(id)) {
				System.out.println("Camp does not exist.");
				return;
			}
			
			do {
				content = utils.InputParser.parseInString(sc,
						"Enter the new enquiry. Enter 'C' to cancel. ", 
						INPUT_MAX_ATTEMPTS, "C");
			} while (content == null);
			if (enquiryService.editRequest(id, content))
				System.out.println("Enquiry  successfully modified.");
			else
				System.out.println("No such enquiry or cannot modify.");
		}
		else //delete
		{
			if (!campStudentService.existCamp(id)) {
				System.out.println("Camp does not exist.");
				return;
			}
			if (enquiryService.deleteRequest(id))
				System.out.println("Enquiry successfully deleted.");
			else
				System.out.println("No such enquiry or cannot delete.");
		}

	}
}
