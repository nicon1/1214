import java.math.BigDecimal;

import edu.sc.csce.bill.api.BILLIntf;
import edu.sc.csce.bill.api.BILLIntfAct;
import edu.sc.csce.bill.model.Bill;
import edu.sc.csce.bill.model.StudentRecord;
import edu.sc.csce.bill.model.Transaction;

public class BALANCETest 
{
	private static String studentRecordsPath = "src/resources/students.json";
	private static String usersPath = "src/resources/users.json";
	
	public static void main(String[] args) throws Exception 
	{
		int counter = 0;
		BILLIntf billtest = new BILLIntfAct();
		try 
		{
			billtest.loadUsers(usersPath);
			billtest.loadRecords(studentRecordsPath);
			billtest.logIn("mhunt");
			billtest.applyPayment("mhunt", BigDecimal.valueOf(0.01), "");
		} 
		catch (Exception ex) 
		{
			System.out.println("error");
		}
		
		try 
		{
			StudentRecord studentRecord = billtest.getRecord("mhunt");
			System.out.println("Student name: "+studentRecord.getStudent().getFirstName() + " " + studentRecord.getStudent().getLastName());
			System.out.println("Id: "+billtest.getRecord("mhunt").getStudent().getId());
			System.out.println("Status: "+billtest.getRecord("mhunt").getClassStatus());
			System.out.println("College: "+studentRecord.getCollege());
			//????
			System.out.println("Balance: "+studentRecord.getBalance());
			studentRecord.setOutsideInsurance(true);
		} 
		catch (Exception ex) 
		{
			System.out.println(ex.getMessage());
		}
	}
	
	
//	public static void main(String[] args) throws Exception 
//	{
//		int counter = 0;
//		BILLIntf testBill = new BILLIntfAct();
//		testBill.loadUsers("users_02");
//		testBill.loadRecords("students_02");
//		testBill.logIn("mhunt");
//		testBill.applyPayment("mhunt", BigDecimal.valueOf(0.01), "");
//		StudentRecord studentRecord = testBill.getRecord("mhunt");
//		System.out.println("Balance: "+ studentRecord.getBalance());
//		studentRecord.setOutsideInsurance(true);
//		Bill bill = testBill.generateBill("mhunt");
//		BigDecimal totalCharges = BigDecimal.valueOf(0);
//		for (Transaction t: bill.getTransaction())
//		{
//			System.out.println("Note : "+t.getNote()+"\nAmount : "+t.getAmount().toString());
//			totalCharges = totalCharges.add(t.getAmount());
//		}
//		public static Student getStudent() 
//		{
//			Student student = new Student();
//			Student.setId("mhunt");
//			Student.setFirstName("");
//			Student.setLastName("");
//			Student.setAddressCity("");
//			Student.setAddressPostalCode("");
//			Student.setAddressState("");
//			Student.setAddressStreet("");
//			Student.setEmailAddress("");
//			Student.setPhone("");
//			
//		}
//		public static StudentRecord getStudentRecord()
//		{
//			Term term = new Term(null, counter);
//			term.setSemester(Semester.FALL.toString());
//			term.setYear(2017);
//
//			studentRecord.setCollege(College.ENGINEERING_AND_COMPUTING);
//			studentRecord.setClassStatus(ClassStatus.JUNIOR);
//			studentRecord.setCourses(getCourses()); 
//			studentRecord.setResident(false);
//			studentRecord.setActiveDuty(false);
//			studentRecord.setFreeTuition(false);
//			studentRecord.setGradAssistant(false);
//			studentRecord.setInternational(false);
//			studentRecord.setInternationalStatus(InternationalStatus.SHORT_TERM);
//			studentRecord.setNationalStudentExchange(false);
//			studentRecord.setCapstoneEnrolled(null);
//			studentRecord.setTermBegan(term);
//			studentRecord.setOutsideInsurance(true);
//			studentRecord.setScholarship(Scholarship.DEPARTMENTAL);
//			studentRecord.setStudyAbroad(StudyAbroad.COHORT);
//			studentRecord.setVeteran(false);
//			
//			return studentRecord;
//
//		}
//		public static List<Course> getCourses(){
//			List<Course> courseList = new ArrayList<>();
//			Course c = new Course();
//			c.setId("FakeID");
//			c.setName("FakeName");
//			c.setNumCredits(6);
//			c.setOnline(false);
//			courseList.add(c);
//			return courseList;
//		}
//	}

	public static String getStudentRecordsPath() 
	{
		return studentRecordsPath;
	}

	public static void setStudentRecordsPath(String studentRecordsPath) 
	{
		BALANCETest.studentRecordsPath = studentRecordsPath;
	}

	public static String getUsersPath() 
	{
		return usersPath;
	}

}
