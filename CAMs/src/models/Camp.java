package models;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

import enums.UserGroup;



public class Camp  {
	
	/* Integer id referencing camp. Must be aliased by integer id key for each instance in DataStore */
	private int campId;
	
	/* Name of camp */
	private String name;
	/**
	 * Array of dates in the form [lower bound, upper bound]
	 */
	private ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>> dates;
	
	/*
	 * Indicates visibility of camp
	 */
	private boolean visible;
	
	/* Closing date of camp */
	private LocalDateTime closingDate;
	
	/* user group this camp is open to */
	private UserGroup userGroup;
	
	/* Location of camp */
	private String location;
	
	/* Slots of camp. remaining slots defined by this value - len(registeredStudents) */
	private int totalSlots;
	/* Slots of ccm. remaining slots defined by this value - len(committee) */
	private int ccmSlots;
	
	/* User id of staff. should be immutable. */
	private String staff;
	
	/* stores the string ids of the committee and students associated with this camp respectively. must be pairwise disjoint */
	private ArrayList<String> committee;
	private ArrayList<String> registeredStudents;
	
	/* description of camp */
	private String description;
	
	public Camp(int campId, String name, UserGroup userGroup, String location, int totalSlots, int ccmSlots, String staff, String description, LocalDateTime closingDate)
	{
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
		this.closingDate= closingDate;
		this.dates = new ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>>();
		this.visible = true;
	}
	
	public Camp(int campId, String name, UserGroup userGroup, String location, int totalSlots, int ccmSlots, String staff, String description)
	{
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
		this.dates = new ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>>();
		this.visible = true;
	}

	public int getCampId() {
		return campId;
	}

	public void setCampId(int campId) {
		this.campId = campId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>> getDates() {
		return dates;
	}

	public void setDates(ArrayList<SimpleEntry<LocalDateTime, LocalDateTime>> dates) {
		this.dates = dates;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public LocalDateTime getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDateTime closingDate) {
		this.closingDate = closingDate;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getTotalSlots() {
		return totalSlots;
	}

	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public ArrayList<String> getCommittee() {
		return committee;
	}

	public void setCommittee(ArrayList<String> committee) {
		this.committee = committee;
	}

	public ArrayList<String> getRegisteredStudents() {
		return registeredStudents;
	}

	public void setRegisteredStudents(ArrayList<String> registeredStudents) {
		this.registeredStudents = registeredStudents;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCcmSlots() {
		return ccmSlots;
	}

	public void setCcmSlots(int ccmSlots) {
		this.ccmSlots = ccmSlots;
	}
	
}
