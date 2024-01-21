// Authors:
// Thomas Vuilleumier (@Cobora2001)
// Sebastian Diaz (@MolinaElGringo)
//Arthur Menétrey (@Xenogix)
//Lionel Pollien (@poLionel)

package ch.heigvd;

import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Database
 * This class is used to store the films and the reviews
 */
public class Database {
    // Index with which we start the IDs of the films
    static private final int start = 1;
    // Index of the next film
    private int nextIndex = start;

    // Films and reviews
    private final ConcurrentHashMap<Integer, Film> films;
    private final ConcurrentHashMap<Integer, ReviewsStorage> reviews;

    /**
     * Constructor of the class
     * We initialize the films and the reviews as empty ConcurrentHashMap
     * We add two films and their reviews for the tests
     */
    public Database() {
        this.films = new ConcurrentHashMap<>();
        this.reviews = new ConcurrentHashMap<>();

        // Tests
        LinkedList<Genre> genres1 = new LinkedList<>();
        genres1.add(Genre.Animation);
        LinkedList<Genre> genres2 = new LinkedList<>();
        genres2.add(Genre.Animation);

        films.put(nextIndex, new Film(nextIndex, "L'Arch de Noe", "Un film sur un element majeur de la religion", genres1));
        reviews.put(nextIndex++, new ReviewsStorage());

        genres2.add(Genre.Fantasy);

        films.put(nextIndex, new Film(nextIndex, "San Gohan", "Un film de dragon ball", genres2));
        reviews.put(nextIndex++, new ReviewsStorage());

        reviews.get(1).addReview(4, "No comment because I said so ");
        films.get(1).addReview(reviews.get(1).getMeanReviews());
        reviews.get(1).addReview(5, null);
        films.get(1).addReview(reviews.get(1).getMeanReviews());
        reviews.get(1).addReview(1, "");
        films.get(1).addReview(reviews.get(1).getMeanReviews());
    }

    /**
     * Getter of the films
     * @param ctx Context of the request
     */
    public void getFilms(Context ctx) {
        ctx.json(films);
    }

    /**
     * Getter of the films, but in a table
     * @param ctx Context of the request
     */
    public void getFilmsTable(Context ctx) {
        int size = films.size();
        Film[] tabFilm = new Film[size];
        int i = 0;
        for(Film film : films.values()) {
            tabFilm[i] = film;
            ++i;
        }
        ctx.json(tabFilm);
    }

    /**
     * Getter of the id of the film
     * @param ctx Context of the request
     * @return The id of the film
     */
    private Integer getIdFilm(Context ctx) {
        return ctx.pathParamAsClass("filmId", Integer.class)
                .check(filmId -> films.get(filmId) != null && reviews.get(filmId) != null, "Film not found")
                .getOrThrow(message -> new NotFoundResponse());
    }

    /**
     * Getter of the film
     * @param ctx Context of the request
     */
    public void getFilm(Context ctx) {
        Integer id = getIdFilm(ctx);

        ctx.json(films.get(id));
    }

    /**
     * Create a film from the context
     * @param ctx Context of the request
     * @return The film created
     */
    private Film createFilm(Context ctx) {
        return ctx.bodyValidator(Film.class)
                .check(obj -> obj.getTitle() != null, "Missing title")
                .check(obj -> !obj.getTitle().isEmpty(), "Empty title")
                .check(obj -> !obj.getDescription().isEmpty(), "Empty description")
                .get();
    }

    /**
     * Update a film
     * @param ctx Context of the request
     */
    public void updateFilm(Context ctx) {
        // Get the id of the film to update
        Integer id = getIdFilm(ctx);

        // Create the film from the context
        Film film = createFilm(ctx);

        // Get the film to update
        Film finalFilm = films.get(id);

        String oldTitle = finalFilm.getTitle();

        finalFilm.setTitle(film.getTitle());

        // Check if the new title is not already taken
        for (Film oneFilm : films.values()) {
            if (oneFilm.getTitle().equals(finalFilm.getTitle())) {
                if(oneFilm != finalFilm) {
                    finalFilm.setTitle(oldTitle);
                    throw new ConflictResponse();
                }
            }
        }

        // Update the film
        finalFilm.setDescription(film.getDescription());
        finalFilm.setGenres(film.getGenres());

        /* Je sais pas si on veut garder les reviews ou pas à l'update d'un film
        finalFilm.setMeanReviews();
        finalFilm.setNbReviews();

        reviews.get(id).getReviews().clear();
        */

        ctx.json(finalFilm);
    }

