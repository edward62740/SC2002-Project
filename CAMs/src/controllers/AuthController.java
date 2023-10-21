package controllers;

import java.util.Scanner;

import services.AuthService;
import services.AuthStudentService;
import stores.AuthStore;

public class AuthController {
	private AuthController() {
	};

	private static AuthService authService;
	public static final String pattern = "^[a-zA-Z0-9]+$";
	public static void login() {
		Scanner sc = new Scanner(System.in);
		boolean isOk = false;
		int c = 0;
		String userID, password;
		do {

			while (true) {
				
				System.out.println("Select login domain:");
				System.out.println("1. Student");
				System.out.println("2. Staff ");

				String input = sc.nextLine();

				if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
					c = Integer.parseInt(input);

					if (c < 1 || c > 2) {
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
				return;
			case 1:
				authService = new AuthStudentService();
				break;
			case 2:
				System.out.println("ERROR NOT IMPLEMENTED");
				break;
			}

			

			System.out.print("User ID (Network ID): ");
			userID = sc.nextLine();

			System.out.print("Password: ");
			password = sc.nextLine();

			isOk = authService.login(userID, password);
			if(!isOk && !userID.matches(pattern))
			{
				System.out.println("🚫 User ID should not contain email domain or non-ASCII characters. 🚫 \n");
			}
			else if (!isOk) {
				System.out.println("🚫 Invalid credentials. Try again. 🚫 \n");
			}
		} while (!isOk);
		System.out.println("Log in successful, " + AuthStore.getCurUser().getUserID() + "\n");
		
	}
	public static void logout() {
		authService.logout();
	}
	

}
