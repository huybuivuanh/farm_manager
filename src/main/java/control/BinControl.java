package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.InitialFarm.Crop;
import org.InitialFarm.GrainBin;


public class BinControl {
    /**
     * list of Bins
     */
    public ObservableList<GrainBin> binList;



    /**
     * constructor
     */
    public BinControl(){
        binList = FXCollections.observableArrayList();
    }

    public void addBin(String bin_id, String bin_name, int bin_size, String bin_location, boolean hopper, boolean fan)
    {
        //check if Bin already exists
        boolean binExists= false;


        for (GrainBin bin : binList) {
            if (bin.getID().equals(bin_id)) {
                binExists = true;
                break;
            }
        }
        // if it doesn't, add it. If it does, report it.
        if (!binExists){
            GrainBin Bin = new GrainBin(bin_id, bin_name, bin_location, bin_size, hopper, fan);
            binList.add(Bin);
        }
        else {
            System.out.println("There already is a Bin with the desired ID");
        }
    }

    public void deleteBin(String bin_id){
        GrainBin deleted = null;
        for (GrainBin bin : binList){
            if (bin.getID().equals(bin_id)){
                deleted = bin;
                break;
            }
        }
        if (deleted != null){
            binList.remove(deleted);
        }
        else {
            System.out.println("Bin with ID (" + bin_id + ") does not exist");
        }
    }

    public void addCrop(String bin_id, Crop cropType, int grain, boolean inputBushels, boolean clean, boolean tough){
        GrainBin binSearched = null;
        for (GrainBin bin : binList){
            if (bin.getID().equals(bin_id)){
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

    public Crop getCrop(String bin_id){
        for (GrainBin bin : binList){
            if (bin_id.equals(bin.getID())){
                return bin.getCurrentCrop();
            }
        }
        return null;
    }

    public Crop getLastCrop(String bin_id){
        for (GrainBin bin : binList){
            if (bin_id.equals(bin.getID())){
                return bin.getLastCrop();
            }
        }
        return null;
    }
}
