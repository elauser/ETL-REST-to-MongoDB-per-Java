import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

class ExtractorTest {
    static URL url;
    static String json;
    static Document document;

    @BeforeAll
    static void init(){
        try {
            url = RiotUrls.getSummonerUrlByName("euw1", "xOnionx");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String json = Extractor.readAll(br);
            document = Document.parse(json);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readJson() {
        System.out.println(Extractor.getJson(url));
    }

    @Test
    void readAll() {
    }

}