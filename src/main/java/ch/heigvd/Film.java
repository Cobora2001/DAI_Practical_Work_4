// Authors:
// Thomas Vuilleumier (@Cobora2001)
// Sebastian Diaz (@MolinaElGringo)
//Arthur Menétrey (@Xenogix)
//Lionel Pollien (@poLionel)

package ch.heigvd;

import java.util.LinkedList;

/**
 * Class representing a film
 */
public class Film {
    // Default values
    static private final int meanReviewsDefault = 3;

    // Attributes
    private int id;
    private String title;
    private String description;
    private LinkedList<Genre> genres;
    private int nbReviews;
    private double meanReviews;

    /**
     * Constructor
     */
    public Film() {

    }

    /**
     * set the title of the film
     * @param title the title of the film
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * set the description of the film
     * @param description the description of the film
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set the genres of the film
     * @param genres the genres of the film
     */
    public void setGenres(LinkedList<Genre> genres) {
        this.genres = genres;
    }

    /**
     * set the number of reviews of the film
     * @param nbReviews the number of reviews of the film
     */
    public void setNbReviews(int nbReviews) {
        this.nbReviews = nbReviews;
    }

    /**
     * set the number of reviews of the film to its default value
     */
    public void setNbReviews() {
        this.nbReviews = 0;
    }

    /**
     * set the mean of the reviews of the film
     * @param meanReviews the mean of the reviews of the film
     */
    public void setMeanReviews(double meanReviews) {
        this.meanReviews = meanReviews;
    }

    /**
     * set the mean of the reviews of the film to its default value
     */
    public void setMeanReviews() {
        this.meanReviews = meanReviewsDefault;
    }

    /**
     * Constructor
     * @param id the id of the film
     * @param title the title of the film
     * @param description the description of the film
     * @param genres the genres of the film
     */
    public Film(int id, String title, String description, LinkedList<Genre> genres) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        setMeanReviews();
        setNbReviews();
    }

    /**
     * add a review to the film
     * @param newMeanReviews the new mean of the reviews of the film
     */
    public void addReview(double newMeanReviews) {
        nbReviews += 1;
        this.meanReviews = newMeanReviews;
    }

    /**
     * set the id of the film
     * @param id the id of the film
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * delete a review to the film
     * @param newMeanReviews the new mean of the reviews of the film
     */
    public void deleteReview(double newMeanReviews) {
        nbReviews -= 1;
        this.meanReviews = newMeanReviews;
    }

    /**
     * get the title of the film
     * @return the title of the film
     */
    public String getTitle() {
        return title;
    }

    /**
     * get the description of the film
     * @return the description of the film
     */
    public String getDescription() {
        return description;
    }

    /**
     * get the genres of the film
     * @return the genres of the film
     */
    public LinkedList<Genre> getGenres() {
        return genres;
    }

    /**
     * get the number of reviews of the film
     * @return the number of reviews of the film
     */
    public int getNbReviews() {
        return nbReviews;
    }

    /**
     * get the mean of the reviews of the film
     * @return the mean of the reviews of the film
     */
    public double getMeanReviews() {
        return meanReviews;
    }

    /**
     * get the id of the film
     * @return the id of the film
     */
    public int getId() {
        return id;
    }
}
