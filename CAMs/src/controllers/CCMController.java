package controllers;

import java.util.ArrayList;
import java.util.Scanner;

import services.AuthStudentService;
import services.CampStaffService;
import services.CampStudentService;
import services.EnquiryRequestService;
import services.FileService;
import services.SuggestionRequestService;
import stores.AuthStore;
import stores.DataStore;
import utils.InputParser;
import views.CampView;
import views.RequestView;
import enums.RequestStatus;
import enums.UserGroup;
import interfaces.ICampStaffService;
import interfaces.ICampStudentService;
import interfaces.IRequestService;
import models.Camp;
import models.EnquiryRequest;
import models.Request;
import models.Student;
import models.SuggestionRequest;

/**
 * The {@link CCMController} class handles user interactions for the Camp
 * Committee Members (which are Students with enum set to CCM). It is
 * responsible for application level tasks. It utilizes services
 * {@link ICampStudentService} and
 * {@link SuggestionRequestService},{@link EnquiryRequestService} to perform
 * lower level tasks for camp and requests respectively. It extends
 * {@link UserController}.
 */
public class CCMController extends UserController {

	/**
	 * Max attempts for {@link InputParser}.
	 */
	private static final int INPUT_MAX_ATTEMPTS = 1;
	/**
	 * Instance of {@link IRequestService}. Provides lower-level logic for
	 * handling enquires.
	 */
	private static IRequestService enquiryService = new EnquiryRequestService();
	/**
	 * Instance of {@link IRequestService}. Provides lower-level logic for
	 * handling suggestions.
	 */
	private static IRequestService suggestionService = new SuggestionRequestService();

	/**
	 * Instance of {@link ICampStudentService}. Provides lower-level logic for
	 * handling camp-related functionality for students.
	 */
	private static ICampStudentService campStudentService = new CampStudentService();

	/**
	 * Runs the controller to take in user input and act accordingly.
	 * 
	 * @return true to signal to calling function to exit, false otherwise.
	 */
	public static boolean run() {
		Integer c = 0;

		do {
			System.out.println("\033[1;34m--- CAMP COMMITTEE MENU ---\033[0m");
			if (AuthStore.getCurUser().isDefaultPassword())
				System.out.println("\033[1;31mWarning! You are using the default password.\033[0m");
			System.out.println("\033[1mSelect option:\033[0m");
			System.out.println("\033[1;36m1. View open camps");
			System.out.println("2. View registered camps");
			System.out.println("3. Register for camp (as participant)");
			System.out.println("4. Deregister from camp");
			System.out.println("5. Change password");
			System.out.println("6. View/reply student enquiries");
			System.out.println("7. View suggestions");
			System.out.println("8. New suggestion");
			System.out.println("9. Edit/Delete suggestion");
			System.out.println("10. Generate report\033[0m");
			System.out.println("\033[1;34m----------------------\033[0m");

			c = utils.InputParser.parseInInteger(sc, "", 0, 10, INPUT_MAX_ATTEMPTS, "C");
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
			deregisterCamp();
			break;
		case 5:
			updatePassword();
			break;
		case 6:
			viewReplyEnquiry();
			break;
		case 7:
			viewSuggestion();
			break;
		case 8:
			submitSuggestion();
			break;
		case 9:
			editDeleteSuggestion();
			break;
		case 10:
			generateReport();
			break;
		}
		return false;

	}

	/**
	 * Prints out the list of available camps based on the selected UserGroup
	 */
	private static void viewOpenCamps() {
		String input = "";
		UserGroup userGroup = null;
		boolean invisible = false;
		boolean boolOk = true;
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
		/*
		 * do { System.out.println("Show invisible? (Y/N)"); input =
		 * sc.nextLine().strip(); if(input.equals("Y")) invisible = true; else
		 * if(input.equals("N")) invisible = false; else boolOk = false; } while
		 * (!boolOk);
		 */

		ArrayList<Camp> camps = campStudentService.getCamps(userGroup, false);
		for (Camp i : camps) {
			CampView.printCamp(i, AuthStore.getCurUser());
		}
		if (camps.size() == 0)
			System.out.println("There are no available camps. Modify your search or try again later. ");
	}

