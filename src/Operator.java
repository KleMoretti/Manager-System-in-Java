import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Operator extends AbstractUser {

    Operator(String name, String password, String role) {
        super(name, password, role);
    }

    public void uploadFile() {
        System.out.println("Upload file");

    }

    @Override
    public void showMenu() {
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
                uploadFile();
                break;
            case 2:
                try {
                    downloadFile("file");
                } catch (IOException e) {
                    System.out.println("Cannot download file "+e.getMessage());
                }
                break;
            case 3:
                try {
                    showFileList();
                } catch (SQLException e) {
                    System.out.println("Cannot list all files "+e.getMessage());
                }
                break;
            case 4:
                //changeSelfInfo();
                try {
                    changeSelfInfo("password");
                } catch (SQLException e) {
                    System.out.println("Cannot change password "+e.getMessage());
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
