package org.example.backend.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDB {
    private static final Properties properties = new Properties();

    public static Connection connect() throws IOException, ClassNotFoundException, SQLException {
        properties.load(ConnectionDB.class.getClassLoader().getResourceAsStream("db.properties"));
        Class.forName(properties.getProperty("database.driver"));

        return DriverManager.getConnection(
                properties.getProperty("database.url"),
                properties.getProperty("database.user"),
                properties.getProperty("database.password")
        );
    }
}
