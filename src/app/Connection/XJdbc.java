package app.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class XJdbc {
    private static final String DB_URL =
            "jdbc:sqlserver://localhost\\MSSQLSERVER13:1433;databaseName=Brew;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    static {
        try {
            Class.forName(DRIVER);
            System.out.println("SQL Server JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load SQL Server JDBC Driver!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

//    public static void main(String[] args) {
//        System.out.println("Trying to connect to Brew database...");
//        try (Connection conn = XJdbc.getConnection()) {
//            if (conn != null) {
//                System.out.println("Connected to Brew database successfully!");
//            }
//        } catch (SQLException e) {
//            System.err.println("Connection failed!");
//            e.printStackTrace();
//        }
//    }
}
