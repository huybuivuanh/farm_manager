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
    public static dummy fetchobject(String classType, String classInfo1, String classInfo2) throws NoSuchFieldException {
        String dataBaseEntry = grab("FarmData", "farm_list", classType, classInfo1);
        Document doc = Document.parse(dataBaseEntry);
        //ObjectId id = new ObjectId(doc.getString("fieldName"), doc.get("_id", Document.class).getString("$oid"));
        return new dummy( 31, doc.getString("fieldName"), doc.get("_id", Document.class).getString("$oid"));
    }

    // method2:


    public static void main(String[] args) throws NoSuchFieldException {
        System.out.println(grab("FarmData","farm_list","fieldName","FieldGerald"));

          dummy test =  fetchobject("fieldName", "FieldGerald", "randominfo");
          System.out.println(test);

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
