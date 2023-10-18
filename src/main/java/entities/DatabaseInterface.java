package entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public interface DatabaseInterface {
    public Document classToDoc();


    public Document docToClass();

    public void save();

    public void sync();

    public ObjectId getID();




    public boolean isDatabase();
}
