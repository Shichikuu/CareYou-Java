package dao;

import factory.DonationFactory;
import factory.WithdrawalFactory;
import model.Donation;
import model.Transaction;
import model.User;
import model.Withdrawal;
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

    public int getUserAmount(User user) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE userID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Transaction> getTransactionByUserID(int userID) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE userID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = null;
                    if(rs.getString("transactionType").equals("Withdrawal")) {
                        transaction = withdrawalFactory.createTransactionFromResultSet(rs);
                    }else{
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

    public List<Transaction> getTransactionByProgramId(int programId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE programID = ? ORDER BY transactionDate DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, programId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = null;
                    if(rs.getString("transactionType").equals("Withdrawal")) {
                        transaction = withdrawalFactory.createTransactionFromResultSet(rs);
                    }else{
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

    public List<Donation> getDonationsByProgramId(int programId) {
        List<Donation> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE programID = ? AND transactionType = 'Donation' ORDER BY transactionDate DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, programId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Donation transaction = (Donation) donationFactory.createTransactionFromResultSet(rs);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Donation> getDonationFromUserID(int id) {
        List<Donation> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE userID = ? AND transactionType = 'Donation'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Donation transaction = (Donation) donationFactory.createTransactionFromResultSet(rs);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Withdrawal> getWithdrawalFromUserID(int id) {
        List<Withdrawal> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE userID = ? AND transactionType = 'Withdrawal'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Withdrawal transaction = (Withdrawal) donationFactory.createTransactionFromResultSet(rs);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public int generateId() {
        String sql = "SELECT MAX(transactionID) FROM transactions";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int insertTransaction(int userID, Date transactionDate, int amount, String transactionType, int programID) {
        String sql = "INSERT INTO transactions (userID, transactionDate, amount, transactionType, programID) VALUES (?, ?, ?, ?, ?)";
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

    public void insertDonation(int transactionId, String paymentMethod, boolean isAnonymous) {
        String sql = "INSERT INTO donations (transactionID, paymentMethod, isAnonymous) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.setString(2, paymentMethod);
            stmt.setBoolean(3, isAnonymous);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertWithdrawal(int transactionId, String withdrawalMethod) {
        String sql = "INSERT INTO withdrawals (transactionID, withdrawMethod) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.setString(2, withdrawalMethod);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addComment(int transactionId, String comment) {
        String sql = "UPDATE donations SET comment = ? WHERE transactionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, comment);
            stmt.setInt(2, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSumOfDonations() {
        String sql = "SELECT SUM(amount) FROM transactions WHERE transactionType = 'Donation'";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
