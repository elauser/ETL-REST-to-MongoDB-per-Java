import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class JsonLoader implements Runnable {
    URL url;
    BlockingQueue<Document> returnQueue;

    public JsonLoader() {
    }

    public JsonLoader(BlockingQueue<Document> returnQueue, URL url) {
        this.url = url;
        this.returnQueue = returnQueue;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String response = readAll(br);
            returnQueue.add(Document.parse(response));
        } catch (IOException e) {
            System.out.println("IOException trying to read from URL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String readAll(BufferedReader br) {
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
