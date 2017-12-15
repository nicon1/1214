package edu.sc.csce.bill.exception;

/**
 * CollegeNotMatch.java
 * Purpose: to handle exceptional conditions related to College is correct or not
 */

public class CollegeNotMatch extends Exception
{
	public CollegeNotMatch() 
	{
		super("The user has no right to get access to the record.");
	}
}
