package factory;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {
    public static User createUser(int userId, String username, String email, String password) {
        return new User(userId, username, email, password);
    }

    public static User createUserFromResultSet(ResultSet rs) throws SQLException {
        int userId = rs.getInt("userID");
        String username = rs.getString("userName");
        String email = rs.getString("userEmail");
        String password = rs.getString("userPassword");
        return new User(userId, username, email, password);
    }
}
