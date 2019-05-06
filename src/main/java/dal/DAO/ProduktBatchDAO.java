package dal.DAO;

import dal.DTO.MaybeUseless.IProduktBatch;
import dal.DTO.ProduktBatch;
import dal.DTO.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduktBatchDAO implements IProduktBacthDAO {

    private static final String url = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(url);
    }

    @Override
    public void create(IProduktBatch produkt) throws IDAO.DALException{
        ProduktBatch produktBatch = (ProduktBatch) produkt;
        try(Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO produktBatch (produktBatchID, dato, opskriftIDs) VALUES (?,?,?)");
            statement.setInt(1, produktBatch.getId());
            statement.setString(2, produktBatch.getDato());
            statement.setInt(3, produktBatch.getOpskriftID());

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
        int opskriftID = 0;
        String dato = "";
        ArrayList<Integer> ravareBatchID = new ArrayList<Integer>();
        ArrayList<Integer> ravareMengde = new ArrayList<Integer>();


        try(Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM produktBatch WHERE produktBatchID = ?");
            statement.setInt(1, produktBatchID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            opskriftID = resultSet.getInt("opskriftID");
            dato = resultSet.getString("dato");

            PreparedStatement statement1 = connection.prepareStatement
                    ("SELECT * FROM produkt_råvarer WHERE prouktBatchID = ?");
            statement1.setInt(1,produktBatchID);
            ResultSet resultSet1 = statement1.executeQuery();
            resultSet1.next();

            for (int i = 0 ; resultSet1.next() ; i++){
                ravareBatchID.add(resultSet1.getInt("råvareBatchID"));
                ravareMengde.add(resultSet1.getInt("mængde"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ProduktBatch produktBatch = new ProduktBatch(produktBatchID, dato, opskriftID, ravareBatchID, ravareMengde);
        return produktBatch;
    }

    public List<IProduktBatch> getList(){



        return
    }

    public void update(IProduktBatch produktBatch){

    }

    public void delete(int produktBatchID){

    }
}
