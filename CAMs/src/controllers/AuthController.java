package controllers;

import java.util.Scanner;

import services.AuthService;
import services.AuthStaffService;
import services.AuthStudentService;
import stores.AuthStore;

/**
 * The {@link AuthController} class handles user authentication processes for
 * the CAMs system. It allows users to log in and log out based on their roles.
 * The class provides a text-based interface for user interaction during the
 * authentication process.
 */
public class AuthController {

	// Private constructor to prevent instantiation of the class
	private AuthController() {
	}

	// Static variable to hold the AuthService implementation
	private static AuthService authService;

	// Regular expression pattern for user ID validation
	public static final String pattern = "^[a-zA-Z0-9]+$";

	/**
	 * Allows a user to log in based on their role (Student or Staff).
	 *
	 * @return true if the login is successful, false otherwise
	 */
	public static boolean login() {
		// Scanner for user input
		Scanner sc = new Scanner(System.in);

		boolean isOk = false; // Flag to check if login is successful
		int c = 0; // Variable to store user's choice

		String userID, password; // Variables to store user ID and password

		// Loop until the user successfully logs in or chooses to exit
		do {
			while (true) {
				// Display login domain options
				System.out.println("\033[1mSelect login domain:\033[0m");
				System.out.println("\033[1;36m1. Student");
				System.out.println("2. Staff ");
				System.out.println("\033[1;31m9. Exit\033[0m");

				// Get user input
				String input = sc.nextLine();

				// Validate if the input is an integer
				if (input.matches("[0-9]+")) {
					c = Integer.parseInt(input);

					// Validate user's choice
					if (c < 1 || (c > 2 && c != 9)) {
						System.out.println("Invalid input.!");
					} else {
						break;
					}
				} else {
					System.out.println("Invalid input. Please enter an integer.\n");
				}
			}

			// Create the appropriate AuthService based on the user's choice
			switch (c) {
			case 9:
				System.out.println("Shutting down CAMs...");
				return false;
			case 1:
				authService = new AuthStudentService();
				break;
			case 2:
				authService = new AuthStaffService();
				break;
			}

			// Get user ID and password
			System.out.print("User ID (Network ID): ");
			userID = sc.nextLine();

			System.out.print("Password: ");
			password = sc.nextLine();

			// Attempt to log in using the AuthService implementation
			isOk = authService.login(userID, password);

			// Display appropriate messages for unsuccessful logins
			if (!isOk && !userID.matches(pattern)) {
				System.out.println(
						"\u001b[1;31m🚫 User ID should not contain email domain or non-ASCII characters. 🚫\u001b[0m \n");
			} else if (!isOk) {
				System.out.println("\u001b[1;31m🚫 Invalid credentials. Try again. 🚫\u001b[0m \n");
			}
		} while (!isOk);

		// Display a successful login message
		System.out.println("Log in successful, " + AuthStore.getCurUser().getUserID() + "\n");

		return true;
	}

	/**
	 * Logs the current user out.
	 */
	public static void logout() {
		authService.logout();
	}
}
