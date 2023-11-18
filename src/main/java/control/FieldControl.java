package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.InitialFarm.Chemical;
import org.InitialFarm.Crop;
import org.InitialFarm.dataManager;
import org.bson.types.ObjectId;
import org.entities.ChemicalRecord;
import org.entities.Field;
import org.entities.Year;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;


// need to see history of crop planted
// need to record what crop planted when finished planting
public class FieldControl {


    public ObservableList<Field> fieldList;

    private dataManager dataManager = new dataManager();



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
            Field field = new Field(null, field_id, field_name, field_size, field_location);
            Field dbField = dataManager.saveClass(field);
            fieldList.add(dbField);
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

    public void addCrop(String field_id, Crop crop, double seedingRate, LocalDate seedingDate){
        Field fieldSearched = null;
        for (Field field : fieldList){
            if (field.getID().equals(field_id)){
                fieldSearched = field;
                break;
            }
        }
        if (fieldSearched != null){
            if (fieldSearched.getCurrent_Year() == null){
                Year cropYear = new Year(null, LocalDate.now().getYear(), LocalDate.now());
                Year dbYear = new dataManager().saveClass(cropYear);
                cropYear.setCrop(crop);
                cropYear.setSeeding_rate(seedingRate);
                cropYear.setSeeding_date(seedingDate);
                fieldSearched.setCurrentYear(dbYear);
                fieldSearched.addYear(dbYear);
            }
            else {
                System.out.println("Farm is currently full of crop");
            }
        }
        else {
            System.out.println("Can't find field with ID (" + field_id + ")");
        }
    }

    public void harvest(String fieldID){
        for (Field field : fieldList){
            if (field.getID().equals(fieldID)){
                if (field.getCurrent_Year() != null) {
                    field.getCurrent_Year().harvest(LocalDate.now());
                    field.setCurrentYear(null);
                }
                else {
                    System.out.println("Field with ID (" + fieldID + ") is already harvested or no crop is planted.");
                }
            }
            else {
                System.out.println("Can't find field with ID (" + fieldID + ")");
            }
        }
    }
    public Crop makeCrop(ObjectId dbid,String cropType, String cropVariety, double bushelWeight){
        Crop baseCrop = new Crop(dbid, cropType, cropVariety, bushelWeight);
        Crop dbCrop = dataManager.saveClass(baseCrop);
        return dbCrop;

    }
    public void addChemical(String fieldID, double fertilizerRate, String chemicalSprayed, String chemicalGroup, LocalDate sprayingDate){
        for (Field field : fieldList){
            if (field.getID().equals(fieldID)){
                if (field.getCurrent_Year() != null) {
                    List<String> chemGroup = new ArrayList<>();
                    chemGroup.add(chemicalGroup);
                    Chemical chemical = new Chemical(null, chemicalSprayed, chemGroup);
                    Chemical dbChemical = dataManager.saveClass(chemical);
                    ChemicalRecord chemicalRecord = new ChemicalRecord(null, dbChemical, sprayingDate);
                    ChemicalRecord dbChemRec = dataManager.saveClass(chemicalRecord);

                    field.getCurrent_Year().setFertilizer_rate(fertilizerRate);
                    field.getCurrent_Year().addChemicalRecord(dbChemRec);
                }
                else {
                    System.out.println("Field with ID (" + fieldID + ") is already harvested or no crop is planted.");
                }
            }
            else {
                System.out.println("Can't find field with ID (" + fieldID + ")");
            }
        }
    }


}
