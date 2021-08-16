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
import models.Part;
import java.io.IOException;
import java.util.Optional;

/**
 * This class modifies parts in inventory. This class allows a user to modify the data of a part that is already in the inventory.
 * @author Jarred Harkness
 */

public class ModifyPartController {
    @FXML
    private RadioButton modifyInhouseRadio;

    @FXML
    private RadioButton modifyOutsourcedRadio;

    @FXML
    private TextField modifyPartID;

    @FXML
    private TextField modifyPartName;

    @FXML
    private TextField modifyPartInv;

    @FXML
    private TextField modifyPartPrice;

    @FXML
    private TextField modifyPartMax;

    @FXML
    private TextField modifyPartMin;

    @FXML
    private Label modifyMachineIDLabel;

    @FXML
    private TextField modifyMachineId;

    public static int index;
    public static int modifyIndex() {
        return index;
    }
    int partIndex = modifyIndex();

    /**
     * This method populates the part's information. The setPart method creates an object to transfer the selected part's data into and sets the forms fields with the correct information.
     * It checks if the part is an instance of the InHouse class or the Outsourced class.
     * @param part the part to be edited.
     */
    public void setPart(Part part) {

        if(part instanceof InHouse) {
            modifyInhouseRadio.setSelected(true);
            modifyPartID.setText(String.valueOf(part.getId()));
            modifyPartName.setText(part.getName());
            modifyPartInv.setText(String.valueOf(part.getStock()));
            modifyPartPrice.setText(String.valueOf(part.getPrice()));
            modifyPartMax.setText(String.valueOf(part.getMax()));
            modifyPartMin.setText(String.valueOf(part.getMin()));
            modifyMachineId.setText(String.valueOf(((InHouse) part).getMachineId()));

        }
        if(part instanceof Outsourced){
            modifyOutsourcedRadio.setSelected(true);
            modifyMachineIDLabel.setText("Company Name");
            modifyPartID.setText(String.valueOf(part.getId()));
            modifyPartName.setText(part.getName());
            modifyPartInv.setText(String.valueOf(part.getStock()));
            modifyPartPrice.setText(String.valueOf(part.getPrice()));
            modifyPartMax.setText(String.valueOf(part.getMax()));
            modifyPartMin.setText(String.valueOf(part.getMin()));
            modifyMachineId.setText(((Outsourced) part).getCompanyName());
        }
    }


    /**
     * This method saves the modifications. The saveModifyPart performs a number of checks on the data entered to ensure it is valid. If the user has not entered
     * data in all the fields or intered the incorrect data type they will receive an error.
     * @param event The event starts when the save button is clicked.
     * @throws IOException if the form is not filled out correctly or there is data missing.
     */
    @FXML
    void saveModifyPart (ActionEvent event) throws IOException {
        try {
            if (modifyInhouseRadio.isSelected()) {
                int id = Integer.parseInt(modifyPartID.getText());
                String name = modifyPartName.getText();
                int stock = Integer.parseInt(modifyPartInv.getText());
                double price = Double.parseDouble(modifyPartPrice.getText());
                int max = Integer.parseInt(modifyPartMax.getText());
                int min = Integer.parseInt(modifyPartMin.getText());
                int machineId = Integer.parseInt(modifyMachineId.getText());

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
                Inventory.updatePart(partIndex, new InHouse(id, name, price, stock, min, max, machineId) {
                });

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/views/mainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check the data entered.");
            Optional<ButtonType> result = alert.showAndWait();
        }

        if (modifyOutsourcedRadio.isSelected()){
            try {
                int id = Integer.parseInt(modifyPartID.getText());
                String name = modifyPartName.getText();
                int stock = Integer.parseInt(modifyPartInv.getText());
                double price = Double.parseDouble(modifyPartPrice.getText());
                int max = Integer.parseInt(modifyPartMax.getText());
                int min = Integer.parseInt(modifyPartMin.getText());
                String companyName = modifyMachineId.getText();

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
                Inventory.updatePart(partIndex, new Outsourced(id, name, price, stock, min, max, companyName) {
                });

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/views/mainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();


            } catch (NumberFormatException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check the data entered.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

    /**
     * This method cancels modifications. The cancelModifyPart method will take the user back to the main form without saving any changes to the part.
     * @param event The event starts when the cancel button is pressed.
     * @throws IOException
     */
    @FXML
    void cancelModifyPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No changes will be saved to the current part. \nAre you sure you wish to cancel modifying part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/views/mainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method changes the text of the form. The radialOption changes the part form depending on if the part is made in house or outsourced.
     */
    public void radialOption() {
        if (modifyOutsourcedRadio.isSelected()) {
            modifyMachineId.clear();
            modifyMachineIDLabel.setText("Company Name");
            modifyMachineId.setPromptText("Company Name");
        } else {
            modifyMachineId.clear();
            modifyMachineIDLabel.setText("Machine ID");
            modifyMachineId.setPromptText("Machine ID");
        }
    }

}



