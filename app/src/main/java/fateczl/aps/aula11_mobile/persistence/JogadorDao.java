package fateczl.aps.aula11_mobile.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fateczl.aps.aula11_mobile.model.Jogador;
import fateczl.aps.aula11_mobile.model.Time;

public class JogadorDao implements IJogadorDao, ICRUDDao<Jogador> {
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase dataBase;

    public JogadorDao(Context context) {
        this.context = context;
    }

    @Override
    public JogadorDao open() throws SQLException {
        gDao = new GenericDao(context);
        dataBase = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        dataBase.insert("JOGADOR", null, contentValues);
    }

    @Override
    public int update(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        int retorno = dataBase.update("JOGADOR", contentValues, "id = " + jogador.getId(), null);
        return retorno;
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        dataBase.delete("JOGADOR", "id = " + jogador.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        String querySQL = "SELECT j.id, j.nome, j.dataNasc AS Data_de_Nascimento, " +
                "j.altura , j.peso , t.codigo, t.nome AS Time, t.cidade AS Cidade_Time " +
                "FROM JOGADOR AS j, TIME AS t WHERE j.codigo_time = t.codigo " +
                "AND j.id = " + jogador.getId();

        Cursor cursor = dataBase.rawQuery(querySQL, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("Cidade_Time")));
            time.setNome(cursor.getString(cursor.getColumnIndex("Time")));

            jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setDataNasc(cursor.getString(cursor.getColumnIndex("Data_de_Nascimento")));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(time);
        }
        cursor.close();
        return jogador;
    }

    @SuppressLint("Range")
    @Override
    public List findAll() throws SQLException {
        List<Jogador> listaDeJogadores = new ArrayList<>();

        String querySQL = "SELECT j.id, j.nome, j.dataNasc AS Data_de_Nascimento," +
                "j.altura AS Altura_Jogador, j.peso AS Peso_Jogador, t.codigo, t.nome AS Time," +
                "t.cidade AS Cidade_Time  FROM JOGADOR AS j, TIME AS t WHERE (j.codigo_time = t.codigo)";

        Cursor cursor = dataBase.rawQuery(querySQL, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Jogador jogador = new Jogador();
            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("Cidade_Time")));
            time.setNome(cursor.getString(cursor.getColumnIndex("Time")));

            jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setDataNasc(cursor.getString(cursor.getColumnIndex("Data_de_Nascimento")));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("Altura_Jogador")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("Peso_Jogador")));
            jogador.setTime(time);

            listaDeJogadores.add(jogador);
            cursor.moveToNext();
        }
        cursor.close();
        return listaDeJogadores;
    }

    private static ContentValues getContentValues(Jogador jogador) {
        ContentValues valoresNaTabela = new ContentValues();
        valoresNaTabela.put("id", jogador.getId());
        valoresNaTabela.put("nome", jogador.getNome());
        valoresNaTabela.put("dataNasc", jogador.getDataNasc());
        valoresNaTabela.put("altura", jogador.getAltura());
        valoresNaTabela.put("peso", jogador.getPeso());
        valoresNaTabela.put("codigo_time", jogador.getTime().getCodigo());

        return valoresNaTabela;
    }
}
