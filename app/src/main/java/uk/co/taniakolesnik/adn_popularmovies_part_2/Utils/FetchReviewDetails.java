package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import uk.co.taniakolesnik.adn_popularmovies_part_2.Review;
/**
 * Created by tetianakolesnik on 29/07/2018.
 */

public class FetchReviewDetails extends AsyncTask<String, Void, ArrayList<Review>> {

    Context context;
    ListView listView;

    public FetchReviewDetails(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected ArrayList<Review> doInBackground(String... strings) {
        ArrayList<Review> reviews = MovieUtils.fetchReviewInfo(strings[0]);
        Log.i("FetchReviewDetails", "reviews are " + reviews);
        return reviews;
    }

    @Override
    protected void onPostExecute(ArrayList<Review> arrayList) {
        ReviewAdapter adapter = new ReviewAdapter(context, arrayList);
        listView.setAdapter(adapter);
        ListViewHelper.setListViewHeightBasedOnItems(listView);
    }

    //https://stackoverflow.com/questions/29225539/set-listview-height-dynamically-based-on-multiline-textview-inside-it

}