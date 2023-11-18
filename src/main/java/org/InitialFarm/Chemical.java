package org.InitialFarm;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.entities.DatabaseInterface;

import java.util.List;

public class Chemical implements DatabaseInterface<Chemical> {
    private String chemicalName;
    private ObjectId dbID = null;
    private List<String> chemicalGroup;
    
    
    public Chemical (ObjectId dbid,String chemicalName, List<String> chemicalGroup){
        dbID = dbid;
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
        return dbID;
    }

    @Override
    public Document classToDoc() {

        Document newDoc = new Document();
        newDoc.append("chemicalName", this.chemicalName);
        newDoc.append("chemicalGroup", this.chemicalGroup);
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
}
