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

    private Integer getIdFilm(Context ctx) {
        return ctx.pathParamAsClass("filmId", Integer.class)
                .check(filmId -> films.get(filmId) != null && reviews.get(filmId) != null, "Film not found")
                .getOrThrow(message -> new NotFoundResponse());
    }

    public void getFilm(Context ctx) {
        Integer id = getIdFilm(ctx);

        ctx.json(films.get(id));
    }

    private Film createFilm(Context ctx) {
        return ctx.bodyValidator(Film.class)
                .check(obj -> obj.getTitle() != null, "Missing title")
                .check(obj -> !obj.getTitle().isEmpty(), "Empty title")
                .check(obj -> !obj.getDescription().isEmpty(), "Empty description")
                .get();
    }

    public void updateFilm(Context ctx) {
        Integer id = getIdFilm(ctx);

        Film film = createFilm(ctx);

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
        Film film = createFilm(ctx);

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

    public void deleteFilm(Context ctx) {
        Integer id = getIdFilm(ctx);

        reviews.remove(id);
        films.remove(id);

        ctx.status(HttpStatus.NO_CONTENT);
    }

    public void getReviews(Context ctx) {
        Integer id = getIdFilm(ctx);

        ctx.json(reviews.get(id).getReviews());
    }

    private Integer getIdReview(Context ctx, int idFilm) {
        return ctx.pathParamAsClass("reviewId", Integer.class)
                .check(reviewId -> reviews.get(idFilm).getReviews().get(reviewId) != null, "Review not found")
                .getOrThrow(message -> new NotFoundResponse());
    }

    public void getReview(Context ctx) {
        Integer idFilm = getIdFilm(ctx);

        Integer idReview = getIdReview(ctx, idFilm);

        ctx.json(reviews.get(idFilm).getReviews().get(idReview));
    }

    private Review createReview(Context ctx) {
        return ctx.bodyValidator(Review.class)
                .check(obj -> obj.getRating() != null, "Missing rating")
                .check(obj -> obj.getComment() != null, "Missing comment")
                .get();
    }

    public void addReview(Context ctx) {
        Integer idFilm = getIdFilm(ctx);

        Review review = createReview(ctx);

        Review newReview = new Review();

        newReview.setRating(review.getRating());

        newReview.setComment(review.getComment());

        ReviewsStorage storage = reviews.get(idFilm);

        storage.addReview(newReview);

        films.get(idFilm).addReview(reviews.get(idFilm).getMeanReviews());

        ctx.status(HttpStatus.CREATED);
        ctx.json(newReview);
    }

    public void updateReview(Context ctx) {
        Integer idFilm = getIdFilm(ctx);

        Integer idReview = getIdReview(ctx, idFilm);

        Review review = createReview(ctx);

        Review newReview = reviews.get(idFilm).getReviews().get(idReview);

        newReview.setRating(review.getRating());

        newReview.setComment(review.getComment());

        ctx.json(newReview);
    }

    public void deleteReview(Context ctx) {
        Integer idFilm = getIdFilm(ctx);

        Integer idReview = getIdReview(ctx, idFilm);

        reviews.get(idFilm).delete(idReview);

        films.get(idFilm).deleteReview(reviews.get(idFilm).getMeanReviews());

        ctx.status(HttpStatus.NO_CONTENT);
    }
}
