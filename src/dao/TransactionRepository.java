package dao;

import factory.DonationFactory;
import factory.WithdrawalFactory;
import model.Transaction;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionRepository {

    private static TransactionRepository instance;
    private Connection connection;
    private WithdrawalFactory withdrawalFactory = new WithdrawalFactory();
    private DonationFactory donationFactory = new DonationFactory();

    private TransactionRepository() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static TransactionRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new TransactionRepository();
        }
        return instance;
    }

    public List<Transaction> getTransactionByUserID(int userID) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM transactions t " +
                "LEFT JOIN donations d ON t.transactionId = d.transactionId " +
                "LEFT JOIN withdrawals w ON t.transactionId = w.transactionId " +
                "WHERE t.userId = ? " +
                "ORDER BY t.transactionDate DESC";;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = null;
                    if(rs.getString("transactionType").equals("Withdrawal")) {
                        transaction = withdrawalFactory.createTransactionFromResultSet(rs);
                    }else if(rs.getString("transactionType").equals("Donation")){
                        transaction = donationFactory.createTransactionFromResultSet(rs);
                    }
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

//    public int generateId() {
//        String sql = "SELECT MAX(transactionId) FROM transactions";
//        try (PreparedStatement stmt = connection.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            if (rs.next()) {
//                return rs.getInt(1) + 1;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 1;
//    }

    public int insertTransaction(int userID, Date transactionDate, int amount, String transactionType, int programID) {
        String sql = "INSERT INTO transactions (userId, transactionDate, amount, transactionType, programId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userID);
            stmt.setDate(2, new java.sql.Date(transactionDate.getTime()));
            stmt.setInt(3, amount);
            stmt.setString(4, transactionType);
            stmt.setInt(5, programID);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void insertDonation(int transactionId, String paymentMethod, boolean hasComment) {
        String sql = "INSERT INTO donations (transactionId, paymentMethod, hasComment) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.setString(2, paymentMethod);
            stmt.setBoolean(3, hasComment);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertWithdrawal(int transactionId, String withdrawalMethod) {
        String sql = "INSERT INTO withdrawals (transactionId, withdrawMethod) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.setString(2, withdrawalMethod);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addComment(int transactionId, String content, String username, int amount){
        String sql = "INSERT INTO comments (transactionId, content, username, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.setString(2, content);
            stmt.setString(3, username);
            stmt.setInt(4, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
