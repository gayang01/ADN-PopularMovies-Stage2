package uk.co.taniakolesnik.adn_popularmovies_part_2;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.AppExecutors;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.MovieAsyncTaskLoader;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.MovieRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    //please insert your API key here
    public static final String API_KEY_VALUE = "89d4514e84a96bd998784f6768769127";
    private static final int LOADER_ID = 1;
    MovieRecyclerViewAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerListView;
    @BindView(R.id.empty_View)
    TextView emptyTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBarView;
    private String preference;
    private FavouriteDatabase favouriteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (TextUtils.isEmpty(API_KEY_VALUE)){
           showErrorMessage(getString(R.string.no_api_key_error_pop_up));
           return;
        }

        if (preference == null) {
            preference = getString(R.string.linkPreference_popular);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            showErrorMessage(getString(R.string.no_connection_error));
        }

        adapter = new MovieRecyclerViewAdapter(this, new ArrayList<Movie>());
        recyclerListView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerListView.setAdapter(adapter);

        favouriteDatabase = FavouriteDatabase.getsIntance(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular_menu_item:
                preference = getString(R.string.linkPreference_popular);
                setTitle(getString(R.string.popularMovies_pageName));
                getLoaderManager().restartLoader(LOADER_ID, null, this);
                return true;
            case R.id.topRated_menu_item:
                preference = getString(R.string.linkPreference_top_rated);
                setTitle(getString(R.string.topRatedMovies_pageName));
                getLoaderManager().restartLoader(LOADER_ID, null, this);
                return true;
            case R.id.favourite_menu_item:
                setTitle(getString(R.string.favouritesMovies_pageName));
                loadFavourites();
                return true;
            case R.id.remove_favourites_menu_item:
                removeFavourites();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        String url = makeUrl(preference);
        return new MovieAsyncTaskLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if (data == null) {
            showErrorMessage(getString(R.string.no_date_error));
        } else {
            progressBarView.setVisibility(View.GONE);
            adapter.updateAdapter(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
    }

    private void loadFavourites(){
        AppExecutors.getsInstance().getDatabaseExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<Movie> favourites = favouriteDatabase.favouriteDao().selectAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (favourites.size() == 0){
                            showErrorMessage(getApplicationContext().getString(R.string.no_favourites_error));
                        }
                        adapter.updateAdapter(favourites);
                    }
                });
            }
        });

    }


    private void removeFavourites() {
        AppExecutors.getsInstance().getDatabaseExecutor().execute(new Runnable() {
            @Override
            public void run() {
                favouriteDatabase.favouriteDao().deleteAll();
            }
        });
    }


    private String makeUrl(String preference) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(preference)
                .appendQueryParameter(getString(R.string.api_key), API_KEY_VALUE)
                .build();
        Log.i(TAG, "makeUrl link created" + builder.toString());
        return builder.toString();
    }

    private void showErrorMessage(String message) {
        progressBarView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
        emptyTextView.setText(message);
    }


}
