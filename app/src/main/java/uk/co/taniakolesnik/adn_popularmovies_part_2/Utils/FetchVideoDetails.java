package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import uk.co.taniakolesnik.adn_popularmovies_part_2.Video;

/**
 * Created by tetianakolesnik on 28/07/2018.
 */

public class FetchVideoDetails extends AsyncTask<String, Void, ArrayList<Video>> {

    Context context;
    ListView listView;

    public FetchVideoDetails(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected ArrayList<Video> doInBackground(String... strings) {
        ArrayList<Video> videos = MovieUtils.fetchVideoInfo(strings[0]);
        return videos;
    }

    @Override
    protected void onPostExecute(ArrayList<Video> arrayList) {
        VideoAdapter adapter = new VideoAdapter(context, arrayList);
        listView.setAdapter(adapter);
        ListViewHelper.setListViewHeightBasedOnItems(listView);
    }
}