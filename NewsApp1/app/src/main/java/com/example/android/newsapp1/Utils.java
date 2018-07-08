package com.example.android.newsapp1;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by lhu513 on 5/3/18.
 */

public class Utils {

    private static final String LOG_TAG = Utils.class.getSimpleName();

    private static final String title = "webTitle";

    private static final String section = "sectionName";

    private static final String url = "webUrl";

    private static final String author = "webTitle";

    private static final String date = "webPublicationDate";

    private static final String response = "response";

    private static final String results = "results";

    private static final String tags = "tags";

    private Utils() {
    }

    public static ArrayList<News> getNewsData(String requestUrl) throws InterruptedException {

        URL url = returnUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Request failed.", e);
        }

        ArrayList<News> news = extractFeatureFromJson(jsonResponse);

        return news;
    }

    //This method returns new URL object from the given string URL.

    private static URL returnUrl(String stringUrl) {
        URL url = null;
        try {
            // Try to create an URL from String
            url = new URL(stringUrl);

        } catch (MalformedURLException e) {

            // In case that request failed, print the error message into log
            Log.e(LOG_TAG, "Malformed URL", e);
        }
        return url;
    }

    // This method is to make an HTTP request to the given URL and return a String as the response.

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {

            Log.e(LOG_TAG, "Connection was not established. Problem retrieving JSON News results.", e);
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // This method is used to convert the InputStream into a String which contains the whole JSON response from the server.

    private static String readFromStream(InputStream inputStream) throws IOException {

        // Create a new StringBuilder
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    // This method returns list of {@link News} objects that has been built up from parsing the given JSON response.

    private static ArrayList<News> extractFeatureFromJson(String newsJSON) {

        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        ArrayList<News> newsList = new ArrayList<>();

        // Parse the JSON response string.
        try {
            JSONObject baseJsonNewsResponse = new JSONObject(newsJSON);

            JSONObject responseJsonNews = baseJsonNewsResponse.getJSONObject(response);

            JSONArray newsArray = responseJsonNews.getJSONArray(results);

            for (int i = 0; i < newsArray.length(); i++) {

                // Get a single news article at position i within the list of news
                JSONObject currentNews = newsArray.getJSONObject(i);

                // Extract the section name for the key called "sectionName"
                String newsSection = currentNews.getString(section);

                // Check if newsDate exist and than extract the date for the key called "webPublicationDate"
                String newsDate = "Not Available";

                if (currentNews.has(date)) {
                    newsDate = currentNews.getString(date);
                }

                // Extract the article name for the key called "webTitle"
                String newsTitle = currentNews.getString(title);

                // Extract the value for the key called "webUrl"
                String newsUrl = currentNews.getString(url);

                //Extract the JSONArray associated with the key called "tags",
                JSONArray currentNewsAuthorArray = currentNews.getJSONArray(tags);

                String newsAuthor = "Not Available";

                //Check if "tags" array contains data
                int tagsLenght = currentNewsAuthorArray.length();

                if (tagsLenght == 1) {
                    // Create a JSONObject for author
                    JSONObject currentNewsAuthor = currentNewsAuthorArray.getJSONObject(0);

                    String newsAuthor1 = currentNewsAuthor.getString(author);

                    newsAuthor = "written by: " + newsAuthor1;
                }

                News newNews = new News(newsTitle, newsSection, newsUrl, newsAuthor, newsDate);

                newsList.add(newNews);
            }

        } catch (JSONException e) {
            Log.e("Utils", "JSON results parsing problem.");
        }
        return newsList;
    }
}

