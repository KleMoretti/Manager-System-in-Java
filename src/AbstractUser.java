import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * TODO 抽象用户类，为各用户子类提供模板
 *
 * @author gongjing
 * @date 2016/10/13
 */
public abstract class AbstractUser implements java.io.Serializable {
    private String name;
    private String password;
    private String role;

    String uploadpath = "D:\\@Java\\Object-oriented and multithreaded comprehensive experiment\\Manager System\\uploadfile\\";
    String downloadpath = "D:\\@Java\\Object-oriented and multithreaded comprehensive experiment\\Manager System\\downloadfile\\";

    AbstractUser(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    /**
     * TODO 修改用户自身信息
     *
     * @param password 口令
     * @return boolean 修改是否成功
     * @throws SQLException
     */
    public boolean changeSelfInfo(String password) throws SQLException {
        if (DataProcessing.updateUser(name, password, role)) {
            this.password = password;
            System.out.println("修改成功");
            return true;
        } else {
            return false;
        }
    }

    /**
     * TODO 下载档案文件
     *
     * @param id 档案编号
     * @return boolean 下载是否成功
     * @throws SQLException,IOException
     */
    public boolean downloadFile(String id) throws SQLException, IOException {
        //boolean result=false;
        byte[] buffer = new byte[1024];
        Doc doc = DataProcessing.searchDoc(id);

        if (doc == null) {
            return false;
        }

        File tempFile = new File(uploadpath + doc.getFilename());
        String filename = tempFile.getName();

        BufferedInputStream infile = new BufferedInputStream(new FileInputStream(tempFile));
        BufferedOutputStream targetfile = new BufferedOutputStream(new FileOutputStream(downloadpath + filename));

        while (true) {
            int byteRead = infile.read(buffer);
            if (byteRead == -1) {
                break;
            }
            targetfile.write(buffer, 0, byteRead);
        }
        infile.close();
        targetfile.close();

        return true;
    }

    /**
     * TODO 展示档案文件列表
     *
     * @param
     * @return void
     * @throws SQLException
     */
    public void showFileList() throws SQLException {
        Enumeration<Doc> e = DataProcessing.listDoc();
        Doc doc;
        while (e.hasMoreElements()) {
            doc = e.nextElement();
            System.out.println("Id:" + doc.getId() + "\t Creator:" + doc.getCreator() +
                    "\t Time:" + doc.getTimestamp() + "\t Filename:" + doc.getFilename());
            System.out.println("Description:" + doc.getDescription());
        }

    }

    /**
     * TODO 展示菜单，需子类加以覆盖
     *
     * @param
     * @return void
     * @throws
     */
    public abstract void showMenu() throws SQLException, IOException;

    /**
     * TODO 退出系统
     *
     * @param
     * @return void
     * @throws
     */
    public void exitSystem() {
        try {
            FileWriter filewriter = new FileWriter("uploadfile//doc.txt", true);
            BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
            Enumeration<Doc> e = DataProcessing.listDoc();
            while (e.hasMoreElements()) {
                Doc doc = e.nextElement();
                String id = doc.getId();
                String creator = doc.getCreator();
                String timestamp = doc.getTimestamp().toString();
                String description = doc.getDescription();
                String filename = doc.getFilename();
                bufferedwriter.write(id+" " + creator +" "+ timestamp +" "+ description+" " + filename+"\n");
            }
            bufferedwriter.close();
        } catch (IOException e) {
            System.out.println("Cannot write to file");
        } catch (SQLException e) {
            System.out.println("Cannot list all doc " + e.getMessage());
        }
        System.out.println("系统退出, 谢谢使用 ! ");
        System.exit(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}