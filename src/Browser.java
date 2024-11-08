public class Browser extends User {
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

    }
}
