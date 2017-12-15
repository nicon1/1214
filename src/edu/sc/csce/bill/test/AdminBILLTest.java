package edu.sc.csce.bill.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sc.csce.bill.api.BILLIntfAct;
import edu.sc.csce.bill.model.Bill;
import edu.sc.csce.bill.model.Student;
import edu.sc.csce.bill.model.StudentRecord;

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
		bill.logIn("rbob");
	}//The following functional testing is based on a scenario that user is a ADMIN
	//who holds a user ID "mmattews" and belongs to the GRADUATE_SCHOOL college, 
	//trying to use the BILL system. Which "mhunt" is a PHD STUDENT of ARTS_AND_SCIENCE,
	//and ggay is a SUDENT of ENGINEERING_AND_COMPUTING college.

	@Test
	public void testLogOut() {
		bill.logOut();
		assertNull(bill.getUser());
	}//After mmattews request to log out, the current user should be null.
	
	@Test
	public void testSuccessfulLogin() throws Exception {
		bill.logOut();
		bill.logIn("mmattews");
		assertEquals(bill.getUser(), "mmattews");
	}//After mmattews request to log in, the current user should be showing mmattews.
		
	@Test
	public void testAdminGetRecord() throws Exception {
		StudentRecord record = bill.getRecord("mhunt");
		assertEquals("mhunt", record.getStudent().getId());
	}//After mmattews request to view student ggay's record, the system should return the record with his id.

	@Test (expected = IllegalArgumentException.class)
	public void testAdminGetOthersRecord() throws Exception {
		StudentRecord record = bill.getRecord("ggay");
	}//If mmattews request to view student record of a different college, the system should reject him.
	//Need TS LogInUesrDoesNotHavePermission()-->name spelling error.... Monnan 1207
	

	@Test
	public void testAdminEditRecordInstance() throws Exception {
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
		//Assume the mhunt edit the above information that is 
		//temporarily hold in newstudent and newRecord
		bill.editRecord("mhunt", newRecord, false);
		assertEquals("ggay", newstudent.getId());
		assertEquals("Hello", newstudent.getFirstName());
		assertEquals("Hello", newstudent.getLastName());
		assertEquals("000-000-0000", newstudent.getPhone());
		assertEquals("newemail@email.com", newstudent.getEmailAddress());
		assertEquals("JavaCity", newstudent.getAddressCity());
		assertEquals("JV", newstudent.getAddressState());
		assertEquals("66666", newstudent.getAddressPostalCode());
	}// After rbob request to edit his student record temporarily, the system should temporarily hold the info.
	//Need TS editRecord().
	
	@Test
	public void testAdminEditRecordPermanent() throws Exception {
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
	}// After mmattews request to edit his student record permanently, the system should save it to DB.
	// Need TS editRecord() Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testAdmintEditUneditRecord() throws Exception {
		StudentRecord newRecord = new StudentRecord();
		Student newstudent = new Student();
		newRecord.setStudent(newstudent);
		newstudent.setId("1234");
		//Assume the user edit the above information that is 
		//hold in newstudent and newRecord
		bill.editRecord("mhunt", newRecord, true);
	}// If rbob request to edit a item that is not allowed to change in student record (i.e Id), the system should reject him.
	// Need TS editRecord(), need fix StudentRecordsNotEditedException Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testAdminEditOthersRecord() throws Exception {
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
	}//If mmattews request to edit student record of a different college, the system should reject him.
	//Need TS editRecord(), need TS LogInUesrDoesNotHavePermission() and it has name spelling error... Monnan 1207
	
	@Test
	public void testAdminGerateBill() throws Exception {
		Bill newbill = generateBill("mhunt");
		assertEquals("mhunt", bill.generateBill("mhunt").getId());
		assertEquals("PHD", bill.generateBill("mhunt").getClassStatus());
		assertEquals("12", bill.generateBill("mhunt").getTotalNumCredits());
		assertEquals(false, bill.generateBill("mhunt").getResident());
		assertEquals(false, bill.generateBill("mhunt").getOutsideInsurance());
		assertEquals("13740.00", bill.generateBill("mhunt").getTuition());
		assertEquals("16566.00", bill.generateBill("mhunt").getBlance());
		assertEquals("200.00", bill.generateBill("mhunt").getTotalTechnologyFee());
		assertEquals("2547.00", bill.generateBill("mhunt").getHealthInsurance());
	}//After mmattews request to view his bill status, the system should return his bill status.
	//Need TS generateBill(), need class bill?? Create methods for Bill instance Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testAdminGenerateOhtersBill() throws Exception {
		Bill newbill = generateBill("ggay")
	}//After mmattews request to view bill status of students of a different college , the system should reject him.
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
	}//After mmattews request to view payment history of a period, the system should return the payment record.
	//Need TS viewCharges(), need a class Transaction?? Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testStudentViewOtherCharges() throws Exception {
		ArrayList<Transactions> charges = bill.viewCharges("ggay", 9, 9, 2016, 9, 12,
				2016);
	}//If mmattews request to view other college students' payment history of a period, the system should reject him.
	//Need TS viewCharges(), need set LogInUesrDoesNotHavePermission(). Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testStudentApplyPayment() throws Exception {
		//mhunt's balance in DB now is 16566.00
		bill.applyPayment("mhunt", 100.00, "pay 100");
		assertEquals(16466.00, bill.getRecord("mhunt").getBalance());
	}//After mmattews request to make a payment, the system should record the payment into DB
	//Need TS applyPayment....Monnan 1207
	
	@Test (expected = IllegalArgumentException.class)
	public void testStudentApplyPaymentToOthers() throws Exception {
		bill.applyPayment("ggay", 100.00, "pay 100");
	}//After mmattews request to make a payment for students of different college, the system should reject him.
	//Need TS applyPayment, need LogInUesrDoesNotHavePermission() ....Monnan 1207
	
	

	
	
}
