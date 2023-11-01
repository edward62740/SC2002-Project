package stores;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;


import models.Camp;
import models.EnquiryRequest;
import models.Request;
import models.Student;
import models.SuggestionRequest;
/**
 *  @summary the goal here is to have constant amortized time for all model accesses so that iterables are not
 *  polynomial or worse
 *  
 *  note that since there are no overrides in camp,student etc for equals() and hashCode(), the default
 *  hashmap behavior will compare the memory address. this is okay as we do not need to compare 
 *  multiple reference of the same instance.
 *  
 *  also, the approach taken was to ensure that for each instance of student,camp etc., there exists
 *  globally only one mutable reference (ownership) and zero immutable references to the class instance to control
 *  the lifespan of the instance, given that deletions are possible.
 *  instead, integer ids (camps) and string ids (student,staff) which are aliases of their respective identifiers
 *  are used to "reference" their instances.
 *  So any code should do collection.exist(id)!= noexist_returnvalue instead of reference != null.
 *  and any ids yielding noexist_returnvalue should be deleted whenever possible.
 *  
 *  tldr; the program stores the pointer to class instances only here. id used for single reference and O(1) lookup
 */
public class DataStore {
	private DataStore() {}; //prevent instantiation
	/* Store Students with their user id as key */
	private static HashMap<String, Student> students = new HashMap<String, Student>();
	
	/* Store Staff with their user id as key */
	private static HashMap<String, Student> staff = new HashMap<String, Student>();
	
	
	/* Store Camps with their id as key */
	private static HashMap<Integer, Camp> camps = new HashMap<Integer, Camp>();
	
	/* Store enquiries with keys <CampID, StudentID> */
	private static HashMap<SimpleEntry<Integer,String>, EnquiryRequest> enquiries = new HashMap<SimpleEntry<Integer,String>, EnquiryRequest>();
	
	/* Store suggestions with keys <CampID, StudentID> */
	private static HashMap<SimpleEntry<Integer,String>, SuggestionRequest> suggestions = new HashMap<SimpleEntry<Integer,String>, SuggestionRequest>();
	
	
	
	
	
	public static HashMap<String, Student> getStudents() {
		return students;
	}
	public static void setStudents(HashMap<String, Student> students) {
		DataStore.students = students;
	}
	public static HashMap<Integer, Camp> getCamps() {
		return camps;
	}
	public static void setCamps(HashMap<Integer, Camp> camps) {
		DataStore.camps = camps;
	}

	public static HashMap<String, Student> getStaff() {
		return staff;
	}
	public static void setStaff(HashMap<String, Student> staff) {
		DataStore.staff = staff;
	}
	public static HashMap<SimpleEntry<Integer,String>, SuggestionRequest> getSuggestions() {
		return suggestions;
	}
	public static void setSuggestions(HashMap<SimpleEntry<Integer,String>, SuggestionRequest> suggestions) {
		DataStore.suggestions = suggestions;
	}
	public static HashMap<SimpleEntry<Integer,String>, EnquiryRequest> getEnquiries() {
		return enquiries;
	}
	public static void setEnquiries(HashMap<SimpleEntry<Integer,String>, EnquiryRequest> enquiries) {
		DataStore.enquiries = enquiries;
	}
	
	
}
