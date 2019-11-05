/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mebankcompany.mebankfinancetransaction;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.SpringBootApplication;  
import org.springframework.web.bind.annotation.RequestMapping;  

/**
 *
 * @author Selvi Roselin
 */
@SpringBootApplication  
public class MEBankFinanceTransMain {

    private static final String SAMPLE_CSV_FILE_PATH = "./TransactionFile.csv";
    private static List<FinanceTransactionDO> filteredList;
   
    /**
     * main methods to run and read the CSV files with testing of sample Input data of Account_ID, From_Date and To_Date.
     * @param args
     * @throws IOException 
     */
    public static void readArgumentAndPrintCSVFile() throws IOException {
        String account_ID = "ACC334455";
        String from_Date = "20/10/2018 12:00:00";
        String to_Date = "20/10/2018 19:00:00";
        String current_Date = "20/10/2018 12:47:55";
     
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        LocalDateTime fromDateTime = LocalDateTime.parse(from_Date, formatter);
        LocalDateTime ToDateTime = LocalDateTime.parse(to_Date, formatter);
        LocalDateTime currentDateTime = LocalDateTime.parse(current_Date, formatter);
        //System.out.println("Dates check = " + checkBetweenTwoDates(fromDateTime, ToDateTime, currentDateTime));  
        printCSVFile(account_ID, fromDateTime, ToDateTime);
    }
    
    public static void main(String[] args) throws IOException {
    	SpringApplication.run(MEBankFinanceTransMain.class, args);  
    }
    
    /**
     * checkBetweenTwoDates checks the date between those 2 dates falls under the createdDated.
     * @param fromDateTime
     * @param ToDateTime
     * @param currentDateTime
     * @return 
     */
    public static boolean checkBetweenTwoDates(LocalDateTime fromDateTime, LocalDateTime ToDateTime, LocalDateTime currentDateTime) {
        
        boolean isBefore = fromDateTime.isBefore(currentDateTime) || fromDateTime.isEqual(currentDateTime);
        //System.out.println("dateTimeOne is before dateTimeOne :: " + isBefore);
         
        boolean isAfter = ToDateTime.isAfter(currentDateTime) || ToDateTime.isEqual(currentDateTime);
        //System.out.println("dateTimeOne is after dateTimeTwo :: " + isAfter);
        return (isBefore && isAfter) ? true : false;
    }
    
    /**
     * printCSVFile reads the CSV file, filter the records based on the business scenarios and prints the output in the console.
     * @param account_ID
     * @param fromDateTime
     * @param ToDateTime
     * @return 
     */
     public static String printCSVFile(String account_ID, LocalDateTime fromDateTime, LocalDateTime ToDateTime) {
    	String strFullString = "";
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
       
            List<FinanceTransactionDO> list = new ArrayList<>();
            int iCount = 0;
            FinanceTransactionDO finTranDO = new FinanceTransactionDO();
            
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                finTranDO = new FinanceTransactionDO();
                String trans_ID = csvRecord.get(0);
                String trans_From_Account_ID = csvRecord.get(1);
                String trans_To_Account_ID = csvRecord.get(2);
                String created_Date = csvRecord.get(3);
                String amount = csvRecord.get(4);
                String trans_type = csvRecord.get(5);
                String related_Trans = "";
                if (trans_type != null && !trans_type.isEmpty() && ("REVERSAL".equals(trans_type.trim()) || "transactionType".equals(trans_type.trim()))) {
                    related_Trans = csvRecord.get(6);
                }
                finTranDO.setTrans_ID(trans_ID.trim());
                finTranDO.setTrans_From_Account_ID(trans_From_Account_ID.trim());
                finTranDO.setTrans_To_Account_ID(trans_To_Account_ID.trim());
                finTranDO.setCreated_Date(created_Date.trim());
              
                if (iCount > 0 ) { // Data value
                    finTranDO.setAmount(Double.valueOf(amount.trim()));
                } else {  // Header record
                    finTranDO.setAmount(0.00);
                }
                finTranDO.setTrans_type(trans_type.trim());
                finTranDO.setRelated_Trans(related_Trans.trim());
                list.add(finTranDO);
                iCount++;
            }
       
