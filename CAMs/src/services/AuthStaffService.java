package services;

import java.util.HashMap;

import models.Student;
import stores.*;

public class AuthStaffService extends AuthService {

	@Override
	public boolean login(String uid, String password) {
		// TODO Auto-generated method stub
		HashMap<String, Student> staff = DataStore.getStaff();
		if(staff.containsKey(uid))
		{
			Student s = staff.get(uid);
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
