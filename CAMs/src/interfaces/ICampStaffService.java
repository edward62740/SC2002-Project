package interfaces;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import enums.UserGroup;
import models.Camp;

/**
 * The {@link ICampStaffService} interface defines a contract for
 * staff related camp services.
 */
public interface ICampStaffService {
	/**
	 * Creates a new {@link Camp}.
	 * @param name the name of the camp
	 * @param userGroup the {@link UserGroup} of the camp
	 * @param location the location of the camp
	 * @param totalSlots the total number of slots for students
	 * @param ccmSlots the total number of slots for committee members
	 * @param staff the staff in-charge
	 * @param description description of the camp
	 * @param closingDate the {@link LocalDateTime} indicating the camp registration deadline
	 * @param dates the list of {@link LocalDateTime} indicating the dates the camp runs on.
	 * @return
	 */
	public boolean createACamp(String name, UserGroup userGroup, String location, Integer totalSlots, Integer ccmSlots,
			String staff, String description, LocalDateTime closingDate,
			SimpleEntry<LocalDateTime, LocalDateTime> dates);

	/**
	 * Edit a camp based on its ID and a choice of parameter.
	 * @param id the ID of the camp to edit
	 * @param choice the index of which parameter to edit
	 * @return true if success false otherwise
	 */
	public boolean editACamp(int id, int choice);

	/**
	 * Delete a camp based on its ID
	 * @param id the ID of the camp to delete
	 * @return true if success false otherwise
	 */
	public boolean deleteACamp(Integer id);

	/**
	 * Get a reference to a list of camps, filtered by params.
	 * @param userGroup {@link UserGroup} to filter by
	 * @param showInvisible visibility to filter by
	 * @return {@link ArrayList} of {@link Camp}
	 */
	public ArrayList<Camp> getCamps(UserGroup userGroup, boolean showInvisible);

	/**
	 * Get a reference to a list of camps owned by the user currently active in {@code AuthStore}.
	 * @return {@link ArrayList} of {@link Camp}
	 */
	public ArrayList<Camp> getOwnedCamps();

	/**
	 * Check if camp is owned by the user currently active in {@code AuthStore}.
	 * @param id the ID of the camp to check
	 * @return true if owned false otherwise
	 */
	public boolean isCampOwned(Integer id);

	/**
	 * Generate a CSV report of the camps owned by the user currently active in {@code AuthStore}.
	 */
	public void generateAReport();
}
