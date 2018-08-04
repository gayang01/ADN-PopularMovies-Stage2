package uk.co.taniakolesnik.adn_popularmovies_part_2.Database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by tetianakolesnik on 04/08/2018.
 */

public class CheckIfFavouriteFactory extends ViewModelProvider.NewInstanceFactory {

    private final FavouriteDatabase database;
    private final int movieId;


    public CheckIfFavouriteFactory(FavouriteDatabase database, int movieId) {
        this.database = database;
        this.movieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CheckIfFavouriteViewModel(database, movieId);
    }
}
