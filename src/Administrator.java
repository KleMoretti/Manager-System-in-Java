import java.util.*;

public class Administrator extends User {
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
        System.out.println("5.Download user's file");
        System.out.println("6.List all user's file");
        System.out.println("7.Change password");
        System.out.println("8. Exit system");

    }

    public void changeUserInfo() {
        System.out.println("""
                1. Change Name\
                
                2. Change password\
                
                3. Change role""");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
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

    }

    public void deUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the user's name you want to delete:");
        String name = scanner.nextLine();
        if(DataProcessing.searchUser(name) == null) {
            System.out.println("Invalid user");
            return;
        }
        DataProcessing.delete(name);
    }

    public void addUser() {
        String name, password, role;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input new user's name:");
        name = scanner.nextLine();
        System.out.println("Please input new user's password:");
        password = scanner.nextLine();
        System.out.println("Please input new user's role:");
        role = scanner.nextLine();
        DataProcessing.insert(name, password, role);
    }

    public void listUser() {
        Enumeration e= DataProcessing.getAllUser();
        while (e.hasMoreElements()) {
            User element = (User) e.nextElement();
            System.out.println(element);
        }
    }



}
