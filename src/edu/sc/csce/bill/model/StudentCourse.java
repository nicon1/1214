package edu.sc.csce.bill.model;

public class StudentCourse 
{
	private String id;
	private String name;
	private int numCredits;
	private boolean online;
	
//	public StudentCourse() {
//		
//	} by Monna
	public StudentCourse(String id, String name, int numCredits, boolean online)
	{
		this.setName(id);
		this.setId(name);
		this.setNumCredits(numCredits);
		this.setOnline(online);
	}

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

	public boolean isOnline()
	{
		return online;
	}

	public void setOnline(boolean online)
	{
		this.online = online;
	}
}
