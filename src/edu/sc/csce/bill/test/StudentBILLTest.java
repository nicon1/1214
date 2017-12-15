package edu.sc.csce.bill.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sc.csce.bill.api.BILLIntfAct;
import edu.sc.csce.bill.exception.UserNotFoundException;
import edu.sc.csce.bill.model.Bill;
import edu.sc.csce.bill.model.College;
import edu.sc.csce.bill.model.Date;
import edu.sc.csce.bill.model.InternationalStatus;
import edu.sc.csce.bill.model.Scholarship;
import edu.sc.csce.bill.model.Student;
import edu.sc.csce.bill.model.StudentCourse;
import edu.sc.csce.bill.model.StudentRecord;
import edu.sc.csce.bill.model.StudyAbroad;
import edu.sc.csce.bill.model.Transaction;
import edu.sc.csce.bill.model.Type;




    public class StudentBILLTest {
	private static BILLIntfAct bill;
	private static String studentRecordsPath = "src/resources/students.json";
	private static String usersPath = "src/resources/users.json";


	@BeforeClass
	public static void setUp() throws Exception {
		bill = new BILLIntfAct();
	}

	@Before
	public void setUpUser() throws Exception {
		this.bill.loadUsers(usersPath);
		this.bill.loadRecords(studentRecordsPath);
		bill.logIn("mhunt");
	}//The following functional testing is based on a scenario that user is a STUDENT
	//who holds a user ID "mhunt", trying to use the BILL system. 

	@Test (expected = UserNotFoundException.class)
	public void testNonUserLogin() throws Exception {
		bill.logOut();
		bill.logIn("12345");	
	}//If someone give an user Id which isn't on the user list, the system should reject him.
	
	@Test
	public void testLogOut() {
		bill.logOut();
		assertNull(bill.getUser());
	}//After mhunt request to log out, the current user should be null.
	
	@Test
	public void testSuccessfulLogin() throws Exception {
		bill.logOut();
		bill.logIn("mhunt");
		assertEquals(bill.getUser(), "mhunt");
	}//After mhunt request to log in, the current user should be showing mhunt.
		
	@Test
	public void testStudentGetRecord() throws Exception {
		StudentRecord record = bill.getRecord("mhunt");
		List<StudentCourse> realcourses = new ArrayList<StudentCourse>();
		StudentCourse one = new StudentCourse("Naptime", "NAP 734", 4, false);
		StudentCourse two = new StudentCourse("History of Napping","ART HISTORY 725", 4, false);
		StudentCourse three = new StudentCourse("Sleepy Dancing","DANCE 577", 4, false);
		realcourses.add(one);
		realcourses.add(two);
		realcourses.add(three);
		//Create courses from student.txt for testing.
		Date date1 = new Date(9, 10, 2016);
		Date date2 = new Date(12,15,2016);
		Date date3 = new Date(1,10,2017);
		Date date4 = new Date(1,15,2017);
		Date date5 = new Date(5,15,2017);
		Transaction Tone = new Transaction(Type.PAYMENT,date1,2000.00,null);
		Transaction Ttwo = new Transaction(Type.CHARGE,date2,2000.00,"Final total Fall 2016");
		Transaction Tthree = new Transaction(Type.PAYMENT,date3,100.00,"Return for fee miscalculation");
		Transaction Tfour = new Transaction(Type.PAYMENT,date4,1900.00,null);
		Transaction Tfive = new Transaction(Type.CHARGE,date5,2000.00,"Final total Spring 2016");
		List<Transaction> realtransaction = new ArrayList<Transaction>();
		realtransaction.add(Tone);
		realtransaction.add(Ttwo);
		realtransaction.add(Tthree);
		realtransaction.add(Tfour);
		realtransaction.add(Tfive);
		//Create transaction from student.txt for testing.
		assertEquals("mhunt", record.getStudent().getId());
		assertEquals("Michelle", record.getStudent().getFirstName());
		assertEquals("Hunt", record.getStudent().getLastName());
		assertEquals("999-999-9999", record.getStudent().getPhone());
		assertEquals("mhunt@mailbox.sc.edu", record.getStudent().getEmailAddress());
		assertEquals("221B Baker St.", record.getStudent().getAddressStreet());
		assertEquals("Pittsburgh", record.getStudent().getAddressCity());
		assertEquals("PA", record.getStudent().getAddressState());
		assertEquals(College.ARTS_AND_SCIENCES, record.getCollege());
		assertEquals(true, record.isGradAssistant());
		assertEquals(false, record.isInternational());
		assertEquals(InternationalStatus.NONE, record.getInternationalStatus());
		assertEquals(false, record.isResident());
		assertEquals(false, record.isActiveDuty());
		assertEquals(false, record.isVeteran());
		assertEquals(false, record.isFreeTuition());
		assertEquals(Scholarship.NONE, record.getScholarship());
		assertEquals(StudyAbroad.NONE, record.getStudyAbroad());
		assertEquals(false, record.isNationalStudentExchange());
		assertEquals(false, record.isOutsideInsurance());
		assertEquals(realcourses.get(0).getName(), record.getStudentCourses().get(0).getName());
		assertEquals(realcourses.get(0).getId(), record.getStudentCourses().get(0).getId());
		assertEquals(realcourses.get(0).getNumCredits(), record.getStudentCourses().get(0).getNumCredits());
		assertEquals(realcourses.get(0).isOnline(), record.getStudentCourses().get(0).isOnline());
		//Compare all the instance value of a course.
		assertEquals(realtransaction.get(0).getType().toString(), record.getTransactions().get(0).getType().toString());
		assertEquals(realtransaction.get(0).getTransactionDate().getMonth(), record.getTransactions().get(0).getTransactionDate().getMonth());
		assertEquals(realtransaction.get(0).getAmount(), record.getTransactions().get(0).getAmount(), 1);
		assertEquals(realtransaction.get(0).getNote(), record.getTransactions().get(0).getNote());
		//Compare all the instance value of a transaction
	}//After mhunt request to view his student record, the system should return the record with his id.

	@Test (expected = IllegalArgumentException.class)
	public void testStudentGetOthersRecord() throws Exception {
		StudentRecord record = bill.getRecord("ggay");
	}//If mhunt request to view other student's record, the system should reject him.
	//Need TS LogInUesrDoesNotHavePermission()-->name spelling error.... Monnan 1207
	

	@Test
	public void testStudentEditRecordInstance() throws Exception {
		StudentRecord newRecord = new StudentRecord();
		Student newstudent = new Student();
		newRecord.setStudent(newstudent);
		newstudent.setId("mhunt");
		newstudent.setFirstName("Hello");
		newstudent.setLastName("Hello");
		newstudent.setPhone("000-000-0000");
		newstudent.setEmailAddress("newemail@email.com");
		newstudent.setAddressCity("JavaCity");
		newstudent.setAddressState("JV");
		newstudent.setAddressPostalCode("66666");
		bill.editRecord("mhunt", newRecord, false);
		//Assume the mhunt edit the above information that is 
		//temporarily hold in newstudent and newRecord
		assertEquals("mhunt", newstudent.getId());
		assertEquals("Hello", newstudent.getFirstName());
		assertEquals("Hello", newstudent.getLastName());
		assertEquals("000-000-0000", newstudent.getPhone());
		assertEquals("newemail@email.com", newstudent.getEmailAddress());
		assertEquals("JavaCity", newstudent.getAddressCity());
		assertEquals("JV", newstudent.getAddressState());
		assertEquals("66666", newstudent.getAddressPostalCode());
	}// After mhunt request to edit his student record temporarily, the system should temporarily hold the info.
	//Need TS editRecord().
	
	@Test
	public void testStudentEditRecordPermanent() throws Exception {
		StudentRecord newRecord = new StudentRecord();
		Student newstudent = new Student();
		newRecord.setStudent(newstudent);
		newstudent.setId("mhunt");
		newstudent.setFirstName("Hello");
		newstudent.setLastName("Hello");
		newstudent.setPhone("000-000-0000");
		newstudent.setEmailAddress("newemail@email.com");
		newstudent.setAddressCity("JavaCity");
		newstudent.setAddressState("JV");
		newstudent.setAddressPostalCode("66666");
		//Assume the user edit the above information that is 
		//hold in newstudent and newRecord
		bill.editRecord("mhunt", newRecord, true);
		assertEquals("mhunt", bill.getRecord("mhunt").getStudent().getId());
		assertEquals("Hello", bill.getRecord("mhunt").getStudent().getFirstName());
		assertEquals("Hello", bill.getRecord("mhunt").getStudent().getLastName());
		assertEquals("000-000-0000", bill.getRecord("mhunt").getStudent().getPhone());
		assertEquals("newemail@email.com", bill.getRecord("mhunt").getStudent().getEmailAddress());
		assertEquals("JavaCity", bill.getRecord("mhunt").getStudent().getAddressCity());
		assertEquals("JV", bill.getRecord("mhunt").getStudent().getAddressState());
		assertEquals("66666", bill.getRecord("mhunt").getStudent().getAddressPostalCode());
	}// After mhunt request to edit his student record permanently, the system should save it to DB.
	// Need TS editRecord() Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testStudentEditUneditRecord() throws Exception {
		StudentRecord newRecord = new StudentRecord();
		Student newstudent = new Student();
		newRecord.setStudent(newstudent);
		newstudent.setId("1234");
		//Assume the user edit the above information that is 
		//hold in newstudent and newRecord
		bill.editRecord("mhunt", newRecord, true);
	}// If mhunt request to edit a item that is not to change in student record (i.e Id), the system should reject him.
	// Need TS editRecord(), need fix StudentRecordsNotEditedException Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testStudentEditOthersRecord() throws Exception {
		StudentRecord newRecord = new StudentRecord();
		Student newstudent = new Student();
		newRecord.setStudent(newstudent);
		newstudent.setId("ggay");
		newstudent.setFirstName("Hello");
		newstudent.setLastName("Hello");
		newstudent.setPhone("000-000-0000");
		newstudent.setEmailAddress("new address");
		newstudent.setAddressCity("JavaCity");
		newstudent.setAddressState("JV");
		newstudent.setAddressPostalCode("66666");
		//Assume the user edit the above information that is 
		//hold in newstudent and newRecord
		bill.editRecord("ggay", newRecord, true);
	}//If mhunt request to edit other student's record, the system should reject him.
	//Need TS editRecord(), need TS LogInUesrDoesNotHavePermission() and it has name spelling error... Monnan 1207
	
	@Test
	public void testStudentGerateBill() throws Exception {
		Bill newbill = bill.generateBill("mhunt");
		assertEquals("mhunt", bill.generateBill("mhunt").getId());
		assertEquals("PHD", bill.generateBill("mhunt").getClassStatus());
		assertEquals("12", bill.generateBill("mhunt").getTotalNumCredits());
		assertEquals(false, bill.generateBill("mhunt").getResident());
		assertEquals(false, bill.generateBill("mhunt").getOutsideInsurance());
		assertEquals("13740.00", bill.generateBill("mhunt").getTuition());
		assertEquals("16566.00", bill.generateBill("mhunt").getBlance());
		assertEquals("200.00", bill.generateBill("mhunt").getTotalTechnologyFee());
		assertEquals("2547.00", bill.generateBill("mhunt").getHealthInsurance());
	}//After mhunt request to view his bill status, the system should return his bill status.
	//Need TS generateBill(), need class bill?? Create methods for Bill instance Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testStudentGenerateOhtersBill() throws Exception {
		Bill newbill = generateBill("ggay")
	}//After mhunt request to view other students' bill status, the system should reject him.
	//Need TS generateBill(), need TS LogInUesrDoesNotHavePermission().... Monnan 1207
	
	@Test
	public void testStudentViewCharges() throws Exception {
		ArrayList<Transactions> billrecord = new ArrayList<Transactions>;
		Transactions one = new Transactions();
		TransactionDate onedate = new TransactionDate();
		one.setTransactionDate(onedate);
		one.settype("PAYMENT");
		onedate.setMonth(9);
		onedate.setDay(10);
		onedate.setYear(2016);
		billrecord.add(one);
		//Create a new transaction that holds one transaction data of mhunt from the DB
		ArrayList<Transactions> charges = bill.viewCharges("mhunt", 9, 9, 2016, 9, 12,
				2016);
		assertArrayEquals(billrecord, charges);
	}//After mhunt request to view his payment history of a period, the system should return the payment record.
	//Need TS viewCharges(), need a class Transaction?? Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testStudentViewOtherCharges() throws Exception {
		ArrayList<Transactions> charges = bill.viewCharges("ggay", 9, 9, 2016, 9, 12,
				2016);
	}//If mhunt request to view other students' payment history of a period, the system should reject him.
	//Need TS viewCharges(), need set LogInUesrDoesNotHavePermission(). Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testStudentApplyPayment() throws Exception {
		//mhunt's balance in DB now is 16566.00
		bill.applyPayment("mhunt", 100.00, "pay 100");
		assertEquals(16466.00, bill.getRecord("mhunt").getBalance());
	}//After mhunt request to make a payment, the system should record the payment into DB
	//Need TS applyPayment....Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testStudentApplyPaymentToOthers() throws Exception {
		bill.applyPayment("ggay", 100.00, "pay 100");
	}//After mhunt request to make a payment for other students, the system should reject him.
	//Need TS applyPayment, need LogInUesrDoesNotHavePermission() ....Monnan 1207
	
	

	
	
}
