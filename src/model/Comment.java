package model;

public class Comment {
    private int transactionID;
    private String content;
    private String userName;
    private int userID;

    public Comment(int transactionID, String content, String userName, int userID) {
        this.transactionID = transactionID;
        this.content = content;
        this.userName = userName;
        this.userID = userID;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
