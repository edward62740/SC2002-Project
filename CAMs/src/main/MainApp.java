package main;

import java.util.HashMap;

import controllers.AuthController;
import controllers.CCMController;
import controllers.StudentController;
import enums.UserGroup;
import enums.UserRole;
import models.Camp;
import models.Student;
import stores.AuthStore;
import stores.DataStore;
import views.MenuView;

public class MainApp {
	public static void main(String args[]) {
		Student s = new Student("etan102", UserGroup.SCSE);
		Student s1 = new Student("testuser", UserGroup.SCSE);
		s1.setRole(UserRole.CCM);
		HashMap<String, Student> students = DataStore.getStudents();
		Camp camp = new Camp(0, "name", UserGroup.SCSE, "location info", 50, "user1", "this is a desc");
		Camp camp1 = new Camp(0, "name", UserGroup.ALL, "location info", 40, "user1", "this is a desc");
		DataStore.getCamps().put(0, camp);
		DataStore.getCamps().put(1, camp1);
		students.put(s.getUserID(), s);
		students.put(s1.getUserID(), s1);
		System.out.println(s.getFaculty());
		MenuView.printSplashScreen();
		AuthController.login();

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

				break;
			default:
				break;
			}
			if(exitSignal) break;
		}
		AuthController.logout();

	}
}
