package models;
/**
 * Supplied class Part.java
 */

/**
 *
 *
 * This class creates a part. This class sets the parameters for parts in the inventory. It is extended by the InHouse and Outsourced classes.
 * @author Jarred Harkness
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * This is the part constructor. This constructor sets the requirements to create a new part in the inventory.
     * @param id is the partId as an integer.
     * @param name is the part name as a string.
     * @param price is the part price as a double.
     * @param stock is the part stock as an integer.
     * @param min is the minimum inventory level as an integer.
     * @param max is the maximum inventory level as an integer.
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *This method gets part ID. This method will get the part ID for a specified product in inventory.
     * @return The partId as an integer.
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the part ID. This method allows users to set the partId of a part in inventory.
     * @param id The part ID as an integer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method gets the part name. This method will return the name of a part in inventory.
     * @return The part name as a string.
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the part name. This method allows users to set the Part name of a part in inventory.
     * @param name The part name as a string.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method gets the part price. This method will return the price of a part in inventory.
     * @return The part price as a double.
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method sets the part price. This method allows a user to set the price of a part in inventory.
     * @param price The part price as a double.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method gets the part stock. This method will return the stock of a part that is in inventory.
     * @return The part stock as an integer.
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method sets the part price. This method allows a user to set the stock of a part in inventory.
     * @param stock The stock of a part as an integer.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method will return the minimum stock. This method returns the minimum stock level for a part in inventory.
     * @return The min as an integer.
     */
    public int getMin() {
        return min;
    }

    /**
     * This method sets the minimum stock. This method allows a user to set the minimum stock level for a part in inventory.
     * @param min The min as an integer.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method will return the maximum stock. This method returns the maximum stock level for a part in inventory.
     * @return The max as an integer.
     */
    public int getMax() {
        return max;
    }

    /**
     * This method sets the maximum stock. This method allows a user to set the maximum stock level for a part in inventory.
     * @param max The max as an integer.
     */
    public void setMax(int max) {
        this.max = max;
    }

}