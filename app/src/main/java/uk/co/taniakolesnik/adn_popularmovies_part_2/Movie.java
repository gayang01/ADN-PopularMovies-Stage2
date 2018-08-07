package uk.co.taniakolesnik.adn_popularmovies_part_2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by tetianakolesnik on 02/06/2018.
 */
@Entity(tableName = "favourites")
public class Movie implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int _id;
    private String title;
    private String releaseDate;
    private String imagePath;
    private int voteAverage;
    private String plot;
    int movieId;

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", imagePath='" + imagePath + '\'' +
                ", voteAverage=" + voteAverage +
                ", movieId='" + movieId + '\'' +
                '}';
    }

    @Ignore
    public Movie(String title, String releaseDate, String imagePath, int voteAverage, String plot,
                 int movieId) {
        setTitle(title);
        setReleaseDate(releaseDate);
        setImagePath(imagePath);
        setVoteAverage(voteAverage);
        setPlot(plot);
        setMovieId(movieId);
    }

    public Movie(int id, String title, String releaseDate, String imagePath, int voteAverage,
                 String plot, int movieId) {
        set_id(id);
        setTitle(title);
        setReleaseDate(releaseDate);
        setImagePath(imagePath);
        setVoteAverage(voteAverage);
        setPlot(plot);
        setMovieId(movieId);
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

}
