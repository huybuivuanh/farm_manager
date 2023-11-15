package org.entities;

import org.InitialFarm.Chemical;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public class ChemicalRecord implements DatabaseInterface<ChemicalRecord> {
    Chemical chemical;
    LocalDate date;

    ChemicalRecord(Chemical _chemical, LocalDate _date){
        chemical = _chemical;
        date = _date;
    }
    Chemical getChemical(){ return this.chemical; }
    public LocalDate getDate(){ return this.date; }

    @Override
    public Document classToDoc() {
        return null;
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
        return null;
    }

    @Override
    public boolean isDatabase() {
        return false;
    }
}
