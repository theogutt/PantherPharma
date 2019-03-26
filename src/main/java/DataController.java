import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataController implements IDataController{

    private final static String url ="jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185121?user=s185121&password=k3o9drYe3miV1AQ40cAVh";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection( url);
    }
}
