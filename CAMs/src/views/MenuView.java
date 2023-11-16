package views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@link MenuView} class provides methods for displaying menu-related views in the CAMs system.
 */
public class MenuView {

    private MenuView() {
    }
    
    /**
     * DateTime formatter for printing date
     */
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");


    /**
     * Prints the CAMs system splash screen with the system name and current system time.
     */
    public static void printSplashScreen() {
        System.out.println("\u001b[1;34m"
                + "██████╗  █████╗   ███╗   ███╗ ███████╗\r\n"
                + "██╔════╝ ██╔══██╗ ████╗ ████║ ██╔════╝\r\n"
                + "██║      ███████║ ██╔████╔██║ ███████╗\r\n"
                + "██║      ██╔══██  ██║╚██╔╝██║ ╚════██║\r\n"
                + "╚██████╗ ██║  ██║ ██║ ╚═╝ ██║ ███████║\r\n"
                + " ╚═════╝ ╚═╝  ╚═╝ ╚═╝     ╚═╝ ╚══════╝"
                + "                                   \u001b[0m");
        System.out.println("\u001b[1;34mC\u001b[0m\u001b[1;94mAMP \u001b[0m\u001b[1;34mA\u001b[0m\u001b[1;94mPPLICATION AND \u001b[0m\u001b[1;34mM\u001b[0m\u001b[1;94mANAGEMENT \u001b[0m\u001b[1;34mS\u001b[0m\u001b[1;94mYSTEM\u001b[0m");
        System.out.println("System Time: \u001b[1;32m" + LocalDateTime.now().format(formatter) + "\u001b[0m\s\n\n");
    }
}
