import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class RiotUrlsTest {

    @Test
    void getSummonerUrlByName() {
        String summonerName = "xOnionx";
        String region = "euw1";
        String targetUrl = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/xOnionx?api_key=RGAPI-b8ed159a-6d4b-442e-b3e1-62af8533e190";
        URL url;

        try {
            url = RiotUrls.getSummonerUrlByName(region, summonerName);
            Assertions.assertEquals(targetUrl, url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}