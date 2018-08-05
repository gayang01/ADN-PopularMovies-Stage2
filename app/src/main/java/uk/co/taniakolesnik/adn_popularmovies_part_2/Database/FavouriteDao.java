package uk.co.taniakolesnik.adn_popularmovies_part_2.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Movie;

/**
 * Created by tetianakolesnik on 24/07/2018.
 */

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie favourite);

    @Query("SELECT * FROM favourites")
    LiveData<List<Movie>> selectAll();

    @Query("SELECT COUNT(*) FROM favourites WHERE movieId=:movieId")
    LiveData<Integer> getCountFromFavourites(int movieId);

    @Query("DELETE FROM favourites WHERE movieId=:movieId")
    void deleteFavourite(int movieId);
}
