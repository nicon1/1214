package edu.sc.csce.bill.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.sc.csce.bill.api.BILLIntfAct;
import edu.sc.csce.bill.exception.StudentProfileNonExistent;
import edu.sc.csce.bill.exception.UserNotFoundException;
import edu.sc.csce.bill.exception.UsersNotLoadedException;

public class BILLTest 
{
	private BILLIntfAct bill;
	private static String studentRecordsPath = "src/resources/students.json";
	private static String usersPath = "src/resources/users.json";
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception
	{
		this.bill = new BILLIntfAct();
		this.bill.loadUsers(usersPath);
		this.bill.loadRecords(studentRecordsPath);
	}
	
	@Test
	public void testClearSession_Invalid() throws UserNotFoundException, UsersNotLoadedException 
	{
		exception.expect(UsersNotLoadedException.class);
		bill.clearSession();
		assert(bill.getUser() == null);
	}
	
	@Test(expected=StudentProfileNonExistent.class)
	public void testNonExistedUserRecords() throws Exception 
	{
		bill.getRecord("fakeId");
	}
	
	@Test
	public void testSuccessfulLogin() throws Exception 
	{
		bill.logIn("mhunt");
		assertEquals(bill.getUser(), "mhunt");
	}
}
