package views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.IntStream;

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
		if(camp.getClosingDate() != null)System.out.println("Registration closing date: " + camp.getClosingDate().format(formatter));
		System.out.println("Camp dates: ");
		printDateArray(camp);
		
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
	
	public static void printDateArray(Camp camp)
	{
		
		if(camp.getDates() != null)
		{
			IntStream.range(0, camp.getDates().size())
            .forEach(index ->
                System.out.println(index + ": " +
                		camp.getDates().get(index).getKey().format(formatter) + " - " +
                		camp.getDates().get(index).getValue().format(formatter)
                )
            );
		}
	}
	

}
