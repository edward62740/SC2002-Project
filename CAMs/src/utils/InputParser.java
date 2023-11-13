package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputParser {
	private InputParser() {
	}

	/* takes in reference to scanner, message to display, lower and upper valid bounds of integer, number of retries
	 * and termination character */
	
	
	
	public static Integer parseInInteger(Scanner sc, String msg, int lower, int upper, int retries,
			String termination) {
		String input;
		Integer ret = null;
		do {
			System.out.println(msg);
			input = sc.nextLine();
			if (input.strip().equals(termination))
				return null;
			if (input.matches("-?[0-9]+")) { // If the input is an integer, proceed with the code
				ret = Integer.parseInt(input);
			} else if (ret != null && (ret > upper || ret < lower))
				System.out.println("Out of range.");
			else { // If the input is not an integer, prompt the user to enter again
				System.out.println("Invalid input. Please enter an integer.");
			}

		} while (ret == null && retries-- > 0);

		return ret;
	}

	public static String parseInString(Scanner sc, String msg, int retries, String termination) {

		String ret = null;
		do {
			System.out.println(msg);
			ret = sc.nextLine();
			if (ret.strip().equals(termination))
				return null;

		} while (ret == null && retries-- > 0);

		return ret;
	}
	
	public static LocalDateTime parseInLocalDateTime(Scanner sc, String msg, int retries, String termination) {
        String input;
        LocalDateTime ret = null;
        do {
            System.out.println(msg);
            input = sc.nextLine();
            if (input.strip().equals(termination))
                return null;

            try {
                ret = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid date and time in the format yyyy-MM-dd HH:mm.");
            }

        } while (ret == null && retries-- > 0);

        return ret;
    }
	
	
}
