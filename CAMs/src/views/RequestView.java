package views;


import enums.RequestStatus;
import models.Request;

public class RequestView {
	private RequestView() {};
	public static void printCamp(Request req) {
		System.out.println("--------------------------------------");
		System.out.println("Requester: " + req.getRequesterID());
		System.out.println("Status: " + req.getStatus());
		if(req.getStatus() != RequestStatus.PENDING) System.out.println("Answered by: " + req.getResponderID());
		else System.out.println("Pending Response");
		System.out.println("Content: " + req.getContent());
		
	}
}
