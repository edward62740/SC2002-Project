package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;

import enums.UserGroup;
import models.Student;
import models.Camp;
import models.Staff;
import stores.DataStore;
import utils.InputParser;

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
	
	public static void generateCsvFromCamp(Scanner sc, ArrayList<Integer> camps)
	{
		ArrayList<String> col = generateCsvHeader();
		System.out.println();
		Integer in = null;
		do {
		    int index = 0;
		    System.out.println("Columns to be printed: ");
		    for (String e : col) {
		        System.out.println(index++ + ": " + e);
		    }
		    in = InputParser.parseInInteger(sc, "Enter index to remove inclusion or -1 to continue generation.", -1, col.size(), 0, null);
		    if(in != null && in > -1 && in < col.size()) col.remove(in.intValue());
		} while (in == null || in != -1);
	
		boolean headers = true;
		// Specify the file path where you want to save the serialized object
		String filePath = System.getProperty("user.dir") + "/storage/out.csv";

		try (FileWriter csvWriter = new FileWriter(filePath)) {
		    for (Integer camp : camps) {
		        if (DataStore.getCamps().containsKey(camp)) {
		            Camp c = DataStore.getCamps().get(camp);
		            csvWriter.write(campToCsv(c, headers, col));
		            headers = false;

		            // Add a newline character after each camp's data
		            csvWriter.write("\n");

		            System.out.println("Camp " + camp + " written to CSV successfully.");
		        }
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	private static String extractId(String email) {
		// Assuming the id is the substring before the "@" symbol in the email
		int atIndex = email.indexOf("@");
		if (atIndex != -1) {
			return email.substring(0, atIndex);
		}
		return email;
	}

	
    public static String campToCsv(Camp camp, boolean includeHeaders,  ArrayList<String> columns) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        StringBuilder csvBuilder = new StringBuilder();
 
        if (includeHeaders) {
           
            csvBuilder.append(String.join(",", columns)).append("\n");
        }

        if (columns.contains("Camp ID")) csvBuilder.append(camp.getCampId()).append(",");
        if (columns.contains("Name")) csvBuilder.append(escapeCsvField(camp.getName())).append(",");
        if (columns.contains("Date")) csvBuilder.append(formatDates(camp.getDates())).append(",");
        if (columns.contains("Visible")) csvBuilder.append(camp.isVisible()).append(",");
        if (columns.contains("Closing Date")) {
            csvBuilder.append(camp.getClosingDate() != null ? camp.getClosingDate().format(formatter) : "").append(",");
        }
        if (columns.contains("User Group")) {
            csvBuilder.append(camp.getUserGroup() != null ? camp.getUserGroup().name() : "").append(",");
        }
        if (columns.contains("Location")) csvBuilder.append(escapeCsvField(camp.getLocation())).append(",");
        if (columns.contains("Student Slots")) csvBuilder.append(camp.getTotalSlots()).append(",");
        if (columns.contains("Committee Slots")) csvBuilder.append(camp.getCcmSlots()).append(",");
        if (columns.contains("Staff Name")) csvBuilder.append(escapeCsvField(camp.getStaff())).append(",");
        if (columns.contains("List of Committee")) csvBuilder.append(String.join(";", (camp.getCommittee()))).append(",");
        if (columns.contains("List of Students")) csvBuilder.append(String.join(";", (camp.getRegisteredStudents()))).append(",");
        if (columns.contains("Description")) csvBuilder.append(escapeCsvField(camp.getDescription())).append(",");


        return csvBuilder.toString();
    }

    public static ArrayList<String> generateCsvHeader() {
    	ArrayList<String> headers = new ArrayList<>();
        headers.add("Camp ID");
        headers.add("Name");
        headers.add("Date");
        headers.add("Visible");
        headers.add("Closing Date");
        headers.add("User Group");
        headers.add("Location");
        headers.add("Student Slots");
        headers.add("Committee Slots");
        headers.add("Staff Name");
        headers.add("List of Committee");
        headers.add("List of Students");
        headers.add("Description");

        return headers;
    }
    private static String formatDates(ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>> dateList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder datesBuilder = new StringBuilder();

        for (SimpleEntry<LocalDateTime, LocalDateTime> entry : dateList) {
            datesBuilder.append(entry.getKey().format(formatter)).append(" to ")
                    .append(entry.getValue().format(formatter)).append(";");
        }

        return datesBuilder.toString();
    }
    private static String escapeCsvField(String field) {
        // Handle null values and fields containing commas
        return (field != null && field.contains(",")) ? "\"" + field + "\"" : field;
    }

}
