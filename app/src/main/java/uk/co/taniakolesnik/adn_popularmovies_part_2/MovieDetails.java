package uk.co.taniakolesnik.adn_popularmovies_part_2;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.AppExecutors;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.FetchReviewDetails;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Utils.FetchVideoDetails;

public class MovieDetails extends AppCompatActivity {

    private static final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/w500/";
    FavouriteDatabase favouriteDatabase;
    @BindView(R.id.title_tv) TextView titleTextView;
    @BindView(R.id.releaseDate_tv) TextView releaseDateTextView;
    @BindView(R.id.vote_tv) TextView voteTextView;
    @BindView(R.id.plot_tv) TextView plotTextView;
    @BindView(R.id.posterImageView) ImageView posterView;
    @BindView(R.id.addToFav_button) FloatingActionButton favouriteButton;
    @BindView(R.id.video_list_view) ListView videoListView;
    @BindView(R.id.review_list_view) ListView reviewListView;

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
        setFavouriteButtonColor(movieId);

        final Movie favourite = new Movie(title, releaseDate,image, vote, plot, movieId);

        String videoUrl = makeVideoUrl(movieId);
        String reviewUrl = makeReviewUrl(movieId);
        new FetchVideoDetails(this, videoListView).execute(videoUrl);
        new FetchReviewDetails(this, reviewListView).execute(reviewUrl);

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getsInstance().getDatabaseExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        int count = favouriteDatabase.favouriteDao().getCountFromFavourites(movieId);
                        if (count == 0){
                            addMovieToFavourites(favourite);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    favouriteButton.setImageResource(R.drawable.ic_star_yellow);
                                }
                            });
                        } else {
                            removeMovieFromFavourites(movieId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    favouriteButton.setImageResource(R.drawable.ic_star_black);
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    private void setFavouriteButtonColor(final int movieId) {
        favouriteDatabase = FavouriteDatabase.getsIntance(this);
        AppExecutors.getsInstance().getDatabaseExecutor().execute(new Runnable() {
            @Override
            public void run() {
                int count = favouriteDatabase.favouriteDao().getCountFromFavourites(movieId);
                if (count == 0){
                    favouriteButton.setImageResource(R.drawable.ic_star_black);
                } else {
                    favouriteButton.setImageResource(R.drawable.ic_star_yellow);
                }
            }
        });
    }

    private void setMovieDetails(String title, String releaseDate, int vote, String plot, String image) {

        String imagePath = IMAGE_URL_BASE + image;
        titleTextView.setText(title);
        releaseDateTextView.setText(releaseDate);
        voteTextView.setText(String.valueOf(vote) + getString(R.string.vote_max));
        plotTextView.setText(plot);
        Uri uri = Uri.parse(imagePath);
        Picasso.with(this).load(uri).into(posterView);
    }

    private String makeVideoUrl(int movieId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("videos")
                .appendQueryParameter(getString(R.string.api_key), MainActivity.API_KEY_VALUE)
                .build();
        return builder.toString();
    }

    private String makeReviewUrl(int movieId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("reviews")
                .appendQueryParameter(getString(R.string.api_key), MainActivity.API_KEY_VALUE)
                .build();
        return builder.toString();
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
            }
        });

    }

}
