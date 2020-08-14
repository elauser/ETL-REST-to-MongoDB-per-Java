import org.bson.Document;

import java.util.List;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SummonerTransformer implements Runnable {
    Queue<Document> matchesStaging = new ConcurrentLinkedQueue<Document>();
    Queue<String> extractableSummonerIds = new ConcurrentLinkedQueue<String>();
    Queue<Document> matchesCore = new ConcurrentLinkedQueue<Document>();

    TreeSet<String> oldSummoners = new TreeSet<>();

    public SummonerTransformer(Queue<Document> matchesStaging, Queue<String> extractableSummonerIds, Queue<Document> matchesCore) {
        this.extractableSummonerIds = extractableSummonerIds;
        this.matchesStaging = matchesStaging;
        this.matchesCore = matchesCore;
    }

    @Override
    public void run() {
        while(true){
            if(matchesStaging.isEmpty()){
                waitFor(5000);
            } else {
                Document match = matchesStaging.poll();
                extractNewSummoners(match);
                matchesCore.add(match);
            }
        }
    }

    protected void extractNewSummoners(Document document){
        List<Document> participants = (List<Document>)document.get("participantIdentities");
        for(Document doc : participants){
            Document player = (Document)doc.get("player");
            String accountId = player.getString("accountId");
            if(!oldSummoners.contains(accountId)){
                extractableSummonerIds.add(accountId);
                oldSummoners.add(accountId);
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
