package interfaces;

import models.User;

/**
 * The {@link IAuthService} interface defines a contract for managing
 * authentication services, such as login and logout.
 */
public interface IAuthService {
	/**
	 * Logs in a user with the specified user ID and password.
	 *
	 * @param userID   the ID of the user
	 * @param password the user's password
	 * @return true if the login was successful, false otherwise
	 */
	public boolean login(String userID, String password);

	/**
	 * Logs out the currently logged in user.
	 *
	 * @return true if the logout was successful, false otherwise
	 */
	public boolean logout();

	/**
	 * Checks if the password for the given user is correct.
	 * 
	 * @param user     the user to check for
	 * @param password the password to check against
	 * @return true if correct false otherwise
	 */
	public boolean isAuth(User user, String password);
}