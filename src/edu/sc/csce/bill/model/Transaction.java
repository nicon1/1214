package edu.sc.csce.bill.model;

//import java.math.double;

public class Transaction 
{
//	public Transaction() {
//		
//	}
//	public Transaction(Type type,Date transactionDate,double amount, String note) {
//		super();
//		this.type = type;
//		this.transactionDate = transactionDate;
//		this.amount = amount;
//		this.note = note;
//	} // by Monna
	private Type type;
	private Date transactionDate;
	private double amount; //by Monna--1214hao
	//private double amount;
	private String note;
	
	public Transaction(Type type,Date transactionDate,double amount, String note) 
	{
		super();
		this.type = type;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.note = note;
	}
	
	public Type getType()
	{
		return type;
	}
	public void setType(Type type)
	{
		this.type = type;
	}
	public Date getTransactionDate()
	{
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate)
	{
		this.transactionDate = transactionDate;
	}
//		public double getAmount()
//	{
//		return amount;
//	}
//	public void setAmount(double amount)
//	{
//		this.amount = amount;
//	} // Monna
	
	public double getAmount()
	{
		return amount;
	}
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
}
