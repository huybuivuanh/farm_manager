package org.InitialFarm;

import org.bson.types.ObjectId;

public class GrainBin {
    //dynamic
    private Crop currentCrop;
    private Crop lastCrop;
    private int cropBushels;
    private int cropLbs;
    private boolean tough;// moisture
    private boolean clean;

    //Static
    private final String binName;
    private final String binLocation;
    private final int binSize;
    private final boolean hopper;
    private final boolean fan;
    private  ObjectId dbID;
    private final String ID;

    public GrainBin(String id, String binName, String binLocation, int binSize, boolean hopper, boolean fan){
        this.ID = id;
        this.binName = binName;
        this.binLocation = binLocation;
        this.binSize = binSize;
        this.hopper = hopper;
        this.fan = fan;
        //TODO binID
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
            this.cropBushels = 0;
            this.cropLbs = 0;
        }
    }
    public String getID(){return ID;}
    public ObjectId getDbID(){return dbID;}
    public Crop getLastCrop(){return lastCrop;}
    public Crop getCurrentCrop(){return currentCrop;}
    public int getCropLbs(){return  this.cropLbs;}
    public int getCropBushels(){return this.cropBushels;}
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

    private int lbsToBushels(int lbs){
        return (int)(lbs*currentCrop.getBushelWeight());
    }

    private int bushelsToLbs(int bushels){
        return (int)(bushels/currentCrop.getBushelWeight());
    }
}
