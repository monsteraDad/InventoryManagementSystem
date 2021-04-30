package model;

import Controllers.InHouse;
import Controllers.Inventory;
import Controllers.Outsourced;
import Controllers.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This class initializes the application.  */
public class InventorySystem extends Application {
/** This method initializes the Main Form. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
/** This is the main method. This method is the first called when the user runs the Program.
    sample data has been established in this method for testing purposes.
    @param args  input data to be loaded into the parts and products table. */
    public static void main(String[] args) {
        //adding sample data
        InHouse part1 = new InHouse(11, "Wheel", 15.00, 12, 10, 20, 15800);
        Outsourced part2 = new Outsourced(12, "Grips", 5.00, 6, 2, 3, "Grippie Boys");
        Product product1 = new Product(1000,"Bike", 155.50,5, 100, 180);
        Product product2 = new Product(1001,"Helmut", 155.50,5, 100, 180);


        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        launch(args);
    }
}
