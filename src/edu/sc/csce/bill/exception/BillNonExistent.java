package edu.sc.csce.bill.exception;

/**
 * BillNonExistent.java
 * Purpose: to handle exceptional conditions related to existence of bill when requesting
 */

public class BillNonExistent extends Exception 
{
	public BillNonExistent(String message)
	{
		super("cannot be found in the database.");
	}

}
