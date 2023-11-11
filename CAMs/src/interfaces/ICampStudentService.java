package interfaces;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import enums.UserGroup;
import enums.UserRole;
import models.Camp;
import models.Student;
import stores.AuthStore;
import stores.DataStore;

public interface ICampStudentService {
	
	
	public boolean existCamp(Integer id);
	
	
	public ArrayList<Camp> getCamps(UserGroup userGroup, boolean showInvisible);
	public ArrayList<Camp> getRegisteredCamps();

	public boolean registerForCamp(Integer id);
	
	public boolean registerForCCM(Integer id);
	public boolean existVacancyMember(Integer id);
	public boolean existVacancyCCM(Integer id);
	
	public boolean isPreviouslyRegistered(Integer id);
	
	public boolean isAlreadyRegistered(Integer id);
	
	public boolean isAlreadyCCM(Integer id);
	public boolean deregisterFromCamp(Integer id);

	public boolean isCampNotClashWithExisting(Integer id);
	
	
	
	public boolean isCampDateBeforeDeadline(Integer id);
	
	public void removeNullCampReference();

}
