package dal;

import dal.Objekter.Test;

import java.util.List;

public interface IDAO {
    void create(Test objekt) throws DALException;

    Test get(int id) throws DALException;

    List<Test> getList() throws DALException;

    void update(Test objekt) throws DALException;

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
