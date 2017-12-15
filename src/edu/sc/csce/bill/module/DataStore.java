package edu.sc.csce.bill.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.time.LocalDate;
import java.util.GregorianCalendar;


import com.google.gson.Gson;

import edu.sc.csce.bill.exception.BillNonExistent;
import edu.sc.csce.bill.exception.BillsNotSavedException;
import edu.sc.csce.bill.exception.CoursesNotLoadedException;
import edu.sc.csce.bill.exception.LogInUesrDoesNotHavePermission;
import edu.sc.csce.bill.exception.StudentProfileNonExistent;
import edu.sc.csce.bill.exception.StudentRecordsNotLoadedException;
import edu.sc.csce.bill.exception.StudentRecordsNotSavedException;
import edu.sc.csce.bill.exception.UserNotFoundException;
import edu.sc.csce.bill.exception.UsersNotLoadedException;
import edu.sc.csce.bill.model.Bill;
import edu.sc.csce.bill.model.ClassStatus;
import edu.sc.csce.bill.model.College;
import edu.sc.csce.bill.model.Course;
import edu.sc.csce.bill.model.Date;
import edu.sc.csce.bill.model.Permission;
import edu.sc.csce.bill.model.Semester;
import edu.sc.csce.bill.model.Student;
import edu.sc.csce.bill.model.StudentRecord;
import edu.sc.csce.bill.model.Term;
import edu.sc.csce.bill.model.Transaction;
import edu.sc.csce.bill.model.Type;

public class DataStore 
{
	public static List<StudentRecord> studentRecords;
	private static List<StudentRecord> studentRecordsOriginal;
	private static String studentRecordsFileName;
	public static List<Course> courses;
	private static List<Course> coursesOriginal;
	private static String coursesFileName;
	public static List<Permission> users;
	public static List<Permission> usersOriginal;
	private static String usersFileName;
	public static List<Bill> billHistory;
	private static String billFileName;


	/**
	 * Load a list of courses from the file at the location provided by "fileName"
	 * @param fileName The path of the courses file.
	 */
	public static void loadCourses(String fileName) throws CoursesNotLoadedException 
	{
		coursesFileName = fileName;
		String coursesStr = null;
		BufferedReader br = null;
		try 
		{
			br = new BufferedReader(new FileReader(fileName));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) 
		    {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    coursesStr = sb.toString();
		    br.close();
		} catch (Exception ex) 
		{
			throw new CoursesNotLoadedException();
		}
		

		if (coursesStr == null || "".equals(coursesStr)) 
		{
			throw new CoursesNotLoadedException();
		}
		
		Gson gson = new Gson();
		try 
		{
			Course[] courses = gson.fromJson(coursesStr, Course[].class);
			DataStore.courses = Arrays.asList(courses);
			DataStore.coursesOriginal = Arrays.asList(courses);
		} catch (Exception ex) 
		{
			throw new CoursesNotLoadedException();
		}
	}
	
	/**
	 * Load a list of student records from the file at the location provided by "fileName"
	 * @param fileName The path of the student records file.
	 */
	public static void loadRecords(String fileName) throws StudentRecordsNotLoadedException 
	{
		studentRecordsFileName = fileName;
		String recordsStr = null;
		BufferedReader br = null;
		try 
		{
			br = new BufferedReader(new FileReader(fileName));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) 
		    {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    recordsStr = sb.toString();
		    br.close();
		} catch (Exception ex) 
		{
			throw new StudentRecordsNotLoadedException();
		}
		
		if (recordsStr == null || "".equals(recordsStr)) 
		{
			throw new StudentRecordsNotLoadedException();
		}
		
		Gson gson = new Gson();
		try 
		{
			StudentRecord[] studentRecords = gson.fromJson(recordsStr, StudentRecord[].class);
			DataStore.studentRecords = Arrays.asList(studentRecords);
			DataStore.studentRecordsOriginal = Arrays.asList(studentRecords);
		} catch (Exception ex) 
		{
			throw new StudentRecordsNotLoadedException();
		}
	}
	
