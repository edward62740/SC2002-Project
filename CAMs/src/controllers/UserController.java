package controllers;

import java.util.Scanner;

import stores.AuthStore;

/**
 * The {@link UserController} class provides a way to change password. It is
 * also the base class for the specific user controllers.
 */
public class UserController {

	
	/**
	 * Instance of {@link Scanner}. Used to read input from user..
	 */
	protected static final Scanner sc = new Scanner(System.in);

	/**
	 * @return true if success false otherwise.
	 */
	protected static boolean updatePassword() {
		String input1 = null, input2 = null;
		do {
			System.out.println("Enter the new password");
			input1 = sc.nextLine();
		} while (input1 == null);
		do {
			System.out.println("Enter the new password again");
			input2 = sc.nextLine();
		} while (input2 == null);

		if (!input1.equals(input2)) {
			System.out.println("Error! Passwords do not match. ");
			return false;
		}
		if (input1.equals(AuthStore.getCurUser().getPassword())) {
			System.out.println("Error! New password must be different from the previous one.");
			return false;
		}
		AuthStore.getCurUser().setPassword(input1);
		AuthStore.getCurUser().setDefaultPassword(false);
		System.out.println("Password changed successfully.");
		return true;
	}

}
