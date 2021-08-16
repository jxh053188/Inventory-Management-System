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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import static models.Product.*;

/**
 * This class allows a user to modify a product. This class controls the modify product form and allows a user to change the data and associated parts of each product that is already in inventory.
 * @author Jarred Harkness
 */
public class ModifyProductController implements Initializable {
    Product product;

    @FXML
    private TextField modifyProductId;

    @FXML
    private TextField modifyProductName;

    @FXML
    private TextField modifyProductInvLevel;

    @FXML
    private TextField modifyProductPrice;

    @FXML
    private TextField modifyProductMax;

    @FXML
    private TextField modifyProductMin;

    @FXML
    private TextField modifyProductPartSearch;

    @FXML
    private TableView<Part> modifyProductAllPartsTable;

    @FXML
    private TableColumn<Part, Integer> allPartsIdColumn;

    @FXML
    private TableColumn<Part, String> allPartsNameColumn;

    @FXML
    private TableColumn<Part, Integer> allPartsInvColumn;

    @FXML
    private TableColumn<Part, Double> allPartsPriceColumn;

    @FXML
    private TableView<Part> modifyAssociatedPartTable;

    @FXML
    private TableColumn<Part, Integer> assocPartIdColumn;

    @FXML
    private TableColumn<Part, String> assocPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> assocInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> assocPriceColumn;

    @FXML
    private final ObservableList<Part> lookupParts = FXCollections.observableArrayList();
    ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

    public static int index;
    int productIndex = modifyIndex();
    public static int modifyIndex() {
        return index;
    }

    /**
     * This method initializes the page. The initialize method populates the allPartsTable when the modify product form loads.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modifyProductAllPartsTable.setItems(Inventory.getAllParts());
        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * This method fills the product information. This method pulls all of the product data for the chosen product and populates the modify product form.
     * @param product is the product being generated to be modified.
     */
   public void setProduct(Product product) {
        this.product = product;
        modifyProductId.setText(Integer.toString(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductInvLevel.setText(Integer.toString(product.getStock()));
        modifyProductPrice.setText(Double.toString(product.getPrice()));
        modifyProductMax.setText(Integer.toString(product.getMax()));
        modifyProductMin.setText(Integer.toString(product.getMin()));
        tempAssociatedParts = product.getAllAssociatedParts();
        modifyAssociatedPartTable.setItems(product.getAllAssociatedParts());
        assocPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * This method saves product modifications. This method performs a number of different checks to ensure that the user has entered valid data before saving the changes. If the data is not valid the user will receive a warning.
     * @param event The event begins when the user clicks the save button.
     */
    @FXML
    void saveModifyProduct(ActionEvent event) {
        try {
            int productId = Integer.parseInt(modifyProductId.getText());
            String name = modifyProductName.getText();
            double price = Double.parseDouble(modifyProductPrice.getText());
            int stock = Integer.parseInt(modifyProductInvLevel.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());


            if (price <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The price is invalid. \nPlease enter a price higher than 0.");
                Optional<ButtonType> result = alert.showAndWait();
                return;
            }

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The minimum is higher than maximum. \nPlease enter a lower minimum or higher maximum.");
                Optional<ButtonType> result = alert.showAndWait();
                return;
            }
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Stock is not between min and max. \nPlease enter stock again.");
                Optional<ButtonType> result = alert.showAndWait();
                return;
            }

            Product product = new Product(productId, name, price, stock, min, max);
            for(Part p : tempAssociatedParts){
                product.addAssociatedPart(p);
            }
            Inventory.updateProduct(productIndex, product);



            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/views/mainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check data entered.");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * Ths method adds an associated part to a product. This method allows a user to add an associated part to a product while it is being modified.
     * @param event The event begins when the user clicks the add button.
     */
    public void addAssociatedParts(ActionEvent event) {
        ObservableList<Part> selectedPart;
        selectedPart = modifyProductAllPartsTable.getSelectionModel().getSelectedItems();
        for (Part part : selectedPart) {
            product.addAssociatedPart(part);
        }
    }

    /**
     * This method removes an associated part. This method allows a user to remove an associated part from a product during modification.
     * @param event The event begins when the user has selected a part and clicks the remove associated part button.
     * @throws NoSuchElementException will throw if there is a null pointer associated with the removal.
     */
    public void removeAssociatedParts(ActionEvent event) throws NoSuchElementException {
        try {
            ObservableList<Part> selectedPart;
            selectedPart = modifyAssociatedPartTable.getSelectionModel().getSelectedItems();
            for (Part part : selectedPart) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    product.deleteAssociatedPart(part);
                }
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    /**
     * This method searches the parts table. This method allows the user to search the parts table by ID, name, or partial name.
     * @param event The even begins when the user types in the search field.
     */
    @FXML
    void searchPart(KeyEvent event) {
        if (modifyProductPartSearch.getText().isEmpty()) {
            modifyProductAllPartsTable.setItems(Inventory.getAllParts());
        } else {
            lookupParts.clear();
            for (Part part : Inventory.getAllParts()) {
                if (part.getName().contains(modifyProductPartSearch.getText()) || part.getName().toLowerCase().contains(modifyProductPartSearch.getText()) || part.getName().equalsIgnoreCase(modifyProductPartSearch.getText())) {
                    lookupParts.add(part);
                }
                if (modifyProductPartSearch.getText().contains(String.valueOf(part.getId()))) {
                    lookupParts.add(part);
                }
            }
            modifyProductAllPartsTable.setItems(lookupParts);
            modifyProductAllPartsTable.refresh();
        }
    }

    /**
     * This method closes the modify screen. This method allows a user to close the modify product screen without saving any changes to the data they have entered.
     * @param event The event begins when the user clicks the cancel button.
     * @throws IOException Exception will be thrown if there is now mainForm.fxml file found.
     */
    @FXML
    void cancelModifyProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The form will be cleared and no data will be saved. \nAre you sure you wish to cancel modifying the product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/views/mainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }


}



