package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Movie;
import uk.co.taniakolesnik.adn_popularmovies_part_2.MovieDetails;
import uk.co.taniakolesnik.adn_popularmovies_part_2.R;

/**
 * Created by tetianakolesnik on 06/06/2018.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private static final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/w185/";
    Context context;
    private LayoutInflater layoutInflater;
    private List<Movie> mData;

    public MovieRecyclerViewAdapter(Context context, List<Movie> mData) {
        this.layoutInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.context = context;
    }

    public void updateAdapter(List<Movie> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_list_item, parent, false);
        return new MovieRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieRecyclerViewAdapter.ViewHolder holder, int position) {
        Movie movie = mData.get(position);
        String imagePath = IMAGE_URL_BASE + movie.getImagePath();
        Picasso.with(holder.itemView.getContext()).load(imagePath).into(holder.recyclerTextView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recyclerListItem)
        ImageView recyclerTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie movie = mData.get(getAdapterPosition());
            Intent intent = new Intent(context, MovieDetails.class);
            intent.putExtra(context.getResources().getString(R.string.movie_title_bundle), movie.getTitle());
            intent.putExtra(context.getResources().getString(R.string.movie_releaseDate_bundle), movie.getReleaseDate());
            intent.putExtra(context.getResources().getString(R.string.movie_vote_bundle), movie.getVoteAverage());
            intent.putExtra(context.getResources().getString(R.string.movie_id_bundle), movie.getMovieId());
            intent.putExtra(context.getResources().getString(R.string.movie_plot_bundle), movie.getPlot());
            intent.putExtra(context.getString(R.string.movie_image_bundle), movie.getImagePath());
            context.startActivity(intent);

        }

    }

}

