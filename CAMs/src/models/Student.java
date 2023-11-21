package models;

import java.util.ArrayList;

import enums.UserGroup;
import enums.UserRole;

/**
 * The {@link Student} class represents a student with additional attributes such as camps,
 * previous camps, committee membership, and points (applicable if the role is CCM).
 * Extends the User class.
 */
public class Student extends User {

    /* Integer array of camp IDs that this user is registered to */
    private ArrayList<Integer> camps;

    /* Integer array of camp IDs that this user was registered to.
     * Note that camps AND prevCamps must be pairwise disjoint or there will be undefined behavior */
    private ArrayList<Integer> prevCamps;

    /* Integer of camp ID that this student is CCM of iff role == CCM. Invalid otherwise. */
    private Integer committee;

    /* Points of student, valid iff role == CCM. Invalid otherwise. */
    private int points;

    /**
     * Constructs a new Student with the specified user ID, faculty, and initializes
     * the role as STUDENT. Initializes the camp-related attributes.
     *
     * @param id  The user ID
     * @param fac The user's faculty defined by UserGroup
     */
    public Student(String id, UserGroup fac) {
        super(id, fac, UserRole.STUDENT);
        camps = new ArrayList<Integer>();
        prevCamps = new ArrayList<Integer>();
        committee = null;
        setPoints(0);
    }

    /**
     * Gets the list of camp IDs that this student is currently registered for.
     *
     * @return The list of camp IDs.
     */
    public ArrayList<Integer> getCamps() {
        return camps;
    }

    /**
     * Adds a camp ID to the list of camps that this student is registered for.
     *
     * @param k The camp ID to add.
     * @return true if the addition is successful, false otherwise.
     */
    public boolean addCamp(Integer k) {
        return camps.add(k);
    }

    /**
     * Removes a camp ID from the list of camps that this student is registered for.
     * Adds the removed camp ID to the list of previous camps.
     * Note that this ensures that the two lists are pairwise disjoint as there is no setter for prevCamps.
     *
     * @param k The camp ID to remove.
     * @return true if the removal is successful, false otherwise.
     */
    public boolean removeCamps(Integer k) {
        if (camps.contains(k)) {
            camps.remove(k);
            prevCamps.add(k);
            return true;
        }
        return false;
    }

    /**
     * Gets the list of camp IDs that this student was previously registered for.
     *
     * @return The list of previous camp IDs
     */
    public ArrayList<Integer> getPrevCamps() {
        return prevCamps;
    }

    /**
     * Gets the camp ID that this student is the committee member of, if the role is CCM.
     *
     * @return The camp ID if the student is a committee member, null otherwise.
     */
    public Integer getCommittee() {
        return committee;
    }

    /**
     * Sets the camp ID that this student is the committee member of, if the role is CCM.
     *
     * @param committee The new camp ID to set.
     */
    public void setCommittee(Integer committee) {
        this.committee = committee;
    }

    /**
     * Gets the points of the student, applicable only if the role is CCM.
     *
     * @return The points if the student is a committee member, 0 otherwise.
     */
    public int getPoints() {
        if (this.getRole() != UserRole.CCM) return 0;
        return points;
    }

    /**
     * Sets the points of the student, applicable only if the role is CCM.
     *
     * @param points The new points value to set.
     * @return true if the setting is successful, false otherwise.
     */
    public boolean setPoints(int points) {
        if (this.getRole() != UserRole.CCM) return false;
        this.points = points;
        return true;
    }
}