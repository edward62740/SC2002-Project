package interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import enums.RequestStatus;
import models.EnquiryRequest;
import models.Request;
import models.Student;
import stores.AuthStore;
import stores.DataStore;


/**
 * The {@link IRequestService} interface defines a contract for
 * managing requests.
 */
public interface IRequestService {
	
	/**
	 * Create a new {@link Request}.
	 * @param content the content of the request
	 * @param campId the camp ID this {@link Request} is associated with
	 * @return true if success false otherwise
	 */
	public boolean createNewRequest(String content, Integer campId);
	
	/**
	 * Deletes a {@link Request}.
	 * @param campId the camp ID associated with the {@link Request} to be deleted.
	 * @return true if success false otherwise
	 */
	public boolean deleteRequest(Integer campId);
	
	/**
	 * Updates the content of a {@link Request}.
	 * @param campId the camp ID associated with the {@link Request} to be edited
	 * @param content the content to replace with
	 * @return true if success false otherwise
	 */
	public boolean editRequest(Integer campId, String content);
	
	/**
	 * Check if a {@link Request} for a specific user and camp pair already exists.
	 * The user is the currently logged in user in {@link AuthStore}.
	 * @param campId the camp ID to check
	 * @return true if exists false otherwise
	 */
	public boolean isQueuedRequestForUser(Integer campId);

	
	/**
	 * Get a list of {@link Request} filtered by a specific camp ID.
	 * @param campId the camp ID to filter by
	 * @return {@link ArrayList} of {@link Request}
	 */
	public ArrayList<? extends Request> getRequestByCamp(Integer campId);

	/**
	 * Get a list of {@link Request} filtered by a specific user ID.
	 * @param uId the user ID to filter by
	 * @return {@link ArrayList} of {@link Request}
	 */
	public ArrayList<? extends Request> getRequestByUser(String uId);

}
