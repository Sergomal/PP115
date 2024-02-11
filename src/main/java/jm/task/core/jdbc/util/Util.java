package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost/user";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Connected... OK!");

            }

        } catch (SQLException e) {
            System.out.println("Connected... FAIL!");

        }

        return connection;

    }

}












