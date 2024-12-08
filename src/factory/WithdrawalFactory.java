package factory;


import data.TransactionData;
import data.WithdrawalData;
import model.Donation;
import model.Transaction;
import model.Withdrawal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class WithdrawalFactory implements TransactionFactory {

    @Override
    public Transaction createTransaction(TransactionData data) {
        if (!(data instanceof WithdrawalData withdrawalData)) {
            throw new IllegalArgumentException("Invalid data type for Withdrawal");
        }

        int transactionID = 0;
        int userID = withdrawalData.getUserID();
        Date transactionDate = withdrawalData.getTransactionDate();
        int amount = withdrawalData.getAmount();
        int programID = withdrawalData.getProgramID();
//        String bankAccount = withdrawalData.getBankAccount();
//        String bankName = withdrawalData.getBankName();
        String withdrawMethod = withdrawalData.getWithdrawMethod();

        return new Withdrawal(transactionID, userID, transactionDate, amount, programID, withdrawMethod);
    }

    @Override
    public Transaction createTransactionFromResultSet(ResultSet rs) throws SQLException {
        int transactionID = rs.getInt("transactionID");
        int userID = rs.getInt("userID");
        Date transactionDate = rs.getDate("transactionDate");
        int amount = rs.getInt("amount");
        int programID = rs.getInt("programID");
        // String bankAccount = rs.getString("bankAccount");
        // String bankName = rs.getString("bankName");
        String withdrawMethod = rs.getString("withdrawMethod");
        return new Withdrawal(transactionID, userID, transactionDate, amount, programID, withdrawMethod);
    }
}
