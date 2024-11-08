import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataProcessing dataProcessing = new DataProcessing();
        User user = null;
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        String name, number;
        while (true) {
            System.out.println("Please input your name:");
            name = scanner.nextLine();
            System.out.println("Please input your number:");
            number = scanner.nextLine();
            user = DataProcessing.search(name, number);
            if (user == null) {
                System.out.println("Invalid user");
                continue;
            } else {
                while (true) {
                    user.showMenu();
                    SelectMenu(user);
                }
            }


        }
    }

    static void SelectMenu(User user) {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        switch (user.getRole()) {
            case "administrator": {
                Administrator administrator = (Administrator) user;
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        administrator.addUser();
                        break;
                    case 2:
                        administrator.deUser();
                        break;
                    case 3:
                        administrator.changeUserInfo();
                        break;
                    case 4:
                        administrator.listUser();
                        break;
                    case 5:
                        //downloadUserFile
                        administrator.downloadFile("file");
                        break;
                    case 6:
                        //listUserFile
                        administrator.showFileList();
                        break;
                    case 7:
                        //changePassword
                        administrator.changeSelfInfo("password");
                        break;
                    case 8:
                        //exitSystem
                        administrator.exitSystem();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
                break;
            }
            case "operator": {
                Operator operator = (Operator) user;
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        //uploadFile
                        operator.uploadFile();
                        break;
                    case 2:
                        //downloadFile
                        operator.downloadFile("file");
                        break;
                    case 3:
                        //listFile
                        operator.showFileList();
                        break;
                    case 4:
                        //listUser
                        break;
                    case 5:
                        //exit
                        operator.exitSystem();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }

                break;
            }
            case "browser": {
                Browser browser = (Browser) user;
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        //downloadFile
                        browser.downloadFile("file");
                        break;
                    case 2:
                        //listFile
                        browser.showFileList();
                        break;
                    case 3:
                        //changePassword
                        browser.changeSelfInfo("password");
                        break;
                    case 5:
                        //exit
                        browser.exitSystem();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }

                break;
            }

            default: {
                System.out.println("Invalid user");
            }
        }
    }
}
