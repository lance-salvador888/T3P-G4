/**
 * Class for Database connection methods
 * By: Navreet Dhillon
 */

package org.example.workshop6javafx;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String PROPERTIES_FILE = "C:/connection.properties";

    public static Connection getConnection() {
        Connection conn = null;
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            Properties props = new Properties();
            props.load(fis);

            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            conn = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
