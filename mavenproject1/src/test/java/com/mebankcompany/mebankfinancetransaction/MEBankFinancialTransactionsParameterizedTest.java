package com.mebankcompany.mebankfinancetransaction;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.Test;
import org.junit.Before;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class MEBankFinancialTransactionsParameterizedTest {
	   String account_ID;
	   String from_Date ;
       String to_Date;
       String current_Date;
	   private Boolean expectedResult;
	   int count;
	   double sum;
	   private MEBankFinanceTransMain objMEBankFinanceTransMain;
	   
	   @Before
	   public void initialize() {
		   objMEBankFinanceTransMain = new MEBankFinanceTransMain();
	   }

	   // Each parameter should be placed as an argument here
	   // Every time runner triggers, it will pass the arguments
	   // from parameters we defined in inputMEFinancialTransaction() method
	   public MEBankFinancialTransactionsParameterizedTest(String account_ID, String from_Date, String to_Date, String current_Date, Boolean expectedResult, int count, double sum) {
	      this.account_ID = account_ID;
	      this.from_Date = from_Date;
	      this.to_Date = to_Date;
	      this.current_Date = current_Date;
	      this.expectedResult = expectedResult;
	      this.count = count;
	      this.sum = sum;
	   }

	   @Parameterized.Parameters
	   public static Collection inputMEFinancialTransaction() {
	      return Arrays.asList(new Object[][] {
	         {"ACC334455", "20/10/2018 12:00:00" ,"20/10/2018 19:00:00","20/10/2018 12:47:55", true, 1, 25},
	         {"ACC998877", "20/10/2018 12:00:00" ,"20/10/2018 19:00:00","20/10/2018 12:47:55", true, 1, 5},
	         {"ACC998877", "20/10/2018 12:00:00" ,"20/10/2018 19:00:00","20/10/2018 01:47:55", false, 1, 5},
	         {"ACC998877", "20/10/2018 12:00:00" ,"20/10/2018 19:00:00","20/10/2018 10:47:55", false, 1, 5},
	         {"ACC334455", "20/10/2018 12:00:00" ,"21/10/2018 19:00:00","21/10/2018 12:47:55", true, 2, 32.25},
	      });
	   }

	   /**
	    * This test runs 5 times based on the input arraylist of elements -> Financial Transactions.
	    */
	   @Test
	   public void given_METransaction_Details_when_Dates_Between_those_current_Date_Return_True_Or_False() {
	      System.out.println("Parameterized Number is : Account_ID = " + this.account_ID + "," + " From_Date = " + this.from_Date + " , To_Date = " + this.to_Date + " , Current_Date=" + this.current_Date);
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
	      LocalDateTime fromDateTime = LocalDateTime.parse(this.from_Date, formatter);
	      LocalDateTime ToDateTime = LocalDateTime.parse(this.to_Date, formatter);
	      LocalDateTime currentDateTime = LocalDateTime.parse(this.current_Date, formatter);
	      assertEquals(expectedResult, 
	    		  MEBankFinanceTransMain.checkBetweenTwoDates(fromDateTime, ToDateTime, currentDateTime));
	   }
	   
	   /**
	    * This test runs 5 times based on the input arraylist of elements -> Financial Transactions.
	    */
	   @Test
	   public void given_METransaction_Details_when_Dates_Between_those_current_Date_Return_True_Or_False_And_Evaluate_Relative_Balance() {
		    System.out.println("Parameterized Number is : Account_ID = " + this.account_ID + "," + " From_Date = " + this.from_Date + " , To_Date = " + this.to_Date + " , Current_Date=" + this.current_Date);
		      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
		      LocalDateTime fromDateTime = LocalDateTime.parse(this.from_Date, formatter);
		      LocalDateTime ToDateTime = LocalDateTime.parse(this.to_Date, formatter);
		      LocalDateTime currentDateTime = LocalDateTime.parse(this.current_Date, formatter);
		      assertTrue(MEBankFinanceTransMain.printCSVFile(account_ID, fromDateTime, ToDateTime).length() > 0);
		      assertTrue(MEBankFinanceTransMain.getFilteredList().size() == this.count);
		      double sum = MEBankFinanceTransMain.getFilteredList().stream().map(FinanceTransactionDO::getAmount).mapToDouble(Double::doubleValue).sum();
		      assertTrue(sum == this.sum);
	   }

}
