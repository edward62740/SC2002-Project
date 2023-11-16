package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import controllers.AuthController;
import controllers.CCMController;
import controllers.StaffController;
import controllers.StudentController;
import enums.UserGroup;
import enums.UserRole;
import models.Camp;
import models.Student;
import services.FileService;
import models.Staff;
import stores.AuthStore;
import stores.DataStore;
import views.MenuView;

/**
 * Entry point of the program.
 */
public class MainApp {
	public static void main(String args[]) {
		try
		{

			/* Adding stuff to test the program logic.. remove during deployment */
			System.out.println(System.getProperty("java.runtime.version"));
			try {
				FileService.readUserFromCsv();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			System.out.println(System.getProperty("java.runtime.version"));
			System.out.println("Starting CAMs...");
			TimeUnit.MILLISECONDS.sleep(500);
			
			
			
			MenuView.printSplashScreen();
			
			while (true) {
				boolean logoutSignal = false;
				logoutSignal = !AuthController.login();
		
				if (logoutSignal) {
					System.out.println("Received SIGABRT. SHUTTING DOWN CAMs. ");
					break;
				}
				while (true) {
					boolean exitSignal = false;
					switch (AuthStore.getCurUser().getRole()) {
					case STUDENT:
						exitSignal = StudentController.run();
						break;
					case CCM:
						exitSignal = CCMController.run();
						break;
					case STAFF:
						exitSignal = StaffController.run();
						break;
					default:
						break;
					}
					if (exitSignal) {
						System.out.println("Received SIGINT. LOG OUT. ");
						break;
					}
				}

				AuthController.logout();

			}

		}
		catch (Exception e) {

		}
	}
}
