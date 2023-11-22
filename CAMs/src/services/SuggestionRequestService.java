package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import enums.RequestStatus;
import enums.UserRole;
import interfaces.IRequestService;
import models.SuggestionRequest;
import models.User;
import models.Request;
import models.Student;
import stores.AuthStore;
import stores.DataStore;

/**
 * The {@link SuggestionRequestService} implements {@link IRequestService}, and provides request related functionalities for suggestions.
 */
public class SuggestionRequestService implements IRequestService {
	public boolean createNewRequest(String content, Integer campId)
	{
		User s = AuthStore.getCurUser();
		SuggestionRequest req = new SuggestionRequest(s.getUserID(), campId, content);
		SimpleEntry<Integer, String> p = new SimpleEntry<Integer, String>(campId, s.getUserID());
		if(DataStore.getCamps().containsKey(campId))
		{
			DataStore.getSuggestions().put(p, req);
			if(s.getRole() == UserRole.CCM && s instanceof Student)
			{
				Student st = (Student)s;
				st.setPoints(st.getPoints() + 1); // give one point for making suggestion
				
			}

			return true;
		}
		return false;
	}
	
	public boolean deleteRequest(Integer campId)
	{
		User s = AuthStore.getCurUser();
		if(DataStore.getCamps().containsKey(campId))
		{
			SimpleEntry<Integer, String> p = new SimpleEntry<Integer, String>(campId, s.getUserID());
			if(DataStore.getSuggestions().containsKey(p)) 
			{
				if(DataStore.getSuggestions().get(p).getStatus() != RequestStatus.PENDING) return false;
				DataStore.getSuggestions().remove(p);
				return true;
			}
		}
		return false;
	}
	
	public boolean editRequest(Integer campId, String content)
	{
		User s = AuthStore.getCurUser();
		if(DataStore.getCamps().containsKey(campId))
		{
			SimpleEntry<Integer, String> p = new SimpleEntry<Integer, String>(campId, s.getUserID());
			if(DataStore.getSuggestions().containsKey(p)) 
			{
				if(DataStore.getSuggestions().get(p).getStatus() != RequestStatus.PENDING) return false;
				DataStore.getSuggestions().get(p).setContent(content);
				return true;
			}
		}
		return false;
	}
	
	public boolean isQueuedRequestForUser(Integer campId)
	{
		User s = AuthStore.getCurUser();
		if(DataStore.getCamps().containsKey(campId))
		{
			SimpleEntry<Integer, String> p = new SimpleEntry<Integer, String>(campId, s.getUserID());
			if(DataStore.getSuggestions().containsKey(p)) return true;
		}
		return false;
	}
	
	public ArrayList<SuggestionRequest> getRequestByCamp(Integer campId)
	{
		HashMap<SimpleEntry<Integer,String>, SuggestionRequest> requests = DataStore.getSuggestions();
        ArrayList<SuggestionRequest> matchingRequests = new ArrayList<>();

        for (SimpleEntry<Integer, String> key : requests.keySet()) {
            if (key.getKey().equals(campId)) {
                matchingRequests.add(requests.get(key));
            }
        }

        return matchingRequests;
	}
	
	public ArrayList<SuggestionRequest> getRequestByUser(String uId)
	{
		HashMap<SimpleEntry<Integer,String>, SuggestionRequest> requests = DataStore.getSuggestions();
        ArrayList<SuggestionRequest> matchingRequests = new ArrayList<>();

        for (SimpleEntry<Integer, String> key : requests.keySet()) {
            if (key.getValue().equals(uId)) {
                matchingRequests.add(requests.get(key));
            }
        }

        return matchingRequests;
	}


	public boolean handleRequest(Request req, boolean v, String str) {
		if(req != null)
		{
			req.setResponderID(AuthStore.getCurUser().getUserID());
			if(v) {
				req.setStatus(RequestStatus.ACCEPTED);
				String id = req.getRequesterID();
				
				Student s = DataStore.getStudents().get(id);
				if(s.getRole() == UserRole.CCM)
					s.setPoints(s.getPoints() + 1); // give one point for accepted suggestion
				//Need to add 1 point for approved suggestion
			}
			else 
				req.setStatus(RequestStatus.REJECTED);

			return true;
		}
		return false;
	}

	
}