            System.out.println("\n The Output is ");
            strFullString = "<br><br> The Output is "; 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
            List<FinanceTransactionDO> filteredList =
                            list
                            .stream()
                            .filter(financeTrans -> financeTrans.trans_type.startsWith("PAYMENT") &&
                                                    checkBetweenTwoDates(fromDateTime, ToDateTime, LocalDateTime.parse(financeTrans.created_Date,formatter)) &&
                                                    financeTrans.trans_From_Account_ID.trim().startsWith(account_ID.trim())
                                    )
                            .collect(Collectors.toList());
         
            
            setFilteredList(filteredList);
            String name = list.stream().map(l -> l.getTrans_ID() + "," + l.getTrans_From_Account_ID() + "," +
                                                         l.getTrans_To_Account_ID() + "," + l.getCreated_Date() + "," + l.getAmount() + "," +
                                                         l.getTrans_type() + "," + l.getRelated_Trans() +"\n<br>")
				.collect(Collectors.joining(" : ", "<< ", " >>"));
                        
            System.out.println("\n ME Bank Full Financial Transactions : \t" + name); 
            strFullString = strFullString + "<br><br><br> ME Bank Full Financial Transactions : &nbsp; &nbsp;" + name;
            
            name = filteredList.stream().map(l -> l.getTrans_ID() + "," + l.getTrans_From_Account_ID() + "," +
                                                         l.getTrans_To_Account_ID() + "," + l.getCreated_Date() + "," + l.getAmount() + "," +
                                                         l.getTrans_type() + "," + l.getRelated_Trans() +"\n<br>")
				.collect(Collectors.joining(" : ", "<< ", " >>"));
            System.out.println("\n ME Bank Financial Filtered Transactions based on the Business Scenarios : " + name); 
            strFullString = strFullString + "<br><br><br> ME Bank Financial Filtered Transactions based on the business scenarios : " + name;
            double sum = filteredList.stream().map(FinanceTransactionDO::getAmount).mapToDouble(Double::doubleValue).sum();
            System.out.println("Relative balance for the period is: -$" + sum);
            strFullString = strFullString + "<br><br><br> Relative balance for the period is: -$" + sum;
            System.out.println("Number of transactions included is: " + filteredList.stream().count());
            strFullString = strFullString + "<br><br><br> Number of transactions included is: " + filteredList.stream().count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strFullString;
    }
    
    public static List<FinanceTransactionDO> getFilteredList() {
 		return filteredList;
 	}

 	public static void setFilteredList(List<FinanceTransactionDO> filteredList) {
 		MEBankFinanceTransMain.filteredList = filteredList;
 	}
    
 	/**
     * The below codes are not used anywhere
     */
    private void nonUsedCodes() {
        /*System.out.println("Record No - " + csvRecord.getRecordNumber());
           System.out.println("---------------");
           System.out.println("Transaction ID : " + trans_ID);
           System.out.println("Transaction From Account ID : " + trans_From_Account_ID);
           System.out.println("Transaction To Account ID : " + trans_To_Account_ID);
           System.out.println("created_Date : " + created_Date);
           System.out.println("Amount : " + amount);
           System.out.println("Transaction Type : " + trans_type);
           System.out.println("Related Transaction : " + related_Trans);
           System.out.println("---------------\n\n");*/

           /*list.add(trans_ID.trim());
           list.add(trans_From_Account_ID.trim());
           list.add(trans_To_Account_ID.trim());
           list.add(created_Date.trim());
           list.add(amount.trim());
           list.add(trans_type.trim());
           list.add(related_Trans.trim()); */
          /*mapFinancialValue.forEach(
                (key, value)->System.out.println(
                        "Record No - : " + key + "\t\t"
                        + "List of Transactions : " + value));
            System.out.println("\nThe Output is ");
            
            mapFinancialValue.forEach(
                (key, value)-> value.stream().filter((s) -> s.equals(account_ID)).forEach(System.out::println)); */
        
        // LocalDateTime fromDateTime = LocalDateTime.parse("2018-10-20T12:00:0");
        // LocalDateTime ToDateTime = LocalDateTime.parse("2018-10-20T19:00:00");
        // LocalDateTime currentDateTime = LocalDateTime.parse("2018-10-20T12:47:55");
        
        // Map<Integer, ArrayList> mapFinancialValue = new HashMap<>();
        // mapFinancialValue.put(iCount++, (ArrayList) list);
    }
 }
