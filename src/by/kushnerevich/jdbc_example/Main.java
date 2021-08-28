package by.kushnerevich.jdbc_example;

import by.kushnerevich.jdbc_example.dao.BrandDAO;
import by.kushnerevich.jdbc_example.dao.ProductDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionString = "jdbc:mysql://localhost:3306/JDBC?serverTimezone=UTC";
        try (Connection connection = DriverManager.getConnection(
                connectionString,
                System.getenv("DB_USERNAME"),
                System.getenv("DB_PASSWORD"))) {
            BrandDAO brandDAO = new BrandDAO(connection);
            ProductDAO productDAO = new ProductDAO(connection);
        } finally {

        }
    }
}
