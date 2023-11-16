package models;

import enums.RequestStatus;
import enums.UserRole;
import stores.AuthStore;


/**
 * The {@link SuggestionRequest} class defines an suggestion request that CCMs will create on a specific camp.
 * It extends {@link Request} class, which contains the basic information of a request.
 */
public class SuggestionRequest extends Request {
	
	/**
	 * Constructor to create a SuggestionRequest
	 * @param id the ID of the requester
	 * @param campID the ID of the associated camp
	 * @param content the content of the request
	 */
	public SuggestionRequest(String id, Integer campID, String content) {
		super(id, campID, content);
		if(AuthStore.getCurUser().getRole() == UserRole.CCM)
		{
			Student s = (Student) AuthStore.getCurUser();
		}
		// TODO Auto-generated constructor stub
	}

	/**
	 * Performs low-level logical operations to place the {@link SuggestionRequest} in a ACCEPTED/REJECTED state.
	 * @param approve flag to indicate whether to accept or not
	 * @param content the content of the reply
	 * @param responder the ID of the responder
	 * @return true if pending approval false otherwise
	 */
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
