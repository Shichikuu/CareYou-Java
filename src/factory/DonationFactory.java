package factory;

import data.DonationData;
import data.TransactionData;
import model.Donation;
import model.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DonationFactory implements TransactionFactory {
    @Override
    public Transaction createTransaction(TransactionData data) {
        if (!(data instanceof DonationData)) {
            throw new IllegalArgumentException("Invalid data type for Donation");
        }
        DonationData donationData = (DonationData) data;

        int transactionID = donationData.getTransactionID();
        int userID = donationData.getUserID();
        Date transactionDate = donationData.getTransactionDate();
        int amount = donationData.getAmount();
        int programID = donationData.getProgramID();
        String paymentMethod = donationData.getPaymentMethod();
        boolean isAnonymous = donationData.isAnonymous();
        boolean hasComment = donationData.isHasComment();

        return new Donation(transactionID, userID, transactionDate, amount, programID, paymentMethod, isAnonymous, hasComment);
    }

    @Override
    public Transaction createTransactionFromResultSet(ResultSet rs) throws SQLException {

        int transactionID = rs.getInt("transactionID");
        int userID = rs.getInt("userID");
        Date transactionDate = rs.getDate("transactionDate");
        int amount = rs.getInt("amount");
        int programID = rs.getInt("programID");
        String paymentMethod = rs.getString("paymentMethod");
        boolean isAnonymous = rs.getBoolean("isAnonymous");
        boolean hasComment = rs.getBoolean("hasComment");
        return new Donation(transactionID, userID, transactionDate, amount, programID, paymentMethod, isAnonymous, hasComment);
    }
}
