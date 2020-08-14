import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;


public class MatchHistoryTransformer implements Runnable {
    Queue<String> extractableMatchIds;
    Queue<Document> matchListStaging;

    TreeSet<String> usedMatchIds = new TreeSet<>();

    public MatchHistoryTransformer(Queue<Document> matchListStaging, Queue<String> extractableMatchIds) {
        this.extractableMatchIds = extractableMatchIds;
        this.matchListStaging = matchListStaging;
    }

    public void run() {
        Document document;

        while(true){
            while(matchListStaging.isEmpty()){
                waitFor(5000);
            }
            document = matchListStaging.poll();
            List<String> matchList = getMatchList(document);
            stageMatchList(matchList);
        }
    }

    protected List<String> getMatchList(Document document){
        List<String> dirtyMatchList = new ArrayList<String>();
        List<Document> matchList = (List<Document>)document.get("matches");
        for(Document doc : matchList){
            dirtyMatchList.add(Long.toString((Long)doc.get("gameId")));
        }
        return dirtyMatchList;
    }

    protected void stageMatchList(List<String> matchList){
        for(String matchId : matchList){
            if(!usedMatchIds.contains(matchId)) {
                extractableMatchIds.add(matchId);
                usedMatchIds.add(matchId);
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
