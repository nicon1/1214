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
		/**
		 * @param id the id to set
		 */
		this.id = id;
	}
	public String getName()
	{
		/**
		 * @return the name
		 */
		return name;
	}
	public void setName(String name)
	{
		/**
		 * @param setName the setName to set
		 */
		this.name = name;
	}
	public int getNumCredits()
	{
		/**
		 * @return the getNumCredits
		 */
		return numCredits;
	}
	public void setNumCredits(int numCredits)
	{
		/**
		 * @param numCredits the setNumCredits to set
		 */
		this.numCredits = numCredits;
	}
}
