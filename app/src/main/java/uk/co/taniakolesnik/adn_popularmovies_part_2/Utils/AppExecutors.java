package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by tetianakolesnik on 24/07/2018.
 */

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor databaseExecutor;


    public AppExecutors(Executor databaseExecutor) {
        this.databaseExecutor = databaseExecutor;
    }

    public static AppExecutors getsInstance(){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor());
            }
        }
    return sInstance;
    }

    public Executor getDatabaseExecutor(){
        return databaseExecutor;
    }
}
