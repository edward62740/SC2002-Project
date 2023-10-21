package stores;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;


import models.Camp;
import models.Request;
import models.Student;
/*
 *  the goal here is to have constant amortized time for all model accesses so that iterables are not
 *  polynomial 
 */
public class DataStore {
	private DataStore() {};
	private static HashMap<String, Student> students = new HashMap<String, Student>();
	private static HashMap<Integer, Camp> camps = new HashMap<Integer, Camp>();
	
	/* Store Request with keys <CampID, StudentID> for fast look-ups */
	private static HashMap<SimpleEntry<Integer,String>, Request> requests = new HashMap<SimpleEntry<Integer,String>, Request>();
	
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
	public static HashMap<SimpleEntry<Integer,String>, Request> getRequests() {
		return requests;
	}
	public static void setRequests(HashMap<SimpleEntry<Integer,String>, Request> requests) {
		DataStore.requests = requests;
	}
	
	
}
