package uk.co.taniakolesnik.adn_popularmovies_part_2.Database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by tetianakolesnik on 04/08/2018.
 */

public class CheckIfFavouriteViewModel extends ViewModel{


    private LiveData<Integer> count;

    public CheckIfFavouriteViewModel(FavouriteDatabase database, int movieId) {
        count = database.favouriteDao().getCountFromFavourites(movieId);
    }

    public LiveData<Integer> getCount() {
        return count;
    }

}
