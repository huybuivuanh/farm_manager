package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.InitialFarm.Crop;
import org.InitialFarm.GrainBin;
import org.InitialFarm.dataManager;
import org.bson.types.ObjectId;

public class BinControl {
    /**
     * list of Bins
     */
    public ObservableList<GrainBin> binList;

    public ObservableList<String> cropType;

    private dataManager dataManager = new dataManager();



    /**
     * constructor
     */
    public BinControl(){
        binList = FXCollections.observableArrayList();
        cropType = FXCollections.observableArrayList();
    }

    public void addBin(String bin_id, String bin_name, int bin_size, String bin_location, boolean hopper, boolean fan)
    {
        //check if Bin already exists
        boolean binExists= false;


        for (GrainBin bin : binList) {
            if (bin.getDbId().equals(bin_id)) {
                binExists = true;
                break;
            }
        }
        // if it doesn't, add it. If it does, report it.
        if (!binExists){
            GrainBin Bin = new GrainBin(null, bin_name, bin_location, bin_size, hopper, fan);
           GrainBin dbBin = dataManager.saveClass(Bin);

            binList.add(dbBin);
        }
        else {
            System.out.println("There already is a Bin with the desired ID");
        }
    }

    public void deleteBin(ObjectId bin_id){
        GrainBin deleted = null;
        for (GrainBin bin : binList){
            if (bin.getDbId().equals(bin_id)){
                deleted = bin;
                break;
            }
        }
        if (deleted != null){
            dataManager.removeClass(deleted);
            binList.remove(deleted);

        }
        else {
            System.out.println("Bin with ID (" + bin_id + ") does not exist");
        }
    }

    public void addCrop(ObjectId bin_id, Crop cropType, int grain, boolean inputBushels, boolean clean, boolean tough){
        GrainBin binSearched = null;
        for (GrainBin bin : binList){
            if (bin.getDbId().equals(bin_id)){
                binSearched = bin;
                break;
            }
        }
        if (binSearched != null){
            binSearched.addCrop(cropType, grain, inputBushels, clean, tough);
        }
        else {
            System.out.println("Can't find bin with ID (" + bin_id + ")");
        }
    }

    public void clearBin(ObjectId bin_id) {
        GrainBin binSearched = null;
        for (GrainBin bin : binList) {
            if (bin.getDbId().equals(bin_id)) {
                binSearched = bin;
                break;
            }
        }
        if (binSearched != null){
            binSearched.clearBin();
            binSearched = dataManager.updateClass(binSearched);

        }
        else{
            System.out.println("Cant find bin with ID (" + bin_id + ")");
        }
    }

    /**
     * Makes a new Crop initalized in database.
     * @param dbid
     * @param cropType
     * @param cropVariety
     * @param bushelWeight
     * @return
     */
    public Crop makeCrop(ObjectId dbid,String cropType, String cropVariety, double bushelWeight){
        Crop baseCrop = new Crop(dbid, cropType, cropVariety, bushelWeight);
        Crop dbCrop = dataManager.saveClass(baseCrop);
        return dbCrop;

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
}
