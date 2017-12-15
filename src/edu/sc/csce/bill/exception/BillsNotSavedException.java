package edu.sc.csce.bill.exception;

public class BillsNotSavedException extends Exception 
{
	public BillsNotSavedException() 
	{
		super("Bills were not added");
	}
}
