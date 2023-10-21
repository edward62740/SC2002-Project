package services;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import models.Camp;
import models.EnquiryRequest;
import models.Request;
import models.Student;
import stores.AuthStore;
import stores.DataStore;

public class RequestStudentService {
	
	public boolean createNewRequest(String content, Integer campId)
	{
		Student s = (Student) AuthStore.getCurUser();
		EnquiryRequest req = new EnquiryRequest(s.getUserID(), campId, content);
		SimpleEntry<Integer, String> p = new SimpleEntry<Integer, String>(campId, s.getUserID());
		if(DataStore.getCamps().containsKey(campId))
		{
			DataStore.getRequests().put(p, req);
			return true;
		}
		return false;
	}
	
	public ArrayList<Request> getRequestsByCamp(Integer campId)
	{
		HashMap<SimpleEntry<Integer,String>, Request> requests = DataStore.getRequests();
        ArrayList<Request> matchingRequests = new ArrayList<>();

        for (SimpleEntry<Integer, String> key : requests.keySet()) {
            if (key.getKey().equals(campId)) {
                matchingRequests.add(requests.get(key));
            }
        }

        return matchingRequests;
	}
	
	public ArrayList<Request> getRequestsByUser(String uId)
	{
		HashMap<SimpleEntry<Integer,String>, Request> requests = DataStore.getRequests();
        ArrayList<Request> matchingRequests = new ArrayList<>();

        for (SimpleEntry<Integer, String> key : requests.keySet()) {
            if (key.getValue().equals(uId)) {
                matchingRequests.add(requests.get(key));
            }
        }

        return matchingRequests;
	}
}
