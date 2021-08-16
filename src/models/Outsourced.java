package models;

import models.Part;

/**
 *
 * This class creates outsourced parts. This class allows users to create outsourced parts. It extends the parts class.
 * @author Jarred Harkness
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * This is the outsourced part constructor. This constructor allows users to create new outsourced parts in inventory.
     * @param id is the part ID number as an integer.
     * @param name is the part name as a string.
     * @param price is the part price as a double.
     * @param stock is the part stock as an integer.
     * @param min is the minimum stock level as an integer.
     * @param max is the maximum stock level as an integer.
     * @param companyName is the company name that provides the part as a string.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Company name setter. This setter allows the user to set the company name of a new or modified part.
     * @param companyName sets company name as a string.
     */
    public void setCompanyName(String companyName) { this.companyName = companyName;}

    /**
     * Company name getter. This method allows the user to get the company name of a part.
     * @return the company name as a string.
     */
    public String getCompanyName() {return companyName;}
}
