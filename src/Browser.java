import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Jia
 */
public class Browser extends AbstractUser {
    public Browser(String name, String number, String browser) {
        super(name, number, browser);
    }

    @Override
    public void showMenu() {
        System.out.println("Menu");
        System.out.println("1. Download file");
        System.out.println("2. List all files");
        System.out.println("3. Change password");
        System.out.println("4. Exit system");

        Scanner input = new Scanner(System.in);

        int choice = input.nextInt();
        switch (choice) {
            case 1:
                //downloadFile
                try {
                    downloadFile("file");
                } catch (Exception e) {
                    System.out.println("Cannot download file " + e.getMessage());
                }
                break;
            case 2:
                //listFile
                try {
                    showFileList();
                } catch (SQLException e) {
                    System.out.println("Cannot list all files " + e.getMessage());
                }
                break;
            case 3:
                //changePassword
                try {
                    changeSelfInfo("password");
                } catch (SQLException e) {
                    System.out.println("Cannot change password " + e.getMessage());
                }
                break;
            case 4:
                //exit
                exitSystem();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
}
