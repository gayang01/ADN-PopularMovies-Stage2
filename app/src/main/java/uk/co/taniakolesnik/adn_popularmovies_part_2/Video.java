package uk.co.taniakolesnik.adn_popularmovies_part_2;

/**
 * Created by tetianakolesnik on 24/07/2018.
 */

public class Video {

    @Override
    public String toString() {
        return "Video{" +
                "videoKey='" + videoKey + '\'' +
                ", videoName='" + videoName + '\'' +
                ", videoSite='" + videoSite + '\'' +
                ", videoType='" + videoType + '\'' +
                '}';
    }

    private String videoKey;
    private String videoName;
    private String videoSite;
    private String videoType;

    public Video(String videoKey, String videoName, String videoSite, String videoType) {
        this.videoKey = videoKey;
        this.videoName = videoName;
        this.videoSite = videoSite;
        this.videoType = videoType;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoSite() {
        return videoSite;
    }

    public void setVideoSite(String videoSite) {
        this.videoSite = videoSite;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

}


