package uk.co.taniakolesnik.adn_popularmovies_part_2.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import uk.co.taniakolesnik.adn_popularmovies_part_2.Database.FavouriteDatabase;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Movie;

/**
 * Created by tetianakolesnik on 04/08/2018.
 */

public class FavouritesViewModel  extends AndroidViewModel {

    private LiveData<List<Movie>> favourites;

    public FavouritesViewModel(@NonNull Application application) {
        super(application);
        Log.i("FavouritesViewModel", "loadFavourites from LiveData");
        FavouriteDatabase database = FavouriteDatabase.getsIntance(this.getApplication());
        favourites = database.favouriteDao().selectAll();
    }

    public LiveData<List<Movie>> getFavourites() {
        return favourites;
    }

}
