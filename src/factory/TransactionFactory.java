package factory;

import data.TransactionData;
import model.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface TransactionFactory {
    Transaction createTransaction(TransactionData data);
    Transaction createTransactionFromResultSet(ResultSet rs) throws SQLException;
}
