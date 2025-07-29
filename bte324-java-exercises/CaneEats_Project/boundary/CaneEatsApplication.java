package edu.umiami.bte324.caneeats.boundary;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CaneEatsApplication {

    private static final double TAX_RATE = 0.08;  // 8% tax

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loopFlag = true;

        while (loopFlag) {
            System.out.println("Welcome to Cane Eats Application");
            System.out.println("1. Restaurant Menu");
            System.out.println("2. Customer Menu");
            System.out.println("3. Place an Order");
            System.out.println("4. View Past Orders");
            System.out.println("5. Exit the System");
            System.out.print("Input your Choice: ");

            String choice = scanner.nextLine();

            if (choice.equals("5")) {
                loopFlag = false;
            } else if (choice.equals("1")) {
                new RestaurantInput().showRestaurantOptions();
            } else if (choice.equals("2")) {
                new CustomerInput().showCustomerOptions();
            } else if (choice.equals("3")) {
                placeOrder(scanner);
            } else if (choice.equals("4")) {
                viewPastOrders(scanner);
            }
        }
    }

    private static void placeOrder(Scanner scanner) {
        try {
            System.out.print("Enter Customer ID: ");
            String customerId = scanner.nextLine();

            System.out.print("Enter Customer Name: ");
            String customerName = scanner.nextLine();

            System.out.print("Enter Customer Phone: ");
            String customerPhone = scanner.nextLine();

            System.out.print("Enter Customer Email: ");
            String customerEmail = scanner.nextLine();

            System.out.print("Enter Restaurant ID: ");
            String restaurantId = scanner.nextLine();

            String restaurantName = getRestaurantNameById(restaurantId);
            String restaurantPhone = getRestaurantPhoneById(restaurantId);

            List<String[]> items = new ArrayList<>();
            double subtotal = 0.0;

            boolean addingItems = true;
            while (addingItems) {
                System.out.print("Enter Menu Item ID (or type 'done' to finish): ");
                String itemId = scanner.nextLine();

                if (itemId.equalsIgnoreCase("done")) {
                    addingItems = false;
                    continue;
                }

                String itemName = getMenuItemNameById(itemId);
                String itemDescription = getMenuItemDescriptionById(itemId);
                double itemPrice = getMenuItemPriceById(itemId);

                System.out.print("Enter Quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                double itemTotal = itemPrice * quantity;
                subtotal += itemTotal;

                items.add(new String[]{itemName, itemDescription, String.valueOf(quantity),
                        String.format("$%.2f", itemPrice), String.format("$%.2f", itemTotal)});

                System.out.println("Added " + quantity + " x " + itemName + " ($" + itemTotal + ")");
            }

            double tax = subtotal * TAX_RATE;
            double total = subtotal + tax;
            String orderId = generateNextOrderId();
            String orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            StringBuilder orderContent = new StringBuilder();
            orderContent.append("ORDER_ID: ").append(orderId).append("\n");
            orderContent.append("CUSTOMER_ID: ").append(customerId).append("\n");
            orderContent.append("CUSTOMER_NAME: ").append(customerName).append("\n");
            orderContent.append("CUSTOMER_PHONE: ").append(customerPhone).append("\n");
            orderContent.append("CUSTOMER_EMAIL: ").append(customerEmail).append("\n");
            orderContent.append("RESTAURANT_ID: ").append(restaurantId).append("\n");
            orderContent.append("RESTAURANT_NAME: ").append(restaurantName).append("\n");
            orderContent.append("RESTAURANT_PHONE: ").append(restaurantPhone).append("\n");
            orderContent.append("ORDER_DATE: ").append(orderDate).append("\n");
            orderContent.append("ORDER_STATUS: Confirmed\n");

            for (String[] item : items) {
                orderContent.append("-------------------------------\n");
                orderContent.append("ITEM: ").append(item[0]).append("\n");
                orderContent.append("DESCRIPTION: ").append(item[1]).append("\n");
                orderContent.append("QUANTITY: ").append(item[2]).append("\n");
                orderContent.append("PRICE_PER_UNIT: ").append(item[3]).append("\n");
                orderContent.append("ITEM_TOTAL: ").append(item[4]).append("\n");
            }

            orderContent.append("-------------------------------\n");
            orderContent.append(String.format("ORDER_SUBTOTAL: $%.2f\n", subtotal));
            orderContent.append(String.format("TAX (8%%): $%.2f\n", tax));
            orderContent.append(String.format("ORDER_TOTAL: $%.2f\n", total));

            String fileName = orderId + ".txt";
            try (FileWriter fw = new FileWriter(fileName)) {
                fw.write(orderContent.toString());
            }

            System.out.println("Order placed! Saved to file: " + fileName);

        } catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }

    private static String generateNextOrderId() {
        File currentDir = new File(".");
        File[] files = currentDir.listFiles();
        int maxOrderNum = 0;

        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (name.startsWith("ORDER_") && name.endsWith(".txt")) {
                    String numPart = name.substring(6, name.length() - 4); // e.g., 001
                    try {
                        int num = Integer.parseInt(numPart);
                        if (num > maxOrderNum) {
                            maxOrderNum = num;
                        }
                    } catch (NumberFormatException e) {
                        // skip non-matching files
                    }
                }
            }
        }

        int nextOrderNum = maxOrderNum + 1;
        return String.format("ORDER_%03d", nextOrderNum);
    }

    private static void viewPastOrders(Scanner scanner) {
        System.out.print("Enter Order File Name (e.g., ORDER_001.txt): ");
        String fileName = scanner.nextLine();

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Order file not found: " + fileName);
        }
    }

    private static String getRestaurantNameById(String restId) {
        try (Scanner scanner = new Scanner(new File("Restaurants.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\|");
                if (parts[0].equals(restId)) {
                    return parts[1];
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading Restaurants.txt: " + e.getMessage());
        }
        return "Unknown";
    }

    private static String getRestaurantPhoneById(String restId) {
        try (Scanner scanner = new Scanner(new File("Restaurants.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\|");
                if (parts[0].equals(restId)) {
                    return parts[2];
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading Restaurants.txt: " + e.getMessage());
        }
        return "Unknown";
    }

    private static String getMenuItemNameById(String itemId) {
        try (Scanner scanner = new Scanner(new File("MenuItems.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\|");
                if (parts[0].equals(itemId)) {
                    return parts[2];
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading MenuItems.txt: " + e.getMessage());
        }
        return "Unknown Item";
    }

    private static String getMenuItemDescriptionById(String itemId) {
        try (Scanner scanner = new Scanner(new File("MenuItems.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\|");
                if (parts[0].equals(itemId)) {
                    return parts[3];
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading MenuItems.txt: " + e.getMessage());
        }
        return "No Description";
    }

    private static double getMenuItemPriceById(String itemId) {
        try (Scanner scanner = new Scanner(new File("MenuItems.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\|");
                if (parts[0].equals(itemId)) {
                    return Double.parseDouble(parts[4]);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading MenuItems.txt: " + e.getMessage());
        }
        return 0.0;
    }
}