	/**
	 * Prints out a list of camps for which the user currently active in
	 * {@link AuthStore} is registered to.
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
	 * Registers the current user in {@link AuthStore} to a camp based on ID
	 * selection.
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
	 * Deregisters the current user in {@link AuthStore} to a camp based on ID
	 * selection.
	 */
	private static void deregisterCamp() {
		Integer id = null;
		do {
			id = utils.InputParser.parseInInteger(sc, "Enter the camp ID to deregister from. Enter 'C' to cancel. ", 0,
					Integer.MAX_VALUE, INPUT_MAX_ATTEMPTS, "C");
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
	 * Provides functionality to list or reply to enquires by students on the camp
	 * the user stored in {@link AuthStore} is committee of.
	 */
	private static void viewReplyEnquiry() {
		Integer sel = null;
		String input = null;
		do {
			sel = utils.InputParser.parseInInteger(sc,
					"Enter '0' to view enquiries. Enter '1' to respond to an enquiry. Enter 'C' to cancel. ", 0, 1,
					INPUT_MAX_ATTEMPTS, "C");
		} while (sel == null);

		ArrayList<? extends Request> req = enquiryService
				.getRequestByCamp(((Student) AuthStore.getCurUser()).getCommittee());
		for (int i = 0; i < req.size(); i++) {
			System.out.print("Index: " + i);
			RequestView.printReq(req.get(i));
		}

		if (sel == 0) {
			if (req.size() == 0)
				System.out.println("There are no enquiries on the camp you are committee of.");
			return;
		}

		else if (sel == 1) {
			sel = null;
			do {
				sel = utils.InputParser.parseInInteger(sc,
						"Enter the index of enquiry to respond to. Enter 'C' to cancel. ", 0, 1, INPUT_MAX_ATTEMPTS,
						"C");
			} while (sel == null);

			if (sel >= 0 && sel < req.size()) {

				if (!(req.get(sel).getStatus() == RequestStatus.PENDING)) {
					System.out.println("Error. The enquiry is already responded to.");
					return;
				}
				input = "";
				do {
					input = utils.InputParser.parseInString(sc, "Enter the response. Enter 'C' to cancel.", INPUT_MAX_ATTEMPTS,
							"C");
				} while (input == null);
				if (enquiryService.handleRequest(req.get(sel), true, input))
					System.out.println("Response successful");
				else
					System.out.println("Unknown error");
			} else
				System.out.println("Invalid index. ");

		}
	}

	/**
	 * Prints a list of suggestions that the user stored in {@link AuthStore} has
	 * submitted as a committee member.
	 */
	private static void viewSuggestion() {
		ArrayList<? extends Request> req = suggestionService.getRequestByUser(AuthStore.getCurUser().getUserID());
		for (Request r : req) {
			RequestView.printReq(r);
		}
		if (req.size() == 0)
			System.out.println("You did not submit any suggestions. ");
	}

	/**
	 * Submits a new suggestion to the camp that the user stored in
	 * {@link AuthStore} is committee of.
	 */
	private static void submitSuggestion() {
		String input = null;
		Integer id = ((Student) AuthStore.getCurUser()).getCommittee();
		if (!campStudentService.existCamp(id)) {
			System.out.println("Internal error. ");
			return;
		}

		do {
			input = utils.InputParser.parseInString(sc, "Enter the suggestion for your camp . Enter 'C' to cancel. ",
					INPUT_MAX_ATTEMPTS, "C");
		} while (input == null);
		if (suggestionService.isQueuedRequestForUser(id)) {
			System.out.println("You already have a pending suggestion!");
			return;
		}
		suggestionService.createNewRequest(input.strip(), id);
		System.out.println("Operation successful.");
	}

	/**
	 * Provides functionality to edit or delete the suggestion to the camp the user
	 * stored in {@link AuthStore} is committee of.
	 */
	private static void editDeleteSuggestion() {
		Integer id = null;
		Integer sel = null;
		do {
			sel = utils.InputParser.parseInInteger(sc,
					"Enter '0' to edit suggestions. Enter '1' to delete. Enter 'C' to cancel. ", 0, 1,
					INPUT_MAX_ATTEMPTS, "C");
		} while (sel == null);
		id = ((Student) AuthStore.getCurUser()).getCommittee();
		if (!campStudentService.existCamp(id)) {
			System.out.println("Internal error. ");
			return;
		}
		if (sel == 0) // edit
		{
			String content;

			if (!campStudentService.existCamp(id)) {
				System.out.println("Camp does not exist.");
				return;
			}

			do {
				content = utils.InputParser.parseInString(sc, "Enter the new enquiry. Enter 'C' to cancel. ",
						INPUT_MAX_ATTEMPTS, "C");
			} while (content == null);
			if (suggestionService.editRequest(id, content))
				System.out.println("Suggestion successfully modified.");
			else
				System.out.println("No such suggestion or cannot modify.");
		} else // delete
		{
			if (!campStudentService.existCamp(id)) {
				System.out.println("Camp does not exist.");
				return;
			}
			if (suggestionService.deleteRequest(id))
				System.out.println("Suggestion successfully deleted.");
			else
				System.out.println("No such suggestion or cannot delete.");
		}
	}

	/**
	 * Generates a CSV file containing the info of the camps that the user stored in
	 * {@link AuthStore} is committee of, with optional filters.
	 */
	private static void generateReport() {
		Integer camp = ((Student) AuthStore.getCurUser()).getCommittee();
		ArrayList<Integer> camps = new ArrayList<>();
		camps.add(camp);
		FileService.generateCsvFromCamp(sc, camps);
	}
}
