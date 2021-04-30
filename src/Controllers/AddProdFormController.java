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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class Controls the add product form. */
public class AddProdFormController implements Initializable {

    @FXML
    private TextField ProductID;
    @FXML
    private TextField ProductNameTxt;
    @FXML
    private TextField ProductInv;
    @FXML
    private TextField ProductPrice;
    @FXML
    private TextField ProductMax;
    @FXML
    private TextField ProductMin;


    @FXML
    private TextField TableQueryTxt;
    @FXML
    private TableView<Part> PartTableView;
    @FXML
    private TableColumn<Part, Integer> PartIDCol;
    @FXML
    private TableColumn<Part, String> PartNameCol;
    @FXML
    private TableColumn<Part, Integer> InventoryLevelCol;
    @FXML
    private TableColumn<Part, Double> PriceCol;


    @FXML
    private TableView<Part> ProductPartsTableView;
    @FXML
    private TableColumn<Product, Integer> ProdPartIdCol;
    @FXML
    private TableColumn<Product, String> ProdPartNameCol;
    @FXML
    private TableColumn<Product, Integer> ProdPartInventoryLevelCol;
    @FXML
    private TableColumn<Product, Double> ProdPartPriceCol;

    private ObservableList<Part> newAssociatedParts = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;

    /** this method allows for the addition of parts to the associated parts tableview.
        @param event Add button clicked.
        @return added part to associated part. */
    @FXML
    void OnActionAdd(ActionEvent event) {
        Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            newAssociatedParts.add(selectedPart);
            ProductPartsTableView.setItems(newAssociatedParts);
            ProdPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            ProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            ProdPartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            ProdPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Part Selected.");
            alert.setContentText("Please select a Part to Add.");
        }

    }

    /** this method allows for the deletion of associated parts.
        @param event remove associated part clicked. Delete selected part.
        @return delete associated part. */
    @FXML
    void OnActionDelete(ActionEvent event) {
        Part selectedAssociatedPart = ProductPartsTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (selectedAssociatedPart != null) {
            alert.setHeaderText("Would You Like to Delete Selected Product Part?");
            alert.setContentText("Select 'OK' to Proceed with Deletion.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                newAssociatedParts.remove(selectedAssociatedPart);
            }
        } else {
            alert.setTitle("No Product Part Selection Made.");
            alert.setHeaderText("Please Make a Selection for Deletion.");
            alert.show();

        }
    }

    /** This method allows for the parts table to be searched. note that the user needs to hit enter on a blank search bar
        @param event when Part ID or Part name in search bar and user presses enter, search Part table view.
        @return Part in Part table view.  */
    @FXML
    void OnActionSearchPartsTable(ActionEvent event) {
        String searchedPart = TableQueryTxt.getText();
        ObservableList<Part> partMatches = FXCollections.observableArrayList();
        String[] searchCharacters = searchedPart.split(" ");
        try {
        for (int i = 0; i < searchCharacters.length; i++) {
                String parsedCharacters = searchCharacters[i];
                ArrayList<Part> foundProdArray = (ArrayList<Part>) Inventory.lookupPartName(parsedCharacters);
                for (Part matchPart : foundProdArray) {
                    partMatches.add(matchPart);
                }
            }
            PartTableView.setItems(partMatches);

        if(partMatches.size() == 0){
            Part PartSearchID = Inventory.lookupPartID(Integer.parseInt(searchedPart));
            if(PartSearchID != null) {
                partMatches.add(PartSearchID);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No Match Found. Please Check Data Entered and Try Again. ");
                alert.showAndWait();
            }
            PartTableView.setItems(partMatches);
        }
        } catch (NumberFormatException formatException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Match Found. Please Check Data Entered and Try Again. ");
            alert.showAndWait();
        }
    }

    /** This method allows for the user to cancel data entry.
        @param event when cancel button is clicked.
        @return Promp user with confirmation, if OK clicked return to main form screen. */
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

    /** This method Saves the Product data. Prompts users with errors to fix data entry errors if applicable.
        @param event when Save button is clicked the Product data is saved to the products observable list.
        @return product data to Product table view on main form and display main form.  */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException {
        //save
        //exit to main form.

        try{
            Inventory.setProductIDCount();
            int productID = Inventory.getProductIDCount();
            String productName = ProductNameTxt.getText();
            int productStock = Integer.parseInt(ProductInv.getText());
            double productPrice = Double.parseDouble(ProductPrice.getText());
            int productMax = Integer.parseInt(ProductMax.getText());
            int productMin = Integer.parseInt(ProductMin.getText());

            //for loop to capture the associated parts
            if (Integer.parseInt(ProductMax.getText()) < Integer.parseInt(ProductMin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Please Fix Product Max or Product Min,");
                alert.setContentText("Product Max Cannot be Less Than Product Min. Product Min Cannot be More Than Product Max.");
                alert.showAndWait();
            }else if (Integer.parseInt(ProductMax.getText()) < Integer.parseInt(ProductInv.getText()) ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Please Fix Product Max or Product INV.");
                alert.setContentText("Product Max Can Not Be Less Than Product INV.");
                alert.showAndWait();
            }else if (Integer.parseInt(ProductInv.getText()) < Integer.parseInt(ProductMin.getText()) ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Please Fix Product Min or Product INV");
                alert.setContentText("Product INV Can Not Be Less Than Product Min.");
                alert.showAndWait();
            } else if (emptyTxt() == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Enter Missing Information.");
                alert.showAndWait();
            } else {
            Product newProduct = new Product(productID, productName, productPrice, productStock, productMax, productMin);
            for (Part associatedPart : newAssociatedParts) {
                newProduct.addAssociatedPart(associatedPart);

            }

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            Inventory.addProduct(newProduct);
            }
        }catch(NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Values");
            alert.setContentText("Please Input Valid Values in each TextField.");
            alert.showAndWait();
        }

    }
    /** This method checks if any text fields are empty.
        @return true if Text Fields are not empty. */
    public boolean emptyTxt() {
        if(this.ProductNameTxt.getText().isEmpty() || this.ProductNameTxt.getText() == null) {
            return false;
        } else if(this.ProductInv.getText().isEmpty() || this.ProductInv.getText() == null) {
            return false;
        } else if(this.ProductPrice.getText().isEmpty() || this.ProductPrice.getText() == null) {
            return false;
        } else if(this.ProductMax.getText().isEmpty() || this.ProductMax.getText() == null) {
            return false;
        } else if(this.ProductMin.getText().isEmpty() || this.ProductMin.getText() == null) {
            return false;
        }
        return true;
    }
    /** This method initializes the data to be loaded into the screen.
        @param resourceBundle list resources used to generate part table view and the product ID. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProductID.setText(String.valueOf(Inventory.getProductIDCount() + 1));
        ProductID.setEditable(false);
        PartTableView.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        InventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
