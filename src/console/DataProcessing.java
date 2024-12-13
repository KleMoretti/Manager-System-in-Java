package console;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.*;

/**
 * TODO 数据处理类
 *
 * @author gongjing
 * @date 2016/10/13
 */
public class DataProcessing {
    private static boolean connectToDB = false;

    static Hashtable<String, AbstractUser> users;
    static Hashtable<String, Doc> docs;

    String drivername="com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/document_schema";
    private static final String USER = "root";
    private static final String PASS = "123456";
    private static Connection connection;
    private static String sql=null;
    private static ResultSet rs=null;
    private static Statement stmt=null;
    private static PreparedStatement prestmt=null;

    /**
     * TODO 初始化，连接数据库
     *
     * @param
     * @return void
     * @throws
     */
    public static void init() throws IOException {
        connectToDB = true;
        try{
            connection=DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
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
        sql="select * from doc_info where Id='"+id+"'";
        stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        rs=stmt.executeQuery(sql);
        if(rs.next()){
            String creator=rs.getString("creator");
            Timestamp timestamp=rs.getTimestamp("timestamp");
            String description=rs.getString("description");
            String filename=rs.getString("filename");
            Doc doc=new Doc(id,creator,timestamp,description,filename);
            return doc;
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
    public static  ResultSet listDoc() throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        sql="select * from doc_info";
        stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        rs=stmt.executeQuery(sql);
        return rs;
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
        if (connection == null || connection.isClosed()) { // 更好的连接检查
            throw new SQLException("Not Connected to Database");
        }

        // 使用try-with-resources自动关闭资源
        try (PreparedStatement checkStmt = connection.prepareStatement("SELECT 1 FROM doc_info WHERE Id = ?")) {
            checkStmt.setString(1, id);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    return false; // 文档已存在
                }
            }
        }

        // 准备并执行插入语句
        String insertSql = "INSERT INTO doc_info (Id, creator, timestamp, description, filename) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
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
        stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        sql="select * from user_info where username='"+name+"'";

        rs=stmt.executeQuery(sql);
        if(rs.next()){
            String password=rs.getString("password");
            String role=rs.getString("role");
            AbstractUser user;
            switch (role) {
                case "administrator":
                    user = new Administrator(name, password, role);
                    break;
                case "operator":
                    user = new Operator(name, password, role);
                    break;
                default:
                    user = new Browser(name, password, role);
            }
            return user;
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
        stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        sql="select * from user_info where username='"+name+ "'"+"and password='"+password+"'";
        rs=stmt.executeQuery(sql);
        if(rs.next()){
            String role=rs.getString("role");
            AbstractUser user;
            switch (role) {
                case "administrator":
                    user = new Administrator(name, password, role);
                    break;
                case "operator":
                    user = new Operator(name, password, role);
                    break;
                default:
                    user = new Browser(name, password, role);
            }
            return user;
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
        sql="select * from user_info";
        stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        rs=stmt.executeQuery(sql);
        return rs;
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
        stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        sql="select * from user_info where username='"+name+"'";
        rs=stmt.executeQuery(sql);
        if(!rs.next()){
            return false;
        }else{
            String sql="update user_info set password=?,role=? where username=?";
            PreparedStatement prestamt=connection.prepareCall(sql);
            prestamt.setString(1,password);
            prestamt.setString(2,role);
            prestamt.setString(3,name);
            return true;
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
        // 使用PreparedStatement防止SQL注入
        String checkSql = "SELECT 1 FROM user_info WHERE username = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, name);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    return false; // 用户已存在
                }
            }
        }
        // 准备插入语句
        sql = "INSERT INTO user_info (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(sql)) {
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
        if(searchUser(name)==null){
            return false;
        }else{
            sql="delete from user_info where username='"+name+"'";
            prestmt=connection.prepareStatement(sql);
            prestmt.executeUpdate();
            return true;
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
        if (connectToDB) {
// close Statement and Connection
            try {
                if(rs!=null){
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectToDB = false;
            }
        }
    }

    public static void sonnectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connectToDB = true;

    }


    public static void main(String[] args) {
    }

}