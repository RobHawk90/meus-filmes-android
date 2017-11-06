package robhawk.com.br.meusfilmes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import robhawk.com.br.meusfilmes.adapter.MoviesAdapter;
import robhawk.com.br.meusfilmes.db.MovieDao;
import robhawk.com.br.meusfilmes.model.Movie;
import robhawk.com.br.meusfilmes.utils.Internet;
import robhawk.com.br.meusfilmes.utils.Toaster;
import robhawk.com.br.meusfilmes.web.Api;
import robhawk.com.br.meusfilmes.web.Callback;

/**
 * Controla a pesquisa de filmes por titulo, acessando a API implementada, validando
 * se ha conexao com a Internet
 */
public class SearchMoviesActivity extends AppCompatActivity {

    private Api mApi;
    private EditText mSearchInput;
    private ListView mMoviesList;
    private SwipeRefreshLayout mLoadMoviesSwipe;
    private MovieDao mDao;
    private AlertDialog mConfirmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.find_movies);

        mApi = new Api();
        mDao = new MovieDao(this);
        mConfirmDialog = setupConfirmDialog();

        mMoviesList = findViewById(R.id.movies_list);
        mLoadMoviesSwipe = findViewById(R.id.load_movies);
        mSearchInput = findViewById(R.id.search_input);

        setupEvents();
    }

    /**
     * Configura eventos de clique
     */
    private void setupEvents() {
        // clique em pesquisar do teclado virtual
        mSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                search();
                return false;
            }
        });

        // clique em cada filme da lista que foi buscado da API
        mMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
                Movie movie = (Movie) adapter.getItemAtPosition(position);
                prepareToSave(movie);
            }
        });

        // cancela o swipe
        mLoadMoviesSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLoadMoviesSwipe.setRefreshing(false);
            }
        });
    }

    /**
     * Constroi e configura a dialog de confirmacao de salvar filme
     *
     * @return AlertDialog - dialog de confirmacao
     */
    private AlertDialog setupConfirmDialog() {
        return new AlertDialog.Builder(this)
                .setTitle(R.string.add_to_favorites)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    /**
     * Configura evento de confirmacao para salvar filme selecionado.
     *
     * @param movie - item selecionado
     */
    private void prepareToSave(final Movie movie) {
        Movie existent = mDao.findById(movie.id);

        if (existent == null)
            mConfirmDialog.setMessage("Deseja adicionar '" + movie.title + "' à sua lista de filmes favoritos?");
        else
            mConfirmDialog.setMessage("'" + movie.title + "' já foi adicionado à sua lista de favoritos, deseja atualiza-lo?");

        mConfirmDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDao.insertOrUpdate(movie);
                Toaster.success(SearchMoviesActivity.this, "'" + movie.title + "' foi salvo");
                dialog.dismiss();
            }
        });

        mConfirmDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // cria menu na action bar para abrir a lista de filmes salvos
        MenuItem myMoviesMenu = menu.add(R.string.app_name);
        myMoviesMenu.setIcon(R.drawable.ic_my_movies);
        myMoviesMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        myMoviesMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                openMyMovies();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void openMyMovies() {
        Intent myMovies = new Intent(this, MyMoviesActivity.class);
        startActivity(myMovies);
    }

    /**
     * Tenta obter filmes por titulo acessando a API.
     */
    private void search() {
        String title = mSearchInput.getText().toString();

        // cancela a busca se nao passou nas validacoes
        if (isAnythingWrong(title)) return;

        // notifica o download da lista de filmes
        mLoadMoviesSwipe.setRefreshing(true);

        // acessa a API para a busca do titulo de filme
        mApi.search(title, new Callback<List<MovieDb>>(this) {

            public void onSuccess(List<MovieDb> movies) {
                MoviesAdapter moviesAdapter = new MoviesAdapter(SearchMoviesActivity.this, Movie.convertList(movies));
                mMoviesList.setAdapter(moviesAdapter);
                mLoadMoviesSwipe.setRefreshing(false);
            }

            public void onError(String error) {
                Toast.makeText(SearchMoviesActivity.this, error, Toast.LENGTH_SHORT).show();
                mLoadMoviesSwipe.setRefreshing(false);
            }
        });
    }

    private boolean isAnythingWrong(String title) {
        // verifica conexao com a Internet
        if (Internet.isOffline(this)) {
            Toaster.warning(this, "Conecte seu dispositivo à Internet para pesquisar vídeos!");
            return true;
        }

        // verifica se ha caracteres suficiente para a busca
        if (title.trim().length() < 3) {
            Toaster.warning(this, "Informe mais de 3 caracteres!");
            return true;
        }

        return false;
    }

}
