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
    private final LinkedList<Year> years;


    /**
     * Initializes a field with a name, size and location
     *
     * @param fName    the name of the animal being created
     * @param size     the alphanumeric ID of the animal being created
     * @param location the type of animal as a string
     */
    public Field(String ID, String fName, double size, String location)
    {
        this.ID = ID;
        this.name = fName;
        this.size = size;
        this.location = location;
        this.years = new LinkedList<>();
    }

    /**
     * Sets the current year
     * @param newYear The new year to be created
     * @param newYearDate The date the new year was created
     */
    public void newYear(int newYear, Date newYearDate){
        if ( this.current_Year != null ) {this.years.add(this.current_Year);}
        this.current_Year = new Year(newYear, newYearDate);
        this.years.add(this.current_Year);
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

    /**
     * Returns the list of years under a field
     * @return the list of years
     */
    public LinkedList<Year> getYears() {
        return this.years;
    }

    /**
     *  Returns all information about a field
     * @return all the field information
     */
    public String toString() {
        return "Name = " + name +
                "  ID = " + ID +
                "  Location = " + location +
                "  Size = " + size + " acres." +
                "  Years = " + years +
                '}';
    }

    /**
     *  Returns the information about a year
     * @param year the year in question
     * @return all the field information for the year
     */
    public String toString(Year year) {
        return  "Name = " + name +
                "  ID = " + ID +
                "  Location = " + location +
                "  Size = " + size + " acres." +
                "  Year = " + year.getYear() +
                "\n" + year + "\n";
    }

    public static void main(String[] args){

        /* For testing the Field class */

        String reason = "Constructor + getName()";
        String ID = "F123";
        String fName = "Field1";
        String location = "Northwest";
        double size = 123;

        // test all methods with this instance
        Field Field1 = new Field(ID ,fName, size, location);
        String result = Field1.getName();
        double result2, expected2;
        String expected = "Field1";

        if (!result.equals(expected))
        {
            System.out.println("Error: Expected: " + expected + " Obtained: " + result
                    + " (" + reason + ")");
        }

        reason = "Testing getName()";
        result = Field1.getName();
        expected = fName;
        if (!result.equals(expected))
        {
            System.out.println("Error: Expected: " + expected + " Obtained: " + result
                    + " (" + reason + ")");
        }

        reason = "Testing getID()";
        result = Field1.getID();
        expected = ID;
        if (!result.equals(expected))
        {
            System.out.println("Error: Expected: " + expected + " Obtained: " + result
                    + " (" + reason + ")");
        }

        reason = "Testing getSize()";
        result2 = Field1.getSize();
        expected2 = size;
        if (result2 != expected2)
        {
            System.out.println("Error: Expected: " + expected2 + " Obtained: " + result2
                    + " (" + reason + ")");
        }

        reason = "Testing getLocation()";
        result = Field1.getLocation();
        expected = location;
        if (!result.equals(expected))
        {
            System.out.println("Error: Expected: " + expected + " Obtained: " + result
                    + " (" + reason + ")");
        }

        reason = "Testing setName()";
        expected = "Field2";
        Field1.setName(expected);
        result = Field1.getName();
        if (!result.equals(expected))
        {
            System.out.println("Error: Expected: " + expected + " Obtained: " + result
                    + " (" + reason + ")");
        }

        reason = "Testing setSize()";
        expected2 = 233;
        Field1.setSize(expected2);
        result2 = Field1.getSize();
        if (result2 != expected2)
        {
            System.out.println("Error: Expected: " + expected2 + " Obtained: " + result2
                    + " (" + reason + ")");
        }

        reason = "Testing years list after constructor";
        int yearResult = Field1.years.size();
        if (yearResult != 0)
        {
            System.out.println("Error: Expected: 0, Obtained: " + yearResult
                    + " (" + reason + ")");
        }


        reason = "Testing getCurrent_year() with  newYear()";
        int year = 2013;
        Date new_date = new Date(2013, 8, 23);
        Field1.newYear(year, new_date);
        int test_year = Field1.getCurrent_Year().getYear();
        if (test_year != year)
        {
            System.out.println("Error: Expected: " +year+ ", Obtained: " + test_year
                    + " (" + reason + ")");
        }

        reason = "Testing years list after newYear()";
        yearResult = Field1.years.size();
        if (yearResult != 1)
        {
            System.out.println("Error: Expected: 1, Obtained: " + yearResult
                    + " (" + reason + ")");
        }

        reason = "Testing getYears()";
        yearResult = Field1.getCurrent_Year().getYear();
        if (yearResult != year)
        {
            System.out.println("Error: Expected: " + year + ", Obtained: " + yearResult
                    + " (" + reason + ")");
        }


        reason = "Testing toString(year)";
        result = Field1.toString(Field1.getCurrent_Year());
        expected ="""
                Name = Field2  ID = F123  Location = Northwest  Size = 233.0 acres.  Year = 2013
                Year{ year = 2013,
                 new year starting date = Tue Sep 23 00:00:00 CST 3913,
                 crop = null,
                 seeding_date = null,
                 seeding_rate = 0.0,
                 fertilizer_rate = 0.0,
                 spraying_date = null,
                 chemical_records = [],
                 task_records = [],
                 harvest_date = null,
                 end_of_year = null}
                """;
        if (!result.equals(expected))
        {
            System.out.println("Error: Expected: \n" + expected + " Obtained: \n" + result
                    + " (" + reason + ")");
        }

        reason = "Testing toString()";
        result = Field1.toString();
        expected = """
                Name = Field2  ID = F123  Location = Northwest  Size = 233.0 acres.  Years = [Year{ year = 2013,
                 new year starting date = Tue Sep 23 00:00:00 CST 3913,
                 crop = null,
                 seeding_date = null,
                 seeding_rate = 0.0,
                 fertilizer_rate = 0.0,
                 spraying_date = null,
                 chemical_records = [],
                 task_records = [],
                 harvest_date = null,
                 end_of_year = null}]}""";
        if (!result.equals(expected))
        {
            System.out.println("Error: Expected: \n" + expected + " Obtained: \n" + result
                    + " (" + reason + ")");
        }

        System.out.println("*** Testing Complete ***");

    }
}
