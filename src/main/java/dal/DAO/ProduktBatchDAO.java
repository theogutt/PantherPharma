package dal.DAO;

import dal.ConnectionController;
import dal.DTO.MaybeUseless.IProduktBatch;
import dal.DTO.ProduktBatch;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduktBatchDAO implements IDAO<IProduktBatch> {

    ConnectionController connectionController = new ConnectionController();


    @Override
    public void create(IProduktBatch produkt) throws SQLException {
        ProduktBatch produktBatch = (ProduktBatch) produkt;
        Connection connection = connectionController.createConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO produktBatch (produktBatchID, dato, opskriftIDs, antal) VALUES (?,?,?,?);");
            statement.setInt(1, produktBatch.getId());
            statement.setString(2, produktBatch.getDato());
            statement.setInt(3, produktBatch.getOpskriftID());
            statement.setInt(4, produktBatch.getAntal());
            statement.executeUpdate();

            PreparedStatement statement1 = connection.prepareStatement
                    ("INSERT INTO produkt_råvarer (produktBatchID, råvareBatchID, mængde) VALUES (?, ?, ?);");
            statement1.setInt(1, produktBatch.getId());
            PreparedStatement statement2 = connection.prepareStatement(
                    "UPDATE råvareBatch SET mængde = (SELECT mængde FROM råvareBatch WHERE råvareBatchID = ?) - ? WHERE råvareBacthID = ?;");

            for (int i = 0 ; i < produktBatch.getRavareBatchIDs().size() ; i++) {
                statement1.setInt(2, produktBatch.getRavareBatchIDs().get(i));
                statement1.setDouble(3, produktBatch.getRavareMengde().get(i));
                statement1.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
    }

    public IProduktBatch get(int produktBatchID) throws SQLException {
        int opskriftID = 0, antal = 0;
        String dato = "";
        ArrayList<Integer> ravareBatchID = new ArrayList<>();
        ArrayList<Double> ravareMengde = new ArrayList<>();
        Connection connection = connectionController.createConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM produktBatch WHERE produktBatchID = ?;");
            statement.setInt(1, produktBatchID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            opskriftID = resultSet.getInt("opskriftID");
            dato = resultSet.getString("dato");
            antal = resultSet.getInt("antal");

            PreparedStatement statement1 = connection.prepareStatement
                    ("SELECT * FROM produkt_råvarer WHERE prouktBatchID = ?;");
            statement1.setInt(1,produktBatchID);
            ResultSet resultSet1 = statement1.executeQuery();

            while(resultSet1.next()){
                ravareBatchID.add(resultSet1.getInt("råvareBatchID"));
                ravareMengde.add(resultSet1.getDouble("mængde"));
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        connection.close();
        return new ProduktBatch(produktBatchID, dato, opskriftID, ravareBatchID, ravareMengde, antal);
    }

    public List<IProduktBatch> getList() throws SQLException {
        int opskriftID, produktBatchID, antal;
        String dato;
        ArrayList<Integer> ravareBatchID = new ArrayList<>();
        ArrayList<Double> ravareMengde = new ArrayList<>();
        ArrayList<IProduktBatch> produktBatches = new ArrayList<>();
        Connection connection = connectionController.createConnection();
        try  {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM produktBatch");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                produktBatchID = resultSet.getInt("produktBatchID");
                opskriftID = resultSet.getInt("opskriftID");
                dato = resultSet.getString("dato");
                antal = resultSet.getInt("antal");
                produktBatches.add(new ProduktBatch(produktBatchID, dato, opskriftID, ravareBatchID, ravareMengde, antal));
            }

            PreparedStatement statement1;
            for (IProduktBatch produktBatch : produktBatches) {
                statement1 = connection.prepareStatement
                        ("SELECT * FORM produkt_råvarer WHERE produktBatchID = ?");
                statement1.setInt(1, produktBatch.getId());
                ResultSet resultSet1 = statement1.executeQuery();
                while (resultSet1.next()) {
                    ravareBatchID.add(resultSet1.getInt("råvareBatchID"));
                    ravareMengde.add(resultSet1.getDouble("mængde"));
                }
                produktBatch.setRavareBatchIDs(ravareBatchID);
                produktBatch.setRavareMengde(ravareMengde);

            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        connection.close();
        return produktBatches;
    }

    public void update(IProduktBatch produktBatch) throws SQLException {
        Connection connection = connectionController.createConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE produktBatch SET produktBatchID = ?, opskriftID = ?, dato = ?, antal = ? WHERE userId = ?;");
            statement.setInt(1, produktBatch.getId());
            statement.setInt(2, produktBatch.getOpskriftID());
            statement.setString(3, produktBatch.getDato());
            statement.setInt(4, produktBatch.getAntal());
            statement.executeUpdate();

            PreparedStatement statement1 = connection.prepareStatement
                    ("DELETE FROM produkt_råvare WHERE produktBatchID = ?;");
            statement1.setInt(1, produktBatch.getId());
            statement1.executeUpdate();

            PreparedStatement statement2 = connection.prepareStatement
                    ("INSERT INTO produkt_råvarer (produktBatchID, råvareBatchID, mængde) VALUES (?,?,?);");
            statement2.setInt(1, produktBatch.getId());
            for (int i = 0 ; i < produktBatch.getRavareBatchIDs().size() ; i++) {
                statement2.setInt(2, produktBatch.getRavareBatchIDs().get(i));
                statement2.setDouble(3, produktBatch.getRavareMengde().get(i));
                statement2.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
        }
        connection.close();
    }

    public void delete(int produktBatchID) throws SQLException {
        Connection connection = connectionController.createConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM produktBatch WHERE produktBatchID = ?");
            statement.setInt(1, produktBatchID);
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement("DELETE FROM produktBatch WHERE produkt_råvare = ?");
            statement1.setInt(1, produktBatchID);
            statement1.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
    }
}
