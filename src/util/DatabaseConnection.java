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

    public ResultSet rs;
    public ResultSetMetaData rsm;
    private Statement st;

    private DatabaseConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
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

    public ResultSet execQuery(String query){
        try {
            rs = st.executeQuery(query);
            rsm = rs.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rs;
    }

    public void execUpdate(String query){
        try {
            st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement prepareStatement(String query){
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ps;
    }
};
