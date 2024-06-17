package samatov.jdbcProject.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DbConfig {

    private static final String PROPERTIES_FILE = "db.properties";
    private static String DATABASE_URL;
    private static String USER;
    private static String PASSWORD;

    private static Connection connection;

    private static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = new Properties();
                properties.load(DbConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
                DATABASE_URL = properties.getProperty("database.url");
                USER = properties.getProperty("database.user");
                PASSWORD = properties.getProperty("database.password");
                connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            } catch (SQLException | IOException e) {
                throw new RuntimeException("Ошибка подключения к базе данных", e);
            }
        }
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static PreparedStatement getPreparedStatementWithGeneratedKeys(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }
}
