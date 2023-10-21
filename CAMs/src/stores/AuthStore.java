package stores;

import models.User;

public class AuthStore {
	private static User curUser;

	public static User getCurUser() {
		return curUser;
	}

	public static void setCurUser(User curUser) {
		AuthStore.curUser = curUser;
	}
	public static boolean isLoggedIn()
	{
		if(curUser != null) return true;
		else return false;
	}

}
