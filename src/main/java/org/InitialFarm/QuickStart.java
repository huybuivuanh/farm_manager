package org.InitialFarm;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import java.util.Iterator;


//Made by Theodore Buckley

// Default config file form mongoDB for a class (use a quickstart query to get data from the daatabase)
public class QuickStart {
    public static void main( String[] args ) {
        // Replace the placeholder with your MongoDB deployment's connection string

        // this is what can be used to connect to the server
        String uri = "mongodb+srv://renegade70:zO2aNiJnxbRLLCwH@cluster0.3gqgbzx.mongodb.net/?retryWrites=true&w=majority";

        // in our code, to use mongodb methods, need to create instances of object classes. First is the client.
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            // after getting the client, need to make a reference to the database. choose one database from within our cluster.
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");

            // out of that database we need to grab a collection of data so this would be stuff like movie names or whatever
            MongoCollection<Document> collection = database.getCollection("movies");

            // to find an entry (document ) within that collection
            // or do other stuff with it, you need to specifiy entry fieldname and value
            // kind of like a dictionary to access the proper info.
            Document doc = collection.find(eq("plot", "A woman, with the aid of her police officer sweetheart, endeavors to uncover the prostitution ring that has kidnapped her sister, and the philanthropist who secretly runs it.")).first();

            // then make sure that the document has actually been oppened meaning found.
            if (doc != null) {
                System.out.println(doc);

                // the below code prints out the entire content of the collection

//                FindIterable<Document> iterDoc = collection.find();
//                Iterator it = iterDoc.iterator();
//                while (it.hasNext()) {
//                    System.out.println(it.next());
//                }
            }
            else {
                System.out.println("No matching documents found.");
            }

        }

    }
}