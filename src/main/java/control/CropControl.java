package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.InitialFarm.Crop;

import java.util.ArrayList;

public class CropControl {
    public ObservableList<Crop> cropList;
    public ObservableList<Crop> currentCrop;
    public ObservableList<Crop> lastCrop;

    public ObservableList<String> cropVariety;

    public CropControl(){
        cropList = FXCollections.observableArrayList();
        currentCrop = FXCollections.observableArrayList();
        lastCrop = FXCollections.observableArrayList();
        cropVariety = FXCollections.observableArrayList();
    }

    public void addCrop(Crop crop){
        cropList.add(crop);
    }

    public void setCurrentCrop(String crop_id){
        for (Crop crop : cropList){
            if (crop.getID().equals(crop_id)){
                currentCrop.add(crop);
                break;
            }
        }
    }
    public void setLastCrop(String crop_id){
        for (Crop crop : cropList){
            if (crop.getID().equals(crop_id)){
                lastCrop.add(crop);
                break;
            }
        }
    }

    public void addCropVariety(String crop_variety){
        boolean existed = false;
        for (String variety : cropVariety){
            if (variety.equals(crop_variety)){
                existed = true;
                break;
            }
        }
        if (!existed){
            System.out.println("not existed");
            cropVariety.add(crop_variety);
        } else {
            System.out.println("Crop variety already existed");
        }
    }
}
