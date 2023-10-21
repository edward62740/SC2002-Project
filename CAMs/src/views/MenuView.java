package views;
import java.time.LocalDateTime;

public class MenuView {
	private MenuView() {};
	public static void printSplashScreen() {
		System.out.println("\u250F" + "\u2501".repeat(98) + "\u2513");
		System.out.println(
				"\u2503                                    CAMs                                 \u2503");
		//System.out.println(LocalDateTime.now());
		System.out.println("\u2517" + "\u2501".repeat(98) + "\u251B");
	}

}
