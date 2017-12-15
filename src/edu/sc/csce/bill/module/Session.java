package edu.sc.csce.bill.module;

import edu.sc.csce.bill.exception.UserNotFoundException;
import edu.sc.csce.bill.model.College;
import edu.sc.csce.bill.model.Permission;
import edu.sc.csce.bill.model.Role;

public class Session 
{
	private String userId;
	private String firstName;
	private String lastName;
	private Role role;
	private College college;
	//getter and setter
	/**
	 * Set the currently logged in user id.
	 * @param id
	 */
	public void setUser(String id) 
	{
		userId = id;
	}
	
	/**
	 * Get the currently logged in user id.
	 * @return Currently logged in user id.
	 */
	public String getUser() 
	{
		return userId;
	}
	
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public College getCollege()
	{
		return college;
	}

	public void setCollege(College college)
	{
		this.college = college;
	}

	
	/*public Session(String userId, String firstName, String lastName, Role role, College college)
	{
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.college = college;
	}*/

	//first time login
	public void login(String userid) throws UserNotFoundException
	{
		Permission user = DataStore.getPermissionByUserId(userid);
		if(user == null)
		{
			throw new UserNotFoundException();
		}
		
		this.userId = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.role = user.getRole();
		this.college = user.getCollege(); 
	}
	
	/**
	 * Clear out the currently logged in user id.
	 */
	public void clearSession() 
	{
		userId = null;
//        firstName = null;
//		lastName = null;
//		role = null;
//		college = null;	// by Monna
    }
}
