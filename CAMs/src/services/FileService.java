package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import enums.UserGroup;
import models.Student;
import models.Staff;
import stores.DataStore;

public class FileService {

	public static void readUserFromCsv() throws FileNotFoundException {

		String workspacePath = System.getProperty("user.dir");

		File file = new File(workspacePath + "/storage/student_list.csv");
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// Split the line into columns using comma as the delimiter
			String[] columns = line.split(",");

			// Extract id and fac from the columns
			String id = extractId(columns[1]);
			String fac = columns[2].trim(); // Assuming fac is in the third column

			UserGroup userGroup = null;
			// Create a new Student object
			for (UserGroup u : UserGroup.values())
				if (fac.strip().equals(u.toString())) {
					userGroup = u;
				}
			if (userGroup == null) {
				System.out.println("Failed: " + id);
				return;
			}
			Student student = new Student(id, userGroup);
			DataStore.getStudents().put(id, student);
			System.out.println("Loaded user: " + id);
		}

		file = new File(workspacePath + "/storage/staff_list.csv");
		scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// Split the line into columns using comma as the delimiter
			String[] columns = line.split(",");

			// Extract id and fac from the columns
			String id = extractId(columns[1]);
			String fac = columns[2].trim(); // Assuming fac is in the third column

			UserGroup userGroup = null;
			// Create a new Student object
			for (UserGroup u : UserGroup.values())
				if (fac.strip().equals(u.toString())) {
					userGroup = u;
				}
			if (userGroup == null) {
				System.out.println("Failed: " + id);
				return;
			}
			Staff staff = new Staff(id, userGroup);
			DataStore.getStaff().put(id, staff);
			System.out.println("Loaded user: " + id);
		}

		// Close the scanner
		scanner.close();
	}

	private static String extractId(String email) {
		// Assuming the id is the substring before the "@" symbol in the email
		int atIndex = email.indexOf("@");
		if (atIndex != -1) {
			return email.substring(0, atIndex);
		}
		return email;
	}

}
