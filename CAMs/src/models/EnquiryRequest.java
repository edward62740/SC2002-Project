package models;

import enums.RequestStatus;

public class EnquiryRequest extends Request {

	private String response; 
	public EnquiryRequest(String id, Integer campID, String content) {
		super(id, campID, content);
		// TODO Auto-generated constructor stub
	}
	
	public boolean respond(String content, String responder)
	{
		if(this.getStatus() == RequestStatus.PENDING)
		{
			this.response = content;
			this.setResponderID(responder);
			this.setStatus(RequestStatus.REPLIED);
			return true;
		}
		return false;
	}
}
