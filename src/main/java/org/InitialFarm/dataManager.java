package org.InitialFarm;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

import static org.InitialFarm.DataFetch.*;

/**
 *   data Manager is supposed to:
 *   1) grab instance of object from database for other classes to work with
 *      a) use the data fetch to get data from the database
 *      b) create an object of a  class matching the type of data that got pulled
 *      c) return that object for other classes to work on/with
 *   2) sync changes in fields of object that got changed in database
 */
public class dataManager {

    /**
     * Used to fetch object by:
     * 1) grabbing object details => parse through;
     * 2) build it using constructor;
     * 3) return it;
     */
    public static dummy fetchObject(String classType, String classInfo1, String classInfo2) throws NoSuchFieldException {
        Document doc=  grab("FarmData", "farm_list", classType, classInfo1);
        //ObjectId id = new ObjectId(doc.getString("fieldName"), doc.get("_id", Document.class).getString("$oid"));

        // the id is a special case in mongodb and needs to be put into an ObjectId, it cant be cast to string right away
        ObjectId id = doc.getObjectId("_id");
        return new dummy( 31, doc.getString("fieldName"), ""+ id);
    }

    // method2:

    /**
    * Translates an object into a JSON Document representation of itself.
    */
    public static Document translateToDoc ( dummy dum)
    {
        Document newDoc = new Document();
        int dumsInt = dum.dummyHeight;
        String dumsName = dum.dummyName;
        String dumsId= dum.dummyId;

        newDoc.append("fieldName", dumsName);
        newDoc.append("acres", dumsInt);
        Date added = new Date();
        newDoc.append("Date Added:",added.getTime());
        newDoc.append("fieldid", dumsId);
        return newDoc;
    }

    // method2:
//    public static void sync (dummy dum){
//
//    }
//


    public static void main(String[] args) throws NoSuchFieldException {
        System.out.println(grab("FarmData","farm_list","fieldName","FieldGerald"));

          dummy test =  fetchObject("fieldName", "FieldGerald", "randominfo");
          System.out.println(test);

          System.out.println(translateToDoc(test));

//        Document newDoc = new Document();
//        newDoc.append("fieldName", "kamal's Field");
//        newDoc.append("acres",51);
//        Date added = new Date();
//        newDoc.append("Date Added:",added.getTime());
//
//        insertDoc(newDoc,"FarmData","farm_list");
//
//        addCollection("FarmData","farm_bins");
//
//        System.out.println(getCollectionList("FarmData"));
//        System.out.println(getDatabaseList());
    }
}
