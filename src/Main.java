import dao.UserDAO;
import model.User;
import view.AssetView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.println("===== Digital Asset Tracker Login =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);  // create User object

        if (userDAO.validateUser(user)) {  // pass User object here
            String role = userDAO.getUserRole(username);
            System.out.println("Login successful! Role: " + role);
            AssetView view = new AssetView();
            view.displayMenu(role, username);
        } else {
            System.out.println("Invalid credentials. Access denied.");
        }

        scanner.close();
    }
}
