package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the add part form. */
public class AddPartFormController implements Initializable {

    @FXML
    private RadioButton InHouseRBtn;
    @FXML
    private Label InOrOut;
    @FXML
    private TextField AddPartIdTxt;
    @FXML
    private TextField AddNameTxt;
    @FXML
    private TextField AddPartInvTxt;
    @FXML
    private TextField AddPartCostTxt;
    @FXML
    private TextField AddPartMaxTxt;
    @FXML
    private TextField AddPartMinTxt;
    @FXML
    private TextField InOrOutTxt;

    Stage stage;
    Parent scene;

    /** This method saves data to the Part array.
        @param event Saves data entered while exception handling.
        @return store Part in the part observable list to be called in the Main for.
      */
    @FXML
    void OnActionSaveBtn(ActionEvent event) throws IOException {
        //RETRIEVE THE VALUES FROM THE TEXT FIELDS SEND TO PART TABLE VIEW ON MAIN FORM
        try {
            Inventory.setPartIDCount();
            int id = Inventory.getPartIDCount();
            String name = AddNameTxt.getText();
            int stock = Integer.parseInt(AddPartInvTxt.getText());
            Double price = Double.parseDouble(AddPartCostTxt.getText());
            int max = Integer.parseInt(AddPartMaxTxt.getText());
            int min = Integer.parseInt(AddPartMinTxt.getText());
            if (Integer.parseInt(AddPartMaxTxt.getText()) < Integer.parseInt(AddPartMinTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Part Max Can not be Less Than Part Min.");
                alert.showAndWait();
            } else if (Integer.parseInt(AddPartMaxTxt.getText()) < Integer.parseInt(AddPartInvTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Part Max Can Not Be Less Than Part INV");
                alert.showAndWait();
            } else if (Integer.parseInt(AddPartMinTxt.getText()) > Integer.parseInt(AddPartInvTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Part Min Can Not Be Greater Than Part INV");
                alert.showAndWait();
            } else if (emptyTxt() == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter missing Information.");
                alert.showAndWait();
            } else if (InHouseRBtn.isSelected()) {
                int machineID = Integer.parseInt(InOrOutTxt.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
            } else {
                String companyName = InOrOutTxt.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter Valid Data Types.");
            alert.showAndWait();
        }
        }


    /** This Method Checks each text field for empty fields or null values.
      @return true if text fields are not empty.  */
    public Boolean emptyTxt() {
        if(this.AddNameTxt.getText().isEmpty() || this.AddNameTxt.getText() == null) {
            return false;
        } else if(this.AddPartInvTxt.getText().isEmpty() || this.AddPartInvTxt.getText() == null) {
            return false;
        } else if(this.AddPartCostTxt.getText().isEmpty() || this.AddPartCostTxt.getText() == null) {
            return false;
        } else if(this.AddPartMaxTxt.getText().isEmpty() || this.AddPartMaxTxt.getText() == null) {
            return false;
        } else if(this.AddPartMinTxt.getText().isEmpty() || this.AddPartMinTxt.getText() == null) {
            return false;
        }
        return true;
    }
    /** This Method Switches to Outsource Part. When the Outsourced Radio button is selected print Company name to InOrOut Label.
     * @param event InOrOut label being changed
     * @return InOrOut Label as Company Name. */
    @FXML
    void onActionOutsourced(ActionEvent event) {
        InOrOut.setText("Company Name");
    }
    /** This method Switches to InHouse Part. When the InHouse Radio button is selected print Machine ID to InOrOut Label.
        @param event InOrOutLabel being changed.
        @return InOrOut Label as Machine ID. */
    @FXML
    void onActionInHouse(ActionEvent event) throws IOException{
        InOrOut.setText("Machine ID");
    }

    /** this method allows to cancel adding a part. When the cancel button is clicked the user will be promped with a confirmatoin page.
        If user clicks OK, Take user to Main Form.
        @param event To the Main Form.
        @return To Main Form*/
    @FXML
    void OnActionToMainForm(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please Confirm!");
        alert.setContentText("Are You Sure You Wish to Cancel. All Data Entered Will Be Lost.");
        Optional<ButtonType> results = alert.showAndWait();
        if (results.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** This method initializes the Part ID. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddPartIdTxt.setText(String.valueOf(Inventory.getPartIDCount()+1));
        AddPartIdTxt.setEditable(false);
    }
}
