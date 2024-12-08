package dao;

import factory.UserFactory;
import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.Date;

public class UserRepository {

    private static UserRepository instance;
    private Connection connection;

    private UserRepository() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static UserRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

//    public List<Organization> getTop1Organization() {
//        List<Organization> Organizations = new ArrayList<>();
//        String sql = "SELECT u.*, SUM(t.amount) as totalAmount " +
//                "FROM users u " +
//                "JOIN transactions t ON u.userID = t.userID " +
//                "JOIN organizations o ON u.userID = o.userID " +
//                "WHERE u.role = 'organization' " +
//                "GROUP BY u.userID " +
//                "ORDER BY totalAmount DESC " +
//                "LIMIT 10";
//        try (PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                User user = UserFactory.createUserFromResultSet(rs);
//                Organization organization = OrganizationFactory.createOrganizationFromResultSet(user, rs);
//                Organizations.add(organization);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return Organizations;
//    }
    public User validateUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE userEmail = ? AND userPassword = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = UserFactory.createUserFromResultSet(rs);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUserEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE userEmail = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM users WHERE userName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = UserFactory.createUserFromResultSet(rs);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserProfile(User curr, String name, String email, String password) {
        if (isUserEmailExists(email) && !curr.getEmail().equals(email)) {
            System.out.println("User with this email already exists.");
            return;
        }
        if(isUserNameExists(name) && !curr.getUsername().equals(name)){
            System.out.println("User with this name already exists.");
            return;
        }
        String sql = "UPDATE users SET userName = ?, userEmail = ?, userPassword = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password); // Consider hashing the password
            stmt.setInt(4, curr.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int generateUserId() {
        String sql = "SELECT MAX(userId) FROM users";
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

    public User register(String name, String email, String password) {
        if (isUserEmailExists(email)) {
            System.out.println("User with this email already exists.");
            return null;
        }
        if(isUserNameExists(name)){
            System.out.println("User with this name already exists.");
            return null;
        }
        String sql = "INSERT INTO users (userName, userEmail, userPassword, joinDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password); // Hash the password
            stmt.setDate(4, new java.sql.Date(new Date().getTime()));
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userID = generatedKeys.getInt(1);
                    User user = UserFactory.createUser(userID, name, email, password);
                    return user;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changeRole(User user, String role) {
        String sql = "UPDATE users SET role = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role);
            stmt.setInt(2, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
