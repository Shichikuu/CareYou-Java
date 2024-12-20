package model;

import dao.TransactionRepository;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import static main.Main.currUser;

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

    public Transaction(Program program) {
        this.userID = currUser.getUserId();
        this.transactionDate = new Date();
        this.programID = program.getProgramID();
    }


    // Template method
    public final int processTransaction(Program program) {
        int errorCode = validateTransaction(program);
        if(errorCode == 1) {
            createTransactionHeader();
            executeTransaction(program);
            recordTransaction();
        }
        return errorCode;
    }

    protected abstract int validateTransaction(Program program);

    protected void createTransactionHeader(){
        try {
            TransactionRepository transactionRepo = TransactionRepository.getInstance();
            int id = transactionRepo.insertTransaction(userID, transactionDate, amount, transactionType, programID);
            setTransactionID(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void executeTransaction(Program program);

    protected abstract void recordTransaction();

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

    private String formatAmount(double amount) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeID);
        currencyFormat.setMaximumFractionDigits(0);
        String formattedPrice = currencyFormat.format(amount);

        return formattedPrice.replace("Rp", "Rp ");
    }

    public int getAmount() {
        return amount;
    }


    public String getStringAmount() {
        return formatAmount(amount);
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
