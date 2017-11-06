package robhawk.com.br.meusfilmes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import robhawk.com.br.meusfilmes.adapter.MoviesAdapter;
import robhawk.com.br.meusfilmes.db.MovieDao;

/**
 * Controla a lista de filmes salvos, sendo possivel ordenar por titulo e ano.
 */
public class MyMoviesActivity extends AppCompatActivity {

    private MovieDao mDao;
    private ListView mFavoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_movies);

        mDao = new MovieDao(this);

        mFavoriteList = findViewById(R.id.favorite_movies_list);
        final CheckBox titleOrder = findViewById(R.id.order_title);
        final CheckBox dateOrder = findViewById(R.id.order_date);

        titleOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleOrder.setChecked(true);
                dateOrder.setChecked(false);
                MoviesAdapter adapter = (MoviesAdapter) mFavoriteList.getAdapter();
                adapter.orderByTitle();
            }
        });

        dateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateOrder.setChecked(true);
                titleOrder.setChecked(false);
                MoviesAdapter adapter = (MoviesAdapter) mFavoriteList.getAdapter();
                adapter.orderByYear();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        MoviesAdapter moviesAdapter = new MoviesAdapter(this, mDao.listAll());
        mFavoriteList.setAdapter(moviesAdapter);
    }
}
