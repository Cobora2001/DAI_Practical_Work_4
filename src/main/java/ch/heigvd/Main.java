package ch.heigvd;

import ch.heigvd.Database;
import io.javalin.Javalin;

public class Main {
    public final static int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        // Databases
        Database database = new Database();

        // Movies routes
        app.post("/dashboard/", database::addFilm);
        app.post("/dashboard", database::addFilm);
        app.get("/dashboard/", database::getFilms);
        app.get("/dashboard", database::getFilms);

        // Movie routes
        app.get("/dashboard/{filmId}", database::getFilm);
        app.get("/dashboard/{filmId}/", database::getFilm);
        app.put("/dashboard/{filmId}", database::updateFilm);
        app.put("/dashboard/{filmId}/", database::updateFilm);

        // Reviews routes
        app.post("/dashboard/{filmId}/reviews", database::addReview);
        app.post("/dashboard/{filmId}/reviews/", database::addReview);
        app.get("/dashboard/{filmId}/reviews", database::getReviews);
        app.get("/dashboard/{filmId}/reviews/", database::getReviews);

        // Review routes
        app.get("/dashboard/{filmId}/reviews/{reviewId}", database::getReview);
        app.get("/dashboard/{filmId}/reviews/{reviewId}/", database::getReview);
        app.put("/dashboard/{filmId}/reviews/{reviewId}", database::updateReview);
        app.put("/dashboard/{filmId}/reviews/{reviewId}/", database::updateReview);

        // Genres routes

        // Genre routes

        app.start(PORT);
    }
}