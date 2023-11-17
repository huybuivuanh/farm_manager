package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.InitialFarm.Crop;
import org.bson.types.ObjectId;


public class CropControl {
    public ObservableList<Crop> cropList;

    public ObservableList<String> cropType;

    public CropControl(){
        cropList = FXCollections.observableArrayList();
        cropType = FXCollections.observableArrayList();
    }

    // methods for crop scene in bin view
    public void addCrop(Crop crop){
        cropList.add(crop);
    }


    public void addCropType(String crop_type){
        boolean existed = false;
        for (String type : cropType){
            if (type.equals(crop_type)){
                existed = true;
                break;
            }
        }
        if (!existed){
            cropType.add(crop_type);
        } else {
            System.out.println("Crop type already existed");
        }
    }

    public void deleteCropWhenDeleteBin(ObjectId db_id){
        for (Crop crop : cropList) {
            if (db_id == crop.getDbId()) {
                cropList.remove(crop);
            }
        }
    }



    // methods for crop scene in field view

}
