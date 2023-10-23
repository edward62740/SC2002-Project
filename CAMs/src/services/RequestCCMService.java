package services;

import java.util.ArrayList;

import enums.RequestStatus;
import models.Request;
import models.Student;
import stores.AuthStore;

public class RequestCCMService extends RequestStudentService {
	
	public ArrayList<Request> getRequestsAsCCM()
	{
		Student s = (Student) AuthStore.getCurUser();
		return super.getRequestsByCamp(s.getCommittee());
	}
	
	public boolean isPendingResponseRequest(Request req)
	{
		if(req != null)
		{
			if(req.getStatus() == RequestStatus.PENDING) return true;
		}
		return false;
	}
	
	public boolean isOwnRequest(Request req)
	{
		if(req != null)
		{
			if(req.getRequesterID() == AuthStore.getCurUser().getUserID()) return true;
		}
		return false;
	}
	public boolean respondToRequest(Request req, String resp)
	{
		if(req != null)
		{
			req.setResponderID(AuthStore.getCurUser().getUserID());
			req.setResponse(resp);
			req.setStatus(RequestStatus.REPLIED);
			return true;
		}
		return false;
	}
}
