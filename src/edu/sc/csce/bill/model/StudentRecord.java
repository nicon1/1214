package edu.sc.csce.bill.model;

import java.math.BigDecimal;
import java.util.List;

public class StudentRecord
{
//	public StudentRecord() {
//		super();
//	} //by Monna
	private Student student;
	private College college;
	private BigDecimal balance;
	private Term termBegan;
	private Term capstoneEnrolled;
	private ClassStatus classStatus;
	private boolean gradAssistant;
	private boolean international;
	private InternationalStatus internationalStatus;
	private boolean resident;
	private boolean activeDuty;
	private boolean veteran;
	private boolean freeTuition;
	private Scholarship scholarship;
	private StudyAbroad studyAbroad;
	private boolean nationalStudentExchange;	
	private boolean outsideInsurance;
	private List<StudentCourse> courses;
	private List<Transaction> transactions; //note
	//getter and setter
	public BigDecimal getBalance() 
	{
		return balance;
	}
	public Student getStudent()
	{
		return student;
	}
	public void setStudent(Student student)
	{
		this.student = student;
	}
	public College getCollege()
	{
		return college;
	}
	public void setCollege(College college)
	{
		this.college = college;
	}
	public Term getTermBegan()
	{
		return termBegan;
	}
	public void setTermBegan(Term termBegan)
	{
		this.termBegan = termBegan;
	}
	public Term getCapstoneEnrolled()
	{
		return capstoneEnrolled;
	}
	public void setCapstoneEnrolled(Term capstoneEnrolled)
	{
		this.capstoneEnrolled = capstoneEnrolled;
	}
	public ClassStatus getClassStatus()
	{
		return classStatus;
	}
	public void setClassStatus(ClassStatus classStatus)
	{
		this.classStatus = classStatus;
	}
	public boolean isGradAssistant()
	{
		return gradAssistant;
	}
	public void setGradAssistant(boolean gradAssistant)
	{
		this.gradAssistant = gradAssistant;
	}
	public boolean isInternational()
	{
		return international;
	}
	public void setInternational(boolean international)
	{
		this.international = international;
	}
	public InternationalStatus getInternationalStatus()
	{
		return internationalStatus;
	}
	public void setInternationalStatus(InternationalStatus internationalStatus)
	{
		this.internationalStatus = internationalStatus;
	}
	public boolean isResident()
	{
		return resident;
	}
	public void setResident(boolean resident)
	{
		this.resident = resident;
	}
	public boolean isActiveDuty()
	{
		return activeDuty;
	}
	public void setActiveDuty(boolean activeDuty)
	{
		this.activeDuty = activeDuty;
	}
	public boolean isVeteran()
	{
		return veteran;
	}
	public void setVeteran(boolean veteran)
	{
		this.veteran = veteran;
	}
	public boolean isFreeTuition()
	{
		return freeTuition;
	}
	public void setFreeTuition(boolean freeTuition)
	{
		this.freeTuition = freeTuition;
	}
	public Scholarship getScholarship()
	{
		return scholarship;
	}
	public void setScholarship(Scholarship scholarship)
	{
		this.scholarship = scholarship;
	}
	public StudyAbroad getStudyAbroad()
	{
		return studyAbroad;
	}
	public void setStudyAbroad(StudyAbroad studyAbroad)
	{
		this.studyAbroad = studyAbroad;
	}
	public boolean isNationalStudentExchange()
	{
		return nationalStudentExchange;
	}
	public void setNationalStudentExchange(boolean nationalStudentExchange)
	{
		this.nationalStudentExchange = nationalStudentExchange;
	}
	public boolean isOutsideInsurance()
	{
		return outsideInsurance;
	}
	public void setOutsideInsurance(boolean outsideInsurance)
	{
		this.outsideInsurance = outsideInsurance;
	}
	public List<StudentCourse> getStudentCourses()
	{
		return courses;
	}
	public void setStudentCourses(List<StudentCourse> studentCourses)
	{
		this.courses = studentCourses;
	}
	public List<Transaction> getTransactions()
	{
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions)
	{
		this.transactions = transactions;
	}
	public void addTransactionsList(List<Transaction> transaction)
	{
		this.transactions.addAll(transaction);
	}
	public void addTransactions(Transaction transaction)
	{
		this.transactions.add(transaction);
	}
	//constructor
	public StudentRecord(Student student, College college, Term termBegan, Term capstoneEnrolled,
			ClassStatus classStatus, boolean gradAssistant, boolean international,
			InternationalStatus internationalStatus, boolean resident, boolean activeDuty, boolean veteran,
			boolean freeTuition, Scholarship scholarship, StudyAbroad studyAbroad, boolean nationalStudentExchange,
			boolean outsideInsurance, List<StudentCourse> studentCourses, List<Transaction> transactions)
	{
		super();
		this.student = student;
		this.college = college;
		this.termBegan = termBegan;
		this.capstoneEnrolled = capstoneEnrolled;
		this.classStatus = classStatus;
		this.gradAssistant = gradAssistant;
		this.international = international;
		this.internationalStatus = internationalStatus;
		this.resident = resident;
		this.activeDuty = activeDuty;
		this.veteran = veteran;
		this.freeTuition = freeTuition;
		this.scholarship = scholarship;
		this.studyAbroad = studyAbroad;
		this.nationalStudentExchange = nationalStudentExchange;
		this.outsideInsurance = outsideInsurance;
		this.courses = studentCourses;
		this.transactions = transactions;
	}
}
