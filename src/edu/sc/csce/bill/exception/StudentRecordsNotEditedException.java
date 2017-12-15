package edu.sc.csce.bill.exception;

public class StudentRecordsNotEditedException extends Exception 
{
	public StudentRecordsNotEditedException() 
	{
		super("Student records were not loaded. Make sure the path is correct and the data is in a valid JSON format.");
	}
}
