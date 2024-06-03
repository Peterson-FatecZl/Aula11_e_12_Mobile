package fateczl.aps.aula11_mobile.controller;

import java.sql.SQLException;
import java.util.List;

import fateczl.aps.aula11_mobile.model.Jogador;
import fateczl.aps.aula11_mobile.persistence.JogadorDao;

public class JogadorController implements IController<Jogador> {

    public final JogadorDao jogadorDao;
    public JogadorController(JogadorDao jogadorDao){
        this.jogadorDao = jogadorDao;
    }
    @Override
    public Jogador buscar(Jogador jogador) throws SQLException {
        if (jogadorDao.open() == null){
            jogadorDao.open();
        }
        return jogadorDao.findOne(jogador);
    }

    @Override
    public void inserir(Jogador jogador) throws SQLException {
        if (jogadorDao.open() == null){
            jogadorDao.open();
        }
        jogadorDao.insert(jogador);
        jogadorDao.close();
    }

    @Override
    public void modificar(Jogador jogador) throws SQLException {
        if (jogadorDao.open() == null){
            jogadorDao.open();
        }
        jogadorDao.update(jogador);
        jogadorDao.close();
    }

    @Override
    public void deletar(Jogador jogador) throws SQLException {
        if (jogadorDao.open() == null){
            jogadorDao.open();
        }
        jogadorDao.delete(jogador);
        jogadorDao.close();
    }

    @Override
    public List<Jogador> listar() throws SQLException {
        if(jogadorDao.open() == null){
            jogadorDao.open();
        }
        return jogadorDao.findAll();
    }
}
