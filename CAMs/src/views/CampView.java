package views;

import models.Camp;
import models.User;

public class CampView {
	private CampView() {};
	public static void printCamp(Camp camp) {
		System.out.println("--------------------------------------");
		System.out.println("Camp name: " + camp.getName());
		System.out.println("ID: " + camp.getCampId());
		System.out.println("Description: " + camp.getDescription());
		System.out.println("Faculty: " + camp.getUserGroup());
		
	}
	public static void printPosition(Camp camp, User user) {
		if(camp.getCommittee().contains(user.getUserID())) System.out.println("Position: Committee");
		else  System.out.println("Position: Member");
	}
	

}
