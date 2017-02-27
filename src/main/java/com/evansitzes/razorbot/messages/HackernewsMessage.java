package com.evansitzes.razorbot.messages;

import com.evansitzes.razorbot.helpers.InputConverter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by evan on 2/27/17.
 */
public class HackernewsMessage {

    private static final String TOP_NEWS_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final String STORY_URL = "https://hacker-news.firebaseio.com/v0/item/{STORY_ID}.json";

    public static String getTopStory() {
        final String[] topStories = getTopStories();
        return getStoryUrl(topStories[0]);
    }

    public static boolean isValid(final String message) {
        return message.toLowerCase().contains("news")
               || message.toLowerCase().contains("story");
    }


    private static String[] getTopStories() {
        final DefaultHttpClient httpClient = new DefaultHttpClient();
        final HttpGet httpGetRequest = new HttpGet(TOP_NEWS_URL);

        final HttpResponse response;
        try {
            response = httpClient.execute(httpGetRequest);
            System.out.println("Response code: " + response.getStatusLine());
            final String result = InputConverter.convertStreamToString(response.getEntity().getContent());
            final String[] topStories = result.replace("[", "").split(",");

            httpClient.getConnectionManager().shutdown();
            return topStories;

        } catch (final IOException e) {
            // TODO log exception and catch higher up
            throw new RuntimeException("Unable to get Hacker News top stories.", e.getCause());
        }
    }

    private static String getStoryUrl(final String storyId) {
        final DefaultHttpClient httpClient = new DefaultHttpClient();
        final HttpGet httpGetRequest = new HttpGet(STORY_URL.replace("{STORY_ID}", storyId));

        final HttpResponse response;
        try {
            response = httpClient.execute(httpGetRequest);
            System.out.println("Response code: " + response.getStatusLine());
            final String result = InputConverter.convertStreamToString(response.getEntity().getContent());
            final JSONObject json = (JSONObject) new JSONParser().parse(result);

            httpClient.getConnectionManager().shutdown();
            return json.get("url").toString();

            // TODO log exception and catch higher up
        } catch (final IOException e) {
            throw new RuntimeException("Unable to get Hacker News specific story.", e.getCause());
        } catch (final ParseException e) {
            throw new RuntimeException("Unable to parse JSON response.", e.getCause());
        }
    }



}
