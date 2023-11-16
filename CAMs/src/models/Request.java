package models;

import enums.RequestStatus;

/**
 * The {@link Request} class defines a request object which contains the IDs of the requester and responder, the contents of the request and 
 * a {@link RequestStatus} enum to indicate the status of the request. 
 */
public class Request {
	/**
	 * String ID of the requester.
	 */
	private String requesterID;
	/**
	 * String ID of the responder.
	 */
	private String responderID;
	
	/**
	 * {@link RequestStatus} enum to indicate status of this request.
	 */
	private RequestStatus status;
	
	/**
	 * String to store the content of the request.
	 */
	private String content;
	/**
	 * String to store the content of the response.
	 */
	private String response;
	
	/**
	 * Integer to hold the associated camp ID.
	 */
	private Integer campID;
	
	/**
	 * Constructor to create a request.
	 * @param id the ID of the requester
	 * @param campId the ID of the associated camp
	 * @param content the content of the request
	 */
	public Request(String id, Integer campId, String content)
	{
		this.requesterID = id;
		this.setCampID(campId);
		this.content = content;
		this.status = RequestStatus.PENDING;
	}
	/**
	 * Gets the requester's ID.
	 *
	 * @return The requester's ID.
	 */
	public String getRequesterID() {
	    return requesterID;
	}

	/**
	 * Sets the requester's ID.
	 *
	 * @param requesterID The new requester's ID to set.
	 */
	public void setRequesterID(String requesterID) {
	    this.requesterID = requesterID;
	}

	/**
	 * Gets the responder's ID.
	 *
	 * @return The responder's ID.
	 */
	public String getResponderID() {
	    return responderID;
	}

	/**
	 * Sets the responder's ID.
	 *
	 * @param responderID The new responder's ID to set.
	 */
	public void setResponderID(String responderID) {
	    this.responderID = responderID;
	}

	/**
	 * Gets the status of the request.
	 *
	 * @return The request status.
	 */
	public RequestStatus getStatus() {
	    return status;
	}

	/**
	 * Sets the status of the request.
	 *
	 * @param status The new request status to set.
	 */
	public void setStatus(RequestStatus status) {
	    this.status = status;
	}

	/**
	 * Gets the content of the request.
	 *
	 * @return The content of the request.
	 */
	public String getContent() {
	    return content;
	}

	/**
	 * Sets the content of the request.
	 *
	 * @param content The new content to set.
	 */
	public void setContent(String content) {
	    this.content = content;
	}

	/**
	 * Gets the response to the request.
	 *
	 * @return The response to the request.
	 */
	public String getResponse() {
	    return response;
	}

	/**
	 * Sets the response to the request.
	 *
	 * @param response The new response to set.
	 */
	public void setResponse(String response) {
	    this.response = response;
	}

	/**
	 * Gets the camp ID associated with the request.
	 *
	 * @return The camp ID.
	 */
	public Integer getCampID() {
	    return campID;
	}

	/**
	 * Sets the camp ID associated with the request.
	 *
	 * @param campID The new camp ID to set.
	 */
	public void setCampID(Integer campID) {
	    this.campID = campID;
	}

	
	
}
