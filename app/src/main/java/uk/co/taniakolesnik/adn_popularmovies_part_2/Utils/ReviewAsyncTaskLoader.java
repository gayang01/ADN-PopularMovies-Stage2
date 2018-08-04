package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import uk.co.taniakolesnik.adn_popularmovies_part_2.Review;

/**
 * Created by tetianakolesnik on 04/08/2018.
 */

public class ReviewAsyncTaskLoader extends AsyncTaskLoader<List<Review>>  {

    private static String url;

    public ReviewAsyncTaskLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public ArrayList<Review> loadInBackground() {
        ArrayList<Review> results = MovieUtils.fetchReviewInfo(url);
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
