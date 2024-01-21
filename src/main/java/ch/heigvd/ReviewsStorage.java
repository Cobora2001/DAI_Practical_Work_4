// Authors:
// Thomas Vuilleumier (@Cobora2001)
// Sebastian Diaz (@MolinaElGringo)
//Arthur Menétrey (@Xenogix)
//Lionel Pollien (@poLionel)

package ch.heigvd;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Class representing the storage of reviews
 */
public class ReviewsStorage {
    // Default values
    static private final int start = 1;
    static private final int min = 1;
    static private final int max = 5;

    // Attributes
    private int nextId = start;
    private final ConcurrentHashMap<Integer, Review> reviews;

    /**
     * Constructor
     */
    public ReviewsStorage() {
        this.reviews = new ConcurrentHashMap<>();
    }

    /**
     * Constructor
     * @param rating the rating of the review
     * @param comment the comment of the review
     */
    public void addReview(int rating, String comment) {
        if(rating >= min && rating <= max) {
            int id = getNextId();
            reviews.put(id, new Review(id, rating, comment ));
        }
    }

    /**
     * add a review to the storage
     * @param review the review to add
     */
    public void addReview(Review review) {
        if(review != null && review.getRating() >= min && review.getRating() <= max) {
            reviews.put(getNextId(), review);
        }
    }

    /**
     * get the next id, and increment it
     * @return the next id
     */
    public int getNextId() {
        return nextId++;
    }

    /**
     * get the reviews
     * @return the reviews
     */
    public ConcurrentHashMap<Integer, Review> getReviews() {
        return reviews;
    }

    /**
     * get the mean of the reviews
     * @return the mean of the reviews
     */
    public double getMeanReviews() {
        double somme = 0;
        int divider = 0;
        for(Review review : reviews.values()) {
            ++divider;
            somme += review.getRating();
        }

        return divider > 0 ? somme / divider : 3.;
    }

    /**
     * delete a review
     * @param id the id of the review to delete
     */
    public void delete(int id) {
        reviews.remove(id);
    }
}
