package models;
import java.io.Serializable;

import enums.UserGroup;
import enums.UserRole;

/**
 * The {@link User} class represents a user with attributes such as userID, password, faculty,
 * default password status, and role.
 */
public class User {

    /* User id xxxx in email xxxx@ntu.edu.sg */
    private String userID;

    /* Password string, defaults to "password" */
    private String password;

    /* Faculty defined by enum {@link UserGroup} */
    private UserGroup faculty;

    /* Boolean to indicate whether the password is the default */
    private boolean isDefaultPassword;

    /* Role defined by enum {@link UserRole} */
    private UserRole role;

    /**
     * Constructs a new User with the specified user ID, faculty, and role.
     *
     * @param uid    The user ID.
     * @param faculty The user's faculty defined by UserGroup.
     * @param role    The user's role defined by UserRole.
     */
    public User(String uid, UserGroup faculty, UserRole role) {
        this.userID = uid;
        this.faculty = faculty;
        this.password = "password";
        this.isDefaultPassword = true;
        this.setRole(role);
    }

    /**
     * Gets the user ID.
     *
     * @return The user ID.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the user ID.
     *
     * @param userID The new user ID to set.
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Gets the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's faculty.
     *
     * @return The user's faculty defined by UserGroup.
     */
    public UserGroup getFaculty() {
        return faculty;
    }

    /**
     * Sets the user's faculty.
     *
     * @param faculty The new faculty to set.
     */
    public void setFaculty(UserGroup faculty) {
        this.faculty = faculty;
    }

    /**
     * Checks whether the password is the default.
     *
     * @return true if the password is the default, false otherwise.
     */
    public boolean isDefaultPassword() {
        return isDefaultPassword;
    }

    /**
     * Sets the default password status.
     *
     * @param isDefaultPassword The new default password status to set.
     */
    public void setDefaultPassword(boolean isDefaultPassword) {
        this.isDefaultPassword = isDefaultPassword;
    }

    /**
     * Gets the user's role.
     *
     * @return The user's role defined by UserRole.
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     *
     * @param role The new role to set.
     */
    public void setRole(UserRole role) {
        this.role = role;
    }
}

