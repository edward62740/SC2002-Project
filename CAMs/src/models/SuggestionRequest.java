package models;

import enums.RequestStatus;
import enums.UserRole;
import stores.AuthStore;

public class SuggestionRequest extends Request {
	public SuggestionRequest(String id, Integer campID, String content) {
		super(id, campID, content);
		if(AuthStore.getCurUser().getRole() == UserRole.CCM)
		{
			Student s = (Student) AuthStore.getCurUser();
			s.setPoints(s.getPoints() + 1); // give one point for responding

		}
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
