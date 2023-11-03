package services;

import enums.UserGroup;
import models.Camp;
import models.String;
import models.Student;
import models.Staff;
import stores.AuthStore;
import stores.DataStore;

public class CampStaffService {
	
	public void createACamp(int campId, String name, UserGroup userGroup, String location, int totalSlots, int ccmSlots, String staff, String description) {
		Camp newcamp = new Camp(int campId, String name, UserGroup userGroup, String location, int totalSlots, int ccmSlots, String staff, String description);
		Staff.addOwnedCamp(campId);
	}
	// HELP/////////////////////////////
	public void editACamp() {
		
	}
	
	// HELP////////////////////////////
	public void deleteACamp(Integer id) {
		// UNDO add camp id to student entity AND add student username to camp entity
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if(isCampOwned(id))
		{
			Staff st = (Staff) AuthStore.getCurUser();
			// (HELP) Need help to implement a for loop to iterate through the list of registered students to delete them
			
			camp.getRegisteredStudents().remove(s.getUserID());
			if((s.getCamps().contains(id)))
			{
				return s.removeCamps(id);
			}
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
		ArrayList<Integer> ref = st.CampsCreated();
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
	
	public boolean isCampOwned(integer id) {
		if((((Staff)AuthStore.getCurUser()).CampsCreated().contains(id))) return true;
		else return false;
	}
	public void generateAReport() {
		
	}
}