	/**
	 * Load a list of users from the file at the location provided by "fileName"
	 * @param fileName The path of the users file.
	 */
	public static void loadUsers(String fileName) throws UsersNotLoadedException 
	{
		usersFileName = fileName;
		String usersStr = null;
		BufferedReader br = null;
		try 
		{
			br = new BufferedReader(new FileReader(fileName));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) 
		    {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    usersStr = sb.toString();
		    br.close();
		} catch (Exception ex) 
		{
			throw new UsersNotLoadedException();
		}
		
		if (usersStr == null || "".equals(usersStr)) 
		{
			throw new UsersNotLoadedException();
		}
		
		Gson gson = new Gson();
		try 
		{
			Permission[] users = gson.fromJson(usersStr, Permission[].class);
			DataStore.users = Arrays.asList(users);
			DataStore.usersOriginal = Arrays.asList(users);
		} catch (Exception ex) 
		{
			throw new UsersNotLoadedException();
		}
	}
	
	
	/**
	 * Returns the Permission object associated with the given user id.
	 * @param userId The user id to search for.
	 * @return Returns the Permission that matches the given user id.
	 */
	public static Permission getPermissionByUserId(String userId) 
	{
		if (users == null) 
		{
			return null;
		} //else
		
		for (Permission user : users) 
		{
			String id = user.getId();
			if (id != null && id.equals(userId)) 
			{
				return user;
			}
		} //else
		
		return null;
	}
	
	/**
	 * Saves the given student records to the provided file name.
	 * @param fileName File name of the file to save to.
	 * @param studentRecords The list of student records to save.
	 * @throws StudentRecordsNotSavedException
	 */
	private static void saveStudentRecords(String fileName, List<StudentRecord> studentRecords) throws StudentRecordsNotSavedException 
	{
		Gson gson = new Gson();
		String json = gson.toJson(studentRecords);
		
		try 
		{
			File myFile = new File(fileName);
	        myFile.createNewFile();
	        FileOutputStream fOut = new FileOutputStream(myFile);
	        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	        myOutWriter.append(json);
	        myOutWriter.close();
	        fOut.close();
		} catch (Exception ex) 
		{
			throw new StudentRecordsNotSavedException();
		}
	}
	
	
	/**
	 * Returns the List<string> object associated with the given user college.
	 * @param college The college to search for.
	 * @return Returns the List of student id that matches the given college.
	 */
	public static List<String> getStudentId(College college) 
	{
		List<String> studentIDs = new ArrayList<String>();
		if(college==College.ARTS_AND_SCIENCES||college==College.ENGINEERING_AND_COMPUTING)
		{
			for(StudentRecord record:studentRecords)
			{
				if(record.getCollege()==college)
				{
					String id = record.getStudent().getId();
					studentIDs.add(id);
				}
			}
		}else if(college==College.GRADUATE_SCHOOL)
		{
			for(StudentRecord record:studentRecords)
			{
				if(record.getClassStatus()==ClassStatus.MASTERS||record.getClassStatus()==ClassStatus.PHD)
				{
					String id = record.getStudent().getId();
					studentIDs.add(id);
				}
			}
		}
		return studentIDs;
	}
	
	/**
	 * Returns the StudentRecord object associated with the given user id.
	 * @param userId The user id to search for.
	 * @return Returns the StudentRecord that matches the given user id.
	 */
	public static StudentRecord getStudentRecord(String userId) 
	{
		if (studentRecords == null) 
		{
			return null;
		} //else
		
		for (StudentRecord record : studentRecords) 
		{
			Student student = record.getStudent();
			if (student != null) 
			{
				String id = student.getId();
				if (id != null && id.equals(userId)) 
				{
					return record;
				}
			}
		} //else
		
		return null;
	}
	
	/**
	 * Update a StudentRecord of an existing student.
	 * @param userId The id of the student to whom the student record belongs.
	 * @param newRecord The new StudentRecord that will replace the old one.
	 * @param permanent Indicates whether the change is permanent or temporary.
	 * @throws StudentProfileNonExistent
	 */
	public static void updateStudentRecord(String userId, StudentRecord newRecord, boolean permanent) throws StudentProfileNonExistent, StudentRecordsNotSavedException 
	{
		StudentRecord record = getStudentRecord(userId);
		if (record == null) 
		{
			throw new StudentProfileNonExistent();
		} //else
		
		int indexOfRecord = studentRecords.indexOf(record);
		if (indexOfRecord < 0) 
		{
			throw new StudentProfileNonExistent();
		} //else
		
		//overwrite existing Record
		studentRecords.set(indexOfRecord, newRecord);
		
		if (permanent) 
		{
			int index = studentRecordsOriginal.indexOf(record);
			if (index >= 0) 
			{
				studentRecordsOriginal.set(index, record);
				saveStudentRecords(studentRecordsFileName, studentRecordsOriginal);
			}
		}
	}
	
