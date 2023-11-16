package models;

import enums.RequestStatus;

/**
 * The {@link EnquiryRequest} class defines an enquiry request that students will create on a specific camp.
 * It extends {@link Request} class, which contains the basic information of a request.
 */
public class EnquiryRequest extends Request {

	/**
	 * Constructor to create a EnquiryRequest
	 * @param id the ID of the requester
	 * @param campID the ID of the associated camp
	 * @param content the content of the request
	 */
	public EnquiryRequest(String id, Integer campID, String content) {
		super(id, campID, content);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Performs low-level logical operations to place the {@link EnquiryRequest} in a REPLIED state.
	 * @param content the content of the reply
	 * @param responder the ID of the responder
	 * @return true if pending reply false otherwise
	 */
	public boolean respond(String content, String responder)
	{
		if(this.getStatus() == RequestStatus.PENDING)
		{
			this.setResponderID(responder);
			this.setStatus(RequestStatus.REPLIED);
			return true;
		}
		return false;
	}
}
