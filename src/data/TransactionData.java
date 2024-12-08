package data;

import java.util.Date;

public abstract class TransactionData {

    protected int userID;
    protected Date transactionDate;
    protected int amount;
    protected int programID;

    public TransactionData(int userID, Date transactionDate, int amount, int programID) {
        this.userID = userID;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.programID = programID;
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
}