	/*
	 * generateBill
	 */
	public static Bill generateBill(String userId)
	{
		double balance = 0;
		Bill retBill = new Bill();
		double totalTransac = 0;
		LocalDate dateNow = LocalDate.now();
		int dayNow = dateNow.getDayOfMonth();
		int monthNow = dateNow.getMonthValue();
		int yearNow = dateNow.getYear();
		for(StudentRecord record : studentRecords)
		{
			if(record.getStudent().getId()==userId)
			{
				retBill.setStudent(record.getStudent());
				retBill.setCollege(record.getCollege());
				retBill.setClassStatus(record.getClassStatus());
				retBill.setTransaction(record.getTransactions());
				double charge = Count.countFinalFee(record).doubleValue();
				for(Transaction transac :retBill.getTransaction())
				{
					int month = transac.getTransactionDate().getMonth();
					int day = transac.getTransactionDate().getDay();
					int year = transac.getTransactionDate().getYear();
					Calendar currentDateBefore3Months = Calendar.getInstance();
					currentDateBefore3Months.add(Calendar.MONTH, -3);
					java.util.Date transacDate = new GregorianCalendar(year, month - 1, day).getTime();
					java.util.Date endDate = new GregorianCalendar(yearNow, monthNow - 1, dayNow).getTime();
					//get transactions this semester(within 3 months)
					if (transacDate.after(currentDateBefore3Months.getTime()) && transacDate.before(endDate)) 
					{
						totalTransac = totalTransac + transac.getAmount();
					}
				}
				balance = charge - totalTransac;
				retBill.setBalance(balance);
			}
			return retBill;
		}
		return null;
	}

	/*
	 * applyPayment
	 */
	public static void applyPayment(String userId, double amount, String note)throws Exception
	{
		Type type = Type.PAYMENT;
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
	    int year = calendar.get(Calendar.YEAR);
	    edu.sc.csce.bill.model.Date dateI = new edu.sc.csce.bill.model.Date(month,day,year);

		Transaction transac = new Transaction(type,dateI,amount,note);
		for(StudentRecord record : studentRecords)
		{
			if(record.getStudent().getId()==userId)
			{
				record.addTransactions(transac);
			}
		}
	}
	
	public static void addBill(Bill bill)throws BillsNotSavedException
	{
		if(bill==null)
		{
			throw new BillsNotSavedException();
		}
		if(billHistory==null)
		{
			billHistory = new ArrayList<Bill>();
		}else
		{
			billHistory.add(bill);
		}
	}
	
	public static List<Bill> getBillHistory()
	{
		return billHistory;
	}
	
	//get bill using user id
	public static List<Bill> getBill(String userId)
	{
		List<Bill>retBill = new ArrayList<Bill>();
		for(Bill bill : billHistory)
		{
			Student student = bill.getStudent();
			if (student != null) 
			{
				String id = student.getId();
				if (id != null && id.equals(userId)) 
				{
					retBill.add(bill);
				}
			}
		}
		return retBill;
	}
	public static void setBillHistory(List<Bill> billHistory)
	{
		DataStore.billHistory = billHistory;
	}
	
//		public static void addPayment(String userId, double amount, String note) throws UserNotFoundException, StudentProfileNonExistent,  LogInUesrDoesNotHavePermission
//	{
//		// TODO
//	} by Monna
	
	public static void addPayment(String userId, BigDecimal amount, String note) throws UserNotFoundException, StudentProfileNonExistent,  LogInUesrDoesNotHavePermission
	{
		// TODO
	}

	public void getCurrentBill(String userId) throws StudentProfileNonExistent, LogInUesrDoesNotHavePermission, UserNotFoundException, BillNonExistent
	{
		// TODO
	}
	public static Date[] getStartDateAndEndDate(Semester semesterBegin, int yearBegin) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Term getTerm(Date currentDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
