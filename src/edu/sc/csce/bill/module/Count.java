package edu.sc.csce.bill.module;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.sc.csce.bill.model.ClassStatus;
import edu.sc.csce.bill.model.InternationalStatus;
import edu.sc.csce.bill.model.Scholarship;
import edu.sc.csce.bill.model.StudentCourse;
import edu.sc.csce.bill.model.StudentRecord;
import edu.sc.csce.bill.model.StudyAbroad;
import edu.sc.csce.bill.model.Transaction;
import edu.sc.csce.bill.model.Type;

public class Count 
{
	public static BigDecimal countFinalFee(StudentRecord studentRecord)
	{
		BigDecimal finalFee = new BigDecimal(0);
		Map<String, BigDecimal> currentCharges = countFee(studentRecord);
		
		for (Map.Entry<String, BigDecimal> entry : currentCharges.entrySet())
			finalFee.add(entry.getValue()); 
		
		return finalFee;
	}
	
	public static Map<String, BigDecimal> countcharges(StudentRecord studentRecord) 
	{
		Map<String, BigDecimal> charges = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> tuitions = countTuition(studentRecord);
		Map<String, BigDecimal> fees = countFee(studentRecord);
		
		charges.putAll(tuitions);
		charges.putAll(fees);
		
		return charges;
	}
	
	public static Map<String, BigDecimal> countTuition(StudentRecord studentRecord) 
	{
		Map<String, BigDecimal> tuitions = new HashMap<String, BigDecimal>();
		
		int totalNonOnlineCredit = getNonOnlineCredit(studentRecord.getStudentCourses());
		int totalOnlineCredit = getOnlineCredit(studentRecord.getStudentCourses());
		
		if (studentRecord.getClassStatus().equals(ClassStatus.MASTERS) ||studentRecord.getClassStatus().equals(ClassStatus.PHD)){				
			if (studentRecord.isResident())
			{
				if (studentRecord.isGradAssistant())
					tuitions.put("RESIDENT GRADUATE ASSISTANT", new BigDecimal(0));			
				else 
					tuitions.put("GRADUATE RESIDENT", new BigDecimal((totalNonOnlineCredit+totalOnlineCredit)*gradResidentTuition));			
			}
			else 
			{
				if (studentRecord.isGradAssistant())
				{
					tuitions.put("RESIDENT GRADUATE ASSISTANT", new BigDecimal((totalNonOnlineCredit+totalOnlineCredit)*(gradResidentTuition-gradResidentTuition)));	
				}
				else if (!studentRecord.getStudyAbroad().equals(StudyAbroad.NONE))
				{
					tuitions.put("GRADUATE STUDY ABROAD", new BigDecimal((totalNonOnlineCredit+totalOnlineCredit)*(gradResidentTuition-gradResidentTuition)));	
				}
				else
				{ 					
					tuitions.put("GRADUATE NONRESIDENT", new BigDecimal((totalNonOnlineCredit*gradResidentTuition)+(totalOnlineCredit*gradResidentTuition)));	
				}
			}
		}
		else
		{
			if (studentRecord.isFreeTuition())
				tuitions.put("FREE TUITION", new BigDecimal(0));
			else if (!studentRecord.getStudyAbroad().equals(StudyAbroad.NONE))
				tuitions.put("STUDY ABROAD", new BigDecimal(totalNonOnlineCredit*(undergradNonResidentTuition-undergradResidentVeteran)));
			else
			{
				if (studentRecord.isActiveDuty())
					tuitions.put("MILITARY UNDERGRADUATE", new BigDecimal(totalNonOnlineCredit*undergradActDutyTuition));
				else if (studentRecord.isResident() || studentRecord.isVeteran())
				{
					tuitions.put("UNDERGRADUATE RESIDENT VETERAN", new BigDecimal(totalNonOnlineCredit*undergradResidentVeteran));
				}
				else if (!studentRecord.isResident() && (
						studentRecord.getScholarship().equals(Scholarship.DEPARTMENTAL)|| 
						studentRecord.getScholarship().equals(Scholarship.WOODROW)|| 
						studentRecord.getScholarship().equals(Scholarship.ATHLETIC)))
				{
						tuitions.put("UNDERGRADUATE NONRESIDENT SCHOLARSHIP WOODROW/DEPARTMENTAL/ATHLETIC", new BigDecimal(totalNonOnlineCredit*undergradNonResidentScholarshipWDA));
				}
				else if (!studentRecord.isResident() && (studentRecord.getScholarship().equals(Scholarship.NONE)))
						tuitions.put("UNDERGRADUATE - NONRESIDENT - TUITION", new BigDecimal(totalNonOnlineCredit*undergradNonResidentTuition));
			}	
		}
		
		return tuitions;
	}
	
