package robhawk.com.br.meusfilmes.model;

import java.util.LinkedList;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by Robert on 05/11/2017.
 * Modelo para manipulacao dos dados de filmes.
 */

public class Movie {

    public int id;
    public String title;
    public String overview;
    public String posterPath;
    public String releaseDate;
    private boolean adult;
    public int voteCount;
    public float voteAverage;

    public static List<Movie> convertList(List<MovieDb> movies) {
        List<Movie> converted = new LinkedList<>();
        for (MovieDb movie : movies)
            converted.add(new Movie(movie));
        return converted;
    }

    public Movie() {
    }

    private Movie(MovieDb movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.overview = movie.getOverview();
        this.posterPath = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/" + movie.getPosterPath();
        this.releaseDate = movie.getReleaseDate();
        this.adult = movie.isAdult();
        this.voteCount = movie.getVoteCount();
        this.voteAverage = movie.getVoteAverage();
    }

    public int getAdult() {
        return adult ? 1 : 0;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult > 0;
    }

    public String getYearStr() {
        if (isReleaseDateInvalid())
            return "Ano não informado";

        String[] yearMonthDay = releaseDate.split("-");

        return yearMonthDay[0];
    }

    public int getYear() {
        try {
            return Integer.valueOf(getYearStr());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean isReleaseDateInvalid() {
        return releaseDate == null || releaseDate.isEmpty() || !releaseDate.contains("-");
    }

    @Override
    public String toString() {
        return title;
    }

}
