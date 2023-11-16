package org.InitialFarm;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.entities.DatabaseInterface;

public class GrainBin implements DatabaseInterface<GrainBin> {
    //dynamic
    private Crop currentCrop;
    private Crop lastCrop;
    private Double cropBushels = 0.0;
    private Double cropLbs = 0.0;
    private boolean tough;// moisture
    private boolean clean;

    //Static
    private final String binName;
    private final String binLocation;
    private final int binSize;
    private final boolean hopper;
    private final boolean fan;
    private ObjectId dbID;


    public GrainBin(ObjectId dbID,String binName, String binLocation, int binSize, boolean hopper, boolean fan){
        this.dbID = dbID;
        this.binName = binName;
        this.binLocation = binLocation;
        this.binSize = binSize;
        this.hopper = hopper;
        this.fan = fan;
        //TODO binID
    }

    public void setCurrentCrop(Crop crop){
        this.currentCrop = crop;
    }

    public void unloadBin(int grain, boolean isBushels){
        if(isBushels){
            this.cropBushels -= grain;
            this.cropLbs = bushelsToLbs(this.cropBushels);
        }else {
            this.cropLbs -= grain;
            this.cropBushels = lbsToBushels(this.cropLbs);
        }
        if (this.cropBushels <= 0){
            this.cropBushels = 0.0;
            this.cropLbs = 0.0;
        }
    }

    public Crop getLastCrop(){return lastCrop;}
    public Crop getCurrentCrop(){return currentCrop;}
    public Double getCropLbs(){return  this.cropLbs;}
    public Double getCropBushels(){return this.cropBushels;}
    public int getBinSize(){return binSize;}
    public String getBinLocation(){return binLocation;}
    public String getBinName(){ return binName;}
    public boolean isTough(){return this.tough;}
    public boolean isClean(){return this.clean;}
    public boolean isHopper(){ return hopper;}
    public boolean isFan(){ return fan; }

    public Boolean isEmpty(){
        if (cropBushels <= 0){
            return true;
        }else {
            return false;
        }
    }

    public void addCrop(Crop cropType, int grain, boolean inputBushels, boolean clean, boolean tough){
        if (isEmpty() && cropType != currentCrop){
            if (this.currentCrop != null) {
                this.lastCrop = this.currentCrop;
            }
            this.currentCrop = cropType;
        }else if (cropType != this.currentCrop){
            //TODO throw exception
        }

        if (inputBushels){
            fillBushels(grain);
        }else {
            fillLbs(grain);
        }
        this.clean = clean;
        this.tough = tough;
    }

    private void fillLbs( int lbs){
        if (cropBushels + lbsToBushels(lbs) > binSize){
            //TODO throw exception
        }
        this.cropLbs += lbs;
        this.cropBushels += lbsToBushels(lbs);
    }

    private void fillBushels(int bushels){
        if (cropBushels + bushels > binSize){
            //TODO throw exception
        }
        this.cropBushels += bushels;
        this.cropLbs += bushelsToLbs(bushels);
    }

    private Double lbsToBushels(double lbs){
        return (lbs*currentCrop.getBushelWeight());
    }

    private Double bushelsToLbs(double bushels){
        return (bushels/currentCrop.getBushelWeight());
    }

    @Override
    public Document classToDoc() {
        Document newDoc = new Document();
        newDoc.append("binName", this.binName);
        newDoc.append("binLocation", this.binLocation);
        newDoc.append("binSize", this.binSize);
        newDoc.append("hopper", this.hopper);
        newDoc.append("fan", this.fan);
        if (this.currentCrop != null){
            newDoc.append("currentCrop", this.currentCrop.getDbId());
        }
        else{
            newDoc.append("currentCrop", null);
        }
        if (this.lastCrop != null){
            newDoc.append("lastCrop", this.lastCrop.getDbId());
        }
        else{
            newDoc.append("lastCrop", null);
        }
        newDoc.append("cropBushels", this.cropBushels);
        newDoc.append("cropLbs", this.cropLbs);
        newDoc.append("tough", this.tough);
        newDoc.append("clean", this.clean);
        return newDoc;
    }

    @Override
    public Document docToClass() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public void sync() {

    }

    @Override
    public ObjectId getDbId() {
        return dbID;
    }

    @Override
    public boolean isDatabase() {
        return false;
    }

    public void setLastCrop(Crop crop) {
        this.lastCrop = crop;
    }

    public void setCropBushels(Double cropBushels) {
        this.cropBushels = cropBushels;
    }

    public void setCropLbs(Double cropLbs) {
        this.cropLbs = cropLbs;
    }

    public void setTough(Boolean tough) {
        this.tough = tough;
    }

    public void setClean(Boolean clean) {
        this.clean = clean;
    }
}
