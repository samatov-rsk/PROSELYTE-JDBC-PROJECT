package samatov.jdbcProject.utils;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConfigLiquebase {
    private static final String PROPERTIES_FILE = "db.properties";
    private static String DATABASE_URL;
    private static String USER;
    private static String PASSWORD;
    private static String CHANGELOG_FILE;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            Properties properties = new Properties();
            properties.load(ConfigLiquebase.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
            DATABASE_URL = properties.getProperty("database.url");
            USER = properties.getProperty("database.user");
            PASSWORD = properties.getProperty("database.password");
            CHANGELOG_FILE = properties.getProperty("changeLogFile");
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка загрузки файла свойств", ex);
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(CHANGELOG_FILE,
                    new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
