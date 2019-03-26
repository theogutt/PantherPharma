import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataController implements IDataController{

    private final static String url ="jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185121?user=s185121&password=k3o9drYe3miV1AQ40cAVh";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection( url);
    }

    @Override
    public void opretIngrediens() throws DALException {

    }

    @Override
    public void opdaterIngrediens() throws DALException {

    }

    @Override
    public void sletIngrediens() throws DALException {

    }

    @Override
    public void opretRåvarebatch() throws DALException {

    }

    @Override
    public void opdaterRåvarebatch() throws DALException {

    }

    @Override
    public void sletRåvarebatch() throws DALException {

    }

    @Override
    public void opretProduktbatch() throws DALException {

    }

    @Override
    public void opdaterProduktbatch() throws DALException {

    }

    @Override
    public void sletProduktbatch() throws DALException {

    }
}
