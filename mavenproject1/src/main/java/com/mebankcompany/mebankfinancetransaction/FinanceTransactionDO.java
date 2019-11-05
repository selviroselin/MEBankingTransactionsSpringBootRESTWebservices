/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mebankcompany.mebankfinancetransaction;

/**
 *
 * @author Selvi Roselin
 */
public class FinanceTransactionDO {
    protected String trans_ID;
    protected String trans_From_Account_ID;
    protected String trans_To_Account_ID;
    protected String created_Date;
    protected double amount;
    protected String trans_type;
    protected String related_Trans;
    
    /**
     * 
     * @return
     */
    public String getTrans_ID() {
        return trans_ID;
    }

    /**
     * 
     * @param trans_ID
     */
    public void setTrans_ID(String trans_ID) {
        this.trans_ID = trans_ID;
    }

    /**
     * 
     * @return
     */
    public String getTrans_From_Account_ID() {
        return trans_From_Account_ID;
    }

    /**
     * 
     * @param trans_From_Account_ID
     */
    public void setTrans_From_Account_ID(String trans_From_Account_ID) {
        this.trans_From_Account_ID = trans_From_Account_ID;
    }

    /**
     * 
     * @return
     */
    public String getTrans_To_Account_ID() {
        return trans_To_Account_ID;
    }
    
    /**
     * 
     * @param trans_To_Account_ID
     */
    public void setTrans_To_Account_ID(String trans_To_Account_ID) {
        this.trans_To_Account_ID = trans_To_Account_ID;
    }

    /**
     * 
     * @return
     */
    public String getCreated_Date() {
        return created_Date;
    }

    /**
     * 
     * @param created_Date
     */
    public void setCreated_Date(String created_Date) {
        this.created_Date = created_Date;
    }

    /**
     * 
     * @return
     */
    public double getAmount() {
        return amount;
    }
    
    /**
     * 
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     */
    public String getTrans_type() {
        return trans_type;
    }
    
    /**
     * 
     * @param trans_type
     */
    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    /**
     * 
     * @return
     */
    public String getRelated_Trans() {
        return related_Trans;
    }
    
    /**
     * 
     * @param related_Trans
     */
    public void setRelated_Trans(String related_Trans) {
        this.related_Trans = related_Trans;
    }
    
}
