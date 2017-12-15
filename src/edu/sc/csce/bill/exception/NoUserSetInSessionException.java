package edu.sc.csce.bill.exception;

public class NoUserSetInSessionException extends Exception 
{
	public NoUserSetInSessionException() 
	{		
		super("No user is set in the login session.");
	}
}
