package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import enums.UserGroup;
import enums.UserRole;
import models.Camp;
import models.Student;
import stores.AuthStore;
import stores.DataStore;

/* review: downcasting here should be safe... since something else would have gone horribly wrong if authstore user
 * contains reference to wrong type or null while controller is running.
 */
public class CampStudentService {

	public boolean existCamp(Integer id)
	{
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if(camp == null) return false;
		else return true;
	}
	
	
	public ArrayList<Camp> getCamps(UserGroup userGroup, boolean showInvisible) {
		HashMap<Integer, Camp> camps = DataStore.getCamps();

		ArrayList<Camp> ret = camps.values().stream()
				.filter(c -> ((c.getUserGroup() == userGroup) || userGroup == UserGroup.ALL)
						&& (c.isVisible() || showInvisible == true))
				.collect(Collectors.toCollection(ArrayList::new));
		return ret;
	}

	public ArrayList<Camp> getRegisteredCamps() {
		Student s = (Student) AuthStore.getCurUser();
		ArrayList<Integer> ref = s.getCamps();
		HashMap<Integer, Camp> camps = DataStore.getCamps();

		ArrayList<Camp> registeredCamps = new ArrayList<Camp>();

		for (Integer campId : ref) {
			Camp camp = camps.get(campId);
			if (camp != null) {
				registeredCamps.add(camp);
			}
		}

		return registeredCamps;

	}

	public boolean registerForCamp(Integer id) {
		if (existCamp(id)) {
			Student s = (Student) AuthStore.getCurUser();
			s.getCamps().add(id);
			return true;
		} else
			return false;

	}
	
	public boolean registerForCCM(Integer id)
	{
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if (existCamp(id)) {
			Student s = (Student) AuthStore.getCurUser();
			s.setCommittee(id);
			s.setRole(UserRole.CCM);
			s.getCamps().add(id);
			camp.getCommittee().add(s.getUserID());
			return true;
		}
		else return false;
		
	}
	public boolean existVacancyMember(Integer id)
	{
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if (existCamp(id)) {
			if(camp.getTotalSlots() - camp.getRegisteredStudents().size() > 0) return true;
			return false;
		}
		else return false;
		
	}
	public boolean existVacancyCCM(Integer id)
	{
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if (existCamp(id)) {
			if(camp.getCcmSlots() - camp.getCommittee().size() > 0) return true;
			return false;
		}
		else return false;
		
	}
	

	
	public boolean isPreviouslyRegistered(Integer id)
	{
		if((((Student)AuthStore.getCurUser()).getPrevCamps().contains(id))) return true;
		else return false;
	}
	public boolean isAlreadyRegistered(Integer id)
	{
		if((((Student)AuthStore.getCurUser()).getCamps().contains(id))) return true;
		else return false;
	}
	
	public boolean isAlreadyCCM(Integer id)
	{
		Student s = (Student) AuthStore.getCurUser();
		if((s.getCommittee() == id)) return true;
		else return false;
	}
	
	public boolean deregisterFromCamp(Integer id)
	{
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if(camp != null)
		{
			Student s = (Student) AuthStore.getCurUser();
			if((s.getCamps().contains(id)))
			{
				return s.removeCamps(id);
			}
		}
		return false;
	}
}
