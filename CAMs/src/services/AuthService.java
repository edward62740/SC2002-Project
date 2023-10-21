package services;

import models.User;
import stores.AuthStore;

public abstract class AuthService {

	public abstract boolean login(String uid, String password);

	public abstract boolean logout();

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
