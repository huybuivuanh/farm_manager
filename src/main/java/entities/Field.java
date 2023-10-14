package entities;

import java.util.Date;
import java.util.LinkedList;

public class Field
{

    /**
     * The unique ID of the field
     */
    private final String ID;

    /**
     * The name of the field
     */
    private String name;

    /**
     * The location of the field
     */
    private final String location;

    /**
     * The size of the field in acres.
     */
    private double size;

    /**
     * The current year
     */
    private Year current_Year;

    /**
     * The information of previous years
     */
    private LinkedList<Year> years;


    /**
     * Initializes a field with a name, size and location
     *
     * @param fName    the name of the animal being created
     * @param size     the alphanumeric ID of the animal being created
     * @param location the type of animal as a string
     * @param years    the list of years
     */
    public Field(String ID, String fName, double size, String location, LinkedList<Year> years)
    {
        this.ID = ID;
        this.name = fName;
        this.size = size;
        this.location = location;
        this.years = new LinkedList<Year>();
    }

    /**
     * Sets the current year
     * @param newYear The new year to be created
     * @param newYearDate The date the new year was created
     */
    public void newYear (int newYear, Date newYearDate){
        this.years.add(this.current_Year);
        this.current_Year = new Year(newYear, newYearDate);
    }

    /**
     * Returns the current year
     * @return the current year
     */
    public Year getCurrent_Year(){ return this.current_Year; }

    /**
     * Returns the ID of the field
     * @return the field ID
     */
    public String getID() { return ID; }

    /**
     * Returns the field Name
     * @return the Name of the field
     */
    public String getName() { return name; }

    /**
     * Updates the name of the field
     * @param name the new name of the field
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the location of the field
     * @return the location of the field
     */
    public String getLocation() { return location; }

    /**
     * Returns the size of the field
     * @return the size of the field
     */
    public double getSize() {
        return size;
    }

    /**
     * Updates the size of the field
     * @param size the new size value to be updated
     */
    public void setSize(double size) {
        this.size = size;
    }
}
