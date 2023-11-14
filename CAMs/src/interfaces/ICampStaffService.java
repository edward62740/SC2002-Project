package interfaces;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import enums.UserGroup;
import models.Camp;

public interface ICampStaffService {
	public boolean createACamp(String name, UserGroup userGroup, String location, Integer totalSlots, Integer ccmSlots,
			String staff, String description, LocalDateTime closingDate,
			SimpleEntry<LocalDateTime, LocalDateTime> dates);

	public boolean editACamp(int id, int choice);

	public boolean deleteACamp(Integer id);

	public ArrayList<Camp> getCamps(UserGroup userGroup, boolean showInvisible);

	public ArrayList<Camp> getOwnedCamps();

	public boolean isCampOwned(Integer id);

	public void generateAReport();
}
