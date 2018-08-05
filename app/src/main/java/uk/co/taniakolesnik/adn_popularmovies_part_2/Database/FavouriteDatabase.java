package uk.co.taniakolesnik.adn_popularmovies_part_2.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Movie;

/**
 * Created by tetianakolesnik on 24/07/2018.
 */

@Database(entities = {Movie.class}, version = 3, exportSchema = false)
public abstract class FavouriteDatabase extends RoomDatabase{


    private static FavouriteDatabase sIntance;
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "AppDatabase";

    public static FavouriteDatabase getsIntance(final Context context){
        if (sIntance == null) {
            synchronized (LOCK) {
                sIntance = Room.databaseBuilder(context, FavouriteDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return sIntance;
    }

    public abstract FavouriteDao favouriteDao();
}
