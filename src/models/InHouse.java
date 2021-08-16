package models;


/**
 *
 * This class creates InHouse parts. This class includes the constructor getter and setting for in house parts.
 * @author Jarred Harkness
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * In house part constructor. This method is the public constructor for in house parts.
     * @param id is the part ID as an integer.
     * @param name is the part name as a string.
     * @param price is the part price as a double.
     * @param stock is the part stock as an integer.
     * @param min is the minimum inventory level as an integer.
     * @param max is the maximum inventory level as an integer
     * @param machineID is the machine ID number as an integer.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineId = machineID;
    }

    /**
     * Allows user to get machine ID. This method allows a user to get the machine ID for a part.
     * @return machine ID as an integer.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Allows user to set machine ID. This method allows a user to set the machine ID for a part.
     * @param machineId sets machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
