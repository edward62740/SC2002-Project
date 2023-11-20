package views;


import enums.RequestStatus;
import models.EnquiryRequest;
import models.Request;

/**
 * The {@link RequestView} provides functions to print out the content of a request to CLI.
 */
public class RequestView {

	private RequestView() {};
	/**
	 * Prints the content of a given {@link Request}.
	 * @param req the request to pri nt
	 */
	public static void printReq(Request req) {
		
		System.out.println("--------------------------------------");
		System.out.println("Requester: " + req.getRequesterID());
		System.out.println("Status: " + req.getStatus());
		if(req.getStatus() != RequestStatus.PENDING) System.out.println("Answered by: " + req.getResponderID());
		else System.out.println("Pending Response");
		System.out.println("Content: " + req.getContent());
		if(req instanceof EnquiryRequest) System.out.println("Response: " + req.getResponse());
		System.out.println("--------------------------------------");
	}
}
