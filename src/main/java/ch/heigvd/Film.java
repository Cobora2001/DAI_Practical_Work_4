package ch.heigvd;

import java.util.LinkedList;
import java.util.Objects;

public class Film {
    static private int nextID = 1;

    private final int id;
    private String title;
    private String description;
    private final LinkedList<Review> reviews;
    private final LinkedList<Genre> genres;

    private double meanReview = -1;

    private Film(String title, String description, LinkedList<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = Objects.requireNonNullElseGet(genres, LinkedList::new);
        this.reviews = new LinkedList<>();
        this.id = nextID++;
    }

    public Film createFilm(String title, String description, LinkedList<Genre> genres) {
        if(title == null || title.isEmpty()) {
            System.out.println("Invalid title, aborting");
            return null;
        }
        return new Film(title, description, genres);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void addGenre(Genre genre) {
        boolean isInList = false;
        int size = genres.size();

        for(int i = 0; i < size && !isInList; ++i) {
            if(genre == genres.get(i)) {
                isInList = true;
            }
        }

        if(!isInList) {
            genres.add(genre);
        }
    }

    public void addReview(Review review) {
        reviews.add(review);
        meanReview = meanReview();
    }

    private double meanReview() {
        int numberOfReviews = 0;
        double sum = 0;

        for(Review review : reviews) {
            ++numberOfReviews;
            sum += review.getRating();
        }

        return sum / numberOfReviews;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nTitle: " + title +
                "\nDescription: " + description +
                (meanReview < 0 ? "" : "\nMean of the reviews (3 by default)" + meanReview) +
                "\nGenre(s): " + genres;
    }
}
