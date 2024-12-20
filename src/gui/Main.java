package gui;

import console.AbstractUser;
import console.DocClient;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * @author 贾智超
 */
public class Main {
    LoginFrame login;
    FileBrowsingFrame fileBrowsingFrame;
    DocClient client;
    FileManagerFrame fileManagerFrame;
    AbstractUser user;


    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(() -> new Main().startFrame());
    }

    public void startFrame() {
        login = new LoginFrame(this);
        fileBrowsingFrame = new FileBrowsingFrame(this);
        login.setVisible(true);
        try {
            client = new DocClient("localhost", 9999);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method to handle successful login and transition to file browsing frame
     *
     * @return
     */
    public CompletableFuture<Boolean> attemptLogin(String name, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try  { // Use actual server address and port
                client.sendMessage("CLIENT>>> USER_LOGIN" +" "+ name + " " + password);

                String response = client.receiveMessage().join().toString(); // Wait for message reception completion
                System.out.println("Response from server: " + response);

                if ("LOGIN_SUCCESS".equals(response)) {
                    AbstractUser receivedUser = (AbstractUser) client.receiveMessage().join();
                    if (receivedUser != null) {
                        SwingUtilities.invokeLater(() -> {
                            user = receivedUser;
                            fileBrowsingFrame.setUser(user);
                            showFileBrowsingFrameAfterLogin();
                        });
                        return true;
                    } else {
                        System.err.println("User not found in database.");
                        return false;
                    }
                } else {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Invalid username or password!"));
                    return false;
                }
            } catch (IOException | RuntimeException ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error connecting to server: " + ex.getMessage()));
                client.close();
                return false;
            }
        });
    }



    public void showFileBrowsingFrameAfterLogin() {
        showFileBrowsingFrame();
        closeLoginFrame();
    }

    private void showFileBrowsingFrame() {
        fileBrowsingFrame.setVisible(true);
    }

    private void closeLoginFrame() {
        if (login != null ) {
            login.dispose();
        }
    }

    /**
     * 在登录成功后，你可以将用户信息传递给 fileManagerFrame
     */
    public void setUser(AbstractUser user) {
        this.user = user;
        fileManagerFrame.setUser(this.user);  // 传递给 FileManagerFrame
    }

    public AbstractUser getUser() {
        return user;
    }

}
