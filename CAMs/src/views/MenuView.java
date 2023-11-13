package views;
import java.time.LocalDateTime;

public class MenuView {
	private MenuView() {};
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


		System.out.println("System Time: \u001b[1;32m" + LocalDateTime.now() + "\u001b[0m\s\n\n");
		
	}

}
