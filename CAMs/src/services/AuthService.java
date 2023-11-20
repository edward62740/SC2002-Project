package services;

import interfaces.IAuthService;
import interfaces.ICampStaffService;
import models.User;
import stores.AuthStore;


/**
 * The {@link AuthService} implements {@link IAuthService}, and is an abstract class that also provides functionality to verify password.
 */
public abstract class AuthService implements IAuthService{

	public abstract boolean login(String uid, String password);

	public abstract boolean logout(); // TODO implement

	public boolean isAuth(User user, String password) {
		if (user != null) {
			if (user.getPassword().equals(password)) {
				AuthStore.setCurUser(user);
				return true;
			}
		}
		return false;
	}
}
