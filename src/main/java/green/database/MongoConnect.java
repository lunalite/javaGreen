package green.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import green.config.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;


public final class MongoConnect {
    private static final Logger logger = Logger.getLogger(MongoConnect.class.getName());
    private static MongoClient mongoClient;

    public MongoConnect() {
    }

    public static void connect() {
        try {
            MongoClientURI uri = new MongoClientURI(Configuration.dbHost);
            mongoClient = new MongoClient(uri);
            logger.info("Connected to database at " + Configuration.dbHost);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.toString(), e);
        }
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }
}
