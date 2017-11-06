package robhawk.com.br.meusfilmes.web;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

/**
 * Created by Robert on 04/11/2017.
 * Acessa a API https://www.themoviedb.org/documentation/api
 * Utiliza a lib https://github.com/holgerbrandl/themoviedbapi
 */

public class Api {

    public void search(final String title, final Callback<List<MovieDb>> callback) {
        new Thread() {
            @Override
            public void run() {
                TmdbApi tmdb = new TmdbApi(ApiKey.PRIVATE_KEY); // Crie a classe ApiKey com o atributo statico PRIVATE_KEY e atribua sua chave.
                TmdbSearch search = tmdb.getSearch();
                MovieResultsPage response = search.searchMovie(title, null, "pt-BR", false, 0);
                List<MovieDb> results = response.getResults();
                callback.success(results);
            }
        }.start();
    }

}
