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

    static enum ROLE_ENUM {
        /**
         * administrator
         */
        administrator("administrator"),
        /**
         * operator
         */
        operator("operator"),
        /**
         * browser
         */
        browser("browser");

        private String role;

        ROLE_ENUM(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }

    static {
        users = new Hashtable<String, AbstractUser>();
        users.put("rose", new Browser("rose", "123", "browser"));
        users.put("jack", new Operator("jack", "123", "operator"));
        users.put("kate", new Administrator("kate", "123", "administrator"));
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        docs = new Hashtable<String, Doc>();
        FileReader filerader= null;
        try {
            filerader = new FileReader("uploadfile\\Doc.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br=new BufferedReader(filerader);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(" ");
                Timestamp timestamp = Timestamp.valueOf(temp[2]+" "+temp[3]);
                docs.put(temp[0], new Doc(temp[0], temp[1], timestamp, temp[4], temp[5]));
            }
        }  catch (IllegalArgumentException | IOException e) {
            System.err.println("Invalid timestamp format in line: ");
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO 初始化，连接数据库
     *
     * @param
     * @return void
     * @throws
     */
    public static void init() throws IOException {
        connectToDB = true;
    }

    /**
     * TODO 按档案编号搜索档案信息，返回null时表明未找到
     *
     * @param id
     * @return Doc
     * @throws SQLException
     */
    public static Doc searchDoc(String id) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        if (docs.containsKey(id)) {
            Doc temp = docs.get(id);
            return temp;
        }
        return null;
    }

    /**
     * TODO 列出所有档案信息
     *
     * @param
     * @return Enumeration<Doc>
     * @throws SQLException
     */
    public static Enumeration<Doc> listDoc() throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }

        Enumeration<Doc> e = docs.elements();
        return e;
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
        Doc doc;

        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }

        if (docs.containsKey(id)) {
            return false;
        } else {
            doc = new Doc(id, creator, timestamp, description, filename);
            docs.put(id, doc);
            return true;
        }
    }

    /**
     * TODO 按用户名搜索用户，返回null时表明未找到符合条件的用户
     *
     * @param name 用户名
     * @return AbstractUser
     * @throws SQLException
     */
    public static AbstractUser searchUser(String name) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }

        if (users.containsKey(name)) {
            return users.get(name);
        }
        return null;
    }

    /**
     * TODO 按用户名、密码搜索用户，返回null时表明未找到符合条件的用户
     *
     * @param name     用户名
     * @param password 密码
     * @return AbstractUser
     * @throws SQLException
     */
    public static AbstractUser searchUser(String name, String password) throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }

        if (users.containsKey(name)) {
            AbstractUser temp = users.get(name);
            if ((temp.getPassword()).equals(password)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * TODO 取出所有的用户
     *
     * @param
     * @return Enumeration<AbstractUser>
     * @throws SQLException
     */
    public static Enumeration<AbstractUser> listUser() throws SQLException {
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }

        Enumeration<AbstractUser> e = users.elements();
        return e;
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
        AbstractUser user;
        if (users.containsKey(name)) {
            switch (ROLE_ENUM.valueOf(role.toLowerCase())) {
                case administrator:
                    user = new Administrator(name, password, role);
                    break;
                case operator:
                    user = new Operator(name, password, role);
                    break;
                default:
                    user = new Browser(name, password, role);
            }
            users.put(name, user);
            return true;
        } else {
            return false;
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
        AbstractUser user;
        if (users.containsKey(name)) {
            return false;
        } else {
            switch (ROLE_ENUM.valueOf(role.toLowerCase())) {
                case administrator:
                    user = new Administrator(name, password, role);
                    break;
                case operator:
                    user = new Operator(name, password, role);
                    break;
                default:
                    user = new Browser(name, password, role);
            }
            users.put(name, user);
            return true;
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
        if (users.containsKey(name)) {
            users.remove(name);
            return true;
        } else {
            return false;
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
            } finally {
                connectToDB = false;
            }
        }
    }


    public static void main(String[] args) {
    }

}