package edu.sc.csce.bill.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sc.csce.bill.api.BILLIntfAct;
import edu.sc.csce.bill.exception.StudentRecordsNotEditedException;
import edu.sc.csce.bill.exception.UserNotFoundException;
import edu.sc.csce.bill.model.Bill;
import edu.sc.csce.bill.model.ClassStatus;
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

public class AdminBILLTest {
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
	}// The following functional testing is based on a scenario that user is a
		// STUDENT
		// who holds a user ID "mhunt", trying to use the BILL system.

	@Test(expected = UserNotFoundException.class)
	public void testNonUserLogin() throws Exception {
		bill.logOut();
		bill.logIn("12345");
	}// If someone give an user Id which isn't on the user list, the system should
		// reject him.

	@Test
	public void testLogOut() throws Exception {
		bill.logOut();
		assertNull(bill.getUser());
	}// After mhunt request to log out, the current user should be null.

	@Test
	public void testSuccessfulLogin() throws Exception {
		bill.logOut();
		bill.logIn("mhunt");
		assertEquals(bill.getUser(), "mhunt");
	}// After mhunt request to log in, the current user should be showing mhunt.

	@Test
	public void testStudentGetRecord() throws Exception {
		StudentRecord record = bill.getRecord("mhunt");
		// ************delete
		// Create transaction from student.txt for testing.
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
		// Compare profile basic information.
		assertEquals("Naptime", record.getStudentCourses().get(0).getName());
		assertEquals("NAP 734", record.getStudentCourses().get(0).getId());
		assertEquals(4, record.getStudentCourses().get(0).getNumCredits());
		assertEquals(false, record.getStudentCourses().get(0).isOnline());
		// Compare all the instance value of the first course record.
		assertEquals("Naptime", record.getStudentCourses().get(0).getName());
		assertEquals("NAP 734", record.getStudentCourses().get(0).getId());
		assertEquals(4, record.getStudentCourses().get(0).getNumCredits());
		assertEquals(false, record.getStudentCourses().get(0).isOnline());
		// Compare all the instance value of the second course record.
		assertEquals(Type.PAYMENT, record.getTransactions().get(0).getType());
		assertEquals(9, record.getTransactions().get(0).getTransactionDate().getMonth());
		assertEquals(10, record.getTransactions().get(0).getTransactionDate().getDay());
		assertEquals(2016, record.getTransactions().get(0).getTransactionDate().getYear());
		assertEquals(2000, record.getTransactions().get(0).getAmount(), 2);
		assertEquals(null, record.getTransactions().get(0).getNote());
		// Compare all the instance value of the first transaction record.
		assertEquals(Type.CHARGE, record.getTransactions().get(1).getType());
		assertEquals(12, record.getTransactions().get(1).getTransactionDate().getMonth());
		assertEquals(15, record.getTransactions().get(1).getTransactionDate().getDay());
		assertEquals(2016, record.getTransactions().get(1).getTransactionDate().getYear());
		assertEquals(2000, record.getTransactions().get(1).getAmount(), 2);
		assertEquals("Final total Fall 2016", record.getTransactions().get(1).getNote());
		// Compare all the instance value of the second transaction record.
	}// After mhunt request to view his student record, the system should return the
		// record with his id.

	@Test(expected = Exception.class)
	public void testStudentGetOthersRecord() throws Exception {
		StudentRecord record = bill.getRecord("ggay");
	}// If mhunt request to view other student's record, the system should reject
		// him.
		// Need TS LogInUesrDoesNotHavePermission()-->name spelling error.... Monnan
		// 1207

	@Test
	public void testStudentEditRecordInstance() throws Exception {
		StudentRecord newRecord = new StudentRecord();
		Student newstudent = new Student();
		newstudent.setId("mhunt");
		newstudent.setFirstName("Hello");
		newstudent.setLastName("Hello");
		newstudent.setPhone("000-000-0000");
		newstudent.setEmailAddress("newemail@email.com");
		newstudent.setAddressCity("JavaCity");
		newstudent.setAddressState("JV");
		newstudent.setAddressPostalCode("66666");
		newRecord.setStudent(newstudent);

		bill.editRecord("mhunt", newRecord, false);
		// Assume the mhunt edit the above information that is temporarily hold in
		// newstudent and newRecord
		assertEquals("mhunt", bill.getRecord("mhunt").getStudent().getId());
		assertEquals("Hello", bill.getRecord("mhunt").getStudent().getFirstName());
		assertEquals("Hello", bill.getRecord("mhunt").getStudent().getLastName());
		assertEquals("000-000-0000", bill.getRecord("mhunt").getStudent().getPhone());
		assertEquals("newemail@email.com", bill.getRecord("mhunt").getStudent().getEmailAddress());
		assertEquals("JavaCity", bill.getRecord("mhunt").getStudent().getAddressCity());
		assertEquals("JV", bill.getRecord("mhunt").getStudent().getAddressState());
		assertEquals("66666", bill.getRecord("mhunt").getStudent().getAddressPostalCode());
	}// After mhunt request to edit his student record temporarily, the system should
		// temporarily hold the info.
		// Need TS editRecord().

	@Test
	public void testStudentEditRecordPermanent() throws Exception {
		StudentRecord newRecord = new StudentRecord();
		Student newstudent = new Student();
		newstudent.setId("mhunt");
		newstudent.setFirstName("Hello");
		newstudent.setLastName("Hello");
		newstudent.setPhone("000-000-0000");
		newstudent.setEmailAddress("newemail@email.com");
		newstudent.setAddressCity("JavaCity");
		newstudent.setAddressState("JV");
		newstudent.setAddressPostalCode("66666");
		newRecord.setStudent(newstudent);
		// Assume the user edit the above information that is hold in newstudent and
		// newRecord
		bill.editRecord("mhunt", newRecord, true);
		assertEquals("mhunt", bill.getRecord("mhunt").getStudent().getId());
		assertEquals("Hello", bill.getRecord("mhunt").getStudent().getFirstName());
		assertEquals("Hello", bill.getRecord("mhunt").getStudent().getLastName());
		assertEquals("000-000-0000", bill.getRecord("mhunt").getStudent().getPhone());
		assertEquals("newemail@email.com", bill.getRecord("mhunt").getStudent().getEmailAddress());
		assertEquals("JavaCity", bill.getRecord("mhunt").getStudent().getAddressCity());
		assertEquals("JV", bill.getRecord("mhunt").getStudent().getAddressState());
		assertEquals("66666", bill.getRecord("mhunt").getStudent().getAddressPostalCode());
	}// After mhunt request to edit his student record permanently, the system should
		// save it to DB.
		// Need TS editRecord() Monnan 1207

	@Test(expected = StudentRecordsNotEditedException.class)
	public void testStudentEditUneditRecord() throws Exception {
		StudentRecord newRecord = new StudentRecord();
		Student newstudent = new Student();
		newstudent.setId("1234");
		newRecord.setStudent(newstudent);
		// Assume the user edit the above information that is hold in newstudent and
		// newRecord
		bill.editRecord("mhunt", newRecord, true);
	}// If mhunt request to edit a item that is not to change in student record (i.e
		// Id), the system should reject him.
		// Need TS editRecord(), need fix StudentRecordsNotEditedException Monnan 1207

	@Test(expected = Exception.class)
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
		// Assume the user edit the above information that is
		// hold in newstudent and newRecord
		bill.editRecord("ggay", newRecord, true);
	}// If mhunt request to edit other student's record, the system should reject
		// him.
		// Need TS editRecord(), need TS LogInUesrDoesNotHavePermission() and it has
		// name spelling error... Monnan 1207

	@Test
	public void testStudentGerateBill() throws Exception {
		Bill newbill = bill.generateBill("mhunt");
		// Create a new bill object to hold the value after calling the method.
		assertEquals("mhunt", newbill.getStudent().getId());
		assertEquals(College.ARTS_AND_SCIENCES, newbill.getCollege());
		assertEquals(ClassStatus.PHD, newbill.getClassStatus());
		assertEquals(0, newbill.getBalance(), 2);
		// Compare all personal information in bill
		
	}// After mhunt request to view his bill status, the system should return his
		// bill status.
		// Need TS generateBill(), need class bill?? Create methods for Bill instance
		// Monnan 1207

	@Test(expected = Exception.class)
	public void testStudentGenerateOhtersBill() throws Exception {
		Bill newbill = bill.generateBill("ggay");
	}// After mhunt request to view other students' bill status, the system should
		// reject him.
		// Need TS generateBill(), need TS LogInUesrDoesNotHavePermission().... Monnan
		// 1207

	@Test
	public void testStudentViewCharges() throws Exception {
		bill.generateBill("mhunt");
		Bill newbill = bill.viewCharges("mhunt", 8, 10, 2016, 10, 10, 2016);
		// Create a new transaction that holds one transaction data of mhunt from the DB
		Bill viewcharge = bill.viewCharges("mhunt", 9, 9, 2016, 9, 12, 2016);
		assertEquals(Type.CHARGE, newbill.getTransaction().get(0).getType());
		assertEquals(13740.00, newbill.getTransaction().get(0).getAmount(), 2);
		assertEquals("GRADUATE - NONRESIDENT - TUITION", newbill.getTransaction().get(0).getNote());
		// Compare first transaction data: TUITION
		assertEquals(Type.CHARGE, newbill.getTransaction().get(1).getType());
		assertEquals(200.00, newbill.getTransaction().get(1).getAmount(), 2);
		assertEquals("TECHNOLOGY FEE", newbill.getTransaction().get(1).getNote());
		// Compare second transaction data: TECHNOLOGY FEE
		assertEquals(Type.CHARGE, newbill.getTransaction().get(2).getType());
		assertEquals(2547.00, newbill.getTransaction().get(2).getAmount(), 2);
		assertEquals("HEALTH INSURANCE - (STUDENTS WITHOUT COVERAGE) - CONTRACT W/THIRD PARTY",
				newbill.getTransaction().get(2).getNote());
		// Compare the 3rd data HEALTH INSURANCE FEE
		assertEquals(Type.CHARGE, newbill.getTransaction().get(3).getType());
		assertEquals(40.00, newbill.getTransaction().get(3).getAmount(), 2);
		assertEquals("LAB FEE - ART HISTORY", newbill.getTransaction().get(3).getNote());
		// Compare the 4th data HEALTH INSURANCE FEE
		assertEquals(Type.CHARGE, newbill.getTransaction().get(4).getType());
		assertEquals(75.00, newbill.getTransaction().get(4).getAmount(), 2);
		assertEquals("LAB FEE - DANCE", newbill.getTransaction().get(4).getNote());
		// Compare the 5rd data HEALTH INSURANCE FEE
		assertEquals(2000, viewcharge.getTransaction().get(0).getAmount(), 2);
		
		
		
		
	}// After mhunt request to view his payment history of a period, the system
		// should return the payment record.
		// Need TS viewCharges(), need a class Transaction?? Monnan 1207

	@Test(expected = Exception.class)
	public void testStudentViewOtherCharges() throws Exception {
		Bill charges = bill.viewCharges("ggay", 9, 9, 2016, 9, 12, 2016);
	}// If mhunt request to view other students' payment history of a period, the
		// system should reject him.
		// Need TS viewCharges(), need set LogInUesrDoesNotHavePermission(). Monnan 1207

	@Test
	public void testStudentApplyPayment() throws Exception {
		// mhunt's balance in DB now is 16566.00
		bill.generateBill("mhunt");
		bill.applyPayment("mhunt", 100.00, "pay 100");
	
		assertNull(bill.getRecord("mhunt").getBalance());
		assertEquals(100.00, bill.generateBill("mhunt").getBalance(),2);
	}// After mhunt request to make a payment, the system should record the payment
		// into DB
		// Need TS applyPayment....Monnan 1207

	@Test(expected = Exception.class)
	public void testStudentApplyPaymentToOthers() throws Exception {
		bill.applyPayment("ggay", 100.00, "pay 100");
	}// After mhunt request to make a payment for other students, the system should
		// reject him.
		// Need TS applyPayment, need LogInUesrDoesNotHavePermission() ....Monnan 1207

}
