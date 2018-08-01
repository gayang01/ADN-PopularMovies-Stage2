package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Movie;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Review;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Video;

/**
 * Created by tetianakolesnik on 02/06/2018.
 */

public class MovieUtils {

    public static ArrayList<Movie> fetchMoviInfo(String urlString) {
        URL url = createUrl(urlString);
        String jsonReply = makeHTTPRequest(url);
        return extractMovie(jsonReply);
    }

    public static ArrayList<Video> fetchVideoInfo(String urlString) {
        URL url = createUrl(urlString);
        String jsonReply = makeHTTPRequest(url);
        return extractVideo(jsonReply);
    }

    public static ArrayList<Review> fetchReviewInfo(String urlString) {
        URL url = createUrl(urlString);
        String jsonReply = makeHTTPRequest(url);
        return extractReview(jsonReply);
    }

    private static URL createUrl(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) {
        String jsonReply = "";
        if (url == null) {
            return jsonReply;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonReply = readFromInout(inputStream);
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return jsonReply;
    }

    private static String readFromInout(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static ArrayList<Movie> extractMovie(String jsonReply) {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(jsonReply);
            JSONArray jsonArray = json.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.optString("original_title");
                String releaseDate = jsonObject.optString("release_date");
                String releaseDateYear = getReleaseYear(releaseDate);
                String imagePath = jsonObject.optString("poster_path");
                int voteAverage = jsonObject.optInt("vote_average");
                int movieId = jsonObject.getInt("id");
                String plot = jsonObject.optString("overview");
                Movie movie = new Movie(title, releaseDateYear, imagePath, voteAverage, plot, movieId);
                //Log.i(TAG, movie.toString());
                movieArrayList.add(movie);
            }
            return movieArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getReleaseYear(String dateString){
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy");

        Date date = null;
        try {
            date = inputDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String year = outputDateFormat.format(date);
        return year;
    }

    private static ArrayList<Video> extractVideo(String jsonReply) {
        ArrayList<Video> videoArrayList = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(jsonReply);
            JSONArray jsonArray = json.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String videoKey = jsonObject.optString("key");
                String videoName = jsonObject.optString("name");
                String videoSite = jsonObject.optString("site");
                String videoType = jsonObject.optString("type");
                Video video = new Video(videoKey, videoName, videoSite, videoType);
                Log.i("MovieUtils", video.toString());
                videoArrayList.add(video);
            }
            return videoArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Review> extractReview(String jsonReply) {
        ArrayList<Review> reviewArrayList = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(jsonReply);
            JSONArray jsonArray = json.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String reviewAuthor = jsonObject.optString("author");
                String reviewContent = jsonObject.optString("content");
                int reviewId = jsonObject.optInt("id");
                String reviewUrl = jsonObject.optString("url");
                Review review = new Review(reviewAuthor, reviewContent, reviewId, reviewUrl);
                Log.i("MovieUtils", review.toString());
                reviewArrayList.add(review);
            }
            return reviewArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
