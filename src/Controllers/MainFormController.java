package Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Inventory;
import models.Part;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * This class controls the main screen. The MainFormController controls the different functions on the main screen. It is the first screen that is
 * loaded when the application starts and where the user is returned after adding or modifying a product or part.
 * It contains two table views of all the parts and all of the products in the system.
 *
 * <h1><b>Runtime Errors</b></h1>
 * <p><b>While testing the product search function, I kept receiving a runtime error of a null pointer exception. After re-writing the search method in a different way I still received the same error. I looked for an tables and declarations that had not been correctly initiated but couldn't find any in the main controller.
 * Finally I realized that the issue was in the fxml file for the main form. I had named the product search textfield in the fxml file different than I had declared in the controller. After correcting the spelling, the search worked.</b></p>
 * @author Jarred Harkness
 */

public class MainFormController implements Initializable {

    @FXML
    private TextField partSearchTF;

    @FXML
    private TableView<Part> mainPartsTable;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TextField productSearchTF;

    @FXML
    private TableView<Product> mainProductsTable;

    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productInvLevelColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private static final ObservableList<Part> lookupParts = FXCollections.observableArrayList();

    @FXML
    private static final ObservableList<Product> lookupProduct = FXCollections.observableArrayList();

    /**
     * This method initializes the form. The Override Initialize method sets up the parts and product tables with the sample data that is in inventory.
     * It also refreshes the tables after a new product or part has been added.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPartsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductsTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method exits the application. The exitApplication method prompts the user for confirmation and if accepted will close the application.
     * @param event starts the event upon button click.
     */
    @FXML
    void exitApplication(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Data in this application will be reset.\nAny changes you have made will not be saved. \nAre you sure you wish exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Runtime.getRuntime().exit(0);
        }
    }

    /**
     * This method opens the add part screen. The method addPartButtonPress opens the add part windows and allows the user to enter data for a new part.
     * @param event The event begins when the add part button is pressed.
     * @throws IOException If the user does not enter the required information or leaves a field blank.
     */
    @FXML
    void addPartButtonPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/views/addPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * This method opens the modify parts screen. The modifyPartButtonPress method finds the selected part index and sends it to the modify part screen before
     * loading the modify part screen. If no part is selected, the user will be shown an error.
     * @param event The event begins when a part is selected and the modify button is pressed.
     * @throws IOException will be thrown if the modifyPart.fxml file is not found during loading.
     */
    @FXML
    void modifyPartButtonPress(ActionEvent event) throws IOException {
        if (mainPartsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No part is selected. \nPlease select the part you want to modify.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Part selectedPart = mainPartsTable.getSelectionModel().getSelectedItem();
            ModifyPartController.index = Inventory.getAllParts().indexOf(selectedPart);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/modifyPart.fxml"));
            loader.load();
            ModifyPartController controller = loader.getController();
            controller.setPart(mainPartsTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method deletes a part. The deletePart method removes a part from inventory. The user is alerted if a part is not selected and they are
     * asked to confirm if they want to delete the part.
     * @param event The event begins when the delete button under the parts table is pressed.
     */
    @FXML
    void deletePart(ActionEvent event) {
        Part selected = mainPartsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No part is selected. \nPlease select the part you want to delete.");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selected);
                mainPartsTable.refresh();
            }
        }
    }

    /**
     * This method opens the add product screen. The addProductButtonPress method opens the add product screen and prepares the application to accept data for a new product.
     * @param event The event begins when the add button under the product table is pressed.
     * @throws IOException An error will be thrown if the addProduct.fxml file is not found.
      */
    @FXML
    void addProductButtonPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/views/addProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method deletes a product. The deleteProduct method will remove a product from Inventory. First it ensures the user has selected a part.
     * If they have it will check to ensure that the product does not have any parts associated with it. If there are parts
     * associated with the product, the deletion will fail. If there are no parts associated with the product,
     * the user is asked to confirm and if accepted, the product will be deleted.
     * @param event The event begins when the user clicks the delete button under the product table.
     */
    @FXML
    void deleteProduct(ActionEvent event) {
        Product selected = mainProductsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No product is selected. \nPlease select the product you want to delete.");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else if (!selected.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This product has associated parts and can not be deleted. \nPlease remove associated parts and try again.");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selected);
                mainProductsTable.refresh();
            }
        }
    }

    /**
     * This method opens the modify product screen. The modifyProductButtonPress method allows a user to modify a product that is already in inventory. First it checks
     * to make sure a product is selected, and If it is transfers the index of that product to the modifyProduct controller,
     * then it loads the modifyProduct screen.
     * @param event The event begins when the modify button under the product table is pressed.
     * @throws IOException when the modifyProduct.fxml cannot be found.
     */
    public void modifyProductButtonPress(ActionEvent event) throws IOException {
        if(mainProductsTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No product is selected. \nPlease select the product you want to modify.");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Product selectedProduct = mainProductsTable.getSelectionModel().getSelectedItem();
            ModifyProductController.index = Inventory.getAllProducts().indexOf(selectedProduct);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/modifyProduct.fxml"));
            loader.load();
            ModifyProductController controller = loader.getController();
            controller.setProduct(mainProductsTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * This method allows user to search parts. The searchPart method allows a user to search the parts table by ID, name or partial name.
     * @param event The event starts after every typed key.
     */
    @FXML
    void searchPart(KeyEvent event) {

        if (partSearchTF.getText().isEmpty()) {
            mainPartsTable.setItems(Inventory.getAllParts());
        } else {
            lookupParts.clear();
            for (Part part : Inventory.getAllParts()) {
                if (part.getName().contains(partSearchTF.getText()) || part.getName().toLowerCase().contains(partSearchTF.getText()) || part.getName().equalsIgnoreCase(partSearchTF.getText())) {
                    lookupParts.add(part);
                }
                if (partSearchTF.getText().contains(String.valueOf(part.getId()))) {
                    lookupParts.add(part);
                }
            }
            mainPartsTable.setItems(lookupParts);
            mainPartsTable.refresh();
        }
    }


    /**
     * This method allows users to search products. The searchProduct method allows a user to search the products table by ID, name, or partial name.
     * @param event The event begins whenever a key is typed.
     */
    @FXML
    void searchProduct(KeyEvent event) {
        if (productSearchTF.getText().isEmpty()) {
            mainProductsTable.setItems(Inventory.getAllProducts());
        } else if (!productSearchTF.getText().isEmpty()) {
            lookupProduct.clear();
            for (Product product : Inventory.getAllProducts()) {
                if (product.getName().contains(productSearchTF.getText()) || product.getName().toLowerCase().contains(productSearchTF.getText()) || product.getName().equalsIgnoreCase(productSearchTF.getText())) {
                    lookupProduct.add(product);
                }

                if (productSearchTF.getText().contains(String.valueOf(product.getId()))) {
                    lookupProduct.add(product);
                }
                mainProductsTable.setItems(lookupProduct);
                mainProductsTable.refresh();

            }
        }
    }


}
