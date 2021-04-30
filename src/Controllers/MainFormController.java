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

/** This Class Controls the Main Form.
    The feature I would add as an update would be a password protected entry point.
    This would enable the business to grow to allow for specific roles of data entry, data storage, data analysis, and data management of the inventory.
 */
public class MainFormController implements Initializable {

    @FXML
    private TextField PartTableQueryTxt;
    @FXML
    private TableView<Part> PartTV;
    @FXML
    private TableColumn<Part, Integer> PartIDCol;
    @FXML
    private TableColumn<Part, String> PartNameCol;
    @FXML
    private TableColumn<Part, Integer> PartInventoryLevelCol;
    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    @FXML
    private TextField ProductTableQuery;
    @FXML
    private TableView<Product> ProductTV;
    @FXML
    private TableColumn<Product, Integer> ProdIdCol;
    @FXML
    private TableColumn<Product, String> ProdNameCol;
    @FXML
    private TableColumn<Product, Integer> ProdInventoryLevelCol;
    @FXML
    private TableColumn<Product, Double> ProdPriceCol;
    Stage stage;
    Parent scene;

    /**
     * This method allows for deletion of part from the part table view
     * @param event Delete button clicked.
     * @return Prompt user with confirmation, delete part. If no part selected tell user.
     */
    @FXML
    void OnActionDeletePart(ActionEvent event) {
        Part selectedPart = PartTV.getSelectionModel().getSelectedItem();
        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        if (selectedPart != null) {
            confirmDelete.setHeaderText("Would You Like to Delete Selected Part.");
            confirmDelete.setContentText("Select 'OK' to Proceed with Deletion.");
            Optional<ButtonType> result = confirmDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        } else {
            confirmDelete.setTitle("No Part Selection Made.");
            confirmDelete.setHeaderText("Please Make a Selection for Deletion.");
            confirmDelete.showAndWait();
        }
    }

    /**
     * This method allows for deletion of product from the product table.
     * @param event Delete button clicked
     * @return Prompt user with confirmation, delete product. If no product selected tell user.
     */
    @FXML
    void OnActionDeleteProd(ActionEvent event) throws IOException {
        Product selectedProduct = ProductTV.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (selectedProduct == null){
            alert.setTitle("No Product Selection Made.");
            alert.setHeaderText("Please Make a Selection for Deletion.");
            alert.show();
            } else if (!checkProduct() && selectedProduct !=null){
            alert.setHeaderText("Product Has Associated Parts.");
            alert.setContentText("Please delete Associated Parts Before Deletion.");
            alert.showAndWait();
        } else if (selectedProduct != null && checkProduct()) {
            alert.setHeaderText("Please Confirm!");
            alert.setContentText("Are You Sure You Wish To Delete Product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletedProduct(selectedProduct);
                ProductTV.refresh();
            }
        }
    }
    /** This method checks the Selected Product For Associated Parts.
        @return true if the Selected Products associatedParts list is empty. */
    public boolean checkProduct(){
        Product selectedProduct = ProductTV.getSelectionModel().getSelectedItem();
        if(selectedProduct.getAssociatedParts().isEmpty()) {
            return true;
        } else{
            return false;
        }
    }
    /**
     * This method switches screens to the add part form.
     * @param event add button clicked.
     * @return add part form.
     */
    @FXML
    void OnActionToAddPartForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method switches screens to the add product form.
     * @param event add button clicked.
     * @return add product screen.
     */
    @FXML
    void OnActionToAddProdForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProdForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method switches screens to the modify part form.
     * @param event Modify button clicked.
     * @return selected part to the modify part form.
     */
    @FXML
    void OnActionToModifyPartFrom(ActionEvent event) throws IOException {
       try {
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/view/ModPartForm.fxml"));
           loader.load();

           //send data from part table view in main form to modify text fields
           ModPartFormController MPartFController = loader.getController();
           MPartFController.sendPart(PartTV.getSelectionModel().getSelectedItem());

           stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
           Parent scene = loader.getRoot();
           stage.setScene(new Scene(scene));
           stage.show();
       } catch(NullPointerException exception) {
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setHeaderText("No Selection Made");
           alert.setContentText("Please Select an Item to Modify.");
           alert.showAndWait();
       }
    }


    /**
     * This Method switches screens to the modify product form.
     *
     * @param event Modify button clicked.
     * @return selected product to the modify product form.
     */
    @FXML
    void OnActionToModifyProdForm(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModProdForm.fxml"));
            loader.load();

            //send data to page from main form parts table view to mod part controller
            ModProdFormController MProdFController = loader.getController();
            MProdFController.sendProduct(ProductTV.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No Selection Made");
            alert.setContentText("Please select an Item to Modify.");
            alert.showAndWait();
        }

    }

    /**
     * This method implements a exit button to terminate the application.
     * @param event Exit button clicked.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * This method allows for the Parts table to be searched by part ID or part Name.
     * @param event when enter is pressed search part table view.
     * @return searched part or error message in the table view.
     */
    @FXML
    void onActionSearchPartTable(ActionEvent event) throws IOException {

        String searchedPart = PartTableQueryTxt.getText();
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
            if (partMatches.size() == 0) {
                Part PartSearchID = Inventory.lookupPartID(Integer.parseInt(searchedPart));
                if (PartSearchID != null) {
                    partMatches.add(PartSearchID);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("No Match Found. Please Check Data Entered and Try Again. ");
                    alert.showAndWait();
                }
                PartTV.setItems(partMatches);
            }

        } catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Match Found. Please Check Data Entered and Try Again. ");
            alert.showAndWait();
        }
        if (PartTableQueryTxt.getText().isEmpty()) {
            PartTV.refresh();
        }
        PartTV.setItems(partMatches);
    }

    /**
     * This method allows for searching the Product table view by product name or product ID.
     * @param event when enter is pressed search product table view.
     * @return searched product or error message in the table view.
     */
    @FXML
    void OnActionSearchProdTable(ActionEvent event) throws IOException {
        String searchedProd = ProductTableQuery.getText();
        ObservableList<Product> matches = FXCollections.observableArrayList();
        String[] searchCharacters = searchedProd.split(" ");
        try {
            for (int i = 0; i < searchCharacters.length; i++) {
                String parsedCharacters = searchCharacters[i];
                ArrayList<Product> foundProdArray = (ArrayList<Product>) Inventory.lookupProductName(parsedCharacters);
                for (Product matchProd : foundProdArray) {
                    matches.add(matchProd);
                }
            }
            ProductTV.setItems(matches);
            if (matches.size() == 0) {
                Product ProductSearchID = Inventory.lookupProductID(Integer.parseInt(searchedProd));
                if (ProductSearchID != null) {
                    matches.add(ProductSearchID);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("No Match Found. Please Check Data Entered and Try Again. ");
                    alert.showAndWait();
                }
                ProductTV.setItems(matches);
            }
        } catch (NumberFormatException formatException){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Match Found. Please Check Data Entered and Try Again. ");
            alert.showAndWait();
    }
}
    /** This method initializes data in the main form table views.
        @param resourceBundle places the parts and products in associated table views. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       //setting the Part Table view
        PartTV.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //creating the data to fill the part table view
        ProductTV.setItems(Inventory.getAllProducts());
        ProdIdCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        ProdNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        ProdInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("ProductStock"));
        ProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("ProductPrice"));
    }
}






