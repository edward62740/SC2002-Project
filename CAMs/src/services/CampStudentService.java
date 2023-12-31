package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import enums.UserGroup;
import enums.UserRole;
import interfaces.ICampStaffService;
import interfaces.ICampStudentService;
import models.Camp;
import models.Student;
import stores.AuthStore;
import stores.DataStore;

/**
 * The {@link CampStudentService} implements {@link ICampStudentService}, and provides camp related functionalities for staff.
 */
public class CampStudentService implements ICampStudentService{
	
	utils.RangeChecker<LocalDateTime> dateChecker = new utils.RangeChecker<LocalDateTime>(LocalDateTime::compareTo);

	/* wrapper for null check. extra calls are trivial since there is constant lookup time for hashmap */
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
			if(!s.getCamps().contains(id)) s.getCamps().add(id);
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
			
			// remove camp id to student entity if  already present
			if(!s.getCamps().contains(id)) s.getCamps().add(id);
			// remove student username if already registered as normal member
			camp.getRegisteredStudents().remove(s.getUserID());
			
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
		if(existCamp(id))
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
	
	/* cubic time complexity since this finds the intersection between 2d arrays (dates) of all camps .. */
	public boolean isCampNotClashWithExisting(Integer id)
	{
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if(existCamp(id))
		{
			Student s = (Student) AuthStore.getCurUser();
			for(Integer c : s.getCamps())
			{
				if(c == null) continue;
				if(c == id) continue; // dont check if it already exists here
				if(dateChecker.isIntersect(camp.getDates(), camps.get(c).getDates())) return false;
			}
		}
		return true;
	}
	
	
	
	public boolean isCampDateBeforeDeadline(Integer id)
	{
		HashMap<Integer, Camp> camps = DataStore.getCamps();
		Camp camp = camps.get(id);
		if(existCamp(id))
		{
			// return true if no closing date or currently before closing date
			if(camp.getClosingDate() == null) return true;
			if(LocalDateTime.now().isBefore(camp.getClosingDate())) return true;
		}
		return false;
	}
	
	public void removeNullCampReference()
	{
		
	}
}
