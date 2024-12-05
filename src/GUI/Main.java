package GUI;

import console.AbstractUser;
import console.DataProcessing;
import java.sql.SQLException;

public class Main {
    LoginFrame login;
    FileBrowsingFrame fileBrowsingFrame;
    DataProcessing dataProcessing;
    FileManagerFrame fileManagerFrame;  // 用于文件管理的界面
    AbstractUser user;

    public static void main(String[] args) throws SQLException {
        new Main().startFrame();
    }

    public void startFrame() {
        login = new LoginFrame(this);
        fileBrowsingFrame = new FileBrowsingFrame(this);
        dataProcessing = new DataProcessing();
        login.show();
    }

    public boolean searchUser(String name, String password) throws SQLException {
        return DataProcessing.searchUser(name, password) != null;

    }

    // Method to handle successful login and transition to file browsing frame
    public void loginSuccess(String name, String password) {
        try {
            user = DataProcessing.searchUser(name, password);
            if (user != null) {
                fileBrowsingFrame.setUser(user);
                showFileBrowsingFrame();  // Proceed to file browsing screen if user is found
            } else {
                // Handle invalid login case
                System.out.println("Invalid username or password!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showFileBrowsingFrame() {
        fileBrowsingFrame.show();
    }

    public void closeLoginFrame() {
        if (login != null) {
            login.dispose();
            login = null;
        }
    }

    public LoginFrame getLoginFrame() {
        return login;
    }

    // 在登录成功后，你可以将用户信息传递给 fileManagerFrame
    public void setUser(AbstractUser user) {
        this.user = user;
        fileManagerFrame.setUser(this.user);  // 传递给 FileManagerFrame
    }

    public AbstractUser getUser() {
        return user;
    }

}
