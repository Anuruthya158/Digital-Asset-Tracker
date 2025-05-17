package view;

import controller.AssetController;
import controller.AssetSearchService;
import controller.UserController;
import model.Asset;

import java.util.List;
import java.util.Scanner;

public class AssetView {
    private Scanner scanner = new Scanner(System.in);
    private AssetController controller = new AssetController();
    private AssetSearchService searchService = new AssetSearchService();
    private UserController userController = new UserController();

    public void displayMenu(String role, String username) {
        while (true) {
            System.out.println("\n===== Digital Asset Tracker =====");

            if ("admin".equalsIgnoreCase(role)) {
                System.out.println("0. Register New User");
                System.out.println("1. Add Asset");
                System.out.println("2. View All Assets");
                System.out.println("3. Assign Asset");
                System.out.println("4. Return Asset");
                System.out.println("5. Search Assets");
                System.out.println("6. Delete User");
                System.out.println("7. Exit");
            } else {
                System.out.println("1. View My Assets");
                System.out.println("2. Exit");
            }

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if ("admin".equalsIgnoreCase(role)) {
            	switch (choice) {
                case 0 -> registerUser();
                case 1 -> addAsset();
                case 2 -> viewAssets();
                case 3 -> assignAsset();
                case 4 -> returnAsset();
                case 5 -> searchAssets();
                case 6 -> deleteUser(username);  // NEW CASE
                case 7 -> {
                    System.out.println("Exiting... Bye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Try again.");
            }

            } else {
                switch (choice) {
                    case 1 -> viewAssetsByUser(username);
                    case 2 -> {
                        System.out.println("Exiting... Bye!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private void registerUser() {
        System.out.print("Enter new username: ");
        String newUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (admin/employee): ");
        String role = scanner.nextLine();

        boolean success = userController.registerUser(newUser, password, role);
        System.out.println(success ? "User registered successfully!" : "Failed to register user.");
    }

    private void addAsset() {
        System.out.print("Enter asset type (e.g. Laptop): ");
        String type = scanner.nextLine();

        System.out.print("Enter brand (e.g. Dell): ");
        String brand = scanner.nextLine();

        System.out.print("Enter serial number: ");
        String serialNumber = scanner.nextLine();

        boolean success = controller.addAsset(type, brand, serialNumber);
        System.out.println(success ? "Asset added successfully!" : "Failed to add asset.");
    }

    private void viewAssets() {
        List<Asset> assets = controller.viewAllAssets();
        if (assets.isEmpty()) {
            System.out.println("No assets found.");
            return;
        }
        System.out.println("\n--- Asset List ---");
        for (Asset asset : assets) {
            System.out.printf("ID: %d | Type: %s | Brand: %s | Serial#: %s | Status: %s | Assigned to: %s | Date: %s\n",
                    asset.getAssetId(), asset.getType(), asset.getBrand(), asset.getSerialNumber(),
                    asset.getStatus(),
                    asset.getAssignedTo() == null ? "-" : asset.getAssignedTo(),
                    asset.getAssignedDate() == null ? "-" : asset.getAssignedDate());
        }
    }

    private void viewAssetsByUser(String username) {
        List<Asset> assets = controller.viewAssetsByUser(username);
        if (assets.isEmpty()) {
            System.out.println("You have no assets assigned.");
            return;
        }
        System.out.println("\n--- My Assets ---");
        for (Asset asset : assets) {
            System.out.printf("ID: %d | Type: %s | Brand: %s | Serial#: %s | Status: %s | Assigned Date: %s\n",
                    asset.getAssetId(), asset.getType(), asset.getBrand(), asset.getSerialNumber(),
                    asset.getStatus(),
                    asset.getAssignedDate() == null ? "-" : asset.getAssignedDate());
        }
    }

    private void assignAsset() {
        System.out.print("Enter asset ID to assign: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        boolean success = controller.assignAsset(id, name);
        System.out.println(success ? "Asset assigned successfully!" : "Failed to assign asset. Maybe it's already assigned?");
    }

    private void returnAsset() {
        System.out.print("Enter asset ID to return: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean success = controller.returnAsset(id);
        System.out.println(success ? "Asset returned successfully!" : "Failed to return asset.");
    }

    private void searchAssets() {
        searchService.searchAssets();
    }
    
    private void deleteUser(String currentUsername) {
        List<String> users = userController.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found in the system.");
            return;
        }

        System.out.println("\n--- Registered Users ---");
        for (String user : users) {
            if (!user.equalsIgnoreCase(currentUsername)) {
                System.out.println("- " + user);
            }
        }

        System.out.print("\nEnter the username you want to delete: ");
        String usernameToDelete = scanner.nextLine();

        if (usernameToDelete.equalsIgnoreCase(currentUsername)) {
            System.out.println("🚫 You cannot delete your own account.");
            return;
        }

        String role = userController.getUserRole(usernameToDelete);
        if (role == null) {
            System.out.println("❌ User not found.");
            return;
        } else if (role.equalsIgnoreCase("admin")) {
            System.out.println("❌ You cannot delete another admin.");
            return;
        }

        System.out.print("⚠ Are you sure you want to delete this user? (y/n): ");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("✅ Deletion cancelled.");
            return;
        }

        boolean success = userController.deleteUser(usernameToDelete);
        System.out.println(success ? "✅ User deleted successfully!" : "❌ Deletion failed.");
    }

}
