package controllers;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;

import services.AuthStudentService;
import services.CampStaffService;
import services.CampStudentService;
import services.EnquiryRequestService;
import services.SuggestionRequestService;

import stores.AuthStore;
import utils.InputParser;
import views.CampView;
import views.RequestView;
import enums.RequestStatus;
import enums.UserGroup;
import interfaces.ICampStudentService;
import models.Camp;
import models.EnquiryRequest;
import models.SuggestionRequest;
import models.Request;
import models.Student;
import models.Staff;
import enums.UserGroup;

public class StaffController extends UserController {

	private static final int INPUT_MAX_ATTEMPTS = 1;
	private static ICampStudentService campStudentService = new CampStudentService();
	private static EnquiryRequestService enquiryService = new EnquiryRequestService();
	private static SuggestionRequestService suggestionService = new SuggestionRequestService();

	private static CampStaffService campStaffService = new CampStaffService();

	static Scanner sc = new Scanner(System.in);

	public static boolean run() {
		Integer c = 0;

		do {
			System.out.println("\033[1;35m--- STAFF MENU ---\033[0m");
			if (AuthStore.getCurUser().isDefaultPassword())
			    System.out.println("\033[1;31mWarning! You are using the default password.\033[0m");
			System.out.println("\033[1mSelect option:\033[0m");
			System.out.println("\033[1;36m0. Exit");
			System.out.println("1. Create Camp");
			System.out.println("2. Edit Camp");
			System.out.println("3. Delete Camp");
			System.out.println("4. View All Camps");
			System.out.println("5. View Owned Camps");
			System.out.println("6. View/Reply Suggestions");
			System.out.println("7. View/Reply Enquiry");
			System.out.println("8. Change password");
			System.out.println("9. Generate Report\033[0m");
			System.out.println("\033[1;35m----------------------\033[0m");

			c = utils.InputParser.parseInInteger(sc, "", 0, 7, INPUT_MAX_ATTEMPTS, "C");
		} while (c == null);

		switch (c) {
		case 0:
			System.out.println("Shutting down CAMs...");
			return true;
		case 1:
			createCamp();
			break;
		case 2:
			editCamp();
			break;
		case 3:
			deleteCamp();
			break;
		case 4:
			viewOpenCamps();
			break;
		case 5:
			viewOwnedCamps();
			break;
		case 6:
			viewReplySuggestions();
			break;
		case 7:
			viewReplyEnquiry();
			break;
		case 8:
			updatePassword();
		case 9:
			System.out.println("Placeholder to use CSV service");
			break;

		}
		return false;

	}

	public static void createCamp() {

		String c_name = null, c_location = null, c_staff = null, c_description = null;
		Integer c_ccmSlot = null, c_totalSlots = null;
		String input = null;
		UserGroup c_userGroup = null;
		LocalDateTime c_closingDate = null;
		SimpleEntry<LocalDateTime, LocalDateTime> c_dates = null;

		do {
			c_name = utils.InputParser.parseInString(sc, "Enter the camp name", 0, "C");

		} while (c_name == null);

		do {
			System.out.println("Enter user group. Available options: ");
			for (UserGroup u : UserGroup.values())
				System.out.print(u + ", ");
			input = sc.nextLine();
			for (UserGroup u : UserGroup.values())
				if (input.strip().equals(u.toString())) {
					c_userGroup = u;
				}
			if (c_userGroup == null)
				System.out.println("Invalid user group. ");
		} while (c_userGroup == null);

		//System.out.println("Enter location: ");
		c_location = utils.InputParser.parseInString(sc, "Enter location", 0, "C");

		do {
			c_ccmSlot = utils.InputParser.parseInInteger(sc, "Set max committee", 0, Integer.MAX_VALUE,
					INPUT_MAX_ATTEMPTS, "C");

		} while (c_ccmSlot == null);

		do {
			c_totalSlots = utils.InputParser.parseInInteger(sc, "Set max slots", 0, Integer.MAX_VALUE,
					INPUT_MAX_ATTEMPTS, "C");

		} while (c_totalSlots == null);

		c_staff = AuthStore.getCurUser().getUserID();

		do {
			c_description = utils.InputParser.parseInString(sc, "Enter the camp description", 0, "C");

		} while (c_description == null);


		

		do {
			c_closingDate = utils.InputParser.parseInLocalDateTime(sc,
					"Please enter a valid closing date and time in the format yyyy-MM-dd HH:mm.", 0, "C");
		} while (c_closingDate == null);
		// set new description
	
		System.out.println("Please enter a valid date and time in the format yyyy-MM-dd HH:mm");
		LocalDateTime dt1 = null;
		LocalDateTime dt2 = null;
		do {
			dt1 = utils.InputParser.parseInLocalDateTime(sc, "Starting date: ", 0, "C");
		} while (dt1 == null);
		do {
			dt2 = utils.InputParser.parseInLocalDateTime(sc, "Ending date: ", 0, "C");
		} while (dt2 == null);

		if (dt2.isBefore(dt1)) {
			System.out.println("Starting date must be BEFORE ending date");
			return;
		}

		c_dates = new SimpleEntry<>(dt1, dt2);
		
		campStaffService.createACamp(c_name, c_userGroup, c_location, c_totalSlots, c_ccmSlot, c_staff, c_description, c_closingDate, c_dates);
		System.out.println("Camp succesfully created!");
	}

