package model;

import java.util.Date;

public class Withdrawal extends Transaction {
    private String bankAccount;
    private String bankName;
    private String withdrawMethod;

    // Constructors

    public Withdrawal(int transactionID, int userID, Date transactionDate, int amount, Integer programID, String bankAccount, String bankName, String withdrawMethod) {
        super(transactionID, userID, transactionDate, amount, programID);
        this.bankAccount = bankAccount;
        this.bankName = bankName;
        this.withdrawMethod = withdrawMethod;
        this.transactionType = "Withdrawal";
    }

    @Override
    protected void validateTransaction() {
        // Validate withdrawal-specific details
    }

    @Override
    protected void executeTransaction() {
        // Execute withdrawal-specific logic
    }
}
