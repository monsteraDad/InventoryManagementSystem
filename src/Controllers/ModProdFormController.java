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

/** This Class controls the modify product form. */
public class ModProdFormController implements Initializable {

    @FXML
    private TextField ModProdNameTxt;
    @FXML
    private TextField ModProdInvTxt;
    @FXML
    private TextField ModProdPriceTxt;
    @FXML
    private TextField ModProdMaxTxt;
    @FXML
    private TextField ModProdMinTxt;
    @FXML
    private TextField ModProdIdTxt;
    @FXML
    private TextField PartTableQuery;

    /** This method tells the controller where to get the data for the part table view. */
    @FXML
    private TableView<Part> PartTableView;
    @FXML
    private TableColumn<Part , Integer> PartIdCol;
    @FXML
    private TableColumn<Part, String> PartNameCol;
    @FXML
    private TableColumn<Part, Integer> PartInventoryLevelCol;
    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    /** This method allows for the controller to know where to find the data for the product part table view. */
    @FXML
    private TableView<Part> ProductPartTableView;
    @FXML
    private TableColumn<Product, Integer> ProdPartIDCol;
    @FXML
    private TableColumn<Product, String> ProdPartNameCol;
    @FXML
    private TableColumn<Product, Integer> ProdPartInventoryLevelTxt;
    @FXML
    private TableColumn<Product, Double> ProdPartPriceCol;

    private ObservableList<Part> newAssociatedParts = FXCollections.observableArrayList();

    private int productIndex;
    Stage stage;
    Parent scene;

    /** This method saves part data to associated part table view.
        @param event  Add button clicked.
        @return add selected part to the associated parts table. */
    @FXML
    void OnActionAddPart(ActionEvent event) {
        Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();
       if (selectedPart != null) {
           newAssociatedParts.add(selectedPart);
           ProductPartTableView.setItems(newAssociatedParts);
           ProdPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
           ProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
           ProdPartInventoryLevelTxt.setCellValueFactory(new PropertyValueFactory<>("stock"));
           ProdPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       } else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText("Part Not Selected");
           alert.setContentText("Please Select a Part to Add. ");
       }
    }

    /** This method allows for the deletion of parts from the associated parts table view.
        @param event remove associated part button clicked.
        @return Prompt user with confirmation, if OK clicked, delete selected item.
        @retrun If user did not select item prompt user with Error Alert. */
    @FXML
    void OnActionDeleteProdPart(ActionEvent event) {
        Part selectedAssociatedPart = ProductPartTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (selectedAssociatedPart != null) {
            alert.setHeaderText("Would You Like to Delete Selected Product Part.");
            alert.setContentText("Select 'OK' to Proceed with Deletion.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                newAssociatedParts.remove(selectedAssociatedPart);
                ProductPartTableView.refresh();
            }
        } else {
            alert.setTitle("No Product Part Selection Made.");
            alert.setHeaderText("Please Make a Selection for Deletion.");
            alert.show();

        }

    }

    /** This allows the user to save the changes they made and exit to the main form.
        @param event save button clicked.
        @return updated product to the product observable list and display main form.
        prompt user with error window if any data entry errors occur. */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException {
            try {
                int productID = Integer.parseInt(ModProdIdTxt.getText());
                String productName = ModProdNameTxt.getText();
                int productStock = Integer.parseInt(ModProdInvTxt.getText());
                double productPrice = Double.parseDouble(ModProdPriceTxt.getText());
                int productMax = Integer.parseInt(ModProdMaxTxt.getText());
                int productMin = Integer.parseInt(ModProdMinTxt.getText());
                if (Integer.parseInt(ModProdMaxTxt.getText()) < Integer.parseInt(ModProdMinTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Product Max Can Not Be Less Than Product Min.");
                    alert.showAndWait();
                } else if (Integer.parseInt(ModProdMaxTxt.getText()) < Integer.parseInt(ModProdInvTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Product Max Can Not be Less Than Product INV.");
                    alert.showAndWait();
                } else if (Integer.parseInt(ModProdInvTxt.getText()) < Integer.parseInt(ModProdMinTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Product INV Can Not Be Less Than Product Min.");
                    alert.showAndWait();
                } else if (emptyTxt() == false) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please Enter Missing Information.");
                    alert.showAndWait();
                }else {
                    Product Product = new Product(productID, productName, (int) productPrice, productStock, productMax, productMin);
                    for (Part associatedPart : newAssociatedParts) {
                        Product.addAssociatedPart(associatedPart);
                    }
                    Inventory.updateProduct(this.productIndex, Product);
                    stage = (Stage)((Button) event.getSource()).getScene().getWindow();
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

/** This method checks if TextFields are empty.
    @return true if no TextFields are empty. */
public boolean emptyTxt() {
    if(this.ModProdNameTxt.getText().isEmpty() || this.ModProdNameTxt.getText() == null) {
        return false;
    } else if(this.ModProdInvTxt.getText().isEmpty() || this.ModProdInvTxt.getText() == null) {
        return false;
    } else if(this.ModProdPriceTxt.getText().isEmpty() || this.ModProdPriceTxt.getText() == null) {
        return false;
    } else if(this.ModProdMaxTxt.getText().isEmpty() || this.ModProdMaxTxt.getText() == null) {
        return false;
    } else if(this.ModProdMaxTxt.getText().isEmpty() || this.ModProdMaxTxt.getText() == null) {
        return false;
    }
    return true;
}
    /** This method allows the user to search part table view.
        @param event when user hits enter with text in the field.
        @return part from the search of the part table view by part ID or part Name.*/
    @FXML
    void OnActionSearchPartTable(ActionEvent event) {
        String searchedPart = PartTableQuery.getText();
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
            if (partMatches.size() == 0) {
                Part PartSearchID = Inventory.lookupPartID(Integer.parseInt(searchedPart));
                if (PartSearchID != null) {
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

    /** This method allows the user to cancel data entry.
        @param event cancel button clicked.
        @return confirmation prompt, if OK clicked, delete selected part. */
    @FXML
    void OnActionToMainForm(ActionEvent event) throws IOException {
        //promp user with warning
        //exit to main page
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

    /** This method is for sending products data from the main form products table view.
        @param Product used to get the product to be modified. */
    public void sendProduct(Product Product){
        this.productIndex = Inventory.getAllProducts().indexOf(Product);
        ModProdIdTxt.setText(String.valueOf(Product.getProductID()));
        ModProdNameTxt.setText(Product.getProductName());
        ModProdInvTxt.setText(String.valueOf(Product.getProductStock()));
        ModProdPriceTxt.setText(String.valueOf(Product.getProductPrice()));
        ModProdMaxTxt.setText(String.valueOf(Product.getProductMax()));
        ModProdMinTxt.setText(String.valueOf(Product.getProductMin()));
        /** I had a logical error of leaving the code bellow in the initialize method of this controller.
            However this piece of code needs to be sent from the MainFormController to be loaded into the appropriate
            associated part table view in the Modify Parts screen. To fix this issue, I simply moved it here, and the error was resolved. */
        newAssociatedParts.addAll(Product.getAssociatedParts());
        ProductPartTableView.setItems(Product.getAssociatedParts());
    }

    /** This method initializes the screen with the data that was selected from the Products table view in main form.
        @param resourceBundle resources to load data into Part Tableview and Product text fields. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize the all parts table view.
        PartTableView.setItems(Inventory.getAllParts());
        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProdPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProdPartInventoryLevelTxt.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProdPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
