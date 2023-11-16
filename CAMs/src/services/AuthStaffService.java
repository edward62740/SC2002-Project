package services;

import java.util.HashMap;

import interfaces.IAuthService;
import models.Staff;
import stores.*;


/**
 * The {@link AuthStaffService} extends {@link AuthService}, and provides log in functionality for staff.
 */
public class AuthStaffService extends AuthService {

	@Override
	public boolean login(String uid, String password) {
		// TODO Auto-generated method stub
		HashMap<String, Staff> staff = DataStore.getStaff();
		if(staff.containsKey(uid))
		{
			Staff s = staff.get(uid);
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
