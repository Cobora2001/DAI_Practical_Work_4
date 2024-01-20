package ch.heigvd;

public class Review {
    private int id;
    private Integer rating;
    private String comment;

    public Review() {

    }

    public Review(int id, Integer rating, String comment) {
        this.id = id;
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

    static public Review create(int id, Integer rating, String comment) {
        return new Review(id, rating, comment == null || comment.isEmpty() ? null : comment);
    }

    public int getId() {
        return id;
    }
}
