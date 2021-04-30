package Controllers;

/** This class contains the InHouse part constructor.*/
public class InHouse extends Part {

    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
    super(id, name, price, stock, min, max);
    this.machineID = machineID;
}

    /** @return the machine ID.*/
    public int getMachineID() {
        return machineID;
    }

    /** @param machineID the machine ID to set. */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
