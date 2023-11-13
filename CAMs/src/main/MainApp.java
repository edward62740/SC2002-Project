package main;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

import controllers.AuthController;
import controllers.CCMController;
import controllers.StaffController;
import controllers.StudentController;
import enums.UserGroup;
import enums.UserRole;
import models.Camp;
import models.Student;
import models.Staff;
import stores.AuthStore;
import stores.DataStore;
import views.MenuView;

public class MainApp {
	public static void main(String args[]) {
		
		/* Adding stuff to test the program logic.. remove during deployment */
		System.out.println(System.getProperty("java.runtime.version"));
		Student s = new Student("etan102", UserGroup.SCSE);
		Student s1 = new Student("testuser", UserGroup.SCSE);
		Staff st = new Staff("teststaff", UserGroup.SCSE);
		s1.setRole(UserRole.STUDENT);
		HashMap<String, Student> students = DataStore.getStudents();
		HashMap<String, Staff> staff = DataStore.getStaff();
		Camp camp = new Camp(0, "name", UserGroup.SCSE, "location info", 50, 0, "user", "this is a desc");
		Camp camp1 = new Camp(1, "name1", UserGroup.ALL, "location info1", 1, 2, "user1", "this is a desc1");
		camp.setClosingDate(LocalDateTime.now().plusHours(1));
		camp1.setClosingDate(LocalDateTime.now().plusHours(1));

		LocalDateTime start = LocalDateTime.now().plusHours(6);
		LocalDateTime start1 = LocalDateTime.now().plusHours(11);
		LocalDateTime end = LocalDateTime.now().plusHours(12);

		SimpleEntry<LocalDateTime, LocalDateTime> dateRange = new SimpleEntry<>(start, end);
		SimpleEntry<LocalDateTime, LocalDateTime> dateRange1 = new SimpleEntry<>(start, end);
		camp.getDates().add(dateRange);
		camp1.getDates().add(dateRange1);
		DataStore.getCamps().put(0, camp);
		DataStore.getCamps().put(1, camp1);
		students.put(s.getUserID(), s);
		students.put(s1.getUserID(), s1);
		st.getOwnedCamps().add(1);
		staff.put(st.getUserID(), st);

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
		System.out.println("Placeholder for save to CSV call");
	}
}
