import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Loader implements Runnable {
    Queue<Document> matchesCore;

    public Loader(Queue<Document> matchesCore) {
        this.matchesCore = matchesCore;
    }

    public void run() {
        while(true){
            if(matchesCore.isEmpty()){
                waitFor(20000);
            } else {
                List<Document> matches = new ArrayList<>();
                while(!matchesCore.isEmpty()){
                    matches.add(matchesCore.poll());
                }
                Mongo.lolMatches.insertMany(matches);
                System.out.println("Matches were loaded");
            }
        }
    }

    private void waitFor(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
