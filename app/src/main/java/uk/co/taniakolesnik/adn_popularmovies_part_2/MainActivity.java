package uk.co.taniakolesnik.adn_popularmovies_part_2;

import android.app.LoaderManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Database.FavouritesViewModel;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.ListViewHelper;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.MovieAsyncTaskLoader;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.MovieRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String TAG = MainActivity.class.getSimpleName();

    //please insert your API key here
    public static final String API_KEY_VALUE = BuildConfig.THEMOVIDEDB_API_KEY;
    private static final String PREFERENCE_KEY = "preference";
    private static final String LIST_KEY = "list";
    private static final int LOADER_ID = 1;
    MovieRecyclerViewAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerListView;
    @BindView(R.id.empty_View)
    TextView emptyTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBarView;
    private String preference;
    private int menuItemId;
    Parcelable mListState;
    GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (TextUtils.isEmpty(API_KEY_VALUE)){
           showErrorMessage(getString(R.string.no_api_key_error_pop_up));
           return;
        }

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(PREFERENCE_KEY)){
                menuItemId = savedInstanceState.getInt(PREFERENCE_KEY);
                setPageAsPerSortSelected(menuItemId);
            }
        } else {
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
        mGridLayoutManager = new GridLayoutManager(this, ListViewHelper.calculateNumbeOfColumns(getApplicationContext()));
        recyclerListView.setLayoutManager(mGridLayoutManager);
        recyclerListView.setAdapter(adapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = recyclerListView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_KEY, mListState);
        outState.putInt(PREFERENCE_KEY, menuItemId);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            menuItemId = savedInstanceState.getInt(PREFERENCE_KEY);
            setPageAsPerSortSelected(menuItemId);
            mListState = savedInstanceState.getParcelable(LIST_KEY);
        }
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
                menuItemId = 0;
                break;
            case R.id.topRated_menu_item:
                menuItemId = 1;
                break;
            case R.id.favourite_menu_item:
                menuItemId = 2;
                break;
        }
        setPageAsPerSortSelected(menuItemId);
        return super.onOptionsItemSelected(item);
    }

    private void setPageAsPerSortSelected(int menuItemId) {
        switch (menuItemId) {
            case 0:
                preference = getString(R.string.linkPreference_popular);
                setTitle(getString(R.string.popularMovies_pageName));
                getLoaderManager().restartLoader(LOADER_ID, null, this);
                break;
            case 1:
                preference = getString(R.string.linkPreference_top_rated);
                setTitle(getString(R.string.topRatedMovies_pageName));
                getLoaderManager().restartLoader(LOADER_ID, null, this);
                break;
            case 2:
                setTitle(getString(R.string.favouritesMovies_pageName));
                setUpViewModel();
                break;
        }
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
            if (menuItemId != 2) {
                adapter.updateAdapter(data);
                if (mListState != null) {
                    mGridLayoutManager.onRestoreInstanceState(mListState);
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
    }

    private void setUpViewModel(){
        FavouritesViewModel viewModel = ViewModelProviders.of(this).get(FavouritesViewModel.class);
        viewModel.getFavourites().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter.updateAdapter(movies);
                if (mListState != null) {
                    mGridLayoutManager.onRestoreInstanceState(mListState);
                }
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
        return builder.toString();
    }

    private void showErrorMessage(String message) {
        progressBarView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
        emptyTextView.setText(message);
    }
}
