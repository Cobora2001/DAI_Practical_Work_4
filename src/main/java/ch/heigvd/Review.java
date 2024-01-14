package ch.heigvd;

public class Review {
    private Integer rating;
    private String comment;

    public Review() {

    }

    public Review(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    static public Review create(Integer rating, String comment) {
        return new Review(rating, comment == null || comment.isEmpty() ? null : comment);
    }
}
