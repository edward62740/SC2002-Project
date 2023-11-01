package models;

import enums.RequestStatus;

public class SuggestionRequest extends Request {
	public SuggestionRequest(String id, Integer campID, String content) {
		super(id, campID, content);
		// TODO Auto-generated constructor stub
	}

	public boolean approve(boolean approve, String content, String responder) {
		if (this.getStatus() == RequestStatus.PENDING) {
			this.setResponse(content);
			this.setResponderID(responder);
			if (approve)
				this.setStatus(RequestStatus.ACCEPTED);
			else
				this.setStatus(RequestStatus.REJECTED);
			return true;
		}
		return false;
	}
}
