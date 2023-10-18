package org.InitialFarm;

import org.bson.types.ObjectId;

import java.util.List;

public class Chemical {
    private String chemicalName;
    private ObjectId chemicalID;
    private List<String> chemicalGroup;
    
    
    public Chemical (String chemicalName, List<String> chemicalGroup){
        this.chemicalName = chemicalName;
        this.chemicalGroup = chemicalGroup;
        //TODO chemicalID
    }

    public String getChemicalName() {
        return this.chemicalName;
    }

    public List<String> getChemicalGroup(){
        return this.chemicalGroup;
    }

    public ObjectId getChemicalID() {
        return chemicalID;
    }
}
