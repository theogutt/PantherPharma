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

    public List<Test> getList() {
        int opskriftID = 0, produktBatchID = 0;
        String dato = "";
        ArrayList<Integer> ravareBatchID = new ArrayList<>();
        ArrayList<Integer> ravareMengde = new ArrayList<>();
        ArrayList<ProduktBatch> produktBatches = new ArrayList<>();

        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM produktBatch");
            ResultSet resultSet = statement.executeQuery();
            for (int i = 0 ; resultSet.next() ; i++){
                produktBatchID = resultSet.getInt("produktBatchID");
                opskriftID = resultSet.getInt("opskriftID");
                dato = resultSet.getString("dato");
                produktBatches.add(new ProduktBatch(produktBatchID, dato, opskriftID, ravareBatchID, ravareMengde));
            }

            PreparedStatement statement1;
            for (int i = 0 ; i < produktBatches.size() ; i++){
                statement1 = connection.prepareStatement
                        ("SELECT * FORM produkt_råvarer WHERE produktBatchID = ?");
                statement1.setInt(1, produktBatches.get(i).getId());
                ResultSet resultSet1 = statement1.executeQuery();
                for (int j = 0 ; resultSet1.next() ; j++){
                    ravareBatchID.add(resultSet1.getInt("råvareBatchID"));
                    ravareMengde.add(resultSet1.getInt("mængde"));
                }
                produktBatches.get(i).setRavareBatchIDs(ravareBatchID);
                produktBatches.get(i).setRavareMengde(ravareMengde);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produktBatches;
    }

    public void update(Test produktBatch){

    }

    public void delete(int produktBatchID){
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM produktBatch WHERE produktBatchID = ?");
            statement.setInt(1, produktBatchID);
            PreparedStatement statement1 = connection.prepareStatement("DELETE FROM produktBatch WHERE produkt_råvare = ?");
            statement1.setInt(1, produktBatchID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
