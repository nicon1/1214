package edu.sc.csce.bill.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.sc.csce.bill.exception.CollegeNotMatch;
import edu.sc.csce.bill.exception.LogInUesrDoesNotHavePermission;
//import edu.sc.csce.bill.exception.NoUserSetInSessionException; //by Monna
import edu.sc.csce.bill.exception.StudentProfileNonExistent;
import edu.sc.csce.bill.exception.StudentRecordsNotEditedException;
import edu.sc.csce.bill.exception.StudentRecordsNotSavedException;
import edu.sc.csce.bill.exception.UserNotFoundException;
import edu.sc.csce.bill.exception.UsersNotLoadedException;
import edu.sc.csce.bill.model.Bill;
import edu.sc.csce.bill.model.ClassStatus;
import edu.sc.csce.bill.model.College;
//import edu.sc.csce.bill.model.Permission; //by Monna
import edu.sc.csce.bill.model.Role;
import edu.sc.csce.bill.model.Student;
import edu.sc.csce.bill.model.StudentCourse; //by Monna
import edu.sc.csce.bill.model.StudentRecord;
import edu.sc.csce.bill.model.Transaction;
import edu.sc.csce.bill.model.Type; //by Monna
import edu.sc.csce.bill.module.DataStore;
import edu.sc.csce.bill.module.Session;
import edu.sc.csce.bill.module.Valid;


public class BILLIntfAct implements BILLIntf 
{
//	private static String studentRecordsPath = "src/resources/students.json";
//	private static String usersPath = "src/resources/users.json";
//	public static List<Permission> users;
//	
//	public static void main(String[] args) {
//		BILLIntfAct bill = new BILLIntfAct();
//		try {
//			bill.loadUsers(usersPath);
//			System.out.println("loadUser");
//			bill.loadRecords(studentRecordsPath);
//			System.out.println("loadRecord");
//			bill.logIn("mhunt");
//			System.out.println(bill.getUser() + "login ok");
//			bill.logOut();
//			System.out.println(bill.getUser() + "logout ok");
//			bill.logIn("mhunt");
//			System.out.println(bill.getUser() + "login ok");
//			System.out.println(bill.session.getRole() + " " + bill.session.getCollege());
//					
//			StudentRecord newRecord = new StudentRecord();
//			Student newstudent = new Student();
//			newRecord.setStudent(newstudent);
//			newstudent.setId("mhunt");
//			newstudent.setFirstName("Hello");
//			newstudent.setLastName("Hello");
//			newstudent.setPhone("000-000-0000");
//			newstudent.setEmailAddress("newemail@email.com");
//			newstudent.setAddressCity("JavaCity");
//			newstudent.setAddressState("JV");
//			newstudent.setAddressPostalCode("66666");
//			//bill.editRecord("mhunt", newRecord, false);
//			System.out.println(newstudent.getId());
//			bill.logOut();
//			bill.logIn ("jsmith");
//			String input = "mhunt";
//			
//			System.out.println(bill.getUser());
//
//			System.out.println(bill.getStudentIDs());
//
//			System.out.println(bill.NoPermission(input));
		
//		}catch(Exception ex) {
//			System.out.println("Exception");
//		}
//		//the main method is for testing use, need delete
//	} //by Monna
	private Session session;

	public BILLIntfAct() 
	{
		session = new Session();
	}

	public void loadUsers(String usersFile) throws Exception 
	{
		DataStore.loadUsers(usersFile);
	}

	public void loadRecords(String recordsFile) throws Exception 
	{
		DataStore.loadRecords(recordsFile);
	}

	public void logIn(String userId) throws UserNotFoundException 
	{
		session.login(userId);
	}
	
	public void logOut() throws Exception 
	{
		session.clearSession();
	}

	public String getUser() 
	{
		if (session.getUser() == null) 
		{
			return null;
		}

		return session.getUser();
	}


	public List<String> getStudentIDs() throws LogInUesrDoesNotHavePermission 
	{
		List<String> studentId;
		String userId = session.getUser();
		Role role = session.getRole();
		College college = session.getCollege();
		if (userId == null) 
		{
			throw new LogInUesrDoesNotHavePermission();
		}
		if (role == Role.STUDENT) 
		{
			throw new LogInUesrDoesNotHavePermission();
		} 
		else 
		{
			studentId = DataStore.getStudentId(college);
		}
		return studentId;
	}

	public StudentRecord getRecord(String userId) throws CollegeNotMatch 
	{
		// validate part here
		String currentUser = session.getUser();
		Role currentRole = session.getRole();
		College college = session.getCollege();
		StudentRecord record;

		record = DataStore.getStudentRecord(userId);
		if (record == null) 
		{
			System.out.println("record not found");
			return null;
		}
		if (record.getCollege() != college && (college == College.GRADUATE_SCHOOL
				&& (record.getClassStatus() != ClassStatus.MASTERS && record.getClassStatus() != ClassStatus.PHD))) {
			throw new CollegeNotMatch();
		}
		return record;
	}

