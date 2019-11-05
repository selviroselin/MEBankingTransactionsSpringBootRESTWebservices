package com.mebankcompany.mebankfinancetransaction;

import static com.mebankcompany.mebankfinancetransaction.MEBankFinanceTransMain.printCSVFile;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;

public class MEBankFinanceTransMainTest {
	
   /**
    * Given ME Bank Finance Transaction Details Account_ID, From_Date, To_Date and it contains the Payment transaction
    * returns successful output 
    * Sum -> Relative balance is 25 
    * Financial Number of Transactions is 1 	
    */
   @Test
   public void test_GivenAcc_ID_From_date_To_Date_When_it_isPayment_thenreturn_Relative_Balance_Success() {
    String account_ID = "ACC334455";
    String from_Date = "20/10/2018 12:00:00";
    String to_Date = "20/10/2018 19:00:00";
    String current_Date = "20/10/2018 12:47:55";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    LocalDateTime fromDateTime = LocalDateTime.parse(from_Date, formatter);
    LocalDateTime ToDateTime = LocalDateTime.parse(to_Date, formatter);
    LocalDateTime currentDateTime = LocalDateTime.parse(current_Date, formatter);
    assertTrue(printCSVFile(account_ID, fromDateTime, ToDateTime).length() > 0);
    assertTrue(MEBankFinanceTransMain.checkBetweenTwoDates(fromDateTime, ToDateTime, currentDateTime));
    assertTrue(MEBankFinanceTransMain.getFilteredList().size() == 1);
    double sum = MEBankFinanceTransMain.getFilteredList().stream().map(FinanceTransactionDO::getAmount).mapToDouble(Double::doubleValue).sum();
    assertTrue(sum == 25);
   }
   
   /**
    * Given ME Bank Finance Transaction Details Account_ID, From_Date, To_Date and it contains the Payment transaction
    * returns failure output 
    * Sum -> Relative balance is not equal to 25 
    * Financial Number of Transactions is not equal to 1	
    */
   @Test
   public void test_GivenAcc_ID_From_date_To_Date_When_it_isPayment_thenreturn_Relative_Balance_fail() {
    String account_ID = "ACC998877";
    String from_Date = "20/10/2018 01:00:00";
    String to_Date = "20/10/2018 19:00:00";
    String current_Date = "20/10/2018 12:47:55";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    LocalDateTime fromDateTime = LocalDateTime.parse(from_Date, formatter);
    LocalDateTime ToDateTime = LocalDateTime.parse(to_Date, formatter);
    LocalDateTime currentDateTime = LocalDateTime.parse(current_Date, formatter);
    assertTrue(printCSVFile(account_ID, fromDateTime, ToDateTime).length() > 0);
    assertTrue(MEBankFinanceTransMain.checkBetweenTwoDates(fromDateTime, ToDateTime, currentDateTime));
    assertTrue(MEBankFinanceTransMain.getFilteredList().size() > 0);
    double sum = MEBankFinanceTransMain.getFilteredList().stream().map(FinanceTransactionDO::getAmount).mapToDouble(Double::doubleValue).sum();
    assertTrue(sum != 25);
   }
   
   /**
    * 
    */
   @Test
   public void test_GivenAcc_ID_From_date_To_Date_When_it_isReversal_thenreturn_Relative_Balance_Success() {
    String account_ID = "ACC334455";
    String from_Date = "20/10/2018 12:00:00";
    String to_Date = "20/10/2018 20:00:00";
    String current_Date = "20/10/2018 12:47:55";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    LocalDateTime fromDateTime = LocalDateTime.parse(from_Date, formatter);
    LocalDateTime ToDateTime = LocalDateTime.parse(to_Date, formatter);
    LocalDateTime currentDateTime = LocalDateTime.parse(current_Date, formatter);
    assertTrue(printCSVFile(account_ID, fromDateTime, ToDateTime).length() > 0);
    assertTrue(MEBankFinanceTransMain.checkBetweenTwoDates(fromDateTime, ToDateTime, currentDateTime));
    assertTrue(MEBankFinanceTransMain.getFilteredList().size() == 1);
    double sum = MEBankFinanceTransMain.getFilteredList().stream().map(FinanceTransactionDO::getAmount).mapToDouble(Double::doubleValue).sum();
    assertTrue(sum == 25);
   }
}
