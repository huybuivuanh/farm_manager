package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.entities.Field;


public class FieldControl {

    /**
     * list of Fields
     */
    public ObservableList<Field> fieldList;

    /**
     * constructor
     */
    public FieldControl(){
        fieldList = FXCollections.observableArrayList();
    }

    public void addField(String field_id, String field_name, double field_size, String field_location)
    {
        //check if Field already exists
        boolean fieldExists= false;


        for (Field Field : fieldList) {
            if (Field.getID().equals(field_id)) {
                fieldExists = true;
            }
        }
        // if it doesn't, add it. If it does, report it.
        if (!fieldExists){
            Field field = new Field(field_id, field_name, field_size, field_location);
            fieldList.add(field);
        }
        else {
            System.out.println("There already is a field with the desired ID");
        }
    }

    public void editField(String old_id, String new_field_id, String new_field_name, double new_field_size, String new_field_location){
        Field edited = null;
        boolean fieldIdAlreadyExists = false;

        // check if Field to be edited exists at all
        for (Field field : fieldList) {
            if (field.getID().equals(old_id)) {
                edited = field;
            }
        }
        if (edited == null)
        {
            System.out.println("Field to be edited could not be found!");

        }
        // if it does
        else{
            // check if its new id is identical to another Field's id.
            for (Field field : fieldList) {
                if (field.getID().equals(new_field_id) && field != edited) {
                    fieldIdAlreadyExists=true;
                    break;
                }
            }
            if (!fieldIdAlreadyExists)
            {
                edited.setID(new_field_id);
                edited.setName(new_field_name);
                edited.setSize(new_field_size);
                edited.setLocation(new_field_location);
            }
            else{
                System.out.println("The suggested new Field ID is already in use.");
            }
        }
    }

    public void deleteField(String field_id){
        Field deleted = null;
        for (Field field : fieldList){
            if (field.getID().equals(field_id)){
                deleted = field;
                break;
            }
        }
        if (deleted != null){
            fieldList.remove(deleted);
        }
        else {
            System.out.println("Field with ID (" + field_id + ") does not exist");
        }
    }

    public String viewField (String id)
    {
        Field viewed = null;
        String returned = null;

        for (Field field : fieldList) {
            if (field.getID().equals(id)) {
                viewed = field;
            }
        }

        if (viewed == null)
        {
            System.out.println("Field to be viewed could not be found!");

        }
        else
        {
            returned = viewed.toString();
        }
        return returned;
    }

}
