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
    public int create(IProduktBatch produkt) throws SQLException {
        ProduktBatch produktBatch = (ProduktBatch) produkt;
        Connection connection = connectionController.createConnection();
        int id = -1;
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO produktBatch (opskriftID , dato, tabletterAntal, batchStatus) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, produktBatch.getOpskriftID());
            statement.setString(2, produktBatch.getDato());
            statement.setInt(3, produktBatch.getAntal());
            statement.setString(4, produktBatch.getStatus());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                id = rs.getInt(1);

            PreparedStatement statement1 = connection.prepareStatement
                    ("INSERT INTO produkt_råvare (produktBatchID, råvareBacthID, mængde) VALUES (?,?,?);");
            statement1.setInt(1, id);
            for (int i = 0; i < produktBatch.getRavareBatchIDs().size(); i++) {
                statement1.setInt(2, produktBatch.getRavareBatchIDs().get(i));
                statement1.setDouble(3, produktBatch.getRavareMengde().get(i));
                statement1.executeUpdate();
            }

            double a = 0.0;
            ResultSet mængdeset;


            PreparedStatement mængdestatement = connection.prepareStatement(
                    "SELECT mængde FROM råvareBatch WHERE råvareBacthID = ?");
            PreparedStatement statement2 = connection.prepareStatement(
                    "UPDATE råvareBatch SET mængde = ? - ? WHERE råvareBacthID = ?;");

            for (int i = 0; i < produktBatch.getRavareBatchIDs().size(); i++) {

                mængdestatement.setInt(1, produktBatch.getRavareBatchIDs().get(i));
                mængdestatement.executeQuery();
                mængdeset = mængdestatement.getResultSet();

                if (mængdeset.next())
                    a = mængdeset.getDouble(1);

                statement2.setDouble(1, a);
                statement2.setDouble(2, produktBatch.getRavareMengde().get(i));
                statement2.setInt(3, produktBatch.getRavareBatchIDs().get(i));
                statement2.executeUpdate();
            }

            PreparedStatement statement3 = connection.prepareStatement(
                    "SELECT SUM(mængde) AS totalMængde FROM råvareBatch WHERE stofID = (SELECT stofID FROM råvareBatch WHERE råvareBacthID = ?);");
            for (int i = 0; i < produktBatch.getRavareBatchIDs().size(); i++) {
                statement3.setInt(1, produktBatch.getRavareBatchIDs().get(i));
                ResultSet totalMængde = statement3.executeQuery();
            }

            PreparedStatement statement4 = connection.prepareStatement(
                    "SELECT MAX(mængde) FROM opskrift_indhold WHERE stofID = (SELECT stofID FROM råvareBatch WHERE råvareBacthID = ?);");
            PreparedStatement statement5 = connection.prepareStatement(
                    "UPDATE indholdsstoffer SET genbestil = TRUE WHERE stofID = (SELECT stofID FROM råvareBatch WHERE råvareBacthID = ?);");

            for (int i = 0; i < produktBatch.getRavareBatchIDs().size(); i++) {
                statement3.setInt(1, produktBatch.getRavareBatchIDs().get(i));
                ResultSet totalMængde = statement3.executeQuery();

                statement4.setInt(1, produktBatch.getRavareBatchIDs().get(i));
                ResultSet opskrift = statement4.executeQuery();

                totalMængde.next();
                opskrift.next();
                if (totalMængde.getInt(1) * 2 < opskrift.getInt(1)) {
                    statement5.setInt(1, id);
                    statement2.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
        return id;
    }

    public IProduktBatch get(int produktBatchID) throws SQLException {
        int opskriftID = 0, antal = 0;
        String status = "", dato = "";
        ArrayList<Integer> ravareBatchID = new ArrayList<>();
        ArrayList<Double> ravareMengde = new ArrayList<>();
        Connection connection = connectionController.createConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM produktBatch WHERE produktBatchID = ?;");
            statement.setInt(1, produktBatchID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            opskriftID = resultSet.getInt(2);
            dato = resultSet.getString(3);
            antal = resultSet.getInt(4);
            status = resultSet.getString(5);

            PreparedStatement statement1 = connection.prepareStatement
                    ("SELECT * FROM produkt_råvare WHERE produktBatchID = ?;");
            statement1.setInt(1, produktBatchID);
            ResultSet resultSet1 = statement1.executeQuery();

            while (resultSet1.next()) {
                ravareBatchID.add(resultSet1.getInt(2));
                ravareMengde.add(resultSet1.getDouble(3));
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
        return new ProduktBatch(produktBatchID, dato, opskriftID, ravareBatchID, ravareMengde, antal, status);
    }

    public List<IProduktBatch> getList() throws SQLException {
        int opskriftID, produktBatchID, antal;
        String status, dato;
        ArrayList<Integer> ravareBatchID = new ArrayList<>();
        ArrayList<Double> ravareMengde = new ArrayList<>();
        ArrayList<IProduktBatch> produktBatches = new ArrayList<>();

        Connection connection = connectionController.createConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM produktBatch;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                produktBatchID = resultSet.getInt(1);
                opskriftID = resultSet.getInt(2);
                dato = resultSet.getString(3);
                antal = resultSet.getInt(4);
                status = resultSet.getString(5);
                produktBatches.add(new ProduktBatch(produktBatchID, dato, opskriftID, ravareBatchID, ravareMengde, antal, status));
            }

            PreparedStatement statement1;
            for (IProduktBatch produktBatch : produktBatches) {
                statement1 = connection.prepareStatement
                        ("SELECT * FROM produkt_råvare WHERE produktBatchID = ?;");
                statement1.setInt(1, produktBatch.getId());
                ResultSet resultSet1 = statement1.executeQuery();
                while (resultSet1.next()) {
                    ravareBatchID.add(resultSet1.getInt(2));
                    ravareMengde.add(resultSet1.getDouble(3));
                }
                produktBatch.setRavareBatchIDs(ravareBatchID);
                produktBatch.setRavareMengde(ravareMengde);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
        return produktBatches;
    }

    public int update(IProduktBatch produktBatch) throws SQLException {
        Connection connection = connectionController.createConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE produktBatch SET opskriftID = ?, dato = ?, tabletterAntal = ?, batchStatus = ? WHERE produktBatchID = ?;");
            statement.setInt(1, produktBatch.getOpskriftID());
            statement.setString(2, produktBatch.getDato());
            statement.setInt(3, produktBatch.getAntal());
            statement.setString(4, produktBatch.getStatus());
            statement.setInt(5, produktBatch.getId());
            statement.executeUpdate();

            PreparedStatement statement1 = connection.prepareStatement
                    ("DELETE FROM produkt_råvare WHERE produktBatchID = ?;");
            statement1.setInt(1, produktBatch.getId());
            statement1.executeUpdate();

            PreparedStatement statement2 = connection.prepareStatement
                    ("INSERT INTO produkt_råvare (produktBatchID, råvareBacthID, mængde) VALUES (?,?,?);");
            statement2.setInt(1, produktBatch.getId());
            for (int i = 0; i < produktBatch.getRavareBatchIDs().size(); i++) {
                statement2.setInt(2, produktBatch.getRavareBatchIDs().get(i));
                statement2.setDouble(3, produktBatch.getRavareMengde().get(i));
                statement2.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
        return 0;
    }

    public void delete(int produktBatchID) throws SQLException {
        Connection connection = connectionController.createConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM produkt_råvare WHERE produktBatchID = ?;");
            statement.setInt(1, produktBatchID);
            statement.executeUpdate();

            PreparedStatement statement1 = connection.prepareStatement(
                    "DELETE FROM produktBatch WHERE produktBatchID = ?;");
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
