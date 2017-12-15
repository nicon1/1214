package edu.sc.csce.bill.exception;

public class CollegeNotMatch extends Exception
{
	public CollegeNotMatch() 
	{
		super("The user has no right to get access to the record.");
	}
}
