package services;

import java.util.HashMap;

import models.Student;
import stores.*;

/**
 * The {@link AuthStudentService} extends {@link AuthService}, and provides log in functionality for staff.
 */
public class AuthStudentService extends AuthService {

	@Override
	public boolean login(String uid, String password) {
		// TODO Auto-generated method stub
		HashMap<String, Student> students = DataStore.getStudents();
		if(students.containsKey(uid))
		{
			Student s = students.get(uid);
			if (s != null) return isAuth(s, password);
		}
		
		return false;
	}

	@Override
	public boolean logout() {
		// TODO Auto-generated method stub
		AuthStore.setCurUser(null);
		return true;
	}
	

}
