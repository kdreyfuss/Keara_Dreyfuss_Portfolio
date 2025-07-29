package edu.umiami.bte324.caneeats.boundary;

import edu.umiami.bte324.caneeats.entities.Customer;

import java.util.Scanner;


public class CustomerInput {
    private Customer customer;
    public void showCustomerOptions()
    {
        boolean loopFlag = true;
        while(loopFlag) {
            System.out.println("Customer Options");
            System.out.println("1. Add New Customer");
            System.out.println("2. View all Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Back to Main Menu");
            System.out.print("Input your Choice: ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            if(choice.equals("5"))
            {
                // the user would like to exit so reset the loopFlag
                // to exit from the while loop.
                loopFlag = false;
            }

            else if (choice.equals("1"))
            {
                inputCustomerInfoFromConsole();
            }

            else if (choice.equals("2"))
            {
                if(this.customer != null) // Make sure that the restaurant is not empty
                    System.out.println(this.customer);

                else
                    System.out.println("No Customer found, Add a new one:");
            }
        }
    }
    public Customer inputCustomerInfoFromConsole()
    {
        System.out.println("Customer Input Screen ");
        System.out.println("Input Customer First Name:");
        Scanner sc = new Scanner(System.in);
        String customerFirstName = sc.nextLine();
        System.out.println("Input Customer Last Name:");
        String customerLastName = sc.nextLine();
        System.out.println("Input Customer Phone Number:");
        String customerPhone = sc.nextLine();
        System.out.println("Input Customer Address:");
        String customerAddress = sc.nextLine();
        System.out.println("Input Customer Email:");
        String customerEmail = sc.nextLine();

        this.customer = new Customer(customerFirstName, customerLastName, customerPhone,customerAddress,customerEmail);

        System.out.println(this.customer);

        return this.customer;


    }
}
