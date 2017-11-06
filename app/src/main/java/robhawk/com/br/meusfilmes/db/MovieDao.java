package robhawk.com.br.meusfilmes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import robhawk.com.br.meusfilmes.model.Movie;

/**
 * Created by Robert on 05/11/2017.
 * Gerencia informacoes de filmes no banco de dados.
 */

public class MovieDao {

    private final DatabaseHelper mHelper;

    public MovieDao(Context context) {
        mHelper = new DatabaseHelper(context);
    }

    public void insertOrUpdate(Movie movie) {
        Movie existent = findById(movie.id);
        if (existent == null) insert(movie);
        else update(movie);
    }

    public List<Movie> listAll() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies", null);
        List<Movie> movies = getResultList(cursor);
        cursor.close();
        return movies;
    }

    private List<Movie> getResultList(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();
        while (cursor.moveToNext())
            movies.add(extract(cursor));
        return movies;
    }

    private void update(Movie movie) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.update("movies", values(movie), "id = ?", new String[]{movie.id + ""});
    }

    private void insert(Movie movie) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.insert("movies", null, values(movie));
    }

    private ContentValues values(Movie movie) {
        ContentValues values = new ContentValues();
        values.put("id", movie.id);
        values.put("title", movie.title);
        values.put("overview", movie.overview);
        values.put("releaseDate", movie.releaseDate);
        values.put("adult", movie.getAdult());
        values.put("voteCount", movie.voteCount);
        values.put("voteAverage", movie.voteAverage);
        return values;
    }

    public Movie findById(int id) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql = "SELECT * FROM movies WHERE id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id + ""});
        Movie movie = getResultObject(cursor);
        cursor.close();
        return movie;
    }

    private Movie getResultObject(Cursor cursor) {
        if (cursor.moveToNext())
            return extract(cursor);
        return null;
    }

    private Movie extract(Cursor c) {
        Movie movie = new Movie();
        movie.id = c.getInt(c.getColumnIndex("id"));
        movie.title = c.getString(c.getColumnIndex("title"));
        movie.overview = c.getString(c.getColumnIndex("overview"));
        movie.releaseDate = c.getString(c.getColumnIndex("releaseDate"));
        movie.setAdult(c.getInt(c.getColumnIndex("adult")));
        movie.voteCount = c.getInt(c.getColumnIndex("voteCount"));
        movie.voteAverage = c.getInt(c.getColumnIndex("voteAverage"));
        return movie;
    }

}
