package com.mebankcompany.mebankfinancetransaction.controller;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;  
import com.mebankcompany.mebankfinancetransaction.MEBankFinanceTransMain;

@RestController  
public class MEBankFinanceTransController {  
	
	/**
	 * 
	 * @return
	 */
    @RequestMapping("/hello")  
    public String hello(){  
        return "Hello!";  
    }  
    
    /**
     * 
     * @param accountID
     * @param fromDate
     * @param toDate
     * @return
     */
    @RequestMapping("/meBankTransactionsSubmit")  
    public String meBankTransactionsSubmit(@RequestParam String accountID, @RequestParam String fromDate, @RequestParam String toDate){  
    	
    	String account_ID = accountID;
        String from_Date = fromDate;
        String to_Date = toDate;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        LocalDateTime fromDateTime = LocalDateTime.parse(from_Date, formatter);
        LocalDateTime ToDateTime = LocalDateTime.parse(to_Date, formatter);
        
        return MEBankFinanceTransMain.printCSVFile(account_ID, fromDateTime, ToDateTime);  
    }  
    
    /**
     * 
     * @return
     */
    @RequestMapping("/meBankTransactions")
    public String meBankTransactions() {  
    	return "</head>\r\n" + 
    			"<body>\r\n" + 
    			"   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"red\">ME Bank Transactions</font>\r\n" + 
    			"    <form method=\"get\" action=\"/meBankTransactionsSubmit\">\r\n" + 
    			"        Account ID : <input type=\"text\" name=\"accountID\" value=\"ACC334455\"/>\r\n" + 
    			"        From_Date : <input type=\"text\" name=\"fromDate\" value=\"20/10/2018 12:00:00\"/>\r\n" + 
    			"        To_Date : <input type=\"text\" name=\"toDate\" value=\"20/10/2018 19:00:00\"/>\r\n" + 
    			"        <input type=\"submit\" />\r\n" + 
    			"    </form>\r\n" + 
    			"</body>\r\n" + 
    			"</html>";
    }
}  
 