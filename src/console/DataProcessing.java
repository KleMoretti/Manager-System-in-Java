package console;

import java.io.*;

import java.sql.*;

/**
 * TODO 数据处理类
 *
 * @author gongjing
 * @date 2016/10/13
 */
public class DataProcessing implements Serializable {
    private static boolean connectToDB = false;

    /**
     * TODO 初始化，连接数据库
     *
     * @param
     * @return void
     * @throws
     */
    public static void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 使用最新的MySQL驱动类
            connectToDB = true;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO 按档案编号搜索档案信息，返回null时表明未找到
     *
     * @param id
     * @return console.Doc
     * @throws SQLException
     */
    public static Doc searchDoc(String id) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        String sql = "SELECT * FROM doc_info WHERE Id = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String creator = rs.getString("creator");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                String description = rs.getString("description");
                String filename = rs.getString("filename");
                return new Doc(id, creator, timestamp, description, filename);
            }
        }
        return null;
    }

    /**
     * TODO 列出所有档案信息
     *
     * @param
     * @return Enumeration<console.Doc>
     * @throws SQLException
     */
    public static ResultSet listDoc() throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        String sql = "SELECT * FROM doc_info";
        Connection conn = DatabasePool.getConnection();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery(sql);

    }

    /**
     * TODO 插入新的档案
     *
     * @param id
     * @param creator
     * @param timestamp
     * @param description
     * @param filename
     * @return boolean
     * @throws SQLException
     */
    public static boolean insertDoc(String id, String creator, Timestamp timestamp, String description, String filename) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        String checkSql = "SELECT 1 FROM doc_info WHERE Id = ?";
        String insertSql = "INSERT INTO doc_info (Id, creator, timestamp, description, filename) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            checkStmt.setString(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false; // 文档已存在
            }
            insertStmt.setString(1, id);
            insertStmt.setString(2, creator);
            insertStmt.setTimestamp(3, timestamp);
            insertStmt.setString(4, description);
            insertStmt.setString(5, filename);
            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * TODO 按用户名搜索用户，返回null时表明未找到符合条件的用户
     *
     * @param name 用户名
     * @return console.AbstractUser
     * @throws SQLException
     */
    public static AbstractUser searchUser(String name) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        String sql = "SELECT * FROM user_info WHERE username = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String password = rs.getString("password");
                String role = rs.getString("role");
                switch (role) {
                    case "administrator":
                        return new Administrator(name, password, role);
                    case "operator":
                        return new Operator(name, password, role);
                    default:
                        return new Browser(name, password, role);
                }
            }
        }
        return null;
    }

    /**
     * TODO 按用户名、密码搜索用户，返回null时表明未找到符合条件的用户
     *
     * @param name     用户名
     * @param password 密码
     * @return console.AbstractUser
     * @throws SQLException
     */
    public static AbstractUser searchUser(String name, String password) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        String sql = "SELECT * FROM user_info WHERE username = ? AND password = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                switch (role) {
                    case "administrator":
                        return new Administrator(name, password, role);
                    case "operator":
                        return new Operator(name, password, role);
                    default:
                        return new Browser(name, password, role);
                }
            }
        }
        return null;
    }

    /**
     * TODO 取出所有的用户
     *
     * @param
     * @return Enumeration<console.AbstractUser>
     * @throws SQLException
     */
    public static ResultSet listUser() throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        String sql = "SELECT * FROM user_info";
        Connection conn = DatabasePool.getConnection();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery(sql);
    }

    /**
     * TODO 修改用户信息
     *
     * @param name     用户名
     * @param password 密码
     * @param role     角色
     * @return boolean
     * @throws SQLException
     */
    public static boolean updateUser(String name, String password, String role) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        String sql = "UPDATE user_info SET password = ?, role = ? WHERE username = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, password);
            pstmt.setString(2, role);
            pstmt.setString(3, name);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * TODO 插入新用户
     *
     * @param name     用户名
     * @param password 密码
     * @param role     角色
     * @return boolean
     * @throws SQLException
     */
    public static boolean insertUser(String name, String password, String role) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        String checkSql = "SELECT 1 FROM user_info WHERE username = ?";
        String insertSql = "INSERT INTO user_info (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false; // 用户已存在
            }
            insertStmt.setString(1, name);
            insertStmt.setString(2, password);
            insertStmt.setString(3, role);
            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * TODO 删除指定用户
     *
     * @param name 用户名
     * @return boolean
     * @throws SQLException
     */
    public static boolean deleteUser(String name) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        String sql = "DELETE FROM user_info WHERE username = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * TODO 关闭数据库连接
     *
     * @param
     * @return void
     * @throws
     */
    public static void disconnectFromDataBase() {
        connectToDB = false;
    }

    public static void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connectToDB = true;
    }


    public static void main(String[] args) {
    }
}
