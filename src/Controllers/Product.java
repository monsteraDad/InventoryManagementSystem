package Controllers;

import Controllers.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class contains the product constructor. */
public class Product {
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int productMax;
    private int productMin;

    //Product Constructor
    public Product(int productID, String productName, double productPrice, int productStock, int productMax, int productMin) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productMax = productMax;
        this.productMin = productMin;
    }


    /** @return the product ID */
    public int getProductID() {
        return productID;
    }

    /** @param productID the product ID to set. */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /** @return the product name */
    public String getProductName() {
        return productName;
    }

    /** @param productName the product name to set. */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /** @return the product price */
    public double getProductPrice() {
        return productPrice;
    }

    /** @param productPrice the product price to set. */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /** @return the product inventory */
    public int getProductStock() {
        return productStock;
    }

    /** @param productStock the product inventory to set. */
    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    /** @return the product max */
    public int getProductMax() {
        return productMax;
    }

    /** @param productMax the product max to set. */
    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    /** @return the product min */
    public int getProductMin() {
        return productMin;
    }

    /** @param productMin  the product min to set. */
    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    /** @param newPart the new part to add to associated parts. */
    public void addAssociatedPart(Part newPart) {
        associatedParts.add(newPart);
    }

    /** @param selectedAssociatedPart selected associated part to remove.
        @return true */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;
        }
    }

    /** @return the associated product parts */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

}

