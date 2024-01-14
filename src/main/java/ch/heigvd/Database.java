package ch.heigvd;

import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class Database {
    static private final int start = 1;
    private int nextIndex = start;

    private final ConcurrentHashMap<Integer, Film> films;
    private final ConcurrentHashMap<Integer, ReviewsStorage> reviews;

    public Database() {
        this.films = new ConcurrentHashMap<>();
        this.reviews = new ConcurrentHashMap<>();

        // Tests pour les gets
        LinkedList<Genre> genres1 = new LinkedList<>();
        genres1.add(Genre.Animation);
        LinkedList<Genre> genres2 = new LinkedList<>();
        genres2.add(Genre.Animation);

        films.put(nextIndex, new Film("L'Arch de Noe", "Un film sur un element majeur de la religion", genres1));
        reviews.put(nextIndex++, new ReviewsStorage());

        genres2.add(Genre.Fantasy);

        films.put(nextIndex, new Film("San Gohan", "Un film de dragon ball", genres2));
        reviews.put(nextIndex++, new ReviewsStorage());

        reviews.get(1).addReview(4, "No comment because I said so ");
        films.get(1).addReview(reviews.get(1).getMeanReviews());
        reviews.get(1).addReview(5, null);
        films.get(1).addReview(reviews.get(1).getMeanReviews());
        reviews.get(1).addReview(1, "");
        films.get(1).addReview(reviews.get(1).getMeanReviews());
    }

    public void getFilms(Context ctx) {
        ctx.json(films);
    }

    public void getFilm(Context ctx) {
        Integer id = ctx.pathParamAsClass("filmId", Integer.class)
                .check(filmId -> films.get(filmId) != null && reviews.get(filmId) != null, "Film not found")
                .getOrThrow(message -> new NotFoundResponse());

        ctx.json(films.get(id));
    }

    public void updateFilm(Context ctx) {
        Integer id = ctx.pathParamAsClass("filmId", Integer.class)
                .check(filmId -> films.get(filmId) != null && reviews.get(filmId) != null, "Film not found")
                .getOrThrow(message -> new NotFoundResponse());

        Film film = ctx.bodyValidator(Film.class)
                .check(obj -> obj.getTitle() != null, "Missing title")
                .check(obj -> !obj.getTitle().isEmpty(), "Empty title")
                .check(obj -> !obj.getDescription().isEmpty(), "Empty description")
                .get();

        Film finalFilm = films.get(id);

        String oldTitle = finalFilm.getTitle();

        finalFilm.setTitle(film.getTitle());

        for (Film oneFilm : films.values()) {
            if (oneFilm.getTitle().equals(finalFilm.getTitle())) {
                if(oneFilm != finalFilm) {
                    finalFilm.setTitle(oldTitle);
                    throw new ConflictResponse();
                }
            }
        }

        finalFilm.setDescription(film.getDescription());
        finalFilm.setGenres(film.getGenres());
        finalFilm.setMeanReviews(3);
        finalFilm.setNbReviews(0);

        ctx.json(finalFilm);
    }

    public void addFilm(Context ctx) {
        Film film = ctx.bodyValidator(Film.class)
                .check(obj -> obj.getTitle() != null, "Missing title")
                .check(obj -> !obj.getTitle().isEmpty(), "Empty title")
                .check(obj -> !obj.getDescription().isEmpty(), "Empty description")
                .get();

        Film finalFilm = new Film();

        finalFilm.setTitle(film.getTitle());
        finalFilm.setDescription(film.getDescription());
        finalFilm.setGenres(film.getGenres());
        finalFilm.setMeanReviews(3);
        finalFilm.setNbReviews(0);

        for (Film oneFilm : films.values()) {
            if (oneFilm.getTitle().equals(finalFilm.getTitle())) {
                throw new ConflictResponse();
            }
        }

        films.put(nextIndex, finalFilm);
        reviews.put(nextIndex++, new ReviewsStorage());

        ctx.status(HttpStatus.CREATED);
        ctx.json(finalFilm);
    }

    public void getReviews(Context ctx) {
        Integer id = ctx.pathParamAsClass("filmId", Integer.class)
                .check(filmId -> reviews.get(filmId) != null && films.get(filmId) != null, "Film not found")
                .getOrThrow(message -> new NotFoundResponse());

        ctx.json(reviews.get(id));
    }

    public void getReview(Context ctx) {
        Integer idFilm = ctx.pathParamAsClass("filmId", Integer.class)
                .check(filmId -> reviews.get(filmId) != null && films.get(filmId) != null, "Film not found")
                .getOrThrow(message -> new NotFoundResponse());

        Integer idReview = ctx.pathParamAsClass("reviewId", Integer.class)
                .check(reviewId -> reviews.get(idFilm).getReviews().get(reviewId) != null, "Review not found")
                .getOrThrow(message -> new NotFoundResponse());

        ctx.json(reviews.get(idFilm).getReviews().get(idReview));
    }

    public void addReview(Context ctx) {
        Integer idFilm = ctx.pathParamAsClass("filmId", Integer.class)
                .check(filmId -> reviews.get(filmId) != null && films.get(filmId) != null, "Film not found")
                .getOrThrow(message -> new NotFoundResponse());

        Review review = ctx.bodyValidator(Review.class)
                .check(obj -> obj.getRating() != null, "Missing rating")
                .check(obj -> obj.getComment() != null, "Missing comment")
                .get();

        Review newReview = new Review();

        newReview.setRating(review.getRating());

        newReview.setComment(review.getComment());

        ReviewsStorage storage = reviews.get(idFilm);

        storage.addReview(newReview);

        ctx.status(HttpStatus.CREATED);
        ctx.json(newReview);

    }

    public void updateReview(Context ctx) {
        Integer idFilm = ctx.pathParamAsClass("filmId", Integer.class)
                .check(filmId -> reviews.get(filmId) != null && films.get(filmId) != null, "Film not found")
                .getOrThrow(message -> new NotFoundResponse());

        Integer idReview = ctx.pathParamAsClass("reviewId", Integer.class)
                .check(reviewId -> reviews.get(idFilm).getReviews().get(reviewId) != null, "Review not found")
                .getOrThrow(message -> new NotFoundResponse());

        Review review = ctx.bodyValidator(Review.class)
                .check(obj -> obj.getRating() != null, "Missing rating")
                .check(obj -> obj.getComment() != null, "Missing comment")
                .get();

        Review newReview = reviews.get(idFilm).getReviews().get(idReview);

        newReview.setRating(review.getRating());

        newReview.setComment(review.getComment());

        ctx.json(newReview);

    }
}
