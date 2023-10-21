package models;

import java.util.ArrayList;

import enums.UserGroup;
import enums.UserRole;

/* it seems sufficient to use enum and an Integer to represent a CCM and which camp they oversee (if any)
 * rather than extending class, because that will require delete and new whenever a student gets promoted, 
 * separate controller and separate hashmap to store
 * 
 */
public class Student extends User{
    private ArrayList<Integer> camps;
    private ArrayList<Integer> prevCamps;
    private Integer committee;

    public Student(String id, UserGroup fac) {
    	super(id, fac, UserRole.STUDENT);
        camps = new ArrayList<Integer>();
        prevCamps = new ArrayList<Integer>();
        committee = null;
     
    }

    public ArrayList<Integer> getCamps() {
        return camps;
    }
    
    public boolean addCamp(Integer k)
    {
    	return camps.add(k);
    }

    public boolean removeCamps(Integer k) {
        if (camps.contains(k)) {
            camps.remove(k);
            prevCamps.add(k);
            return true;
        }
        return false;
    }

	public ArrayList<Integer> getPrevCamps() {
		return prevCamps;
	}

	public Integer getCommittee() {
		return committee;
	}

	public void setCommittee(Integer committee) {
		this.committee = committee;
	}
}