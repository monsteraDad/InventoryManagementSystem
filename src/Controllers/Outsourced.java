package Controllers;

/** This class extends the Part class and contains the Outsourced Part constructor. */
public class Outsourced extends Part {
//declare variable
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** @return the company name. */
    public String getCompanyName() {
        return companyName;
    }

    /** @param  companyName the company name to set. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
