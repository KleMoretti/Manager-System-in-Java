package console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Jia
 * @date 2024/11/15
 */
@SuppressWarnings("InfiniteLoopStatement")
public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        AbstractUser user;
        Scanner scanner = new Scanner(System.in);
        String name, number;

        while (true) {
            System.out.println(
                   " Welcome to the system\n"+
                    "1.Log in\n"+
                    "2.Exit"
                    );
            try {
                int login = scanner.nextInt();
                scanner.nextLine();
                if (login == 2) {
                    System.out.println("Exit system");
                    break;
                } else if (login == 1) {
                    while (true) {
                        System.out.println("Please input your name:");
                        name = scanner.nextLine();
                        System.out.println("Please input your number:");
                        number = scanner.nextLine();
                        try {
                            user = DataProcessing.searchUser(name, number);
                        } catch (SQLException e) {
                            System.out.println("Database error  "+e.getMessage());
                            continue;
                        }
                        if (user == null) {
                            System.out.println("Invalid user");
                        } else {
                            while (true) {
                                user.showMenu();
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
    }
}