	public static Map<String, BigDecimal>  countFee(StudentRecord studentRecord) 
	{
		
		Map<String, BigDecimal> fees = new HashMap<String, BigDecimal>();
		boolean isGradStatus = studentRecord.getClassStatus().equals(ClassStatus.MASTERS) || studentRecord.getClassStatus().equals(ClassStatus.PHD);
		int totalCredit = getTotalCredit(studentRecord.getStudentCourses());
		
		fees.put("TECHNOLOGY FEE", new BigDecimal(totalCredit*technologyFee));
		
		if (totalCredit>=17)
		{
			if (studentRecord.getClassStatus().equals(ClassStatus.MASTERS) || studentRecord.getClassStatus().equals(ClassStatus.PHD))
			{
				if (studentRecord.isResident())
					fees.put("GRADUATE 17 ABOVE", new BigDecimal(gradResidentSeventeenAbove));
				else
					fees.put("GRADUATE NONRESIDENT 17 ABOVE", new BigDecimal(gradNonResidentSeventeenAbove));
			}
			else{
				if (studentRecord.isResident() || studentRecord.isActiveDuty() || !studentRecord.getScholarship().equals(Scholarship.NONE))
						fees.put("UNDERGRADUATE NONRESIDENT 17 ABOVE", new BigDecimal(undergradResidentVeteran));
				else
						fees.put("UNDERGRADUATE NONRESIDENT 17 ABOVE", new BigDecimal(undergradNonResidentSeventeenAbove));
			}
		}
		
		if (studentRecord.getStudyAbroad().equals(StudyAbroad.NONE))
		{
			if (totalCredit < 12 && isGradStatus && studentRecord.isGradAssistant())
			{
				fees.put("GRADUATE ASSISTANTS - LESS THAN 12 HOURS - REQUIRED STUDENT HEALTH CENTER FEE - PER SEMESTER", new BigDecimal(gradAssistantsTwelveHealth));
			}
			else if ((totalCredit >= 9 && totalCredit <= 11) && isGradStatus)
			{
				fees.put("GRADUATE STUDENTS - (9 TO 11 HOURS) - REQUIRED STUDENT HEALTH CENTER FEE - PER SEMESTER", new BigDecimal(gradNineToElevenHealth));
			}
			else if ((totalCredit >= 6 && totalCredit <= 8) && isGradStatus)
			{
				fees.put(" GRADUATE STUDENTS - (6 TO 8 HOURS) - REQUIRED STUDENT HEALTH CENTER FEE - PER SEMESTER", new BigDecimal(gradSixToEightHealth));
			}
			else if ((totalCredit >= 6 && totalCredit <= 11) && !isGradStatus)
			{
				fees.put("UNDERGRADUATE STUDENTS - (6 TO 11 HOURS) - REQUIRED STUDENT HEALTH CENTER FEE - PER SEMESTER", new BigDecimal(undergradSixToElevenHealth));
			}
		}

		if (studentRecord.isInternational() && !studentRecord.getInternationalStatus().equals(InternationalStatus.NONE))
		{
			if (studentRecord.getInternationalStatus().equals(InternationalStatus.SHORT_TERM) && (isGradStatus && totalCredit>=9) ||
					(!isGradStatus && totalCredit>=12))
				fees.put("SHORT TERM INTERNATIONA", new BigDecimal(internationalShortTermFee));
		}
		
		if (!studentRecord.getStudyAbroad().equals(StudyAbroad.NONE))
		{	
			if (studentRecord.isNationalStudentExchange())
				fees.put("NATIONAL STUDENT EXCHANGE", new BigDecimal(nationalStudentExchangeFee));		
			}				
			
		if (studentRecord.isOutsideInsurance())
		{
			if (!studentRecord.getStudyAbroad().equals(StudyAbroad.NONE))
				fees.put("HEALTH INSURANCE", new BigDecimal(outsideInsurance));
			else
				fees.put("STUDY ABROAD INSURANCE", new BigDecimal(outsideStudentAbroadInsurance));
		}
		return fees;
	}
	
	public static BigDecimal countBalance(Map<String, BigDecimal> currentCharges, List<Transaction> allTransaction) 
	{
		BigDecimal balance = new BigDecimal(0);
		for (Transaction t: allTransaction)
		{
			if (t.getType().equals(Type.PAYMENT)) 
				balance = balance.add(t.getAmount());
			else
				balance = balance.subtract(t.getAmount()); 
		}	
		for (Map.Entry<String, BigDecimal> entry : currentCharges.entrySet())
	        balance = balance.subtract(entry.getValue()); 
		return balance;
	}
	
	public static BigDecimal countBalance(List<Transaction> allTransaction) 
	{
		
		BigDecimal balance = new BigDecimal(0);
		for (Transaction t: allTransaction)
		{
			if (t.getType().equals(Type.PAYMENT))
				balance.add(t.getAmount());
			else
				balance.subtract(t.getAmount());
		}
		
		return balance;
	}
	
/*--------------------------------------------------------------------------------------*/	
	
	public static int getTotalCredit(List<StudentCourse> list)
	{
		//?????????
		int totalCredit = 0;
		for(StudentCourse c: list)
		return totalCredit;
		return totalCredit;
	}
	
	public static int getNonOnlineCredit(List<StudentCourse> List)
	{
		int totalCredit = 0;
		for(StudentCourse c: List)
		{
			if(!c.isOnline())
			totalCredit+=c.getNumCredits();
		}
		
		return totalCredit;
	}
	
