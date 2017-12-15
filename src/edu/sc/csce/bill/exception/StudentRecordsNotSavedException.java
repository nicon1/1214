package edu.sc.csce.bill.exception;

public class StudentRecordsNotSavedException extends Exception 
{
	public StudentRecordsNotSavedException() 
	{
		super("Student records were not saved to the specified file.");
	}
}

