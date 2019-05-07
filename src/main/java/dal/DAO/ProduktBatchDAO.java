package dal.DAO;

import dal.DTO.MaybeUseless.IProduktBatch;
import dal.DTO.ProduktBatch;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduktBatchDAO implements IProduktBacthDAO {

    private static final String url = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(url);
    }

    @Override
    public void create(IProduktBatch produkt){
        ProduktBatch produktBatch = (ProduktBatch) produkt;
        try(Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO produktBatch (produktBatchID, dato, opskriftIDs, antal) VALUES (?,?,?,?)");
            statement.setInt(1, produktBatch.getId());
            statement.setString(2, produktBatch.getDato());
            statement.setInt(3, produktBatch.getOpskriftID());
            statement.setInt(4, produktBatch.getAntal());
            statement.executeUpdate();

            PreparedStatement statement1 = connection.prepareStatement
                    ("INSERT INTO produkt_råvarer (produktBatchID, råvareBatchID, mængde) VALUES (?, ?, ?) ");
            statement1.setInt(1, produktBatch.getId());
            for (int i = 0 ; i < produktBatch.getRavareBatchIDs().size() ; i++) {
                statement1.setInt(2, produktBatch.getRavareBatchIDs().get(i));
                statement1.setDouble(3, produktBatch.getRavareMengde().get(i));
                statement1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public IProduktBatch get(int produktBatchID){
        int opskriftID = 0, antal = 0;
        String dato = "";
        ArrayList<Integer> ravareBatchID = new ArrayList<>();
        ArrayList<Double> ravareMengde = new ArrayList<>();

        try(Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM produktBatch WHERE produktBatchID = ?");
            statement.setInt(1, produktBatchID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            opskriftID = resultSet.getInt("opskriftID");
            dato = resultSet.getString("dato");
            antal = resultSet.getInt("antal");

            PreparedStatement statement1 = connection.prepareStatement
                    ("SELECT * FROM produkt_råvarer WHERE prouktBatchID = ?");
            statement1.setInt(1,produktBatchID);
            ResultSet resultSet1 = statement1.executeQuery();

            while(resultSet1.next()){
                ravareBatchID.add(resultSet1.getInt("råvareBatchID"));
                ravareMengde.add(resultSet1.getDouble("mængde"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ProduktBatch(produktBatchID, dato, opskriftID, ravareBatchID, ravareMengde, antal);
    }

    public List<IProduktBatch> getList() {
        int opskriftID, produktBatchID, antal;
        String dato;
        ArrayList<Integer> ravareBatchID = new ArrayList<>();
        ArrayList<Double> ravareMengde = new ArrayList<>();
        ArrayList<IProduktBatch> produktBatches = new ArrayList<>();

        try (Connection connection = createConnection()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produktBatches;
    }

    public void update(IProduktBatch produktBatch){
        try (Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE produktBatch SET produktBatchID = ?, opskriftID = ?, dato = ?, antal = ? WHERE userId = ?");
            statement.setInt(1, produktBatch.getId());
            statement.setInt(2, produktBatch.getOpskriftID());
            statement.setString(3, produktBatch.getDato());
            statement.setInt(4, produktBatch.getAntal());
            statement.executeUpdate();

            PreparedStatement statement1 = connection.prepareStatement
                    ("DELETE FROM produkt_råvare WHERE produktBatchID = ?");
            statement1.setInt(1, produktBatch.getId());
            statement1.executeUpdate();

            PreparedStatement statement2 = connection.prepareStatement
                    ("INSERT INTO produkt_råvarer (produktBatchID, råvareBatchID, mængde) VALUES (?,?,?) ");
            statement2.setInt(1, produktBatch.getId());
            for (int i = 0 ; i < produktBatch.getRavareBatchIDs().size() ; i++) {
                statement2.setInt(2, produktBatch.getRavareBatchIDs().get(i));
                statement2.setDouble(3, produktBatch.getRavareMengde().get(i));
                statement2.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int produktBatchID){
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM produktBatch WHERE produktBatchID = ?");
            statement.setInt(1, produktBatchID);
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement("DELETE FROM produktBatch WHERE produkt_råvare = ?");
            statement1.setInt(1, produktBatchID);
            statement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
