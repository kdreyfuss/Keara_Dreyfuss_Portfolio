package edu.umiami.bte324.caneeats.boundary;

import java.io.*;
import java.util.*;

public class RestaurantInput {

    public void showRestaurantOptions() {
        boolean loopFlag = true;
        Scanner scanner = new Scanner(System.in);

        while (loopFlag) {
            System.out.println("Restaurant Options");
            System.out.println("1. Add New Menu Item");
            System.out.println("2. Modify Existing Menu Item");
            System.out.println("3. Delete Menu Item");
            System.out.println("4. Back to Main Menu");
            System.out.print("Input your Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addMenuItem(scanner);
                    break;
                case "2":
                    modifyMenuItem(scanner);
                    break;
                case "3":
                    deleteMenuItem(scanner);
                    break;
                case "4":
                    loopFlag = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    private void addMenuItem(Scanner scanner) {
        System.out.print("Enter Menu Item ID: ");
        String itemId = scanner.nextLine();

        System.out.print("Enter Restaurant ID: ");
        String restaurantId = scanner.nextLine();

        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter Item Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Item Price: ");
        String price = scanner.nextLine();

        String newItem = itemId + "|" + restaurantId + "|" + itemName + "|" + description + "|" + price;

        try (FileWriter fw = new FileWriter("MenuItems.txt", true)) {
            fw.write(newItem + System.lineSeparator());
            System.out.println("Menu item added successfully!");
        } catch (IOException e) {
            System.out.println("Error adding menu item: " + e.getMessage());
        }
    }

    private void modifyMenuItem(Scanner scanner) {
        System.out.print("Enter Menu Item ID to modify: ");
        String targetItemId = scanner.nextLine();

        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (Scanner fileScanner = new Scanner(new File("MenuItems.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts[0].equals(targetItemId)) {
                    found = true;

                    System.out.print("Enter New Restaurant ID: ");
                    String restaurantId = scanner.nextLine();

                    System.out.print("Enter New Item Name: ");
                    String itemName = scanner.nextLine();

                    System.out.print("Enter New Item Description: ");
                    String description = scanner.nextLine();

                    System.out.print("Enter New Item Price: ");
                    String price = scanner.nextLine();

                    String updatedLine = targetItemId + "|" + restaurantId + "|" + itemName + "|" + description + "|" + price;
                    updatedLines.add(updatedLine);
                    System.out.println("Menu item updated.");
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading MenuItems.txt: " + e.getMessage());
            return;
        }

        if (found) {
            try (FileWriter fw = new FileWriter("MenuItems.txt", false)) {
                for (String l : updatedLines) {
                    fw.write(l + System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println("Error writing MenuItems.txt: " + e.getMessage());
            }
        } else {
            System.out.println("Menu item ID not found.");
        }
    }

    private void deleteMenuItem(Scanner scanner) {
        System.out.print("Enter Menu Item ID to delete: ");
        String targetItemId = scanner.nextLine();

        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (Scanner fileScanner = new Scanner(new File("MenuItems.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts[0].equals(targetItemId)) {
                    found = true;
                    System.out.println("Menu item deleted.");
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading MenuItems.txt: " + e.getMessage());
            return;
        }

        if (found) {
            try (FileWriter fw = new FileWriter("MenuItems.txt", false)) {
                for (String l : updatedLines) {
                    fw.write(l + System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println("Error writing MenuItems.txt: " + e.getMessage());
            }
        } else {
            System.out.println("Menu item ID not found.");
        }
    }
}
