package fateczl.aps.aula11_mobile.controller;

import java.sql.SQLException;
import java.util.List;

import fateczl.aps.aula11_mobile.model.Time;
import fateczl.aps.aula11_mobile.persistence.TimeDao;

public class TimeController implements IController<Time> {

    public final TimeDao timeDao;

    public TimeController(TimeDao timeDao){
        this.timeDao = timeDao;
    }
    @Override
    public Time buscar(Time time) throws SQLException {
        if(timeDao.open() == null){
            timeDao.open();
        }
        return timeDao.findOne(time);
    }

    @Override
    public void inserir(Time time) throws SQLException {
        if(timeDao.open() == null){
            timeDao.open();
        }
        timeDao.insert(time);
        timeDao.close();
    }

    @Override
    public void modificar(Time time) throws SQLException {
        if(timeDao.open() == null){
            timeDao.open();
        }
        timeDao.update(time);
        timeDao.close();
    }

    @Override
    public void deletar(Time time) throws SQLException {
        if(timeDao.open() == null){
            timeDao.open();
        }
        timeDao.delete(time);
        timeDao.close();
    }

    @Override
    public List<Time> listar() throws SQLException {
        if(timeDao.open() == null){
            timeDao.open();
        }
        return timeDao.findAll();
    }

}
