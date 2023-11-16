package org.entities;
/*
Class field that creates/instantiates a field object.
Every field has a unique ID, a name, size, and location.
Every field has a year that houses all the activities performed on the field that year
*/

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class Field implements DatabaseInterface<Field>
{

    /**
     * The unique ID of the field
     */
    private final String ID;

    /**
     * The unique ID of the field to link to the DataBase
     */
    private final ObjectId dbID = null;

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
    public void newYear(int newYear, LocalDate newYearDate){
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
     *  Returns the information about a year
     * @param year the year in question
     * @return all the field information for the year
     */
    public String toString(Year year) {
        return  "Name = " + name +
                "  ID = " + ID +
                "  Location = " + location +
                "  Size = " + size + " acres." +
                "\nYear = " +
                year + "\n";
    }

    /**
     * Translates an object into a JSON Document representation of itself.
     * @param field : a field object that is going to be translated into a doc.
     * @return : a document representation of the field object being passed.
     */
    @Override
    public Document classToDoc(Field field) {
        Document newDoc = new Document();

        //  ObjectId fieldId= field.fieldId; => this is for the database
        String field_id = field.getID();
        String field_name= field.getName();
        Double field_size= field.getSize() ;
        String field_location = field.getLocation();
        // not sure how to include year below


        // might need to add the objectID here still
        newDoc.append("_id", field_id);
        newDoc.append("field_name", field_name);
        newDoc.append("field_size", field_size);
        newDoc.append("field_location", field_location);
        //not sure how to include years below
        newDoc.append("field_years", field.years);

        Date added = new Date();
        newDoc.append("Date Added:",added.getTime());

        return newDoc;
        }

    /**
     * @return
     */
    @Override
    public Document docToClass() {
        return null;
    }

    /**
     *
     */
    @Override
    public void save() {

    }

    /**
     *
     */
    @Override
    public void sync() {

    }

    /**
     * @return
     */
    @Override
    public ObjectId getDbId() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isDatabase() {
        return false;
    }
}
