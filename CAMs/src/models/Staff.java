package models;
import java.util.ArrayList;
import enums.UserGroup;
import enums.UserRole;

/**
 * The {@link Staff} class represents a staff member with additional attributes such as owned camps.
 * Extends the User class.
 */
public class Staff extends User {

    /* Integer array of camp IDs that this user has created */
    private ArrayList<Integer> ownedCamps;

    /**
     * Constructs a new Staff with the specified user ID, faculty, and initializes
     * the role as STAFF. Initializes the owned camps attribute.
     *
     * @param id  The user ID.
     * @param fac The user's faculty defined by UserGroup.
     */
    public Staff(String id, UserGroup fac) {
        super(id, fac, UserRole.STAFF);
        ownedCamps = new ArrayList<Integer>();
    }

    /**
     * Gets the list of camp IDs that this staff member has created.
     *
     * @return The list of owned camp IDs.
     */
    public ArrayList<Integer> getOwnedCamps() {
        return ownedCamps;
    }

    /**
     * Adds a camp ID to the list of camps that this staff member has created.
     *
     * @param k The camp ID to add.
     * @return true if the addition is successful, false otherwise.
     */
    public boolean addOwnedCamps(Integer k) {
        return ownedCamps.add(k);
    }
}