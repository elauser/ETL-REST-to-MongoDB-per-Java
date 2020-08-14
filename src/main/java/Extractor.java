import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Extractor implements Runnable {
    URL url;
    Queue<Document> returnQueue;

    public Extractor(Queue<Document> returnQueue, URL url) {
        this.url = url;
        this.returnQueue = returnQueue;
    }

    public void run() {
        String jsonResponse = getJson(url);
        returnQueue.add(Document.parse(jsonResponse));
        System.out.println("Extracted a Document");
    }

    protected static String getJson(URL url){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readAll(br);
    }

    public static String readAll(BufferedReader br) {
        String line = "";
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
        return  sb.toString();
    }
}
