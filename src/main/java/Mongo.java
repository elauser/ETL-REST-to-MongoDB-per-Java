import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Mongo {
    static final MongoClient client = MongoClients.create(HiddenConstants.CONNECTION_STRING);
    static final MongoDatabase db = client.getDatabase("lol");
    static final MongoCollection<Document> lolMatches = db.getCollection("matches");
}
