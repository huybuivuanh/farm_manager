package org.InitialFarm;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;


import java.nio.file.FileAlreadyExistsException;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class DataFetch {


    static String uri = "mongodb+srv://renegade70:zO2aNiJnxbRLLCwH@cluster0.3gqgbzx.mongodb.net/?retryWrites=true&w=majority";

    /**
     * grabs and return a string of values.
     * @param databaseName a string containing the database name you want to query
     * @param collectionFind  a string containing the actual field you want to query
     * @param fieldName the data you want to look for in string context
     * @param fieldValue the actual value you want to look far in a string
     * Make sure to look at mongodb for reference to the database and what you want to find.
     * @return
     */

    public static Document grab(String databaseName, String collectionFind, String fieldName, String fieldValue) throws NoSuchFieldException {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionFind);
            Document doc = collection.find(eq(fieldName, fieldValue)).first();

            if (doc != null) {
                mongoClient.close();
                return doc;
            } else {
                mongoClient.close();
                throw new NoSuchFieldException("no matching documents found.");
            }
        }
    }


    /**
     *
     * @param databaseName
     * @param collectionFind
     * @param newdata
     * @return
     */
    public static Document grabByID(String databaseName, String collectionFind,ObjectId newdata){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            BasicDBObject query = new BasicDBObject();
            query.put("_id", newdata);
            return database.getCollection(collectionFind).find(query).first();


        }
    }
    /**
     * Adds a document to the database
     * @param input A Document containing the input data you want to send to the database
     * @param databaseName a string containing the database name you wanty to add the document to
     * @param collection a string of the collection of the database you want to add to.
     */

    public static void insertDoc(Document input,String databaseName,String collection) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.getCollection(collection).insertOne(input);
            mongoClient.close();
            System.out.println("Document inserted to database successfully!");

        }
    }

    /**
     *
     * @param key
     * @param newdata
     * @param newId
     * @param databaseName
     * @param collections
     */
    public static void addID(String key, Object newdata, ObjectId newId,String databaseName,String collections){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            BasicDBObject query = new BasicDBObject();
            query.put("_id", newId);
            Document add = new Document();
            add.append(key,newdata);
            Document finaly = new Document();
            finaly.append("$set",add);

            database.getCollection(collections).findOneAndUpdate(query,finaly);
            mongoClient.close();
            System.out.println("Removed the database item successfully");


        }
    }

    /**
     *
     * @param docy a document containing ONLY key and values that you  want to modify
     * @param newId
     * @param databaseName
     * @param collections
     */
    public static void modifyID(Document docy, ObjectId newId,String databaseName,String collections){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            BasicDBObject query = new BasicDBObject();
            query.put("_id", newId);

            database.getCollection(collections).findOneAndReplace(query,docy);

            mongoClient.close();
            System.out.println("modified data succesfully :)");


        }
    }

    /**
     *
     * @param newId
     * @param databaseName
     * @param collections
     */
    public static void remove(ObjectId newId,String databaseName,String collections){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            BasicDBObject query = new BasicDBObject();
            query.put("_id", newId);
            database.getCollection(collections).findOneAndDelete(query);
            mongoClient.close();
            System.out.println("Removed the database item successfully");


        }
    }
    /**
     * Checks to see if a document is contained in a collection/database
     * @param input the document you want to check if it existys (Document)
     * @param databaseName the database you want to check (String)
     * @param collections the collection you want to check (String)
     * @return true or false depending on if the document exists (Boolean)
     */
    public static boolean exists(Document input,String databaseName,String collections){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            if (database.getCollection(collections).find(input).first() != null){
                return true;
            }

        }
        return false;
    }
    public static boolean existsID(ObjectId idcheck,String databaseName,String collections){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            BasicDBObject query = new BasicDBObject();
            query.put("_id", idcheck);

            if (database.getCollection(collections).find(query).first() != null){
                return true;
            }

        }
        return false;
    }
    /**
     *  Adds a collection to the database of your choice
     * @param databaseName a string of the database you want to add the collection to
     * @param collection the name of the collection you want to add
     */
    public static void addCollection(String databaseName, String collection){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.createCollection(collection);
            mongoClient.close();
            System.out.println("Collection added successfully.");

        }
    }

    /**
     * NOT FUNCTIONAL
     * @return List of all the databases
     */
    public static ListDatabasesIterable<Document> getDatabaseList(){
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            ListDatabasesIterable<Document> list = mongoClient.listDatabases();
            mongoClient.close();
            return list;

        }
    }

    /**
     * NOT FUNCTIONAL
     * @param databaseName String of the database you want to get all the collections of.
     * @return a list containing all the collections of style document.
     */
    public static ListCollectionsIterable<Document> getCollectionList(String databaseName){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            ListCollectionsIterable<Document> list = database.listCollections();
            mongoClient.close();

            return list;
        }

    }


    public static void main( String[] args ) throws NoSuchFieldException, FileAlreadyExistsException {
        // Replace the placeholder with your MongoDB deployment's connection string
        System.out.println(grab("FarmData","farm_list","fieldName","FieldGerald"));

        Document newDoc = new Document();
        newDoc.append("fieldName", "Theo's Field");
        newDoc.append("acres",57);
        Date added = new Date();
        newDoc.append("Date Added:",added.getTime());

        Document newdocky = new Document();
        newdocky.append("big test","It is quite testy");
        insertDoc(newDoc,"FarmData","farm_list");
        System.out.println(exists(newdocky,"FarmData","farm_list"));


        ObjectId test = new ObjectId("652c63c018584b74f8bd31df");

        addID("fieldyNameyboi","This is a test for adding",test,"FarmData","farm_list");
        // remove(test,"FarmData","farm_list");


    }


}
