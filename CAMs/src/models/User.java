package models;
import enums.UserGroup;
import enums.UserRole;

public class User {
	
	/* User id xxxx in email xxxx@ntu.edu.sg */
	private String userID;
	
	/* Password string, defaults to "Password" */
	private String password;
	
	/* Faculty defined by enum UserGroup */
	private UserGroup faculty;
	
	/* Boolean to indicate whether password is default */
	private boolean isDefaultPassword;
	
	/* Role defined by enum UserRole */
	private UserRole role;
	
	public User(String uid, UserGroup faculty, UserRole role) {
		this.userID = uid;
		this.faculty = faculty;
		this.password = "Password";
		this.isDefaultPassword = true;
		this.setRole(role);
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserGroup getFaculty() {
		return faculty;
	}
	public void setFaculty(UserGroup faculty) {
		this.faculty = faculty;
	}
	public boolean isDefaultPassword() {
		return isDefaultPassword;
	}
	public void setDefaultPassword(boolean isDefaultPassword) {
		this.isDefaultPassword = isDefaultPassword;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	
}