    /**
     * Add a film
     * @param ctx Context of the request
     */
    public void addFilm(Context ctx) {
        // Create the film from the context
        Film film = createFilm(ctx);

        // Create the film to add
        Film finalFilm = new Film();

        // Initialize the film
        finalFilm.setId(nextIndex);
        finalFilm.setTitle(film.getTitle());
        finalFilm.setDescription(film.getDescription());
        finalFilm.setGenres(film.getGenres());
        finalFilm.setMeanReviews();
        finalFilm.setNbReviews();

        // Check if the title is not already taken
        for (Film oneFilm : films.values()) {
            if (oneFilm.getTitle().equals(finalFilm.getTitle())) {
                throw new ConflictResponse();
            }
        }

        // Add the film
        films.put(nextIndex, finalFilm);
        reviews.put(nextIndex++, new ReviewsStorage());

        ctx.status(HttpStatus.CREATED);
        ctx.json(finalFilm);
    }

    /**
     * Delete a film
     * @param ctx Context of the request
     */
    public void deleteFilm(Context ctx) {
        Integer id = getIdFilm(ctx);

        reviews.remove(id);
        films.remove(id);

        ctx.status(HttpStatus.NO_CONTENT);
    }

    /**
     * Getter of the reviews
     * @param ctx Context of the request
     */
    public void getReviews(Context ctx) {
        Integer id = getIdFilm(ctx);

        ctx.json(reviews.get(id).getReviews());
    }

    /**
     * Getter of the reviews, but in a table
     * @param ctx Context of the request
     */
    public void getReviewsTable(Context ctx) {
        Integer id = getIdFilm(ctx);
        int size = reviews.get(id).getReviews().size();
        Review[] tabReview = new Review[size];
        int i = 0;
        for(Review review : reviews.get(id).getReviews().values()) {
            tabReview[i] = review;
            ++i;
        }
        ctx.json(tabReview);
    }

    /**
     * Getter of the id of the review
     * @param ctx Context of the request
     * @param idFilm ID of the film
     * @return The id of the review
     */
    private Integer getIdReview(Context ctx, int idFilm) {
        return ctx.pathParamAsClass("reviewId", Integer.class)
                .check(reviewId -> reviews.get(idFilm).getReviews().get(reviewId) != null, "Review not found")
                .getOrThrow(message -> new NotFoundResponse());
    }

    /**
     * Getter of the review
     * @param ctx Context of the request
     */
    public void getReview(Context ctx) {
        Integer idFilm = getIdFilm(ctx);

        Integer idReview = getIdReview(ctx, idFilm);

        ctx.json(reviews.get(idFilm).getReviews().get(idReview));
    }

    /**
     * Create a review from the context
     * @param ctx Context of the request
     * @return The review created
     */
    private Review createReview(Context ctx) {
        return ctx.bodyValidator(Review.class)
                .check(obj -> obj.getRating() != null, "Missing rating")
                .check(obj -> obj.getComment() != null, "Missing comment")
                .get();
    }

    /**
     * Add a review
     * @param ctx Context of the request
     */
    public void addReview(Context ctx) {
        // Get the id of the film
        Integer idFilm = getIdFilm(ctx);

        // Create the review from the context
        Review review = createReview(ctx);

        // Create the review to add
        Review newReview = new Review();

        // Initialize the review
        newReview.setRating(review.getRating());
        newReview.setComment(review.getComment());

        // Find the storage of the reviews of the film in question
        ReviewsStorage storage = reviews.get(idFilm);

        // Add the review
        storage.addReview(newReview);

        // Update the mean of the reviews of the film
        films.get(idFilm).addReview(reviews.get(idFilm).getMeanReviews());

        ctx.status(HttpStatus.CREATED);
        ctx.json(newReview);
    }

    /**
     * Update a review
     * @param ctx Context of the request
     */
    public void updateReview(Context ctx) {
        // Get the id of the film
        Integer idFilm = getIdFilm(ctx);

        // Get the id of the review
        Integer idReview = getIdReview(ctx, idFilm);

        // Create the review from the context
        Review review = createReview(ctx);

        // Get the review to update
        Review newReview = reviews.get(idFilm).getReviews().get(idReview);

        // Update the review
        newReview.setRating(review.getRating());
        newReview.setComment(review.getComment());

        ctx.json(newReview);
    }

    /**
     * Delete a review
     * @param ctx Context of the request
     */
    public void deleteReview(Context ctx) {
        // Get the id of the film
        Integer idFilm = getIdFilm(ctx);

        // Get the id of the review
        Integer idReview = getIdReview(ctx, idFilm);

        // Delete the review
        reviews.get(idFilm).delete(idReview);

        // Update the mean of the reviews of the film
        films.get(idFilm).deleteReview(reviews.get(idFilm).getMeanReviews());

        ctx.status(HttpStatus.NO_CONTENT);
    }
}
