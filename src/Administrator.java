import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Jia
 */
public class Administrator extends AbstractUser {
    Administrator(String name, String number, String administrator) {
        super(name, number, administrator);
    }

    @Override
    public void showMenu() {
        System.out.println("Menu");
        System.out.println("1. Add new user information");
        System.out.println("2. Delete user information");
        System.out.println("3. Change user information");
        System.out.println("4. List all user information");
        System.out.println("5. Download user's file");
        System.out.println("6. List all user's file");
        System.out.println("7. Change password");
        System.out.println("8. Exit system");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                try {
                    addUser();
                } catch (SQLException e) {
                    System.out.println("Invalid user "+e.getMessage());
                }
                break;
            case 2:
                try {
                    deUser();
                } catch (SQLException e) {
                    System.out.println("Cannot find the user "+e.getMessage());
                }
                break;
            case 3:
                try{
                changeUserInfo();
                }catch (SQLException e){
                    System.out.println("Cannot change user information "+e.getMessage());
                }
                break;
            case 4:
                try {
                    listUser();
                } catch (SQLException e) {
                    System.out.println("Cannot list all user "+e.getMessage());
                }catch(InputMismatchException e){
                    System.out.println("Invalid input "+e.getMessage());
                }
                break;
            case 5:
                String id;
                System.out.println("Please input the id you want to download:");
                id = input.nextLine();
                try {
                    downloadFile(id);
                } catch (IOException e) {
                    System.out.println("Cannot download file "+e.getMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 6:
                try {
                    showFileList();
                } catch (SQLException e) {
                    System.out.println("Cannot shoe file list "+e.getMessage());
                }
                break;
            case 7:
                try {
                    changeSelfInfo("password");
                } catch (SQLException e) {
                    System.out.println("Cannot change password "+e.getMessage());
                }

                break;
            case 8:
                exitSystem();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }

    }

    public void changeUserInfo() throws SQLException ,InputMismatchException{
        System.out.println("""
                1. Change Name\
                
                2. Change password\
                
                3. Change role""");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        try{
            switch (choice) {
                case 1:
                    System.out.println("Please input new name:");
                    String name = scanner.nextLine();
                    setName(name);
                    break;
                case 2:
                    System.out.println("Please input new password:");
                    String number = scanner.nextLine();
                    setPassword(number);
                    break;
                case 3:
                    System.out.println("Please input new role:");
                    String role = scanner.nextLine();
                    setRole(role);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }catch(InputMismatchException e){
            System.out.println("Invalid input");
        }
    }

    public void deUser() throws SQLException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input the user's name you want to delete:");
            String name = scanner.nextLine();
            if (DataProcessing.searchUser(name) == null) {
                System.out.println("Invalid user");
                return;
            }
            DataProcessing.deleteUser(name);
        } catch (SQLException e) {
            System.out.println("Cannot delete the user");
        }
    }

    public void addUser() throws SQLException {
        try{
            String name, password, role;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input new user's name:");
            name = scanner.nextLine();
            System.out.println("Please input new user's password:");
            password = scanner.nextLine();
            System.out.println("Please input new user's role:");
            role = scanner.nextLine();
            DataProcessing.insertUser(name, password, role);
        }catch (SQLException e){
            System.out.println("Cannot add the user");
        }
    }

    public void listUser() throws SQLException {
        Enumeration<AbstractUser> e = DataProcessing.listUser();
        while (e.hasMoreElements()) {
            AbstractUser element = e.nextElement();
            System.out.println(element.getRole() + "\t\t\t" + element.getName());
        }
    }

}
