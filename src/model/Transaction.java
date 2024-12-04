package model;

import java.util.Date;

public abstract class Transaction {
    protected int transactionID;
    protected int userID;
    protected Date transactionDate;
    protected int amount;
    protected int programID;
    protected String transactionType;

    public Transaction(int transactionID, int userID, Date transactionDate, int amount, int programID) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.programID = programID;
    }


    // Template method
    public final void processTransaction() {
        validateTransaction();
        executeTransaction();
        recordTransaction();
    }

    protected abstract void validateTransaction();

    protected abstract void executeTransaction();

    protected void recordTransaction() {
        // Default implementation for recording transaction
        // Could involve logging, updating database, etc.
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getProgramID() {
        return programID;
    }

    public void setProgramID(Integer programID) {
        this.programID = programID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
