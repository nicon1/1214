package edu.sc.csce.bill.exception;

public class LogInUesrDoesNotHavePermission extends Exception
{
	public LogInUesrDoesNotHavePermission()
	{
		super("The logged in user does not have permission to perform this operation.");
	}
}
