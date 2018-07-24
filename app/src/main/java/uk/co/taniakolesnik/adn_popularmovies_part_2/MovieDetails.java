package uk.co.taniakolesnik.adn_popularmovies_part_2;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.AppExecutors;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();
    FavouriteDatabase favouriteDatabase;

    @BindView(R.id.title_tv) TextView titleTextView;
    @BindView(R.id.releaseDate_tv) TextView releaseDateTextView;
    @BindView(R.id.vote_tv) TextView voteTextView;
    @BindView(R.id.plot_tv) TextView plotTextView;
    @BindView(R.id.posterImageView) ImageView posterView;
    @BindView(R.id.addToFav_button) Button favouriteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String title = intent.getStringExtra(getString(R.string.movie_title_bundle));
        String releaseDate = intent.getStringExtra(getString(R.string.movie_releaseDate_bundle));
        int vote = intent.getIntExtra(getString(R.string.movie_vote_bundle),0);
        final int movieId = intent.getIntExtra(getString(R.string.movie_id_bundle),0);
        String plot = intent.getStringExtra(getString(R.string.movie_plot_bundle));
        String image = intent.getStringExtra(getString(R.string.movie_image_bundle));
        setMovieDetails(title, releaseDate, vote, plot, image);

        final Movie favourite = new Movie(title, releaseDate,image, vote, plot, movieId);

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieToFavourites(favourite);
                CharSequence text = "Movie with movieid " + String.valueOf(movieId) + " added";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });

        favouriteDatabase = FavouriteDatabase.getsIntance(this);

    }



    private void setMovieDetails(String title, String releaseDate, int vote, String plot, String imagePath) {

        titleTextView.setText(title);
        releaseDateTextView.setText(releaseDate);
        voteTextView.setText(String.valueOf(vote));
        plotTextView.setText(plot);
        Uri uri = Uri.parse(imagePath);
        Picasso.with(this).load(uri).into(posterView);
    }

    private void removeMovieFromFavourites(final int movieId) {
        AppExecutors.getsInstance().getDatabaseExecutor().execute(new Runnable() {
            @Override
            public void run() {
                favouriteDatabase.favouriteDao().deleteFavourite(movieId);
            }
        });

    }

    private void addMovieToFavourites(final Movie favourite) {
        AppExecutors.getsInstance().getDatabaseExecutor().execute(new Runnable() {
            @Override
            public void run() {
                favouriteDatabase.favouriteDao().insert(favourite);
                Log.i(TAG, "adding " + favourite.toString());
            }
        });

    }

}
