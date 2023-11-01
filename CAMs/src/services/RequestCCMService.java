package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import enums.RequestStatus;
import models.EnquiryRequest;
import models.Request;
import models.Student;
import models.SuggestionRequest;
import stores.AuthStore;
import stores.DataStore;

public class RequestCCMService extends RequestStudentService  {
	
	public ArrayList<EnquiryRequest> getEnquiriesAsCCM()
	{
		Student s = (Student) AuthStore.getCurUser();
		return super.getRequestByCamp(s.getCommittee());
	}
	
	public boolean isPendingResponseEnquiry(Request req)
	{
		if(req != null)
		{
			if(req.getStatus() == RequestStatus.PENDING) return true;
		}
		return false;
	}
	
	public boolean isOwnEnquiry(Request req)
	{
		if(req != null)
		{
			if(req.getRequesterID() == AuthStore.getCurUser().getUserID()) return true;
		}
		return false;
	}
	public boolean respondToEnquiry(Request req, String resp)
	{
		if(req != null)
		{
			req.setResponderID(AuthStore.getCurUser().getUserID());
			req.setResponse(resp);
			req.setStatus(RequestStatus.REPLIED);
			Student s = (Student) AuthStore.getCurUser();
			s.setPoints(s.getPoints() + 1); // give one point for responding
			return true;
		}
		return false;
	}
	
	public ArrayList<SuggestionRequest> getOwnSuggestions()
	{	
		return getSuggestionRequestsByUser(AuthStore.getCurUser().getUserID());
	}
	
	public boolean createNewSuggestion(Integer id)
	{
		return false;
	}
	
	public ArrayList<SuggestionRequest> getSuggestionRequestsByUser(String uId)
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
}
