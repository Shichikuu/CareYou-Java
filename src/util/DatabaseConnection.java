package util;

import java.sql.*;

//Singleton

public class DatabaseConnection {
    private final String username = "root";
    private final String password = "";
    private final String database = "CareYouDB";
    private final String host = "localhost:3306";

    private String url = String.format("jdbc:mysql://%s/%s", host, database);

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            throw new SQLException(ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // Singleton instance retrieval
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
