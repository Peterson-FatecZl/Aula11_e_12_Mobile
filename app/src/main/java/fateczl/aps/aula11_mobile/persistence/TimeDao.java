package fateczl.aps.aula11_mobile.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fateczl.aps.aula11_mobile.model.Time;

public class TimeDao implements ITimeDao, ICRUDDao<Time> {
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase dataBase;

    public TimeDao(Context context){
        this.context = context;
    }
    @Override
    public TimeDao open() throws SQLException {
        gDao = new GenericDao(context);
        dataBase = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Time time) throws SQLException {
        ContentValues contentValues = getContentValues(time);
        dataBase.insert("TIME", null, contentValues);
    }

    @Override
    public int update(Time time) throws SQLException {
        ContentValues contentValues = getContentValues(time);
        int retorno = dataBase.update("TIME", contentValues, "codigo = " + time.getCodigo(), null);
        return retorno;
    }

    @Override
    public void delete(Time time) throws SQLException {
        dataBase.delete("TIME","codigo = " + time.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Time findOne(Time time) throws SQLException {
        String sqlQuery = "SELECT * FROM TIME WHERE codigo = " + time.getCodigo();
        Cursor cursor = dataBase.rawQuery(sqlQuery, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        }
        cursor.close();
        return time;
    }

    @SuppressLint("Range")
    @Override
    public List<Time> findAll() throws SQLException {
        List<Time> listaDeRetorno = new ArrayList<>();
        String sqlQuery = "SELECT * FROM TIME WHERE codigo";
        Cursor cursor = dataBase.rawQuery(sqlQuery, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            listaDeRetorno.add(time);
            cursor.moveToNext();
        }
        cursor.close();
        return listaDeRetorno;
    }

    private static ContentValues getContentValues(Time time) {
        ContentValues valoresNaTabela = new ContentValues();
        valoresNaTabela.put("codigo", time.getCodigo());
        valoresNaTabela.put("nome", time.getNome());
        valoresNaTabela.put("cidade", time.getCidade());

        return  valoresNaTabela;
    }
}
