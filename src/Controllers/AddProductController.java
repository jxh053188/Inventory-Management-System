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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class adds a new product to inventory. This class controls the add product form and allows a user to create a new product for inventory.
 * @author Jarred Harkness
 */
public class AddProductController implements Initializable {
    @FXML
    private TextField productStockTF;

    @FXML
    private TextField productNameTF;

    @FXML
    private TextField productMaxTF;

    @FXML
    private TextField productMinTF;

    @FXML
    private TextField productPartSearchTF;

    @FXML
    private TableColumn<Part, Integer> allPartIdColumn;

    @FXML
    private TableColumn<Part, String> allPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> allPartInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> allPartPriceColumn;

    @FXML
    private TableView<Part> allPartsTable;

    @FXML
    private TableView<Part> associatedPartTable;

    @FXML
    private TableColumn<Part, Integer> assocPartId;

    @FXML
    private TableColumn<Part, String> assocPartName;

    @FXML
    private TableColumn<Part, Integer> assocInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> assocPriceColumn;

    @FXML
    private TextField productPriceTF;

    @FXML
    private static ObservableList<Part> lookupParts = FXCollections.observableArrayList();

    private int productAutoID;

    private final ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    /**
     * This method initiates the form. The initialize method populates both the allPartsTable when the form loads.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allPartsTable.setItems(Inventory.getAllParts());
        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method cancels adding a new product. The cancelAddProduct method takes the user back to the main form without save any data entered by the user.
     *
     * @param event The event starts when the cancel button is pressed.
     * @throws IOException when the mainForm.fxml files is not found.
     */
    @FXML
    void cancelAddProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The form will be cleared and no data will be saved. \nAre you sure you wish to cancel adding a product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/views/mainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * This method saves a new product. The saveAddProduct method assigns the new product an ID number and performs a number of check to ensure the data entered is valid.
     * If all data is valid, the part is saved. If there are empty fields or the data entered is not valid, the user will receive an error.
     *
     * @param event The event starts when a user clicks the save button.
     * @throws IOException If the user does not enter the required information or has a validation error.
     */
    @FXML
    void saveAddProduct(ActionEvent event) throws IOException {
        if (!Inventory.getAllProducts().isEmpty()) {
            productAutoID = Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1).getId();
        } else {
            productAutoID = 0;
        }
        int productID = productAutoID + 1;

        try {
            String name = productNameTF.getText();
            int stock = Integer.parseInt(productStockTF.getText());
            double price = Double.parseDouble(productPriceTF.getText());
            int max = Integer.parseInt(productMaxTF.getText());
            int min = Integer.parseInt(productMinTF.getText());

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
            Product product = (new Product(productID, name, price, stock, min, max));
            for (Part part : associatedPartsList)
                product.addAssociatedPart(part);
            Inventory.addProduct(product);

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
     * This method show associated parts. The populateAssociatedPartsTableView populates the associated parts table.
     *
     * @param listOfParts is an observable list of parts that have been or will be added when the product is created.
     */
    public void populateAssociatedPartsTableView(ObservableList<Part> listOfParts) {
        associatedPartTable.setItems(listOfParts);
        assocPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method adds associated parts. The addAssociatedParts method allows a user to add parts to a product when it is being created. There are no limits
     * on the amount of parts that can be added or if multiples of the same part can be added.
     *
     * @param event The event begins when a part is selected and the use clicks the add button.
     */
    public void addAssociatedParts(ActionEvent event) {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        associatedPartsList.add(selectedPart);
        populateAssociatedPartsTableView(associatedPartsList);
    }

    /**
     * This method removes associated parts. The deleteAssociatedPart method will will remove a part from the associated parts table. A user will be prompted to confirm
     * they want to delete the associated part before it is removed from the list.
     *
     * @param event The event begins when the remove associated part button is pressed.
     */
    public void deleteAssociatedPart(ActionEvent event) {
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
            associatedPartsList.remove(selectedPart);
        populateAssociatedPartsTableView(associatedPartsList);
    }

    /**
     * This method allows search. The searchPart method allows a user to search the parts table by Part ID, name, or portion of the name.
     *
     * @param event The event begins everytime a key is typed in the search field.
     */
    @FXML
    void searchPart(KeyEvent event) {
        if (productPartSearchTF.getText().isEmpty()) {
            allPartsTable.setItems(Inventory.getAllParts());
        } else {
            lookupParts.clear();
            for (Part part : Inventory.getAllParts()) {
                if (part.getName().contains(productPartSearchTF.getText()) || part.getName().toLowerCase().contains(productPartSearchTF.getText()) || part.getName().equalsIgnoreCase(productPartSearchTF.getText())) {
                    lookupParts.add(part);
                }
                if (productPartSearchTF.getText().contains(String.valueOf(part.getId()))) {
                    lookupParts.add(part);
                }
            }
            allPartsTable.setItems(lookupParts);
            allPartsTable.refresh();
        }
    }

}



