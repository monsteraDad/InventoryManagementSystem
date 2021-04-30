package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/** This Class contains the Observable List Arrays for Parts and products. */
public class Inventory {

//create observable lists for parts and products
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partIDCount = 0;
    private static int ProductIDCount = 0;

    /** @param newPart newPart to add to allParts observable array.*/
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /** @param newProduct  newProduct to add to the allProducts observable array. */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** This method looks up a part by its part ID.
        @param partID PartID to match.
        @return part */
    public static Part lookupPartID(Integer partID){
        for (Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /** This Method looks up a part by its partial or whole name.
        @param partName part name to match.
        @return found part names */
    public static Object lookupPartName(String partName){
        ArrayList<Part> foundPartNames = new ArrayList<>();
        for (int i=0; i<allParts.size(); i++) {
            if (allParts.get(i).getName().contains(partName)){
                foundPartNames.add(allParts.get(i));
            }
        }
        return foundPartNames;
    }

    /** This method looks up a product by its part ID.
     @param productID ProductID to  match.
     @return product */
    public static Product lookupProductID(Integer productID){
       for (int i=0; i<allProducts.size(); i++) {
           if (allProducts.get(i).getProductID() == productID) {
               return allProducts.get(i);
           }
       }
       return null;
    }

    /** This method looks up a product by its partial or whole name.
        @param productName to match.
        @return found product names. */
   //look up product from product name
    public static Object lookupProductName(String productName) {
        ArrayList<Product> foundProductNames = new ArrayList<>();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductName().contains(productName)) {
                foundProductNames.add(allProducts.get(i));
            }
        }
        return foundProductNames;
    }

    /** @param selectedProduct  the product to update.
        @param index  the placement of the product in the allProduct array.*/
     public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
     }

     /** @param selectedPart the part to update.
        @param index the placement of the part in the allParts array. */
     public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /** @param selectedPart the part to delete.
        @return true */
    public static boolean deletePart(Part selectedPart){
       if(allParts.contains(selectedPart)) {
           allParts.remove(selectedPart);
           return true;
       }else {
           return false;
       }
    }

    /** @param selectedProduct  the product to delete.
        @return  true */
    public static boolean deletedProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /** @return all the parts. */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** @return all the products. */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** @return part ID count .  */
    public static int getPartIDCount() {
        return partIDCount;
    }
    /** @return  the Part ID count plus one. */
    public static int setPartIDCount() {
       return partIDCount ++;
    }

    /** @return the product id count */
    public static int getProductIDCount() {
        return ProductIDCount;
    }

    /** this method sets Product count plus one.  */
    public static void setProductIDCount() {
        ProductIDCount ++;
    }

    }




