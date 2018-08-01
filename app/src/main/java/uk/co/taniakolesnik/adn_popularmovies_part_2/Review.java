package uk.co.taniakolesnik.adn_popularmovies_part_2;

/**
 * Created by tetianakolesnik on 28/07/2018.
 */

public class Review {

    private String reviewAuthor;
    private String reviewContent;
    private int reviewId;
    private String reviewUrl;

    public Review(String reviewAuthor, String reviewContent, int reviewId, String reviewUrl) {
        this.reviewAuthor = reviewAuthor;
        this.reviewContent = reviewContent;
        this.reviewId = reviewId;
        this.reviewUrl = reviewUrl;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewAuthor='" + reviewAuthor + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                ", reviewId=" + reviewId +
                ", reviewUrl='" + reviewUrl + '\'' +
                '}';
    }
}
