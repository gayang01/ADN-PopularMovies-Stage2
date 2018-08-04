package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import uk.co.taniakolesnik.adn_popularmovies_part_2.Video;

/**
 * Created by tetianakolesnik on 04/08/2018.
 */

public class VideoAsyncTaskLoader extends AsyncTaskLoader<List<Video>> {

    private static String url;

    public VideoAsyncTaskLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public ArrayList<Video> loadInBackground() {
        ArrayList<Video> results = MovieUtils.fetchVideoInfo(url);
        return results;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}