	public void editRecord(String userId, StudentRecord record, Boolean permanent)
			throws StudentRecordsNotEditedException, StudentProfileNonExistent, StudentRecordsNotSavedException 
	{
		// validate part here
		String currentUser = session.getUser();
		Role currentRole = session.getRole();
		College college = session.getCollege();
		StudentRecord oldRecord;
		StudentRecord oldRecordm;
        //System.out.println("check2"+ session.getUser() + userId);
		//System.out.println("check3"+ session.getRole() + Role.ADMIN.toString());

		//if (() && (currentRole!= Role.ADMIN)) 
		//{
		//	throw new StudentRecordsNotEditedException();
		//} // by Monna 

		if (userId != currentUser && currentRole != Role.ADMIN) 
		{
			throw new StudentRecordsNotEditedException();
		} 
		else if (userId == currentUser && currentRole != Role.ADMIN) 
		{
			// student user can only edit the Student part
			oldRecord = DataStore.getStudentRecord(userId);
			oldRecordm = oldRecord;
			oldRecordm.setStudent(record.getStudent());
			if (oldRecordm != record) 
			{
				throw new StudentRecordsNotEditedException();
			}
		} 
		else 
		{
			oldRecord = DataStore.getStudentRecord(userId);
			if (oldRecord == null) 
			{
				throw new StudentRecordsNotEditedException();
			}
			if (oldRecord.getCollege() != college
					&& (college == College.GRADUATE_SCHOOL && (oldRecord.getClassStatus() != ClassStatus.MASTERS
							&& oldRecord.getClassStatus() != ClassStatus.PHD))) 
			{
				throw new StudentRecordsNotEditedException();
			}
		}
		DataStore.updateStudentRecord(userId, record, permanent);
	}

	public Bill generateBill(String userId) throws Exception 
	{
		Valid.validateUserId(userId);
		return null;
		// getCurrentBill(userId);
	}

	/**
	 * 
	 * @param @param @param @param @param @param @param @returns @throws
	 */
	public Bill viewCharges(String userId, int startMonth, int startDay, int startYear, int endMonth, int endDay,
			int endYear) throws Exception 
	{
		Valid.validateUserId(userId);
		Valid.validateDate(startMonth, startDay, startYear);
		Valid.validateDate(endMonth, endDay, endYear);

		Date startDate = new GregorianCalendar(startYear, startMonth - 1, startDay).getTime();
		Date endDate = new GregorianCalendar(endYear, endMonth - 1, endDay).getTime();

		Valid.validateDateRange(startDate, endDate);

		List<Bill> userBill = DataStore.getBill(userId);
		List<Transaction> retTransaction = new ArrayList<Transaction>();
		StudentRecord record = DataStore.getStudentRecord(userId);

		if (record == null) 
		{
			return null;
		}
		Student student = record.getStudent();
		College college = record.getCollege();
		ClassStatus classStatus = record.getClassStatus();
		double balance = 0;
		for (Bill bill : userBill) 
		{
			List<Transaction> transaction = bill.getTransaction();
			for (Transaction transac : transaction) 
			{
				int month = transac.getTransactionDate().getMonth();
				int day = transac.getTransactionDate().getDay();
				int year = transac.getTransactionDate().getYear();
				Date targetDate = new GregorianCalendar(year, month - 1, day).getTime();
				if (targetDate.before(endDate) && targetDate.after(startDate)) 
				{
					retTransaction.add(transac);
				}
			}
		}
		Bill retBill = new Bill(student, college, classStatus, balance, retTransaction);
		return retBill;
	}

	public void applyPayment(String userId, BigDecimal amount, String note) throws Exception 
	{
		// TODO
		Valid.validateUserId(userId);
		if (amount.doubleValue() > 0 && note != null) 
		{
			DataStore.addPayment(userId, amount, note);
		} else 
		{
			if (amount.doubleValue() != 0)
				throw new IllegalArgumentException("not a valid");
			else
				throw new IllegalArgumentException("not a valid");
		}
	}

	public void applyPayment(String userId, double amount, String note) throws Exception 
	{
		// TODO Auto-generated method stub
	}

	public void clearSession() throws UsersNotLoadedException 
	{
		if (session.getUser() == null) 
		{
			throw new UsersNotLoadedException();
		} // else

		session.clearSession();
	}
	//public boolean NoPermission(String userId) 
	//{
	//	
	//	String CurrentUserId = getUser();
	//	String TargetUserId = userId;
	//	College college = session.getCollege();
	//	List<String> AuthorityStudentlist = DataStore.getStudentId(college);
	//	
	//	if (CurrentUserId == TargetUserId) {
	//	return false;
	//	}
	//	else {
	//		if (session.getRole() == Role.STUDENT) {
	//			return true;
	//		}
	//		else {
	//			for(String studentId : AuthorityStudentlist) {
	//				if (studentId == TargetUserId ) {
	//					return false;
	//				}
	//				else {
	//					return true;
	//				}
	//			}
	//			
	//		} // by Monna
		//return true;
	//}
}
