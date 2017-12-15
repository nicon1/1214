package edu.sc.csce.bill.exception;

public class UserNotFoundException extends Exception 
{
	public UserNotFoundException() 
	{
		super("The user matching the provided userId cannot be found in the database.");
	}
}
