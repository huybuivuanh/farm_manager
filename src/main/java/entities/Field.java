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

    public void newYear (int newYear, Date newYearDate){
        this.years.add(this.current_Year);
        this.current_Year = new Year(newYear, newYearDate);
    }


    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
