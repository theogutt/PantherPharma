package dal.DAO;

import dal.DTO.ProduktBatch;
import dal.DTO.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduktBatchDAO implements IDAO {

    private static final String url = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(url);
    }

    public void create(Test produkt){
        ProduktBatch produktBatch = (ProduktBatch) produkt;
        try(Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO produktBatch (produktBatchID, dato, opskriftIDs) VALUES (?,?,?)");
            statement.setInt(1, produktBatch.getId());
            statement.setString(2, produktBatch.getDato());
            statement.setInt(3, produktBatch.getOpskriftID();

            PreparedStatement statement2 = connection.prepareStatement
                    ("INSERT INTO produkt_råvarer (produktBatchID, råvareBatchID, mængde) VALUES (?,?,?) ");
            for (int i = 0 ; i < produktBatch.getRavareBatchIDs().size() ; i++) {
                statement2.setInt(1, produktBatch.getId());
                statement2.setInt(2, produktBatch.getRavareBatchIDs().get(i));
                statement2.setInt(3, produktBatch.getRavareMengde().get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ProduktBatch get(int produktBatchID){
        int opskriftID = 0, ravareBatchID = 0, ravareMengde = 0;
        String dato = "";
        ArrayList<Integer> ravareBatchIDList = new ArrayList<Integer>();
        ArrayList<Integer> ravareMengdeList = new ArrayList<Integer>();

        try(Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM produktBatch WHERE produktBatchID = ?");
            statement.setInt(1, produktBatchID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();



        } catch (SQLException e) {
            e.printStackTrace();
        }


        return
    }

    public List<Test> getList(){



        return
    }

    public void update(Test produktBatch){

    }

    public void delete(int produktBatchID){

    }
}
