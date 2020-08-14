import org.bson.Document;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.*;

public class ETL implements Runnable {
    ExecutorService exec = Executors.newCachedThreadPool();

    Queue<String> extractableSummonerIds = new ConcurrentLinkedQueue<String>();
    Queue<String> extractableMatchIds = new ConcurrentLinkedQueue<String>();

    Queue<Document> matchListStaging = new ConcurrentLinkedQueue<Document>();
    Queue<Document> matchesStaging = new ConcurrentLinkedQueue<Document>();

    Queue<Document> matchesCore = new ConcurrentLinkedQueue<Document>();

    String region;

    public static void main(String[] args) {
        new ETL("EUW1").initialize();
    }

    public void initialize() {
        try {
            URL url = RiotUrls.getMatchHistory("euw1", HiddenConstants.ACCOUNT_ID, 100);
            new Extractor(matchListStaging, url).run();
            System.out.println(matchListStaging.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        run();
    }

    public ETL(String region) {
        this.region = region;
    }

    public void run() {
        exec.submit(new MatchHistoryTransformer(matchListStaging, extractableMatchIds));
        exec.submit(new SummonerTransformer(matchesStaging, extractableSummonerIds, matchesCore));
        exec.submit(new Loader(matchesCore));
        int repetitions = REQUEST_LIMIT.PER_MINUTE.getValue() / REQUEST_LIMIT.PER_SECOND.getValue();
        long minuteTimer;
        long secondTimer;

        //ETL loop which respects the max Calls/Time set by the Riot API
        while (true) {
            minuteTimer = System.currentTimeMillis();
            for (int i = 0; i < repetitions; i++) {
                secondTimer = System.currentTimeMillis();
                for (int j = 0; j < REQUEST_LIMIT.PER_SECOND.getValue(); j++) {
                    doLimitedRequest();
                }
                waitUntil(secondTimer + 1000);
            }
            waitUntil(minuteTimer + 60000);
        }
    }

    private void doLimitedRequest() {
        while(extractableSummonerIds.isEmpty() && extractableMatchIds.isEmpty()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!extractableMatchIds.isEmpty()) {
            System.out.println("Extract a Match");
            extractMatch();
        } else {
            System.out.println("Extract new Match Ids");
            extractMatchIds();
        }
    }

    private void extractMatch() {
        try {
            URL requestURL = RiotUrls.getMatch(region, extractableMatchIds.poll());
            exec.submit(new Extractor(matchesStaging, requestURL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void extractMatchIds() {
        try {
            URL requestURL = RiotUrls.getMatchHistory(region, extractableSummonerIds.poll(), 20);
            exec.submit(new Extractor(matchListStaging, requestURL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void waitUntil(long targetTime) {
        try {
            Thread.sleep(targetTime - System.currentTimeMillis());
        } catch (IllegalArgumentException e) {
        } catch (InterruptedException e) {
            System.out.println("Sleep got interrupted: " + e.getMessage());
        }
    }

}
