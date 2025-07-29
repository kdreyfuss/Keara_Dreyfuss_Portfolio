package edu.umiami.bte324.caneeats.boundary;

import edu.umiami.bte324.caneeats.Utilities.AddressInfo;

import java.util.Scanner;

public class AddressInput {

    private AddressInfo addressInfo;

    public AddressInfo getAddressInfoFromConsole()
    {
        System.out.println("Address Input Screen");
        System.out.println("Input Street Number :");
        Scanner scanner = new Scanner(System.in);
        String streetNum = scanner.nextLine();
        System.out.println("Input Street Line :");
        String streetLine = scanner.nextLine();
        System.out.println("Input City:");
        String city = scanner.nextLine();
        System.out.println("Input State :");
        String state = scanner.nextLine();
        System.out.println("Input Country:");
        String country = scanner.nextLine();
        System.out.println("Input Zip Code:");
        String zipCode = scanner.nextLine();

        return new AddressInfo(streetLine,streetNum,zipCode,city,state,country);
    }
}
