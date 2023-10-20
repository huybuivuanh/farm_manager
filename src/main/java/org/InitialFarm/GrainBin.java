package org.InitialFarm;

public class GrainBin {
    //dynamic
    private Crop currentCrop;
    private Crop lastCrop;
    private int cropBushels;
    private int cropLbs;
    private Boolean tough;// moisture
    private Boolean clean;

    //Static
    private String binName;
    private String binLocation;
    private int binSize;
    private Boolean hopper;
    private Boolean fan;


    public GrainBin(String binName, String binLocation, int binSize, boolean hopper, boolean fan){
        this.binName = binName;
        this.binLocation = binLocation;
        this.binSize = binSize;
        this.hopper = hopper;
        this.fan = fan;
    }




}
