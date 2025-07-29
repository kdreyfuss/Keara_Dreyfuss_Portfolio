package edu.umiami.bte324.caneeats.entities;

import edu.umiami.bte324.caneeats.Utilities.AddressInfo;

public class Restaurant {
    private String restaurantName;
    private String cuisine;
    private AddressInfo addressInfo;
    private double ratings;
    private String phoneNumber;
    private Menu menu;

    public Restaurant(String restaurantName, String phoneNumber, AddressInfo addressInfo, double ratingsDbl, String restPhone, Customer customer) {
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
    }

    public Restaurant() {
    }

    public Restaurant(String restaurantName, String cuisine, AddressInfo addressInfo, double ratings, String phoneNumber, Menu menu) {
        this.restaurantName = restaurantName;
        this.cuisine = cuisine;
        this.addressInfo = addressInfo;
        this.ratings = ratings;
        this.phoneNumber = phoneNumber;
        this.menu = menu;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void showMenu() {
        menu.displayMenu();
    }

    public void addItems(String name, String description, double price, int calories) {
        Item item4 = new Item(name, description, price, calories );
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantName='" + restaurantName + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", addressInfo=" + addressInfo +
                ", ratings=" + ratings +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
