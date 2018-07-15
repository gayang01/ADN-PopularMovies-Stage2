package uk.co.taniakolesnik.adn_popularmovies_part_1;

import java.util.Date;

/**
 * Created by tetianakolesnik on 02/06/2018.
 */

public class Movie {

    //title, release date, movie poster, vote average, and plot synopsis.

    private String mTitle;
    private String mReleaseDate;
    private String mImagePath;
    private int mVoteAverage;
    private String mPlot;

    private static final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/w185/";

    @Override
    public String toString() {
        return "Movie{" +
                "mTitle='" + mTitle + '\'' +
                ", mReleaseDate=" + mReleaseDate +
                ", mImagePath='" + mImagePath + '\'' +
                ", mVoteAverage=" + mVoteAverage +
                ", mPlot='" + mPlot + '\'' +
                '}';
    }

    public Movie(String mTitle, String mReleaseDate, String mImagePath, int mVoteAverage, String mPlot) {
        setmTitle(mTitle);
        setmReleaseDate(mReleaseDate);
        setmImagePath(mImagePath);
        setmVoteAverage(mVoteAverage);
        setmPlot(mPlot);
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = IMAGE_URL_BASE + mImagePath;
    }

    public int getmVoteAverage() {
        return mVoteAverage;
    }

    public void setmVoteAverage(int mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getmPlot() {
        return mPlot;
    }

    public void setmPlot(String mPlot) {
        this.mPlot = mPlot;
    }


}
