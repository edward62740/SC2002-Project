package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

import enums.UserGroup;

/**
 * The {@link Camp} class defines a camp with various attributes such as
 * ID, name, dates, visibility, closing date, user group, location, slots,
 * staff information, committee, registered students, and a description.
 * Each camp instance is uniquely identified by its camp ID, and the ID must
 * be aliased by an integer ID key for each instance in the data store.
 * More info on reference to instances of Camp in {@link DataStore}.
 */
public class Camp {


    /** Integer ID referencing the camp. */
    private int campId;

    /** Name of the camp. */
    private String name;

    /**
     * Array of date ranges in the form [lower bound, upper bound].
     */
    private ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>> dates;

    /** Indicates the visibility of the camp. */
    private boolean visible;

    /** Closing date of the camp. */
    private LocalDateTime closingDate;

    /** User group this camp is open to. */
    private UserGroup userGroup;

    /** Location of the camp. */
    private String location;

    /** Slots of the camp for registered students. */
    private int totalSlots;

    /** Slots of the camp for the committee. */
    private int ccmSlots;

    /** User ID of staff. Should be immutable. */
    private String staff;

    /** String IDs of the committee associated with this camp. */
    private ArrayList<String> committee;

    /** String IDs of registered students associated with this camp. */
    private ArrayList<String> registeredStudents;

    /** Description of the camp. */
    private String description;

    /**
     * Constructs a new {@link Camp} instance with the specified parameters.
     *
     * @param campId       The integer ID referencing the camp
     * @param name         The name of the camp
     * @param userGroup    The user group this camp is open to
     * @param location     The location of the camp
     * @param totalSlots   The total slots of the camp
     * @param ccmSlots     The slots of the camp committee
     * @param staff        The user ID of staff
     * @param description  The description of the camp
     * @param closingDate  The closing date of the camp
     */
    public Camp(int campId, String name, UserGroup userGroup, String location,
                int totalSlots, int ccmSlots, String staff, String description, LocalDateTime closingDate) {
        this.campId = campId;
        this.name = name;
        this.userGroup = userGroup;
        this.location = location;
        this.totalSlots = totalSlots;
        this.ccmSlots = ccmSlots;
        this.staff = staff;
        this.description = description;
        this.committee = new ArrayList<String>();
        this.registeredStudents = new ArrayList<String>();
        this.closingDate = closingDate;
        this.dates = new ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>>();
        this.visible = true;
    }

    /**
     * Gets the integer ID referencing the camp.
     *
     * @return The camp ID.
     */
    public int getCampId() {
        return campId;
    }

    /**
     * Sets the integer ID referencing the camp.
     *
     * @param campId The camp ID to set.
     */
    public void setCampId(int campId) {
        this.campId = campId;
    }

    /**
     * Gets the name of the camp.
     *
     * @return The camp name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the camp.
     *
     * @param name The camp name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the array of date ranges in the form [lower bound, upper bound].
     *
     * @return The list of date ranges.
     */
    public ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>> getDates() {
        return dates;
    }

    /**
     * Sets the array of date ranges in the form [lower bound, upper bound].
     *
     * @param dates The list of date ranges to set.
     */
    public void setDates(ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>> dates) {
        this.dates = dates;
    }

    /**
     * Checks if the camp is visible.
     *
     * @return true if the camp is visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the camp.
     *
     * @param visible The visibility status to set.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Gets the closing date of the camp.
     *
     * @return The closing date of the camp.
     */
    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    /**
     * Sets the closing date of the camp.
     *
     * @param closingDate The closing date to set.
     */
    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Gets the user group this camp is open to.
     *
     * @return The user group.
     */
    public UserGroup getUserGroup() {
        return userGroup;
    }

    /**
     * Sets the user group this camp is open to.
     *
     * @param userGroup The user group to set.
     */
    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * Gets the location of the camp.
     *
     * @return The location of the camp.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the camp.
     *
     * @param location The location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the total slots of the camp.
     *
     * @return The total slots of the camp.
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Sets the total slots of the camp.
     *
     * @param totalSlots The total slots to set.
     */
    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    /**
     * Gets the user ID of staff.
     *
     * @return The user ID of staff.
     */
    public String getStaff() {
        return staff;
    }

    /**
     * Sets the user ID of staff.
     *
     * @param staff The user ID of staff to set.
     */
    public void setStaff(String staff) {
        this.staff = staff;
    }

    /**
     * Gets the list of committee IDs associated with this camp.
     *
     * @return The list of committee IDs.
     */
    public ArrayList<String> getCommittee() {
        return committee;
    }

    /**
     * Sets the list of committee IDs associated with this camp.
     *
     * @param committee The list of committee IDs to set.
     */
    public void setCommittee(ArrayList<String> committee) {
        this.committee = committee;
    }

    /**
     * Gets the list of registered student IDs associated with this camp.
     *
     * @return The list of registered student IDs.
     */
    public ArrayList<String> getRegisteredStudents() {
        return registeredStudents;
    }

    /**
     * Sets the list of registered student IDs associated with this camp.
     *
     * @param registeredStudents The list of registered student IDs to set.
     */
    public void setRegisteredStudents(ArrayList<String> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    /**
     * Gets the description of the camp.
     *
     * @return The description of the camp.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the camp.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the slots of the camp committee.
     *
     * @return The slots of the camp committee.
     */
    public int getCcmSlots() {
        return ccmSlots;
    }

    /**
     * Sets the slots of the camp committee.
     *
     * @param ccmSlots The slots of the camp committee to set.
     */
    public void setCcmSlots(int ccmSlots) {
        this.ccmSlots = ccmSlots;
    }
}
