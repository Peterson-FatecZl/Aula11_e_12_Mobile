package fateczl.aps.aula11_mobile.persistence;

import java.sql.SQLException;

public interface IJogadorDao {
    public JogadorDao open() throws SQLException;
    public void close();

}
