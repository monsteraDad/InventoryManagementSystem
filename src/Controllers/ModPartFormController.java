package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the modify part form. */
public class ModPartFormController implements Initializable {

    @FXML
    private RadioButton ModInHouseRBtn;
    @FXML
    private RadioButton ModOSPartRBtn;
    @FXML
    private Label InOrOutModPart;
    @FXML
    private TextField ModPartIdTxt;
    @FXML
    private TextField ModPartNameTxt;
    @FXML
    private TextField ModPartInvTxt;
    @FXML
    private TextField ModPartPriceTxt;
    @FXML
    private TextField ModPartMaxTxt;
    @FXML
    private TextField ModPartMachineIdTxt;
    @FXML
    private TextField ModPartMinTxt;

    private int partIndex;

    Stage stage;
    Parent scene;

    /** This method saves updated data to the parts observable list. Prompts user with error screens depending on error occurrence.
        @param event updates data entered into text fields.
        @return updated part to parts observable list.*/
    @FXML
    void OnActionSave(ActionEvent event) throws IOException {
            try {
                int id = Integer.parseInt(ModPartIdTxt.getText());
                String name = ModPartNameTxt.getText();
                int stock = Integer.parseInt(ModPartInvTxt.getText());
                Double price = Double.parseDouble(ModPartPriceTxt.getText());
                int max = Integer.parseInt(ModPartMaxTxt.getText());
                int min = Integer.parseInt(ModPartMinTxt.getText());
                if (Integer.parseInt(ModPartMaxTxt.getText()) < Integer.parseInt(ModPartMinTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Part Max Can Not Be Less Than Part Min.");
                    alert.showAndWait();
                } else if (Integer.parseInt(ModPartMaxTxt.getText()) < Integer.parseInt(ModPartInvTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Part Max Can Not Be Less Than Part INV");
                    alert.showAndWait();
                } else if (Integer.parseInt(ModPartMinTxt.getText()) > Integer.parseInt(ModPartInvTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Part Min Can Not Be More Than Part INV");
                } else if (emptyTxt() == false) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter missing Information.");
                    alert.showAndWait();
                } else if (ModInHouseRBtn.isSelected()) {
                    int machineID = Integer.parseInt(ModPartMachineIdTxt.getText());
                    Part updatedPart = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.updatePart(this.partIndex, updatedPart);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else {
                    ModOSPartRBtn.selectedProperty().set(true);
                    String companyName = ModPartMachineIdTxt.getText();
                    Part updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(this.partIndex, updatedPart);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            } catch (NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Values");
                alert.setContentText("Please Input Valid Values in each TextField.");
                alert.showAndWait();
            }

        }

        /** This Method checks if the text fields are empty.
         @return true if no text field empty. */
        public boolean emptyTxt() {
            if(this.ModPartNameTxt.getText().isEmpty() || this.ModPartNameTxt.getText() == null) {
                return false;
            } else if(this.ModPartInvTxt.getText().isEmpty() || this.ModPartInvTxt.getText() == null) {
                return false;
            } else if(this.ModPartPriceTxt.getText().isEmpty() || this.ModPartPriceTxt.getText() == null) {
                return false;
            } else if(this.ModPartMaxTxt.getText().isEmpty() || this.ModPartMaxTxt.getText() == null) {
                return false;
            } else if(this.ModPartMinTxt.getText().isEmpty() || this.ModPartMinTxt.getText() == null) {
                return false;
            }
            return true;
        }

    /** This method allows user to cancel data entry on form and return to main form.
        @param event when cancel button is clicked prompt user with confirmation.
        @return to the Main form screen. */
    @FXML
    void OnActionToMAinForm(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return to Main Menu?");
        alert.setContentText("Are you sure you wish to Cancel Data Update? All Updates Will be Lost");
        Optional<ButtonType> results = alert.showAndWait();
        if (results.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** This method Switches to InHouse Part. When the InHouse Radio button is selected print Machine ID to InOrOut Label.
     @param event InOrOut Label being changed.
     @return InOrOut Label as Machine ID. */
    @FXML
    void OnActionToModInHousePart(ActionEvent event) {
        InOrOutModPart.setText("Machine ID");
    }
    /** This method Switches to Outsource part.
        @param event InOrOut Label being changed.
        @return InOrOut Label as Company Name. */
    @FXML
    void OnActionToModOSPart(ActionEvent event) {
        InOrOutModPart.setText("Company Name");
    }

    /** This method sends InHouse parts data to the text fields.
        @param modifyPart send the data to be updated from the selected part in the main menu. */
    @FXML
    public void sendPart(Part modifyPart){
        this.partIndex = Inventory.getAllParts().indexOf(modifyPart);
        ModPartIdTxt.setText(String.valueOf(modifyPart.getId()));
        ModPartNameTxt.setText(modifyPart.getName());
        ModPartInvTxt.setText(String.valueOf(modifyPart.getStock()));
        ModPartPriceTxt.setText(String.valueOf(modifyPart.getPrice()));
        ModPartMaxTxt.setText(String.valueOf(modifyPart.getMax()));
        ModPartMinTxt.setText(String.valueOf(modifyPart.getMin()));
        if (modifyPart instanceof InHouse){
            ModInHouseRBtn.selectedProperty().set(true);
            ModPartMachineIdTxt.setText(Integer.toString(((InHouse) modifyPart).getMachineID()));
        } else {
            ModOSPartRBtn.selectedProperty().set(true);
            ModPartMachineIdTxt.setText(String.valueOf(((Outsourced) modifyPart).getCompanyName()));
            InOrOutModPart.setText("Company Name");
        }
    }

    /** This method will initialize the page with the data to be modified. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}}
