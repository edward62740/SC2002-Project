package stores;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import models.Camp;
import models.EnquiryRequest;
import models.Staff;
import models.Student;
import models.SuggestionRequest;

/**
 * The {@link DataStore} class serves as a central storage for various model
 * instances in the application. It maintains hash maps for storing instances of
 * students, staff, camps, enquiries, and suggestions. The goal is to provide
 * constant amortized time for all model accesses, ensuring efficient iterative
 * operations.
 * <p>
 * Note: The default HashMap behavior is used for comparing instances based on
 * memory address, as there are no overrides for equals() and hashCode() in the
 * models.
 * </p>
 * <p>
 * The approach taken ensures that for each instance of Student, Camp, etc.,
 * there exists globally only one mutable reference (ownership) and zero
 * immutable references to the class instance to control the lifespan of the
 * instance, given that deletions are possible. Integer IDs (for camps) and
 * String IDs (for students, staff) are used to reference their instances. It is
 * recommended to check existence using collection.containsKey(id) !=
 * noExistReturnValue and delete IDs yielding noExistReturnValue whenever
 * possible.
 * </p>
 * <p>
 * TODO: The SimpleMap should be replaced with MultiKeyMap or some composite key
 * hashmap.
 * </p>
 */
public class DataStore {
	private DataStore() {
	}; // prevent instantiation

	/* Store Students with their user id as key */
	private static HashMap<String, Student> students = new HashMap<String, Student>();

	/* Store Staff with their user id as key */
	private static HashMap<String, Staff> staff = new HashMap<String, Staff>();

	/* Store Camps with their id as key */
	private static HashMap<Integer, Camp> camps = new HashMap<Integer, Camp>();
	private static Integer campIndexCur = -1;

	/* Store enquires with keys <CampID, StudentID> */
	private static HashMap<SimpleEntry<Integer, String>, EnquiryRequest> enquiries = new HashMap<SimpleEntry<Integer, String>, EnquiryRequest>();

	/* Store suggestions with keys <CampID, StudentID> */
	private static HashMap<SimpleEntry<Integer, String>, SuggestionRequest> suggestions = new HashMap<SimpleEntry<Integer, String>, SuggestionRequest>();

	/**
	 * Gets the map containing student instances.
	 *
	 * @return The map containing student instances.
	 */
	public static HashMap<String, Student> getStudents() {
		return students;
	}

	/**
	 * Sets the map containing student instances.
	 *
	 * @param students The map containing student instances.
	 */
	public static void setStudents(HashMap<String, Student> students) {
		DataStore.students = students;
	}

	/**
	 * Gets the map containing camp instances.
	 *
	 * @return The map containing camp instances.
	 */
	public static HashMap<Integer, Camp> getCamps() {
		return camps;
	}

	/**
	 * Sets the map containing camp instances.
	 *
	 * @param camps The map containing camp instances.
	 */
	public static void setCamps(HashMap<Integer, Camp> camps) {
		DataStore.camps = camps;
	}

	/**
	 * Gets the map containing staff instances.
	 *
	 * @return The map containing staff instances.
	 */
	public static HashMap<String, Staff> getStaff() {
		return staff;
	}

	/**
	 * Sets the map containing staff instances.
	 *
	 * @param staff The map containing staff instances.
	 */
	public static void setStaff(HashMap<String, Staff> staff) {
		DataStore.staff = staff;
	}

	/**
	 * Gets the map containing suggestion instances.
	 *
	 * @return The map containing suggestion instances.
	 */
	public static HashMap<SimpleEntry<Integer, String>, SuggestionRequest> getSuggestions() {
		return suggestions;
	}

	/**
	 * Sets the map containing suggestion instances.
	 *
	 * @param suggestions The map containing suggestion instances.
	 */
	public static void setSuggestions(HashMap<SimpleEntry<Integer, String>, SuggestionRequest> suggestions) {
		DataStore.suggestions = suggestions;
	}

	/**
	 * Gets the map containing enquiry instances.
	 *
	 * @return The map containing enquiry instances.
	 */
	public static HashMap<SimpleEntry<Integer, String>, EnquiryRequest> getEnquiries() {
		return enquiries;
	}

	/**
	 * Sets the map containing enquiry instances.
	 *
	 * @param enquiries The map containing enquiry instances.
	 */
	public static void setEnquiries(HashMap<SimpleEntry<Integer, String>, EnquiryRequest> enquiries) {
		DataStore.enquiries = enquiries;
	}

	/**
	 * Gets the current camp index and increments it.
	 *
	 * @return The current camp index.
	 */
	public static Integer getCampIndexCur() {
		campIndexCur++;
		return campIndexCur;
	}
}
