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

/**
 * The {@link ICampStudentService} interface defines a contract for student
 * related camp services.
 */
public interface ICampStudentService {

	/**
	 * Checks if a camp with a given ID exists in {@link DataStore}.
	 * 
	 * @param id the ID of camp to check for existence
	 * @return true if camp exists false otherwise
	 */
	public boolean existCamp(Integer id);

	/**
	 * Get a reference to a list of camps, filtered by params.
	 * 
	 * @param userGroup     {@link UserGroup} to filter by
	 * @param showInvisible visibility to filter by
	 * @return {@link ArrayList} of {@link Camp}
	 */
	public ArrayList<Camp> getCamps(UserGroup userGroup, boolean showInvisible);

	/**
	 * Get a reference to a list of camps that the user currently active in
	 * {@link AuthStore} is registered to.
	 * 
	 * @return {@link ArrayList} of {@link Camp}
	 */
	public ArrayList<Camp> getRegisteredCamps();

	/**
	 * Register the user currently active in {@link AuthStore} to a camp with a
	 * given ID.
	 * 
	 * @param id the ID of the camp to register for
	 * @return true if success false otherwise
	 */
	public boolean registerForCamp(Integer id);

	/**
	 * Register the user currently active in {@link AuthStore} to a camp (as CCM)
	 * with a given ID.
	 * 
	 * @param id the ID of the camp to register for
	 * @return true if success false otherwise
	 */
	public boolean registerForCCM(Integer id);

	/**
	 * Check if a camp with a given ID has vacancies for normal members.
	 * 
	 * @param id the ID of the camp to check for vacancies
	 * @return true if vacancy exists false otherwise
	 */
	public boolean existVacancyMember(Integer id);

	/**
	 * Check if a camp with a given ID has vacancies for committee members.
	 * 
	 * @param id the ID of the camp to check for vacancies
	 * @return true if vacancy exists false otherwise
	 */
	public boolean existVacancyCCM(Integer id);

	/**
	 * Check if the user currently active in {@link AuthStore} has previously been
	 * registered to the camp with the given ID.
	 * 
	 * @param id the ID of the camp to check previous registration
	 * @return true if previously registered false otherwise
	 */
	public boolean isPreviouslyRegistered(Integer id);

	/**
	 * Check if the user currently active in {@link AuthStore} is already registered
	 * to the camp with the given ID.
	 * 
	 * @param id the ID of the camp to check current registration
	 * @return true if already registered false otherwise
	 */
	public boolean isAlreadyRegistered(Integer id);

	/**
	 * Check if the user currently active in {@link AuthStore} is already registered
	 * to the camp (as CCM) with the given ID.
	 * 
	 * @param id the ID of the camp to check current registration
	 * @return true if already registered false otherwise
	 */
	public boolean isAlreadyCCM(Integer id);

	/**
	 * Deregister the user currently active in {@link AuthStore} from a camp with
	 * the given ID.
	 * 
	 * @param id the ID of the camp to deregister from
	 * @return true if success false otherwise
	 */
	public boolean deregisterFromCamp(Integer id);

	/**
	 * Check if the camp with the given ID clashes with any existing camps that the
	 * user currently active in {@link AuthStore} is registered to.
	 * 
	 * @param id the ID of the camp to perform check on
	 * @return true if success false otherwise
	 */
	public boolean isCampNotClashWithExisting(Integer id);

	/**
	 * @param id
	 * @return
	 */
	public boolean isCampDateBeforeDeadline(Integer id);

	/**
	 * 
	 */
	public void removeNullCampReference();

}
