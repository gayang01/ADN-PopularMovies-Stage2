package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import uk.co.taniakolesnik.adn_popularmovies_part_2.R;
import uk.co.taniakolesnik.adn_popularmovies_part_2.Video;

/**
 * Created by tetianakolesnik on 28/07/2018.
 */


public class VideoAdapter extends ArrayAdapter<Video> {

    private static final String VIDEO_URL_BASE = "https://www.youtube.com/watch?v=";

    Context context;
    ArrayList<Video> videos;

    public VideoAdapter(Context context, ArrayList<Video> videos) {
        super(context, 0, videos);
        this.context = context;
        this.videos = videos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Video video = getItem(position);
        final String videoUrl = VIDEO_URL_BASE + video.getVideoKey();
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.video_list_item, parent, false);
        }
        TextView name = convertView.findViewById(R.id.videoName_tv);
        name.setText(video.getVideoName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(videoUrl));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

}