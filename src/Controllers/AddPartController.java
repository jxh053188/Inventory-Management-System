package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import java.io.IOException;
import java.util.Optional;

/**
 * This class adds new parts to the inventory. The AddPartController controls the functions of the add part screen. It allows a user to add a new part to inventory
 * and performs a number of validation checks.
 * @author Jarred Harkness
 */
public class AddPartController {
    @FXML
    private RadioButton addPartInhouseRadio;

    @FXML
    private RadioButton addPartOutsourcedRadio;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartPrice;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMachineId;

    @FXML
    private TextField addPartMin;

    @FXML
    private Label addPartMachineIdLabel;

    private int partAutoID;

    /**
     * This method cancels adding a new part to inventory. The cancelAddPart allows a user to leave the add part screen and removes any data that they have entered.
     *
     * @param event The event starts when the cancel button is pressed.
     * @throws IOException if the mainForm.fxml file is not found.
     */
    @FXML
    void cancelAddPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The form will be cleared and no data will be saved. \nAre you sure you wish to cancel adding a part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/views/mainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * This button changes the add product form. The radialOption method changes the form text and prompt depending on if the user has chosen to create an in house part
     * or an outsourced part.
     */
    public void radialOption() {
        if (addPartOutsourcedRadio.isSelected()) {
            addPartMachineId.clear();
            addPartMachineIdLabel.setText("Company Name");
            addPartMachineId.setPromptText("Company Name");
        } else {
            addPartMachineId.clear();
            addPartMachineIdLabel.setText("Machine ID");
            addPartMachineId.setPromptText("Machine ID");
        }
    }

    /**
     * This method saves the new part to inventory. The saveAddPart method generates a new part ID and does a number of validation checks to ensure that the user has correctly
     * filled out the form. If the user has left fields empty or filled out the form incorrectly, they will not be allowed to save a new part.
     * The method differentiates between an in house part and an outsourced part.
     *
     * @param event The event starts when the user clicks the save button.
     * @throws IOException if the any fields are left blank or if a user enters the incorrect data type.
     */
    @FXML
    void saveAddPart(ActionEvent event) throws IOException {
        if (!Inventory.getAllParts().isEmpty()) {
            partAutoID = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1).getId();
        } else {
            partAutoID = 0;
        }
        int partID = partAutoID + 1;

        try {
            if (addPartInhouseRadio.isSelected()) {

                String name = addPartName.getText();
                int stock = Integer.parseInt(addPartInv.getText());
                double price = Double.parseDouble(addPartPrice.getText());
                int max = Integer.parseInt(addPartMax.getText());
                int min = Integer.parseInt(addPartMin.getText());
                int machineId = Integer.parseInt(addPartMachineId.getText());

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

                Inventory.addPart(new InHouse(partID, name, price, stock, min, max, machineId) {
                });

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/views/mainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check data entered.");
            Optional<ButtonType> result = alert.showAndWait();
        }

        if (addPartOutsourcedRadio.isSelected()) {
            try {
                String name = addPartName.getText();
                int stock = Integer.parseInt(addPartInv.getText());
                double price = Double.parseDouble(addPartPrice.getText());
                int max = Integer.parseInt(addPartMax.getText());
                int min = Integer.parseInt(addPartMin.getText());
                String companyName = addPartMachineId.getText();

                Inventory.addPart(new Outsourced(partID, name, price, stock, min, max, companyName) {
                });

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

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/views/mainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check data entered.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

}
