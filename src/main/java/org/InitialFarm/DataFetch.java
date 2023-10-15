package org.InitialFarm;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.json.JsonObject;

import java.awt.*;
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

    public static String grab(String databaseName,String collectionFind,String fieldName,String fieldValue) throws NoSuchFieldException {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionFind);
            Document doc = collection.find(eq(fieldName, fieldValue)).first();
            if (doc != null) {
                mongoClient.close();
                return doc.toJson();
            } else {
                mongoClient.close();
                throw new NoSuchFieldException("no matching documents found.");
            }
        }
    }

    /**
     *
     * @param input
     * @param databaseName
     * @param collection
     */
    public static void insertDoc(Document input,String databaseName,String collection){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.getCollection(collection).insertOne(input);
            mongoClient.close();
            System.out.println("Document inserted to database successfully!");

        }
    }

    public static void addCollection(String databaseName, String collection){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.createCollection(collection);
            mongoClient.close();
            System.out.println("Collection added successfully.");

        }
    }

    public static ListDatabasesIterable<Document> getDatabaseList(){
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            ListDatabasesIterable<Document> list = mongoClient.listDatabases();
            mongoClient.close();
            return list;

        }
    }
    public static ListCollectionsIterable<Document> getCollectionList(String databaseName){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            ListCollectionsIterable<Document> list = database.listCollections();
            mongoClient.close();

            return list;
        }

    }


    public static void main( String[] args ) throws NoSuchFieldException {
        // Replace the placeholder with your MongoDB deployment's connection string
        System.out.println(grab("FarmData","farm_list","fieldName","FieldGerald"));

        Document newDoc = new Document();
        newDoc.append("fieldName", "Theo's Field");
        newDoc.append("acres",57);
        Date added = new Date();
        newDoc.append("Date Added:",added.getTime());

        insertDoc(newDoc,"FarmData","farm_list");

        addCollection("FarmData","farm_bins");

        System.out.println(getCollectionList("FarmData"));
        System.out.println(getDatabaseList());


    }


}
