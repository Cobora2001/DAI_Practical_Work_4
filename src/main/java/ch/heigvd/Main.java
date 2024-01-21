// Authors:
// Thomas Vuilleumier (@Cobora2001)
// Sebastian Diaz (@MolinaElGringo)
//Arthur Menétrey (@Xenogix)
//Lionel Pollien (@poLionel)

package ch.heigvd;

import io.javalin.Javalin;

/**
 * Main class of the application
 */
public class Main {
    public final static int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        // Databases
        Database database = new Database();

        // Movies routes
        app.post("/dashboard/", database::addFilm);
        app.post("/dashboard", database::addFilm);
        app.get("/dashboard/", database::getFilmsTable);
        app.get("/dashboard", database::getFilmsTable);

        // Movie routes
        app.get("/dashboard/{filmId}", database::getFilm);
        app.get("/dashboard/{filmId}/", database::getFilm);
        app.put("/dashboard/{filmId}", database::updateFilm);
        app.put("/dashboard/{filmId}/", database::updateFilm);
        app.delete("/dashboard/{filmId}", database::deleteFilm);
        app.delete("/dashboard/{filmId}/", database::deleteFilm);

        // Reviews routes
        app.post("/dashboard/{filmId}/reviews", database::addReview);
        app.post("/dashboard/{filmId}/reviews/", database::addReview);
        app.get("/dashboard/{filmId}/reviews", database::getReviewsTable);
        app.get("/dashboard/{filmId}/reviews/", database::getReviewsTable);

        // Review routes
        app.get("/dashboard/{filmId}/reviews/{reviewId}", database::getReview);
        app.get("/dashboard/{filmId}/reviews/{reviewId}/", database::getReview);
        app.put("/dashboard/{filmId}/reviews/{reviewId}", database::updateReview);
        app.put("/dashboard/{filmId}/reviews/{reviewId}/", database::updateReview);
        app.delete("/dashboard/{filmId}/reviews/{reviewId}", database::deleteReview);
        app.delete("/dashboard/{filmId}/reviews/{reviewId}/", database::deleteReview);

        app.start(PORT);
    }
}