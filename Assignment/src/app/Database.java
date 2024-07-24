package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {



    private static final String URL = "jdbc:mysql://localhost:3306/finance_tracker?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
