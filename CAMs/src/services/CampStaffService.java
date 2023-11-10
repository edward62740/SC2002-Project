package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import enums.UserGroup;
import models.Camp;
import models.String;
import models.Student;
import models.Staff;
import stores.AuthStore;
import stores.DataStore;

public class CampStaffService {
	
	public boolean createACamp(String name, UserGroup userGroup, String location, Integer totalSlots, Integer ccmSlots, String staff, String description) {
		
		Integer campId = DataStore.getCampIndexCur();
		Camp newcamp = new Camp(campId, name, userGroup, location, totalSlots, ccmSlots, staff, description);
		((Staff)AuthStore.getCurUser()).addOwnedCamps(campId);
		
		DataStore.getCamps().put(campId, newcamp);
		return true;
	}
	// HELP/////////////////////////////
	public void editACamp() {
		
	}
	
	// HELP////////////////////////////
	public boolean deleteACamp(Integer id) {
		// UNDO add camp id to student entity AND add student username to camp entity
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if(camp != null)
		{
			if(!isCampOwned(id)) return false; // ensure that this never happens unless valid owner
			// (HELP) Need help to implement a for loop to iterate through the list of registered students to delete them
			// no need.. just delete from hashmap

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
		if((((Staff)AuthStore.getCurUser()).getOwnedCamps().contains(id))) return true;
		else return false;
	}
	public void generateAReport() {
		
	}
}
