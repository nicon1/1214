package edu.sc.csce.bill.model;

public class Date 
{
//	public Date() {
//		
//	}
	public Date(int month, int day, int year) {
		super();
		this.month = month;
		this.day = day;
		this.year = year;
	} //by Monna
	public int month;
	public int day;
	public int year;
	
	public int getMonth()
	{
		return month;
	}
	public void setMonth(int month)
	{
		this.month = month;
	}
	public int getDay()
	{
		return day;
	}
	public void setDay(int day)
	{
		this.day = day;
	}
	public int getYear()
	{
		return year;
	}
	public void setYear(int year)
	{
		this.year = year;
	}
	public boolean after(Date startDate) {
		// TODO Auto-generated method stub
		return false;
	}
}
