package edu.sc.csce.bill.model;

import java.util.List;

public class Bill 
{
//	public Bill() {
//		super();
//	} by 
	private Student student;
	private College college;
	private ClassStatus classStatus;
	private double balance;
	//private static List<Transaction> transaction;
	private List<Transaction> transaction;
	//getter and setter
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
	public ClassStatus getClassStatus()
	{
		return classStatus;
	}
	public void setClassStatus(ClassStatus classStatus)
	{
		this.classStatus = classStatus;
	}
	public double getBalance()
	{
		return balance;
	}
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
//	public List<Transaction> getTransaction()
//	{
//		return transaction;
//	}
	public List<Transaction> getTransaction()
	{
		return this.transaction;
	}
	public void setTransaction(List<Transaction> transaction)
	{
		this.transaction = transaction;
	}
	
	public Bill(Student student, College college, ClassStatus classStatus, double balance,
			List<Transaction> transaction)
	{
		super();
		this.student = student;
		this.college = college;
		this.classStatus = classStatus;
		this.balance = balance;
		this.transaction = transaction;
	}
	
	public Bill()
	{
		super();
	}
}
