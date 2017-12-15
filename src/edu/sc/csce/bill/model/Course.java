package edu.sc.csce.bill.model;

public class Course 
{
	private String id;
	private String name;
	private int numCredits;
	//getter and setter
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getNumCredits()
	{
		return numCredits;
	}
	public void setNumCredits(int numCredits)
	{
		this.numCredits = numCredits;
	}
}
