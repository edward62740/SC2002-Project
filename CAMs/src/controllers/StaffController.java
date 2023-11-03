package controllers;

import java.util.ArrayList;
import java.util.Scanner;

import services.AuthStudentService;
import services.CampStaffService;
import services.EnquiryRequestService;
import services.SuggestionRequestService;

import stores.AuthStore;
import views.CampView;
import views.RequestView;
import enums.RequestStatus;
import enums.UserGroup;
import models.Camp;
import models.EnquiryRequest;
import models.Request;
import models.Student;
import models.Staff;
import enums.UserGroup;

public class StaffController extends UserController{
	
	private static EnquiryRequestService enquiryService = new EnquiryRequestService();
	private static SuggestionRequestService suggestionService = new SuggestionRequestService();
	
	Scanner sc = new Scanner(System.in);
	public static boolean run() {
		Integer c = 0;


		do {
			System.out.println(" --- STAFF MENU --- ");
			if (AuthStore.getCurUser().isDefaultPassword())
				System.out.println("Warning! You are using the default password. ");
			System.out.println("Select option:");
			System.out.println("0. Exit");
			System.out.println("1. Create Camp");
			System.out.println("2. Edit Camp");
			System.out.println("3. Delete Camp");
			System.out.println("4. View Owned Camps");
			System.out.println("5. View/Reply Suggestions");
			System.out.println("6. View/Reply Enquiry");
			System.out.println("7. Generate Report");
			System.out.println(" -------------------- ");

			c = utils.InputParser.parseInInteger(sc, "", 0, 9, INPUT_MAX_ATTEMPTS, "C");
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
			viewOwnedCamps();
			break;
		case 5:
			viewReplySuggestions();
			break;			
		case 6:
			viewReplyEnquiry();
			break;
		case 7:
			System.out.println("Placeholder to use CSV service");
			break;

		}
		return false;

	}
	public static void createCamp() {
		

		System.out.println("Enter camp ID: ");
		int c_id = sc.nextInt();

		System.out.println("Enter camp name: ");
		String c_name = sc.next();
		
		System.out.println("Enter user group: ");
		UserGroup c_userGroup = sc.next();
		
		System.out.println("Enter location: ");
		String c_location = sc.next();
		
		System.out.println("Enter total slots: ");
		int c_totalSlots = sc.nextInt();
		
		System.out.println("Enter CCM slots: ");
		int c_ccmSlot = sc.next();

		
		String c_staff = AuthStore.getCurUser().getUserID(); //Is this how i get the staff name?
		
		System.out.println("Enter camp description: ");
		String c_description = sc.next();
		
		CampStaffService.createACamp(c_id, c_name, c_userGroup, c_location, c_totalSlots, c_ccmSlot, c_staff, c_description);

		
		
	}
	
	public static void editCamp() {
		
	}
	public static void deleteCamp() {
		Integer id = null;
		do {
			id = utils.InputParser.parseInInteger(sc,
					"Enter the camp ID to delete. Enter 'C' to cancel. ", 0, Integer.MAX_VALUE,
					INPUT_MAX_ATTEMPTS, "C");
		} while (id == null);

		if (!campStaffService.isCampOwned(id)) {
			System.out.println("You do not own this camp");
			return;
		}
		else
			CampStaffService.deleteACamp(id);
			System.out.println("Camp successfully deleted.");
	}
	public static void viewOwnedCamps() {
		ArrayList<Camp> camps = campStaffService.getOwnedCamps(userGroup, false);
		for (Camp i : camps) {
			CampView.printCamp(i);
		}
		if (camps.size() == 0)
			System.out.println("There are no available camps. Modify your search or try again later. ");
		
	}
	
	private static void viewReplySuggestions() {
		Integer sel = null;
		String input = null;
		do {
			sel = utils.InputParser.parseInInteger(sc,
					"Enter '0' to view enquiries. Enter '1' to respond to an enquiry. Enter 'C' to cancel. ", 0, 1,
					INPUT_MAX_ATTEMPTS, "C");
		} while (sel == null);

		ArrayList<SuggestionRequest> req = suggestionService
				.getRequestByCamp(((Student) AuthStore.getCurUser()).getCommittee());
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
				input = "";
				do {
					utils.InputParser.parseInString(sc, "Enter the response. Enter 'C' to cancel.", INPUT_MAX_ATTEMPTS,
							"C");
				} while (input == null);
				if (suggestionService.handleRequest(req.get(sel), input))
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

		ArrayList<EnquiryRequest> req = enquiryService
				.getRequestByCamp(((Student) AuthStore.getCurUser()).getCommittee());
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
