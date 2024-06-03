package fateczl.aps.aula11_mobile.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GenericDao extends SQLiteOpenHelper {

    private static final String DATABASE = "AULA11FUTEBOL.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_TIME =
            "CREATE TABLE TIME (" +
                    "codigo INT NOT NULL PRIMARY KEY, " +
                    "nome VARCHAR(30) NOT NULL," +
                    "cidade VARCHAR(20) NOT NULL );";
    private static final String CREATE_TABLE_JOGADOR =
            "CREATE TABLE JOGADOR (" +
                    "id INT NOT NULL PRIMARY KEY, " +
                    "nome VARCHAR(30) NOT NULL," +
                    "dataNasc VARCHAR(10) NOT NULL," +
                    "altura REAL NOT NULL," +
                    "peso REAL NOT NULL," +
                    "codigo_time INT NOT NULL," +
                    "FOREIGN KEY(codigo_time) REFERENCES TIME(codigo));";

    public GenericDao(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TIME);
        db.execSQL(CREATE_TABLE_JOGADOR);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS JOGADOR");
            db.execSQL("DROP TABLE IF EXISTS TIME");
            onCreate(db);
        }
    }
}
