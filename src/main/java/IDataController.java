public interface IDataController {

    //ingrediens
    void opretIngrediens() throws DALException;
    void opdaterIngrediens() throws DALException;
    void sletIngrediens() throws DALException;

    //råvarebatch
    void opretRåvarebatch() throws DALException;
    void opdaterRåvarebatch() throws DALException;
    void sletRåvarebatch() throws DALException;

    //produktbatch
    void opretProduktbatch() throws DALException;
    void opdaterProduktbatch() throws DALException;
    void sletProduktbatch() throws DALException;


    class DALException extends Exception {
        //Til Java serialisering...
        private static final long serialVersionUID = 7355418246336739229L;

        public DALException(String msg, Throwable e) {
            super(msg,e);
        }

        public DALException(String msg) {
            super(msg);
        }
    }
}
