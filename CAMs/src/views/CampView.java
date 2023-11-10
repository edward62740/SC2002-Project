package views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;

import enums.UserRole;
import models.Camp;
import models.User;

public class CampView {
	private CampView() {};
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	
	public static void printCamp(Camp camp, User user) {
		System.out.println("--------------------------------------");
		System.out.println("Camp name: " + camp.getName());
		System.out.println("ID: " + camp.getCampId());
		System.out.println("Description: " + camp.getDescription());
		System.out.println("Faculty: " + camp.getUserGroup());
		System.out.println("Location: " + camp.getLocation());
		System.out.println("Staff in-charge: " + camp.getStaff());
		System.out.println("Remaining Member Slots: " + (camp.getTotalSlots() - camp.getRegisteredStudents().size() + " out of " + camp.getTotalSlots()));
		System.out.println("Remaining Committee Slots: " + (camp.getCcmSlots() - camp.getCommittee().size() + " out of " + camp.getCcmSlots()));
		System.out.println("Registration closing date: " + camp.getClosingDate().format(formatter));
		System.out.println("Camp dates: ");
		for(SimpleEntry<LocalDateTime, LocalDateTime> d : camp.getDates())
		{
			System.out.println(d.getKey().format(formatter) + " - " + d.getValue().format(formatter));
		}
		if(user.getRole() == UserRole.STAFF || user.getRole() == UserRole.CCM)
		{
			System.out.println("List of committee members: ");
			for(String s : camp.getCommittee()) System.out.print(s + ", ");
			System.out.println("");
			System.out.println("List of normal members: ");
			for(String s : camp.getRegisteredStudents()) System.out.print(s + ", ");
			System.out.println("");
		}
		System.out.println("--------------------------------------");
	}
	public static void printPosition(Camp camp, User user) {
		if(camp.getCommittee().contains(user.getUserID())) System.out.println("Position: Committee");
		else if(camp.getRegisteredStudents().contains(user.getUserID())) System.out.println("Position: Member");
		else System.out.println("Position: None");
	}
	

}
