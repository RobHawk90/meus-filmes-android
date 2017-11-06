package robhawk.com.br.meusfilmes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Robert on 05/11/2017.
 * Configura e gerencia a criacao do banco de dados local.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "my_movies_robhawk", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS movies ("
                + " id INTEGER NOT NULL"
                + ", title TEXT NOT NULL"
                + ", overview TEXT"
                + ", releaseDate TEXT NOT NULL"
                + ", adult INTEGER"
                + ", voteCount INTEGER NOT NULL"
                + ", voteAverage REAL NOT NULL"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // primeira versao, nao existem mudancas
    }

}
