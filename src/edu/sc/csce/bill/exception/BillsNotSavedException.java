package edu.sc.csce.bill.exception;

/**
 * BillsNotSavedException.java
 * Purpose: to handle exceptional conditions related to NotSaved of bill when requesting
 */

public class BillsNotSavedException extends Exception 
{
	public BillsNotSavedException() 
	{
		super("Bills were not added");
	}
}
