package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Movie;


/**
 * Created by tetianakolesnik on 03/06/2018.
 */

public class MovieAsyncTaskLoader extends AsyncTaskLoader<List<Movie>> {

    private static String mUrl;

    public MovieAsyncTaskLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    public List<Movie> loadInBackground() {
        List<Movie> results = MovieUtils.fetchMoviInfo(mUrl);
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
