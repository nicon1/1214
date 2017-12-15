package edu.sc.csce.bill.model;

public class Term 
{
	private Semester semester;
	private int year;
	//getter and setter
	public Semester getSemester()
	{
		return semester;
	}
	public void setSemester(Semester semester)
	{
		this.semester = semester;
	}
	public int getYear()
	{
		return year;
	}
	public void setYear(int year)
	{
		this.year = year;
	}
	//constructor
	public Term(Semester semester, int year)
	{
		super();
		this.semester = semester;
		this.year = year;
	}
}
