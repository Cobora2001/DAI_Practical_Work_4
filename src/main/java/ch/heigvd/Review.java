// Authors:
// Thomas Vuilleumier (@Cobora2001)
// Sebastian Diaz (@MolinaElGringo)
//Arthur Menétrey (@Xenogix)
//Lionel Pollien (@poLionel)

package ch.heigvd;

/**
 * Class representing a review
 */
public class Review {
    // Attributes
    private int id;
    private Integer rating;
    private String comment;

    /**
     * Constructor
     */
    public Review() {

    }

    /**
     * Constructor
     * @param id the id of the review
     * @param rating the rating of the review
     * @param comment the comment of the review
     */
    public Review(int id, Integer rating, String comment) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
    }

    /**
     * get the rating of the review
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * get the comment of the review
     */
    public String getComment() {
        return comment;
    }

    /**
     * set the rating of the review
     * @param rating the rating of the review
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * set the comment of the review
     * @param comment the comment of the review
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * create a review
     * @param id the id of the review
     * @param rating the rating of the review
     * @param comment the comment of the review
     */
    static public Review create(int id, Integer rating, String comment) {
        return new Review(id, rating, comment == null || comment.isEmpty() ? null : comment);
    }

    /**
     * get the id of the review
     * @return the id of the review
     */
    public int getId() {
        return id;
    }
}
