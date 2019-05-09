package dal.DAO;
import java.util.List;

public interface IDAO<E> {
    void create(E objekt) throws DALException;

    E get(int id) throws DALException;

    List<E> getList() throws DALException;

    void update(E objekt) throws DALException;

    void delete(int id) throws DALException;

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
