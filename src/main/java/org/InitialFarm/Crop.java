package org.InitialFarm;

import org.bson.types.ObjectId;

public class Crop {
    private String cropType;
    private String cropVariety;
    private float bushelWeight;
    private ObjectId cropID;



    public Crop(String cropType, String cropVariety, float bushelWeight){
        this.cropType = cropType;
        this.cropVariety = cropVariety;
        this.bushelWeight = bushelWeight;
        //TODO cropID
    }

    public String getCropType(){ return this.cropType;}
    public void  setCropType(String newCroptype){
        this.cropType = newCroptype;
    }

    public String getCropVariety(){ return this.cropVariety;}
    public void  setCropVariety(String newCropVariety){
        this.cropVariety = newCropVariety;
    }

    public float getBushelWeight(){ return this.bushelWeight;}
    public void  setBushelWeight(float newBushelWeight){
        this.bushelWeight = newBushelWeight;
    }

    public ObjectId getCropID(){
        return this.cropID;
    }

}
