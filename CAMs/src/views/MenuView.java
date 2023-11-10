package views;
import java.time.LocalDateTime;

public class MenuView {
	private MenuView() {};
	public static void printSplashScreen() {
		System.out.println("\u001b[1;34m\u250F" + "\u2501".repeat(98) + "\u2513\u001b[0m");
		System.out.println(
				"\u001b[1;34m\u2503                                      CAMs                                            \u2503\u001b[0m");
		//System.out.println(LocalDateTime.now());
		System.out.println("\u001b[1;34m\u2517" + "\u2501".repeat(98) + "\u251B\u001b[0m");
	}

}
