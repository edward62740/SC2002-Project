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
		
	    String reset = "\u001B[0m";
	    String yellow = "\u001B[33m";
	    String cyan = "\u001B[36m";
	    String magenta = "\u001B[35m";

	    System.out.println(yellow + "--------------------------------------" + reset);
	    System.out.println(cyan + "Requester: " + req.getRequesterID() + reset);
	    System.out.println(cyan + "Status: " + req.getStatus() + reset);
	    if (req.getStatus() != RequestStatus.PENDING)
	        System.out.println(cyan + "Answered by: " + req.getResponderID() + reset);
	    else
	        System.out.println(magenta + "Pending Response" + reset);
	    System.out.println(cyan + "Content: " + req.getContent() + reset);
	    if (req instanceof EnquiryRequest)
	        System.out.println(magenta + "Response: " + req.getResponse() + reset);
	    System.out.println(yellow + "--------------------------------------" + reset);
	}
}
