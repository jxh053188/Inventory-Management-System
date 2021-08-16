package models;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * This class sets product parameters. This class sets the necessary requirements for a product in inventory.
 * @author Jarred Harkness
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * The product constructor. This is the constructor for a new product in the inventory.
     * @param id product ID as an integer.
     * @param name product name as a string.
     * @param price product price as a double.
     * @param stock product stock as an integer.
     * @param min product minimum level as an integer.
     * @param max product maximum level as an integer.
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method sets the product ID. This method allows a user to set the product Id in inventory.
     * @param id The product ID as an integer.
     */

    public void setId(int id) {this.id = id;}

    /**
     * This method sets the product name. This method allows a user to set the product name in inventory.
     * @param name The product name as a string.
     */

    public void setName(String name) {this.name = name;}

    /**
     * This method sets the product price. This method allows a user to set the product price in inventory.
     * @param price The product price as a double.
     */

    public void setPrice(double price) {this.price = price;}

    /**
     * This method sets the product stock. This method allows a user to set the product stock in inventory.
     * @param stock The stock as an integer.
     */

    public void setStock(int stock) {this.stock = stock;}

    /**
     * This method sets the product minimum level. This method allows a user to set the product minimum stock level in inventory.
     * @param min The product min as an integer.
     */

    public void setMin(int min) {this.min = min;}

    /**
     * This method sets the product maximum level. This method allows a user to set the product maximum stock level in inventory.
     * @param max The product max as an integer.
     */

    public void setMax(int max) {this.max = max;}

    /**
     * This method returns the product ID. This method will return the product Id of a product in inventory.
     * @return The product Id as an integer.
     */
    public int getId() {
        return id;
    }

    /**
     * This method returns the product name. This method will return the product name of a product in inventory.
     * @return The product name as a string.
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the product price. This method will return the product price of a product in inventory.
     * @return The product price as a double.
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method returns the product stock. This method will return the product stock of a product in inventory.
     * @return The product stock as an integer.
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method returns the product minimum. This method will return the product minimum stock level of a product in inventory.
     * @return The min as an integer.
     */
    public int getMin() {
        return min;
    }

    /**
     * This method returns the product maximum. This method will return the product maximum stock level of a product in inventory.
     * @return The max as an integer.
     */
    public int getMax() {
        return max;
    }

    /**
     * This method adds associated parts. This method adds parts to the associated parts list of a product in inventory.
     * @param part Is the part that will be added to the product's associated parts table.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * This method deletes a part associated with a product. This method will delete a selected part from the associated parts list of a product in inventory.
     * @param selectedAssociatedPart is the part to be deleted from the associated parts list.
     * @return True if the part is successfully deleted or false if the part fails to delete.
     */
    public boolean deleteAssociatedPart (Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method returns a list of associated parts. This method will return a list of parts associated with a product in inventory.
     * @return The list of all associated parts for the selected product.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
