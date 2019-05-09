package dal.DAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IDAO<E> {
    void create(E objekt, Connection connection) throws DALException, SQLException;

    E get(int id, Connection connection) throws DALException, SQLException;

    List<E> getList(Connection connection) throws DALException, SQLException;

    void update(E objekt, Connection connection) throws DALException, SQLException;

    void delete(int id, Connection connection) throws DALException, SQLException;

    public class DALException extends Exception {

        //Til Java serialisering...
        private static final long serialVersionUID = 7355418246336739229L;

        public DALException(String msg, Throwable e) {
            super(msg, e);
        }

        public DALException(String msg) {
            super(msg);
        }

    }
}
