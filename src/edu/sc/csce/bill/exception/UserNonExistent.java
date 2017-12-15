package edu.sc.csce.bill.exception;

public class UserNonExistent extends Exception
{
	public UserNonExistent()
	{
		super("The user matching the provided userId cannot be found in the database.");
	}
}

