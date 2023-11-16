package stores;

import models.User;
/**
 * The {@link AuthStore} class is responsible for managing authentication-related information,
 * specifically the currently logged-in user in the application.
 */
public class AuthStore {

    /**
     * The currently logged-in user.
     */
    private static User curUser;

    /**
     * Gets the currently logged-in user.
     *
     * @return The currently logged-in user, or null if no user is logged in
     */
    public static User getCurUser() {
        return curUser;
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param curUser The user to set as the currently logged-in user
     */
    public static void setCurUser(User curUser) {
        AuthStore.curUser = curUser;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return true if a user is logged in false otherwise
     */
    public static boolean isLoggedIn() {
        return curUser != null;
    }
}
