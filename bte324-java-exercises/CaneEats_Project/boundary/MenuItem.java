package edu.umiami.bte324.caneeats.boundary;

public class MenuItem { private String itemName;
    private String description;
    private double price;
    private String restaurantId;

    public MenuItem(String itemName, String description, double price, String restaurantId) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", restaurantId='" + restaurantId + '\'' +
                '}';
    }
}

