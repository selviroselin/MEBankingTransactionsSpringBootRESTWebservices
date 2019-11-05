package com.mebankcompany.mebankfinancetransaction.controller;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 
 * @author Selvi Roselin
 *
 */
public class MEBankFinanceTransControllerTest extends AbstractTest {

   @Override
   @Before
   public void setUp() {
      super.setUp();
   }
   
   /**
    * MEBankTransactions html page load for entering the account ID, From Date and To Date.
    * @throws Exception
    */
   @Test
   public void meBankTransactions() throws Exception {
	  String uri = "/meBankTransactions";
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertTrue(content.length() > 0);
   }

   /**
    * MEBankTransactions Submit functionality failure scenario
    * @throws Exception
    */
   @Test
   public void meBankTransactionsSubmit_Failure() throws Exception {
	  String uri = "/meBankTransactionsSubmit";
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      	 
      int status = mvcResult.getResponse().getStatus();
      assertEquals(400, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertTrue(content.length() == 0);
   }
   
   /**
    * MEBankTransactions Submit functionality successful scenario
    * @throws Exception
    */
   @Test
   public void meBankTransactionsSubmit_Successful() throws Exception {
	  String uri = "/meBankTransactionsSubmit";
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
    	 .param("accountID", "ACC334455")
    	 .param("fromDate","20/10/2018 12:00:00")
    	 .param("toDate","20/10/2018 19:00:00")
         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      	 
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertTrue(content.length() > 0);
   }
}
