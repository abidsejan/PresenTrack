package com.attendance.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;
    private static Properties props = new Properties();
    
    static {
        try {
            // Try multiple ways to find the config file
            InputStream input = null;
            
            // First try: ClassLoader.getResourceAsStream()
            input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties");
            
            // If not found, try alternative approach
            if (input == null) {
                input = DatabaseConnection.class.getResourceAsStream("/config.properties");
            }
            
            // If still not found, try another approach
            if (input == null) {
                input = ClassLoader.getSystemResourceAsStream("config.properties");
            }
            
            if (input == null) {
                throw new RuntimeException("config.properties file not found in classpath");
            }
            
            props.load(input);
            
            String driver = props.getProperty("db.driver");
            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established successfully!");
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }
    
    public static Connection getConnection() {
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}