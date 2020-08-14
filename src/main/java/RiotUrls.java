import java.net.MalformedURLException;
import java.net.URL;

public class RiotUrls {
    static URL getSummonerUrlByName(String region, String summonerName) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://");
        stringBuilder.append(region.toLowerCase());
        stringBuilder.append(".api.riotgames.com/lol/summoner/v4/summoners/by-name/");
        stringBuilder.append(summonerName);
        stringBuilder.append("?api_key=" + HiddenConstants.RIOT_API_KEY);
        return new URL(stringBuilder.toString());
    }

    static URL getSummonerUrlByAccountID(String region, String summonerId) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://");
        stringBuilder.append(region.toLowerCase());
        stringBuilder.append(".api.riotgames.com/lol/summoner/v4/summoners/by-account/");
        stringBuilder.append(summonerId);
        stringBuilder.append("?api_key=" + HiddenConstants.RIOT_API_KEY);
        return new URL(stringBuilder.toString());
    }

    static URL getMatchHistory(String region, String summonerID, int endIndex) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://");
        stringBuilder.append(region.toLowerCase());
        stringBuilder.append(".api.riotgames.com/lol/match/v4/matchlists/by-account/");
        stringBuilder.append(summonerID);
        stringBuilder.append("?endIndex=20");
        stringBuilder.append("&api_key=" + HiddenConstants.RIOT_API_KEY);

        return new URL(stringBuilder.toString());
    }

    static URL getMatch(String region, String matchID) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://");
        stringBuilder.append(region.toLowerCase());
        stringBuilder.append(".api.riotgames.com/lol/match/v4/matches/");
        stringBuilder.append(matchID);
        stringBuilder.append("?api_key=" + HiddenConstants.RIOT_API_KEY);

        return new URL(stringBuilder.toString());
    }

}
