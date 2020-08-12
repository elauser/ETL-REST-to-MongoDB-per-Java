import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Mongo {

    static final MongoClient mongoClient = MongoClients.create(HiddenConstants.CONNECTION_STRING);
    static final MongoDatabase database = mongoClient.getDatabase("lol");

}
