package ch.heigvd;

import java.util.LinkedList;

public class Film {
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

    public Film(String title, String description, LinkedList<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.nbReviews = 0;
        this.meanReviews = 3;
    }

    public Film(String title, String description, String genres) {
        this(title, description, genresSeparator(genres));
    }

    public void addReview(double newMeanReviews) {
        nbReviews += 1;
        this.meanReviews = newMeanReviews;
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

    static private Genre genreInterpreter(String genreString) {
        if(genreString == null) {
            return null;
        }
        if(genreString.equalsIgnoreCase("Action")) {
            return Genre.Action;
        }
        if(genreString.equalsIgnoreCase("Animation")) {
            return Genre.Animation;
        }
        if(genreString.equalsIgnoreCase("Comedy")) {
            return Genre.Comedy;
        }
        if(genreString.equalsIgnoreCase("Crime")) {
            return Genre.Crime;
        }
        if(genreString.equalsIgnoreCase("Drama")) {
            return Genre.Drama;
        }
        if(genreString.equalsIgnoreCase("Experimental")) {
            return Genre.Experimental;
        }
        if(genreString.equalsIgnoreCase("Fantasy")) {
            return Genre.Fantasy;
        }
        if(genreString.equalsIgnoreCase("Historical")) {
            return Genre.Historical;
        }
        if(genreString.equalsIgnoreCase("Horror")) {
            return Genre.Horror;
        }
        if(genreString.equalsIgnoreCase("Romance")) {
            return Genre.Romance;
        }
        if(genreString.equalsIgnoreCase("ScienceFiction")) {
            return Genre.ScienceFiction;
        }
        if(genreString.equalsIgnoreCase("Thriller")) {
            return Genre.Thriller;
        }
        if(genreString.equalsIgnoreCase("Western")) {
            return Genre.Western;
        }
        if(genreString.equalsIgnoreCase("Other")) {
            return Genre.Other;
        }
        return null;
    }

    static private LinkedList<Genre> genresSeparator(String genresString) {
        if(genresString == null) {
            return null;
        }

        String[] genres = genresString.split("-");

        LinkedList<Genre> genresList = new LinkedList<>();

        for(String genreString : genres) {
            Genre genre = genreInterpreter(genreString);
            if(genre != null) {
                boolean notInList = true;
                for(Genre genreList : genresList) {
                    if(genreList == genre) {
                        notInList = false;
                        break;
                    }
                }
                if(notInList) {
                    genresList.add(genre);
                }
            }
        }
        return genresList;
    }

}
