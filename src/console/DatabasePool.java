package console;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabasePool {

    // DataSource for managing the connection pool
    private static BasicDataSource dataSource;

    static {
        initializeDataSource();
    }

    /**
     * Initializes the data source with the database connection parameters.
     * Sets up the connection pool with minimum and maximum idle connections,
     * and maximum open prepared statements.
     */
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


    /**
     * Provides a connection from the connection pool.
     *
     * @return a Connection object from the pool
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}