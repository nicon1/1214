package edu.sc.csce.bill.exception;

/**
 * CoursesNotLoadedException.java
 * Purpose: to handle exceptional conditions related to Courses path is correct or not
 */

public class CoursesNotLoadedException extends Exception
{
	public CoursesNotLoadedException() 
	{
		super("Courses were not loaded. Make sure the path is correct and the data is in a valid JSON format.");
	}
}
