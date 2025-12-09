package TD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getDBConnection() throws SQLException {
        try {
            String URL = "jdbc:postgresql://localhost:5432/product_management_db";
            String PASSWORD = "123456";
            String USER = "product_manager_user";
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw e;
        }
    }

}

