package model;

import java.util.Date;

public class Donation extends Transaction {
    private String paymentMethod;
    private boolean isAnonymous;
    private boolean hasComment;
    private Comment comment;

    // Constructors

    public Donation(int transactionID, int userID, Date transactionDate, int amount, Integer programID, String paymentMethod, boolean isAnonymous, boolean hasComment) {
        super(transactionID, userID, transactionDate, amount, programID);
        this.paymentMethod = paymentMethod;
        this.isAnonymous = isAnonymous;
        this.hasComment = hasComment;
        this.transactionType = "Donation";
    }



    @Override
    protected void validateTransaction() {
        // Validate donation-specific details
    }

    @Override
    protected void executeTransaction() {
        // Execute donation-specific logic
    }
}
