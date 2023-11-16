package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * The {@link InputParser} class provides utility methods for parsing user input.
 */
public class InputParser {
    private InputParser() {
    }

    /**
     * Parses an integer input from the user within specified bounds.
     *
     * @param sc          The {@link Scanner} object for input.
     * @param msg         The message to display prompting for input.
     * @param lower       The lower bound for the valid integer range.
     * @param upper       The upper bound for the valid integer range.
     * @param retries     The number of retries allowed.
     * @param termination The termination character to exit input.
     * @return The parsed integer or null if terminated or retries exhausted.
     */
    public static Integer parseInInteger(Scanner sc, String msg, int lower, int upper, int retries,
            String termination) {
        /**
         * Parses an integer input from the user within specified bounds.
         *
         * @param sc          The {@link Scanner} object for input.
         * @param msg         The message to display prompting for input.
         * @param lower       The lower bound for the valid integer range.
         * @param upper       The upper bound for the valid integer range.
         * @param retries     The number of retries allowed.
         * @param termination The termination character to exit input.
         * @return The parsed integer or null if terminated or retries exhausted.
         */
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

    /**
     * Parses a string input from the user.
     *
     * @param sc          The {@link Scanner} object for input.
     * @param msg         The message to display prompting for input.
     * @param retries     The number of retries allowed.
     * @param termination The termination character to exit input.
     * @return The parsed string or null if terminated or retries exhausted.
     */
    public static String parseInString(Scanner sc, String msg, int retries, String termination) {
        /**
         * Parses a string input from the user.
         *
         * @param sc          The {@link Scanner} object for input.
         * @param msg         The message to display prompting for input.
         * @param retries     The number of retries allowed.
         * @param termination The termination character to exit input.
         * @return The parsed string or null if terminated or retries exhausted.
         */
        String ret = null;
        do {
            System.out.println(msg);
            ret = sc.nextLine();
            if (ret.strip().equals(termination))
                return null;

        } while (ret == null && retries-- > 0);

        return ret;
    }

    /**
     * Parses a {@link LocalDateTime} input from the user.
     *
     * @param sc          The {@link Scanner} object for input.
     * @param msg         The message to display prompting for input.
     * @param retries     The number of retries allowed.
     * @param termination The termination character to exit input.
     * @return The parsed {@link LocalDateTime} or null if terminated or retries exhausted.
     */
    public static LocalDateTime parseInLocalDateTime(Scanner sc, String msg, int retries, String termination) {
        /**
         * Parses a {@link LocalDateTime} input from the user.
         *
         * @param sc          The {@link Scanner} object for input.
         * @param msg         The message to display prompting for input.
         * @param retries     The number of retries allowed.
         * @param termination The termination character to exit input.
         * @return The parsed {@link LocalDateTime} or null if terminated or retries exhausted.
         */
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
