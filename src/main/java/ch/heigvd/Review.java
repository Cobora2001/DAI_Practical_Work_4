package ch.heigvd;

public class Review {
    private static int nextId = 1;

    private final int id;
    private int rating;
    private String comment;

    private Review(int rating, String comment) {
        this.comment = comment;
        this.rating = rating;
        this.id = nextId++;
    }

    static public Review createReview(int rating, String comment) {
        if(rating < 1 || rating > 5) {
            System.out.println("Invalid rating, aborting");
            return null;
        }
        return new Review(rating, comment);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nRating: " + rating + (comment != null ? "\nComment: " + comment : "");
    }

    public void setInfo(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }
}
