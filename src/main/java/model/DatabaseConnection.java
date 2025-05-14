package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public DatabaseConnection() {
        URL = "jdbc:mariadb://localhost:3306/BDD_achoule3";
        USER = "achoule3";
        PASSWORD = "secret";
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
