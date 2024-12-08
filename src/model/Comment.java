package model;

import java.text.NumberFormat;
import java.util.Locale;

public class Comment {
    private int transactionID;
    private String content;
    private String userName;
    private int amount;

    public Comment(int transactionID, String content, String userName, int amount) {
        this.transactionID = transactionID;
        this.content = content;
        this.userName = userName;
        this.amount = amount;
    }

    public Comment(){

    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAmount() {
        return formatAmount(amount);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private String formatAmount(double amount){
        Locale localeID = new Locale("in", "ID");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeID);
        currencyFormat.setMaximumFractionDigits(0);
        String formattedPrice = currencyFormat.format(amount);

        return formattedPrice.replace("Rp", "Rp ");
    }
}
