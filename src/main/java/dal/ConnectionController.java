package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionController {
    //            "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185121?user=s185121&password=k3o9drYe3miV1AQ40cAVh";
    private static final String url =
            "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";
    public Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(url);
    }
}
