package models;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *
 * The Inventory class contains the parameters for inventory objects. The Inventory class contains methods necessary in order to create and maintain a list of parts and products.
 * @author Jarred Harkness
 */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds a new part to the inventory. This method allows new parts to be populated into the list of all parts in inventory.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds a new product to the inventory. This method allows new products to be populated into the list of a products in inventory.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method searches the list of parts. This method will search the list of part for a specified ID.
     * @return The matching part or null if the part is not found.
     */
    public static Part lookupPart(int partId){
       return null;
    }

    /**
     * This method searches the list of products. This method will search the list of products for a specified ID.
     * @return The matching product or null if the product is not found.
     */
    public static Product lookupProduct(int productId){

        return null;
    }

    /**
     * This method searches parts by name. This method will search the list of parts by the specified part name.
     * @return the matching part object or null if the part is not found.
     * @param searchText is the text to be searched. Will return null if nothing is found.
     */
     public static ObservableList<Part> lookupPart(String searchText){
         return null;
     }

    /**
     * This method searches products by name. This method will search the list of products by the specified product name.
     * @param productName Is the product name that you want to search.
     * @return The matching product object or null if the part is not found.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        return null;
    }

    /**
     * This method updates an existing part. This method will update the data of an existing part in the inventory.
     * @param index Is the index of the part to be updated.
     * @param selectedPart Is the the part that will be replacing the old part in inventory.
     */

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method updates an existing product. This method will update the data of an existing product in the inventory.
     * @param newProduct Is the product that will be replacing the old product in inventory.
     */

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * This method will delete a part from inventory. This method will delete a part from the list of all parts in inventory.
     * @Param selectedPart Is the part the user wants to delete.
     * @return True if the part is deleted successfully, otherwise return false.
     */

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * This method will delete a product from inventory. This method will delete a product from the list of all products in inventory.
     * @Param selectedProduct Is the product the user wants to delete.
     * @return True if the product is deleted successfully, otherwise return false.
     */

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * This method will return the list of all parts. This method will return the list of all parts in inventory.
     * @return The list of all parts.
     */
    public static  ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method will return the list of all products. This method will return the list of all products in inventory.
     * @return The list of all products.
     */
    public static  ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}
