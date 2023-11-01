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
	
	/* Integer array of camp ids that this user is registered to */
    private ArrayList<Integer> camps;
    
    /* Integer array of camp ids that this user was registered to. camps AND prevCamps must be pairwise disjoint or 
     * undefined behavior */
    private ArrayList<Integer> prevCamps;
    
    /* Integer of campid that this student is CCM of iff role == CCM. invalid otherwise. */
    private Integer committee;
    
    /* Points of student, valid iff role == CCM. invalid otherwise. */
    private int points;

    public Student(String id, UserGroup fac) {
    	super(id, fac, UserRole.STUDENT);
        camps = new ArrayList<Integer>();
        prevCamps = new ArrayList<Integer>();
        committee = null;
        setPoints(0);
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

	public int getPoints() {
		if(this.getRole() != UserRole.CCM) return 0;
		return points;
	}

	public boolean setPoints(int points) {
		if(this.getRole() != UserRole.CCM) return false;
		this.points = points;
		return true;
	}
}