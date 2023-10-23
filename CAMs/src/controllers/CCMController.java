package controllers;

import java.util.ArrayList;
import java.util.Scanner;

import services.AuthStudentService;
import services.CampStudentService;
import services.RequestCCMService;
import services.RequestStudentService;
import stores.AuthStore;
import views.CampView;
import views.RequestView;
import enums.UserGroup;
import models.Camp;
import models.Request;

public class CCMController extends UserController {

	private static CampStudentService campStudentService = new CampStudentService();
	private static RequestCCMService requestCCMService = new RequestCCMService();

	public static boolean run() {
		int c = 0;

		while (true) {
			System.out.println(" --- CAMP COMMITTEE MENU --- ");
			if (AuthStore.getCurUser().isDefaultPassword())
				System.out.println("Warning! You are using the default password. ");
			System.out.println("Select option:");
			System.out.println("1. View open camps");
			System.out.println("2. View registered camps");
			System.out.println("3. Register for camp (as participant)");
			System.out.println("4. Deregister from camp");
			System.out.println("5. Change password");
			System.out.println("6. View/edit/delete suggestions");
			System.out.println("7. View/reply student enquiries");
			System.out.println("8. New suggestion");
			System.out.println("9. Generate report");
			System.out.println("0. Exit");
			System.out.println(" -------------------- ");
			String input = sc.nextLine();

			if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
				c = Integer.parseInt(input);

				if (c < 0 || c > 9) {
					System.out.println("Invalid input.!");
				} else {
					break;
				}
			} else { // If the input is not an integer, prompt the user to enter again
				System.out.println("Invalid input. Please enter an integer.\n");
			}

		}

		switch (c) {
		case 9:
			System.out.println("Shutting down CAMs...");
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
			viewReplyEnquiry();
			System.out.println("ERROR NOT IMPLEMENTED");
			break;
		case 8:
			System.out.println("ERROR NOT IMPLEMENTED");
			break;
		}
		return false;
	}

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
			CampView.printCamp(i);
		}
		if (camps.size() == 0)
			System.out.println("There are no available camps. Modify your search or try again later. ");
	}

	private static void viewRegisteredCamps() {
		ArrayList<Camp> camps = campStudentService.getRegisteredCamps();
		for (Camp i : camps) {
			CampView.printCamp(i);
			CampView.printPosition(i, AuthStore.getCurUser());
		}

		if (camps.size() == 0)
			System.out.println("You are not registered for any camps. ");
	}

	private static void registerForCamp() {
		Integer id = null;
		String input = "";
		do {
			System.out.println("Enter the camp ID to register for. Enter 'C' to cancel");
			input = sc.nextLine();
			if (input.strip().equals("C"))
				return;
			if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
				id = Integer.parseInt(input);
			} else { // If the input is not an integer, prompt the user to enter again
				System.out.println("Invalid input. Please enter an integer.\n");
			}
		} while (id == null);
		if (campStudentService.isPreviouslyRegistered(id)) {
			System.out.println("You previously deregistered from this camp. Not allowed to re-register.");
			return;
		}
		if (campStudentService.isAlreadyRegistered(id)) {
			System.out.println("You are already registered. Not allowed to re-register.");
			return;
		}
		if (campStudentService.registerForCamp(id))
			System.out.println("Registration successful.");
		else
			System.out.println("Camp ID does not exist.");
	}

	private static void registerForCommittee() {
		Integer id = null;
		String input = "";
		do {
			System.out.println("Enter the camp ID to register as committee for. Enter 'C' to cancel");
			input = sc.nextLine();
			if (input.strip().equals("C"))
				return;
			if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
				id = Integer.parseInt(input);
			} else { // If the input is not an integer, prompt the user to enter again
				System.out.println("Invalid input. Please enter an integer.\n");
			}
		} while (id == null);
		if (campStudentService.isPreviouslyRegistered(id)) {
			System.out.println("You previously deregistered from this camp. Not allowed to re-register.");
			return;
		}
		if (campStudentService.isAlreadyCCM(id)) {
			System.out.println("You are already CCM for another camp.");
			return;
		}
		if (campStudentService.registerForCCM(id))
			System.out.println("Registration successful.");
		else
			System.out.println("Camp ID does not exist.");
	}

	private static void deregisterCamp() {
		Integer id = null;
		String input = "";
		do {
			System.out.println("Enter the camp ID to deregister from. Enter 'C' to cancel");
			input = sc.nextLine();
			if (input.strip().equals("C"))
				return;
			if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
				id = Integer.parseInt(input);
			} else { // If the input is not an integer, prompt the user to enter again
				System.out.println("Invalid input. Please enter an integer.\n");
			}
		} while (id == null);
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
			System.out.println("You are not registered from this camp or does not exist.");
	}
	
	private static void viewReplyEnquiry()
	{
		Integer sel = null;
		String input = null;
		do {
			System.out.println("Enter '0' to view enquiries. Enter '1' to respond to an enquiry. Enter 'C' to cancel");
			input = sc.nextLine();
			if (input.strip().equals("C"))
				return;
			if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
				sel = Integer.parseInt(input);
			} else { // If the input is not an integer, prompt the user to enter again
				System.out.println("Invalid input. Please enter an integer.\n");
			}
		} while (sel == null);
		
		ArrayList<Request> req = requestCCMService.getRequestsAsCCM();
		for(int i=0; i<req.size(); i++)
		{
			System.out.print("Index: " + i);
			RequestView.printReq(req.get(i));
		}
		
		if(sel == 0)
		{
			if(req.size() == 0) System.out.println("There are no enquiries on the camp you are committee of.");
			return;
		}
		else if(sel == 1)
		{
			sel = null;
			do {
				System.out.println("Enter the index of enquiry to respond to. Enter 'C' to cancel");
				input = sc.nextLine();
				if (input.strip().equals("C"))
					return;
				if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
					sel = Integer.parseInt(input);
				} else { // If the input is not an integer, prompt the user to enter again
					System.out.println("Invalid input. Please enter an integer.\n");
				}
			} while (sel == null);
			
			if(sel >= 0 && sel < req.size())
			{
			
				if(!requestCCMService.isPendingResponseRequest(req.get(sel)))
				{
					System.out.println("Error. The enquiry is already responded to.");
					return;
				}
				input = "";
				do {
					System.out.println("Enter the response. Enter 'C' to cancel");
					input = sc.nextLine();
					if (input.strip().equals("C"))
						return;
				} while (input == null);
				if(requestCCMService.respondToRequest(req.get(sel), input)) System.out.println("Response successful");
				else System.out.println("Unknown error");
			} 
			else System.out.println("Invalid index. ");
			
		}
	}
}
