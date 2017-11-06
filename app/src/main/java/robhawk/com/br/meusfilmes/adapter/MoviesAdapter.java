package robhawk.com.br.meusfilmes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import robhawk.com.br.meusfilmes.R;
import robhawk.com.br.meusfilmes.model.Movie;

/**
 * Created by Robert on 04/11/2017.
 * Adapta os dados de {@link Movie} a ListView
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {

    private final ImageLoader mImageLoader;
    private List<Movie> mMovies;

    public MoviesAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, R.layout.adapter_movies);

        mMovies = movies;

        // lib para o download e renderizacao das capas de filmes
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(context).build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(imageLoaderConfig);
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        return mMovies.get(position);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) { // configuracao da view de cada item
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.adapter_movies, parent, false);
        }

        // obtem os elementos da view de cada item da lista
        TextView titleView = view.findViewById(R.id.movie_title);
        final TextView overviewView = view.findViewById(R.id.movie_overview);
        TextView ratingView = view.findViewById(R.id.movie_rating);
        TextView votesView = view.findViewById(R.id.movie_votes);
        TextView releaseYearView = view.findViewById(R.id.movie_release_year);

        // obtem os dados
        final Movie movie = mMovies.get(position);
        String overview = movie.overview;
        overview = overview == null || overview.isEmpty() ? "Sem resumo..." : overview;

        // renderiza os dados
        titleView.setText(movie.title);
        overviewView.setText(overview);
        ratingView.setText(movie.voteAverage + "/10");
        votesView.setText(movie.voteCount + " votos");
        releaseYearView.setText(movie.getYearStr());

        // tenta baixar a capa do filme renderizado
        mImageLoader.loadImage(movie.posterPath, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getContext().getResources(), loadedImage);
                overviewView.setCompoundDrawablesWithIntrinsicBounds(bitmapDrawable, null, null, null);
            }
        });

        return view;
    }

    public void orderByYear() {
        Collections.sort(mMovies, new Comparator<Movie>() {
            @Override
            public int compare(Movie a, Movie b) {
                return a.getYear() > b.getYear() ? 1 : -1;
            }
        });

        notifyDataSetChanged();
    }

    public void orderByTitle() {
        Collections.sort(mMovies, new Comparator<Movie>() {
            @Override
            public int compare(Movie a, Movie b) {
                return a.title.compareTo(b.title);
            }
        });

        notifyDataSetChanged();
    }

}
