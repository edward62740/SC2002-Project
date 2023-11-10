package models;
import java.util.ArrayList;
import enums.UserGroup;
import enums.UserRole;

public class Staff extends User{
	
	/* Integer array of camp ids that this user has created */
    private ArrayList<Integer> ownedCamps;
    
	public Staff(String id, UserGroup fac) {
		super(id, fac, UserRole.STAFF);
	    ownedCamps = new ArrayList<Integer>();
    }
	
	public ArrayList<Integer> getOwnedCamps() {
		return ownedCamps;
	}
	
    public boolean addOwnedCamps(Integer k)
    {
    	return ownedCamps.add(k);
    }


}