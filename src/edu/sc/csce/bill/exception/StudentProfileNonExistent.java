package edu.sc.csce.bill.exception;

public class StudentProfileNonExistent extends Exception
{
	public StudentProfileNonExistent()
	{
		super("cannot be found in the database.");
	}
}
