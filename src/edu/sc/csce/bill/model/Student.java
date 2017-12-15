package edu.sc.csce.bill.model;

public class Student 
{
//	public Student() {
//		super();
//	} //by Monna
	private String id;
	private String firstName;
	private String lastName;
	private String phone;
	private String emailAddress;
	private String addressStreet;
	private String addressCity;
	private String addressState;
	private String addressPostalCode;
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
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getEmailAddress()
	{
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
	public String getAddressStreet()
	{
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet)
	{
		this.addressStreet = addressStreet;
	}
	public String getAddressCity()
	{
		return addressCity;
	}
	public void setAddressCity(String addressCity)
	{
		this.addressCity = addressCity;
	}
	public String getAddressState()
	{
		return addressState;
	}
	public void setAddressState(String addressState)
	{
		this.addressState = addressState;
	}
	public String getAddressPostalCode()
	{
		return addressPostalCode;
	}
	public void setAddressPostalCode(String addressPostalCode)
	{
		this.addressPostalCode = addressPostalCode;
	}
	
	//constructor
	public Student(String id, String firstName, String lastName, String phone, String emailAddress,
			String addressStreet, String addressCity, String addressState, String addressPostalCode)
	{
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.emailAddress = emailAddress;
		this.addressStreet = addressStreet;
		this.addressCity = addressCity;
		this.addressState = addressState;
		this.addressPostalCode = addressPostalCode;
	}
}
