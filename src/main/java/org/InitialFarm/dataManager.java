package org.InitialFarm;
import entities.DatabaseInterface;
import entities.Employee;
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


    // Initialize a class  Does this have an ID yes or no?
    // No it doesnt. Okay re-initalize this class inside the database. Pull out what the database Makes as a new class
    // construct of same and return back to the developer. They should set their class as this new class.
    // IF it does exist (ID is not null). THen we need update that ID position with whatever information it has.
    //

    public static DatabaseInterface saveClass(DatabaseInterface test){
        if (test.getID() == null){

        }

        return null;
    }
    public static Boolean adderMethod(DatabaseInterface test){

        if (test instanceof Employee){
            Document toDatabase = test.classToDoc();

        }
        return null;

    }
    public static dummy fetchObject(String classType, String classInfo1, String classInfo2) throws NoSuchFieldException {
        Document doc=  grab("FarmData", "farm_list", classType, classInfo1);
        //ObjectId id = new ObjectId(doc.getString("fieldName"), doc.get("_id", Document.class).getString("$oid"));

        // the id is a special case in mongodb and needs to be put into an ObjectId, it cant be cast to string right away
        ObjectId id = doc.getObjectId("_id");
        return new dummy( 31, doc.getString("fieldName"),  id);
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
        ObjectId dumsId= dum.dummyId;

        newDoc.append("_id", dumsId);
        newDoc.append("fieldName", dumsName);
        newDoc.append("acres", dumsInt);
        Date added = new Date();
        newDoc.append("Date Added:",added.getTime());

        return newDoc;
    }

    // method2:
    public static void sync (dummy dum){
        Document synced = translateToDoc(dum);
        ObjectId id = synced.getObjectId("_id");
        System.out.println("this is whats in synced: " + id);
        System.out.println( existsID(synced.getObjectId("_id"),"FarmData", "farm_list"));
        if (existsID(synced.getObjectId("_id"),"FarmData", "farm_list"))
        {
            //Document old = grabByID("FarmData", "farmer_list",  synced.getObjectId("_id"));
            modifyID(synced, synced.getObjectId("_id") ,"FarmData", "farm_list");
        }
        else {
            insertDoc(synced, "FarmData", "farm_list");
        }

    }



    public static void main(String[] args) throws NoSuchFieldException {
        System.out.println(grab("FarmData","farm_list","fieldName","FieldGerald"));

          dummy test =  fetchObject("fieldName", "FieldGerald", "randominfo");
          System.out.println(test);

          System.out.println(translateToDoc(test));
          sync(test);
//        ObjectId tester = new ObjectId("652c789b68062b52deac5427");
//
//
//        remove(tester,"FarmData","farm_list");



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
