package models;

import enums.RequestStatus;

public class Request {
	private String requesterID;
	private String responderID;
	
	private RequestStatus status;
	
	private String content;
	private String response;
	
	private Integer campID;
	
	public Request(String id, Integer campId, String content)
	{
		this.requesterID = id;
		this.campID = campId;
		this.content = content;
		this.status = RequestStatus.PENDING;
	}
	public String getRequesterID() {
		return requesterID;
	}
	public void setRequesterID(String requesterID) {
		this.requesterID = requesterID;
	}
	public String getResponderID() {
		return responderID;
	}
	public void setResponderID(String responderID) {
		this.responderID = responderID;
	}
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}

	
	
	
}
