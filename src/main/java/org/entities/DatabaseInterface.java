package org.entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public interface DatabaseInterface<T> {
    public Document classToDoc(T inter);

    public Document docToClass();



    public void save();

    public void sync();
    //changed below to getDbId bcz other classes have ID and getID methods was leading to class
     public ObjectId getDbId();


    public boolean isDatabase();
}
