package ch.heigvd;

import java.util.concurrent.ConcurrentHashMap;

public class ReviewsStorage {
    static private final int start = 1;

    static private final int min = 1;
    static private final int max = 5;

    private int nextId = start;

    private final ConcurrentHashMap<Integer, Review> reviews;

    public ReviewsStorage() {
        this.reviews = new ConcurrentHashMap<>();
    }

    public void addReview(int rating, String comment) {
        if(rating >= min && rating <= max) {
            reviews.put(getNextId(), new Review(rating, comment ));
        }
    }

    public void addReview(Review review) {
        if(review != null && review.getRating() >= min && review.getRating() <= max) {
            reviews.put(getNextId(), review);
        }
    }

    public int getNextId() {
        return nextId++;
    }

    public ConcurrentHashMap<Integer, Review> getReviews() {
        return reviews;
    }

    public double getMeanReviews() {
        double somme = 0;
        int divider = 0;
        for(Review review : reviews.values()) {
            ++divider;
            somme += review.getRating();
        }

        return divider > 0 ? somme / divider : 3.;
    }
}
