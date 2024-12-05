package console;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Operator extends AbstractUser {

    Operator(String name, String password, String role) {
        super(name, password, role);
    }

    /**
     * TODO 上传文件档案
     *
     * @return boolean 下载是否成功
     * @throws SQLException,IOException Exception
     */
    public boolean uploadFile() throws SQLException, IOException {
        //boolean result=false;
        String id;
        Scanner inputting = new Scanner(System.in);
        System.out.println("Please input file id:");
        id = inputting.nextLine();
        byte[] buffer = new byte[1024];
        try {
            Doc doc = DataProcessing.searchDoc(id);

            if (doc == null) {
                Scanner input = new Scanner(System.in);
                System.out.println("Please input source filename:");
                String filename = input.nextLine();
                System.out.println("Please input description:");
                String description = input.nextLine();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                File tempFile = new File(filename);
                File sourceFileName = new File(tempFile.getName());
                BufferedInputStream infile = new BufferedInputStream(new FileInputStream(tempFile));
                BufferedOutputStream targetfile = new BufferedOutputStream(new FileOutputStream(uploadpath + sourceFileName));

                while (true) {
                    int byteRead = infile.read(buffer);
                    if (byteRead == -1) {
                        break;
                    }
                    targetfile.write(buffer, 0, byteRead);
                }
                infile.close();
                targetfile.close();
                DataProcessing.insertDoc(id, getName(), timestamp, description, tempFile.getName());
                return true;
            } else {
                System.out.println("This id has already exists!");
                return false;
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void showMenu() throws SQLException, IOException {
        System.out.println("Menu");
        System.out.println("1. Upload file");
        System.out.println("2. Download file");
        System.out.println("3. List all files");
        System.out.println("4. Change password");
        System.out.println("5. Exit system");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                try {
                    boolean upload = uploadFile();
                    if (upload) {
                        System.out.println("Upload successfully");
                    }
                } catch (SQLException | IOException e) {
                    System.out.println("Cannot upload file " + e.getMessage());
                }

                break;
            case 2:
                try {
                    String id;
                    System.out.println("Please input the id you want to download:");
                    id = input.next();
                    downloadFile(id);
                } catch (IOException e) {
                    System.out.println("Cannot download file " + e.getMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                try {
                    showFileList();
                } catch (SQLException e) {
                    System.out.println("Cannot list all files " + e.getMessage());
                }
                break;
            case 4:
                //changeSelfInfo();
                try {
                    changeSelfInfo("password");
                } catch (SQLException e) {
                    System.out.println("Cannot change password " + e.getMessage());
                }
                break;
            case 5:
                exitSystem();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
}
