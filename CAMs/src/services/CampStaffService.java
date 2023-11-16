package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;

import enums.UserGroup;
import enums.UserRole;
import interfaces.ICampStaffService;
import models.Camp;

import models.Staff;
import stores.AuthStore;
import stores.DataStore;
import views.CampView;

/**
 * The {@link CampStaffService} implements {@link ICampStaffService}, and provides camp related functionalities for staff.
 */
public class CampStaffService implements ICampStaffService {
	static Scanner sc = new Scanner(System.in);

	public boolean createACamp(String name, UserGroup userGroup, String location, Integer totalSlots, Integer ccmSlots,
			String staff, String description, LocalDateTime closingDate,
			SimpleEntry<LocalDateTime, LocalDateTime> dates) {

		Integer campId = DataStore.getCampIndexCur();
		Camp newcamp = new Camp(campId, name, userGroup, location, totalSlots, ccmSlots, staff, description,
				closingDate);
		newcamp.getDates().add(dates);
		((Staff) AuthStore.getCurUser()).addOwnedCamps(campId);

		DataStore.getCamps().put(campId, newcamp);
		return true;
	}


	public boolean editACamp(int id, int choice) {
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		switch (choice) {
		case 1:
			String newname = utils.InputParser.parseInString(sc, "Enter new camp name", 0, "C");
			// set name
			camp.setName(newname);
			// System.out.println("Camp name successfully edited.");
			break;
		case 2:
			String newloc = utils.InputParser.parseInString(sc, "Enter new camp location", 0, "C");
			// set location
			camp.setLocation(newloc);
			// System.out.println("Camp location successfully edited.");
			break;
		case 3:
			String newdesc = utils.InputParser.parseInString(sc, "Enter new camp description", 0, "C");
			// set new description
			camp.setDescription(newdesc);
			// System.out.println("Camp description successfully edited.");
			break;
		case 4:
			LocalDateTime dt = null;
			do {
				dt = utils.InputParser.parseInLocalDateTime(sc,
						"Please enter a valid date and time in the format yyyy-MM-dd HH:mm.", 0, "C");
			} while (dt == null);
			// set new description
			camp.setClosingDate(dt);

			break;
		case 5:

			CampView.printDateArray(camp);
			Integer sel = null;
			do {
				sel = utils.InputParser.parseInInteger(sc, "Enter index of date to edit or -1 to add.", -1,
						camp.getDates().size(), 1, "C");
			} while (sel == null);
			System.out.println("Please enter a valid date and time in the format yyyy-MM-dd HH:mm");
			LocalDateTime dt1 = null;
			LocalDateTime dt2 = null;
			do {
				dt1 = utils.InputParser.parseInLocalDateTime(sc, "Starting date: ", 0, "C");
			} while (dt1 == null);
			do {
				dt2 = utils.InputParser.parseInLocalDateTime(sc, "Ending date: ", 0, "C");
			} while (dt2 == null);

			if (dt2.isBefore(dt1)) {
				System.out.println("Starting date must be BEFORE ending date");
				return false;
			}

			SimpleEntry<LocalDateTime, LocalDateTime> newEntry = new SimpleEntry<>(dt1, dt2);
			camp.getDates().add(newEntry);

			// LocalDateTime dt = utils.InputParser.parseInLocalDateTime(sc, "Enter new camp
			// description", 0, "C");
			// set new description
			// camp.setClosingDate(dt);

			break;

		}
		return true;
	}

	public boolean deleteACamp(Integer id) {
		// UNDO add camp id to student entity AND add student username to camp entity
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if (camp != null) {
			if (!isCampOwned(id))
				return false; // ensure that this never happens unless valid owner
			// remove enum CCM from committee since committee has only one such camp they are committee of.
			ArrayList<String> committee = camp.getCommittee();
			for(String s : committee)
			{
				if(DataStore.getStudents().get(s) != null)
				{
					DataStore.getStudents().get(s).setRole(UserRole.STUDENT);
					DataStore.getStudents().get(s).setPoints(0); //rst points
				}
			}
			camps.remove(id);
			return true;
		}
		return false;
	}

	public ArrayList<Camp> getCamps(UserGroup userGroup, boolean showInvisible) {
		HashMap<Integer, Camp> camps = DataStore.getCamps();

		ArrayList<Camp> ret = camps.values().stream()
				.filter(c -> ((c.getUserGroup() == userGroup) || userGroup == UserGroup.ALL)
						&& (c.isVisible() || showInvisible == true))
				.collect(Collectors.toCollection(ArrayList::new));
		return ret;
	}

	public ArrayList<Camp> getOwnedCamps() {
		Staff st = (Staff) AuthStore.getCurUser();
		ArrayList<Integer> ref = st.getOwnedCamps();
		HashMap<Integer, Camp> camps = DataStore.getCamps();

		ArrayList<Camp> ownedCamps = new ArrayList<Camp>();

		for (Integer campId : ref) {
			Camp camp = camps.get(campId);
			if (camp != null) {
				ownedCamps.add(camp);
			}
		}

		return ownedCamps;

	}

	public boolean isCampOwned(Integer id) {
		if ((((Staff) AuthStore.getCurUser()).getOwnedCamps().contains(id)))
			return true;
		else
			return false;
	}

	public void generateAReport() {

	}
}
