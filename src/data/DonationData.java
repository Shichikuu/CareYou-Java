package data;

import java.util.Date;

public class DonationData extends TransactionData{
    private String paymentMethod;
    private boolean isAnonymous;
    private boolean hasComment;

    public DonationData(int userID, Date transactionDate, int amount, int programID, String paymentMethod, boolean isAnonymous, boolean hasComment) {
        super(userID, transactionDate, amount, programID);
        this.paymentMethod = paymentMethod;
        this.isAnonymous = isAnonymous;
        this.hasComment = hasComment;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public boolean isHasComment() {
        return hasComment;
    }

    public void setHasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }
}
