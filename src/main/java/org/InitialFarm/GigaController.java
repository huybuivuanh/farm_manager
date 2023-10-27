package org.InitialFarm;

import org.entities.Field;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class GigaController {


    ArrayList<Field> fieldList = new ArrayList<Field>();
    dataManager dbinitialize = new dataManager();

    public GigaController(){



    }

    public Field makeField(String id,String fieldname,double size,String location) {
        return new Field(id, fieldname, size, location);
    }


    public Field getField(ObjectId id){
        return null;

    }

}
