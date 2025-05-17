package controller;

import dao.AssetDAO;

import java.util.Scanner;

public class AssetSearchService {
    private AssetDAO dao = new AssetDAO();

    @SuppressWarnings("resource")
	public void searchAssets() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Search by:\n1. Type\n2. Brand\n3. Status");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        String keyword;
        switch (choice) {
            case 1:
                System.out.print("Enter asset type: ");
                keyword = sc.nextLine();
                dao.searchBy("type", keyword);
                break;
            case 2:
                System.out.print("Enter asset brand: ");
                keyword = sc.nextLine();
                dao.searchBy("brand", keyword);
                break;
            case 3:
                System.out.print("Enter asset status (available/assigned): ");
                keyword = sc.nextLine();
                dao.searchBy("status", keyword);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
