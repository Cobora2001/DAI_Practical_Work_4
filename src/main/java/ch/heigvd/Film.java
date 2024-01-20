package ch.heigvd;

import java.util.LinkedList;

public class Film {
    private int id;
    private String title;
    private String description;
    private LinkedList<Genre> genres;
    private int nbReviews;
    private double meanReviews;

    public Film() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenres(LinkedList<Genre> genres) {
        this.genres = genres;
    }

    public void setNbReviews(int nbReviews) {
        this.nbReviews = nbReviews;
    }

    public void setMeanReviews(double meanReviews) {
        this.meanReviews = meanReviews;
    }

    public Film(int id, String title, String description, LinkedList<Genre> genres) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.nbReviews = 0;
        this.meanReviews = 3;
    }

    public void addReview(double newMeanReviews) {
        nbReviews += 1;
        this.meanReviews = newMeanReviews;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void deleteReview(double newMeanReviews) {
        nbReviews -= 1;
        this.meanReviews = newMeanReviews;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LinkedList<Genre> getGenres() {
        return genres;
    }

    public int getNbReviews() {
        return nbReviews;
    }

    public double getMeanReviews() {
        return meanReviews;
    }

    public int getId() {
        return id;
    }
}
