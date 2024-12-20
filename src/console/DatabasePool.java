package console;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabasePool {

    private static BasicDataSource dataSource;

    static {
        initializeDataSource();
    }

    private static void initializeDataSource() {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/document_schema");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Database connection established successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to establish database connection.");
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}