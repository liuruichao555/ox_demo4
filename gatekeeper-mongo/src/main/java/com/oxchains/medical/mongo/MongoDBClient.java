package com.oxchains.medical.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by hugo on 3/11/17.
 */
public class MongoDBClient {
    private MongoClient mongoClient;

    public Datastore datastore;

    private DB db;

    private DBCollection coll;

    private static String MONGO_IP = "127.0.0.1";

    private static int MONGO_PORT = 27017;

    public static String DBName = "oxchain_medical";

    public static void init(String ip, int port){
        MongoDBClient.MONGO_IP = ip;
        MongoDBClient.MONGO_PORT = port;
    }

    private static class InnerHolder{
        private static MongoDBClient mongoDBClient = new MongoDBClient();
    }

    public static MongoDBClient getInstance(){
        return InnerHolder.mongoDBClient;
    }

    private MongoDBClient(){
        mongoClient = new MongoClient(MongoDBClient.MONGO_IP, MongoDBClient.MONGO_PORT);
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.hisign.facemanage.data.*");
        datastore = morphia.createDatastore(mongoClient, DBName);
        datastore.ensureIndexes();
        db = mongoClient.getDB(DBName);
    }
}
