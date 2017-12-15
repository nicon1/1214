package edu.sc.csce.bill.module;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import edu.sc.csce.bill.model.College;
import edu.sc.csce.bill.model.Course;
import edu.sc.csce.bill.model.Date;
import edu.sc.csce.bill.model.Permission;
import edu.sc.csce.bill.model.Role;
import edu.sc.csce.bill.model.Transaction;
import edu.sc.csce.bill.model.Type;

public class Valid 
{

	public Valid() 
	{
		super();
	}
//    private String CurrentUserId;
//    private String TargetUserId;
    
	
//	public static void validateUserId(String userId) {
//		CurrentUserId = getUser();
		
		// TODO
	public static void validateUserId(String userId) 
	{
		// TODO
		try 
		{
			if (userId.isEmpty()) 
			{				
				throw new IllegalArgumentException("id is empty");
			}
//			else if (userId.length() != 8) 
//			{			
//				throw new IllegalArgumentException("illegal legnth");
//			}
//			else if (!userId.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")) 
//			{
//				throw new IllegalArgumentException("Error");
//			}
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		}
	}
		/*
	public static void validateUser(Permission permission) 
	{
		try 
		{
			if (permission == null) 
			{
				throw new IllegalArgumentException("null");
			} 
			else if (permission.getFirstName().isEmpty()) 
			{
				throw new IllegalArgumentException("null");
			} 
			else if (permission.getLastName().isEmpty()) 
			{
				throw new IllegalArgumentException("null");
			}
			validateUserId(permission.getId());
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		}
	}
		*/
	/*
	public static void validateUserRole(List<Permission> Roles) 
	{
		try 
		{
			if (Roles == null)
			{
				throw new IllegalArgumentException("null");
			} 
			else if (Roles.isEmpty()) 
			{
				throw new IllegalArgumentException("empty");
			}
			
			if (Permission.getCollege()
						!= College.ARTS_AND_SCIENCES && Permission.getCollege() 
						!= College.ENGINEERING_AND_COMPUTING && Permission.getCollege() 
						!= College.GRADUATE_SCHOOL) 
			{
				throw new IllegalArgumentException("none exist");
			}
			
			if (Permission.getRole() != Role.ADMIN) 
			{
				throw new IllegalArgumentException("none exist");
			}
			else if (Permission.getRole() != Role.STUDENT)
			{
				throw new IllegalArgumentException("none exist");
			}
		}
			catch (IllegalArgumentException e) 
			{
					e.printStackTrace();
			}
	}

    public static void validateCourse(Course course) 
 	{
     	if (course.getId().isEmpty()) 
     	{
			throw new IllegalArgumentException("empty");
		}
		if (course.getName().isEmpty())
		{
			throw new IllegalArgumentException("empty");
		}
		if (course.getNumCredits() <= 0) 
		{
			throw new IllegalArgumentException("0");
		}
    }
*/
    /*
	@SuppressWarnings("unlikely-arg-type")
	public static void validateTransaction(Transaction transaction) 
	{
		if (transaction == null)
		throw new IllegalArgumentException("Null");

		if (!transaction.getType().equals(Type.CHARGE.toString()) && !transaction.getType().equals(Type.PAYMENT.toString())) 
		{
			throw new IllegalArgumentException("None");
		}
		if (BigDecimal.valueOf(transaction.getAmount()).compareTo(new BigDecimal(0)) != 1) 
		{
			throw new IllegalArgumentException("0");
		}
		if (transaction.getNote() == null) 
		{
			throw new IllegalArgumentException("Null");
		}
		
		validateDate(transaction.getTransactionDate().month, transaction.getTransactionDate().day, transaction.getTransactionDate().year);	
	}

*/
	public static boolean validateDate(int month, int day, int year) 
	{
		if (month > 12 || month < 1)
			throw new IllegalArgumentException("Error");
			
		if (day > 31 || day < 1)
			throw new IllegalArgumentException("Error");
		
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyy");
			String date = String.valueOf(month) + String.valueOf(day) + String.valueOf(year);
			simpleDateFormat.parse(date);
		} catch (ParseException e) 
		{
				throw new IllegalArgumentException ("Error");
		}
		return false;
	}

	public static boolean validateDateRange(java.util.Date startDate, java.util.Date endDate) 
	{
		if ( startDate != null && endDate != null) 
		{
	        if (endDate.after(startDate) && startDate.before(endDate)) 
	        {
	            return true;
	        }
	        else {
	            return false;
	        }
	    }
	    return false;

	}
}