	public static void editCamp() {
		Integer id = null;
		do {
			id = utils.InputParser.parseInInteger(sc, "Enter the camp ID to edit. Enter 'C' to cancel. ", 0,
					Integer.MAX_VALUE, INPUT_MAX_ATTEMPTS, "C");
		} while (id == null);

		if (!campStaffService.isCampOwned(id)) {
			System.out.println("You do not own this camp");
			return;
		}
		Integer choice = null;
		do {
			System.out.println("Select option to edit:");
			System.out.println("1. Camp name");
			System.out.println("2. Camp location");
			System.out.println("3. Camp description");
			System.out.println("4. Camp (registration) closing date");
			System.out.println("5. Camp dates");
			
			choice = utils.InputParser.parseInInteger(sc, "", 0, 5, INPUT_MAX_ATTEMPTS, "C");
		} while (choice == null);
		if (campStaffService.editACamp(id, choice)) {
			System.out.println("Camp successfully edited.");
		}
		else {
			System.out.println("Camp failed to be edited");

		}
	}

	public static void deleteCamp() {
		Integer id = null;
		do {
			id = utils.InputParser.parseInInteger(sc, "Enter the camp ID to delete. Enter 'C' to cancel. ", 0,
					Integer.MAX_VALUE, INPUT_MAX_ATTEMPTS, "C");
		} while (id == null);

		if (!campStaffService.isCampOwned(id)) {
			System.out.println("You do not own this camp");
			return;
		} else if (campStaffService.deleteACamp(id))
			System.out.println("Camp successfully deleted.");
		else
			System.out.println("Operation failed. ");
	}
	
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
	
	public static void viewOwnedCamps() {
		ArrayList<Camp> camps = campStaffService.getOwnedCamps();
		for (Camp i : camps) {
			CampView.printCamp(i, AuthStore.getCurUser());
		}
		if (camps.size() == 0)
			System.out.println("There are no available camps. Modify your search or try again later. ");

	}

	private static void viewReplySuggestions() {
		Integer sel = null;
		String input = null;
		do {
			sel = utils.InputParser.parseInInteger(sc,
					"Enter '0' to view suggestions. Enter '1' to respond to a suggestion. Enter 'C' to cancel. ", 0, 1,
					INPUT_MAX_ATTEMPTS, "C");
		} while (sel == null);

		ArrayList<SuggestionRequest> req = ((Staff) AuthStore.getCurUser()).getOwnedCamps().stream()
		        .map(suggestionService::getRequestByCamp)
		        .flatMap(List::stream)
		        .collect(Collectors.toCollection(ArrayList::new));
		for (int i = 0; i < req.size(); i++) {
			System.out.print("Index: " + i);
			RequestView.printReq(req.get(i));
		}

		if (sel == 0) {
			if (req.size() == 0)
				System.out.println("There are no suggestions on the camp you are staff of.");
			return;
		}

		else if (sel == 1) {
			sel = null;
			do {
				sel = utils.InputParser.parseInInteger(sc,
						"Enter the index of suggestion to respond to. Enter 'C' to cancel. ", 0, 1, INPUT_MAX_ATTEMPTS,
						"C");
			} while (sel == null);

			if (sel >= 0 && sel < req.size()) {

				if (!(req.get(sel).getStatus() == RequestStatus.PENDING)) {
					System.out.println("Error. The suggestion is already responded to.");
					return;
				}
				input = null;
				do {
					input = utils.InputParser.parseInString(sc, "Enter Y to approve and N to reject. ", INPUT_MAX_ATTEMPTS,
							"C");
					input.strip();
				} while (!input.equals("Y") && !input.equals("N"));
				boolean approve = false;
				if(input.equals("Y")) approve = true;
				if (suggestionService.handleRequest(req.get(sel), approve))
					System.out.println("Response successful");
				else
					System.out.println("Unknown error");
			} else
				System.out.println("Invalid index. ");

		}
	}

	private static void viewReplyEnquiry() {
		Integer sel = null;
		String input = null;
		do {
			sel = utils.InputParser.parseInInteger(sc,
					"Enter '0' to view enquiries. Enter '1' to respond to an enquiry. Enter 'C' to cancel. ", 0, 1,
					INPUT_MAX_ATTEMPTS, "C");
		} while (sel == null);

		ArrayList<EnquiryRequest> req = ((Staff) AuthStore.getCurUser()).getOwnedCamps().stream()
		        .map(enquiryService::getRequestByCamp)
		        .flatMap(List::stream)
		        .collect(Collectors.toCollection(ArrayList::new));
		for (int i = 0; i < req.size(); i++) {
			System.out.print("Index: " + i);
			RequestView.printReq(req.get(i));
		}

		if (sel == 0) {
			if (req.size() == 0)
				System.out.println("There are no enquiries on the camp you are staff of.");
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
					utils.InputParser.parseInString(sc, "Enter the response. Enter 'C' to cancel.", INPUT_MAX_ATTEMPTS,
							"C");
				} while (input == null);
				if (enquiryService.handleRequest(req.get(sel), input))
					System.out.println("Response successful");
				else
					System.out.println("Unknown error");
			} else
				System.out.println("Invalid index. ");

		}
	}

	public static void generateReport() {

	}

}
