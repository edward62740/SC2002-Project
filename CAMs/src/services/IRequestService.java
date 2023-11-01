package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import enums.RequestStatus;
import models.EnquiryRequest;
import models.Request;
import models.Student;
import stores.AuthStore;
import stores.DataStore;

public interface IRequestService {
	
	public boolean createNewRequest(String content, Integer campId);
	
	public boolean deleteRequest(Integer campId);
	
	public boolean editRequest(Integer campId, String content);
	
	public boolean isQueuedRequestForUser(Integer campId);

	
	public ArrayList<? extends Request> getRequestByCamp(Integer campId);

	public ArrayList<? extends Request> getRequestByUser(String uId);

}
