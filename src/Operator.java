public class Operator extends User{

    Operator(String name, String password, String role){
        super(name, password, role);
    }

    public void uploadFile(){
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
    }
}
