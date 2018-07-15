package uk.co.taniakolesnik.adn_popularmovies_part_1;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();

    @BindView(R.id.title_value_tv) TextView titleTextView;
    @BindView(R.id.releaseDate_value_tv) TextView releaseDateTextView;
    @BindView(R.id.vote_value_tv) TextView voteTextView;
    @BindView(R.id.plot_value_tv) TextView plotTextView;
    @BindView(R.id.posterImageView) ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String title = intent.getStringExtra(getString(R.string.movie_title_bundle));
        String releaseDate = intent.getStringExtra(getString(R.string.movie_releaseDate_bundle));
        int  vote = intent.getIntExtra(getString(R.string.movie_vote_bundle),0);
        String plot = intent.getStringExtra(getString(R.string.movie_plot_bundle));
        String image = intent.getStringExtra(getString(R.string.movie_image_bundle));

        setMovieDetails(title, releaseDate, vote, plot, image);
    }

    private void setMovieDetails(String title, String releaseDate, int vote, String plot, String imagePath) {

        titleTextView.setText(title);
        releaseDateTextView.setText(releaseDate);
        voteTextView.setText(String.valueOf(vote));
        plotTextView.setText(plot);
        Uri uri = Uri.parse(imagePath);
        Picasso.with(this).load(uri).into(imageView);
    }

}
