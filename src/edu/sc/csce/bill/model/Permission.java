package edu.sc.csce.bill.model;

public class Permission 
{
	private String id;
	private String firstName;
	private String lastName;
//	private Role role; //by Monna
//	private College college; //by Monna
	private static Role role;
	private static College college;
	//getter and setter
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
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
	public static Role getRole()
	{
		return role;
	}
	public static void setRole(Role role)
	{
		Permission.role = role;
	}
	public static College getCollege()
	{
		return college;
	}
	public static void setCollege(College college)
	{
		Permission.college = college;
	}
	
	
//		public Role getRole()
//	{
//		return role;
//	}
//	public void setRole(Role role)
//	{
//		this.role = role;
//	}
//	public College getCollege()
//	{
//		return college;
//	}
//	public void setCollege(College college)
//	{
//		this.college = college;
//	} //by Monna
}
