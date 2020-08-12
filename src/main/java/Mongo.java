import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Mongo {

    static final MongoClient mongoClient = MongoClients.create(HiddenConstants.CONNECTION_STRING);
    static final MongoDatabase database = mongoClient.getDatabase("lol");

    public static void main(String[] args) {

    }



    /*
    static MongoClient mongoClient = MongoClients.create(HiddenConstants.CONNECTION_STRING);
    static MongoDatabase mongoDatabase = mongoClient.getDatabase("lol");
    */

}
