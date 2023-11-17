package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import enums.RequestStatus;
import enums.UserRole;
import interfaces.ICampStudentService;
import interfaces.IRequestService;
import models.EnquiryRequest;
import models.Request;
import models.Student;
import models.User;
import stores.AuthStore;
import stores.DataStore;

/**
 * The {@link EnquiryRequestService} implements {@link IRequestService}, and provides request related functionalities for enquires.
 */
public class EnquiryRequestService implements IRequestService {
	public boolean createNewRequest(String content, Integer campId)
	{
		User s = AuthStore.getCurUser();
		EnquiryRequest req = new EnquiryRequest(s.getUserID(), campId, content);
		SimpleEntry<Integer, String> p = new SimpleEntry<Integer, String>(campId, s.getUserID());
		if(DataStore.getCamps().containsKey(campId))
		{
			DataStore.getEnquiries().put(p, req);
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
			if(DataStore.getEnquiries().containsKey(p)) 
			{
				if(DataStore.getEnquiries().get(p).getStatus() != RequestStatus.PENDING) return false;
				DataStore.getEnquiries().remove(p);
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
			if(DataStore.getEnquiries().containsKey(p)) 
			{
				if(DataStore.getEnquiries().get(p).getStatus() != RequestStatus.PENDING) return false;
				DataStore.getEnquiries().get(p).setContent(content);
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
			if(DataStore.getEnquiries().containsKey(p)) return true;
		}
		return false;
	}
	
	public ArrayList<EnquiryRequest> getRequestByCamp(Integer campId)
	{
		HashMap<SimpleEntry<Integer,String>, EnquiryRequest> requests = DataStore.getEnquiries();
        ArrayList<EnquiryRequest> matchingRequests = new ArrayList<>();

        for (SimpleEntry<Integer, String> key : requests.keySet()) {
            if (key.getKey().equals(campId)) {
                matchingRequests.add(requests.get(key));
            }
        }

        return matchingRequests;
	}
	
	public ArrayList<EnquiryRequest> getRequestByUser(String uId)
	{
		HashMap<SimpleEntry<Integer,String>, EnquiryRequest> requests = DataStore.getEnquiries();
        ArrayList<EnquiryRequest> matchingRequests = new ArrayList<>();

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
			req.setResponse(str);
			req.setStatus(RequestStatus.REPLIED);
			if(AuthStore.getCurUser().getRole() == UserRole.CCM)
			{
				Student s = (Student) AuthStore.getCurUser();
				s.setPoints(s.getPoints() + 1); // give one point for responding
				return true;
			}
			else if(AuthStore.getCurUser().getRole() == UserRole.STAFF)
			{
				// staff gets no points..
				return true;
			}
		}
		return false;
	}
	
	
}
