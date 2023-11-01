package services;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
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
	
	utils.RangeChecker<LocalDateTime> dateChecker = new utils.RangeChecker<>();

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
			// add camp id to student entity AND add student username to camp entity
			s.getCamps().add(id);
			DataStore.getCamps().get(id).getRegisteredStudents().add(s.getUserID());
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
			// "promote" student entity to CCM
			s.setCommittee(id);
			s.setRole(UserRole.CCM);
			
			// add camp id to student entity if not already present
			s.getCamps().add(id);
			// remove student username if already registered as normal member
			camp.getRegisteredStudents().remove(s);
			
			// add student username to committee
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
		// UNDO add camp id to student entity AND add student username to camp entity
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if(camp != null)
		{
			Student s = (Student) AuthStore.getCurUser();
			camp.getRegisteredStudents().remove(s.getUserID());
			if((s.getCamps().contains(id)))
			{
				return s.removeCamps(id);
			}
		}
		return false;
	}
}