	public static int getOnlineCredit(List<StudentCourse> List)
	{
		int totalCredit = 0;
		for(StudentCourse c: List)
		{
			if(c.isOnline())
			totalCredit+=c.getNumCredits();
		}
		return totalCredit;
	}
	
/*--------------------------------------------------------------------------------------*/
	
	public static double getOutsideinsurance() 
	{
		return outsideInsurance;
	}

	public static double getGradnonresidentseventeenabove() 
	{
		return gradNonResidentSeventeenAbove;
	}

	public static double getGradresidentseventeenabove() 
	{
		return gradResidentSeventeenAbove;
	}

	public static double getUndergradnonresidentseventeenabove() 
	{
		return undergradNonResidentSeventeenAbove;
	}

	public static double getUndergradresidentveteran() 
	{
		return undergradResidentVeteran;
	}

	public static double getUndergradnonresidentscholarshipwda() 
	{
		return undergradNonResidentScholarshipWDA;
	}

	public static double getGradsixtoeighthealth() 
	{
		return gradSixToEightHealth;
	}

	public static double getGradninetoelevenhealth() 
	{
		return gradNineToElevenHealth;
	}

	public static double getGradassistantstwelvehealth() 
	{
		return gradAssistantsTwelveHealth;
	}

	public static double getUndergradsixtoelevenhealth() 
	{
		return undergradSixToElevenHealth;
	}

	public static double getOutsidestudentabroadinsurance() 
	{
		return outsideStudentAbroadInsurance;
	}

	public static double getTechnologyfee() 
	{
		return technologyFee;
	}

	public static double getInternationalshorttermfee() 
	{
		return internationalShortTermFee;
	}

	public static double getNationalstudentexchangefee() 
	{
		return nationalStudentExchangeFee;
	}

	public static double getStudyabroadcohortfee() 
	{
		return studyAbroadCohortFee;
	}

	public static double getCapstonefee() 
	{
		return capstoneFee;
	}

	/**
	 * CAPSTONE SCHOLAR FEE
	 */
	private final static double capstoneFee = 150;

	/**
	 * COHORT STUDY ABROAD
	 */
	private final static double studyAbroadCohortFee = 300;
	
	/**
	 * NATIONAL STUDENT EXCHANGE PLACEMENT & ADMINISTRATIVE FEE
	 */
	private final static double nationalStudentExchangeFee = 250;
	
	/**
	 * SHORT TERM INTERNATIONAL STUDENT FEE 
	 */
	private final static double internationalShortTermFee = 200;
	
	private final static double undergradActDutyTuition = 289.50;
	
	/**
	 * TECHNOLOGY FEE 
	 */
	private final static double technologyFee = 17;
	
	/**
	 * MANDATORY STUDY ABROAD INSURANCE RATE
	 */
	private final static double outsideStudentAbroadInsurance = 360;
	
	/**
	 * UNDERGRADUATE STUDENTS 6 TO 11 Credit STUDENT HEALTH CENTER FEE
	 */
	private final static double undergradSixToElevenHealth = 119;
	
	/**
	 * GRADUATE ASSISTANTS(GA) LESS THAN 12  Credit STUDENT HEALTH CENTER
	 */	
	private final static double gradAssistantsTwelveHealth= 178;
	
	/**
	 * GRADUATE STUDENTS 9 TO 11 Credit HEALTH CENTER 
	 */
	private final static double gradNineToElevenHealth = 178;
	
	/**
	 * GRADUATE STUDENTS 6 TO 8 Credit HEALTH CENTER
	 */
    private final static double gradSixToEightHealth = 119;
	
	/**
	 * UNDERGRADUATE NONRESIDENT 17 Credit ABOVE
	 */
	private final static double undergradNonResidentSeventeenAbove = 208;

	/**
	 * GRADUATE RESIDENT 17 Credit ABOVE
	 */
	private final static double gradResidentSeventeenAbove = 80;

	/**
	 * GRADUATE NONRESIDENT 17 Credit ABOVE
	 */
	private final static double gradNonResidentSeventeenAbove = 170;

	/**
	 * HEALTH INSURANCE
	 */
	private final static double outsideInsurance = 2020;
	
	/**
	 * GRADUATE RESIDENT TUITION
	 */
	private final static double gradResidentTuition = 533;

	/**
	 * GRADUATE NONRESIDENT TUITION
	 * 
	private final static double gradNonResidentTuition = 1142;
	
	/**
	 * UNDERGRADUATE RESIDENT VETERAN
	 */
	private final static double undergradResidentVeteran = 494.25;

	/**
	 * UNDERGRADUATE NONRESIDENT TUITION
	 */
	private final static double undergradNonResidentTuition = 1331.75;

	/**
	 * UNDERGRADUATE NONRESIDENT SCHOLARSHIP WOODROW/DEPARTMENTAL/ATHLETIC
	 */
	private final static double undergradNonResidentScholarshipWDA= 733.50;
}