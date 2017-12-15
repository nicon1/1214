package edu.sc.csce.bill.exception;

public class BillNonExistent extends Exception 
{
	public BillNonExistent(String message)
	{
		super("cannot be found in the database.");
	}

}
