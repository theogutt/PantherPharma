package dal.DAO;

import dal.DTO.ProduktBatch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProduktBatchDAO {

    private static final String url = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(url);
    }

    public void createProduktBatch(ProduktBatch produktBatch) throws IDAO.DALException {
        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO produktBatch (opskriftID, dato) VALUES (?, ?);");

            statement.setInt(1, produktBatch.getOpskriftID());
            statement.setString(2, produktBatch.getDato());

            statement.executeUpdate();


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }

}
