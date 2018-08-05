package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import uk.co.taniakolesnik.adn_popularmovies_part_2.R;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Review;

/**
 * Created by tetianakolesnik on 28/07/2018.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {

    Context context;
    List<Review> reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        super(context, 0, reviews);
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Review review = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.review_list_item, parent, false);
        }
        TextView name = convertView.findViewById(R.id.review_author_tv);
        name.setText(review.getReviewAuthor());
        TextView content = convertView.findViewById(R.id.review_content_tv);
        content.setText(review.getReviewContent());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(review.getReviewUrl()));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

}
