package dal.DAO;

import dal.ConnectionController;
import dal.DTO.MaybeUseless.IRåvareBatch;
import dal.DTO.RåvareBatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RåvareBatchDAO implements IDAO<IRåvareBatch> {

    ConnectionController connectionController = new ConnectionController();


    @Override
    public int create(IRåvareBatch batch) throws SQLException {
        Connection connection = connectionController.createConnection();

        int id = -1;
        try {
            connection.setAutoCommit(false);//transaction

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO råvareBatch (stofID, mængde, producent) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, batch.getIndholdsstof());
            statement.setDouble(2, batch.getMængde());
            statement.setString(3, batch.getProducent());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                id = rs.getInt(1);

            connection.commit();//transaction
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
        return id;
    }

    @Override
    public IRåvareBatch get(int id) throws SQLException {
        IRåvareBatch råvareBatch = new RåvareBatch();
        Connection connection = connectionController.createConnection();
        try {
            connection.setAutoCommit(false);//transaction

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM råvareBatch WHERE råvareBacthID = ?;");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                råvareBatch.setId(id);
                råvareBatch.setIndholdsstof(resultSet.getInt(2));
                råvareBatch.setMængde(resultSet.getDouble(3));
                råvareBatch.setProducent(resultSet.getString(4));
            }
            connection.commit();//transaction
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
        return råvareBatch;
    }

    @Override
    public List<IRåvareBatch> getList() throws SQLException {

        List<IRåvareBatch> stoffer = new ArrayList<>();
        Connection connection = connectionController.createConnection();
        try  {
            connection.setAutoCommit(false);//transaction

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM råvareBatch;");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                stoffer.add(new RåvareBatch(
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                resultSet.getInt(3),
                                resultSet.getString(4)));
            }
            connection.commit();//transaction
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();

        return stoffer;
    }

    @Override
    public int update(IRåvareBatch råvareBatch) throws SQLException {

        Connection connection = connectionController.createConnection();

        try {
            connection.setAutoCommit(false);//transaction

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE råvareBatch SET stofID = ?, mængde = ?, producent = ? WHERE råvareBacthID = ?;");

            statement.setInt(1, råvareBatch.getIndholdsstof());
            statement.setDouble(2, råvareBatch.getMængde());
            statement.setString(3, råvareBatch.getProducent());
            statement.setInt(4, råvareBatch.getId());
            statement.executeUpdate();

            connection.commit();//transaction
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
        return 0;
    }

    @Override
    public void delete(int id) throws SQLException {

        Connection connection = connectionController.createConnection();

        try {
            connection.setAutoCommit(false);//transaction

            PreparedStatement statement1 = connection.prepareStatement(
                    "DELETE FROM produkt_råvare WHERE råvareBacthID = ?;");

            statement1.setInt(1, id);
            statement1.executeUpdate();

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM råvareBatch WHERE råvareBacthID = ?;");

            statement.setInt(1, id);
            statement.executeUpdate();


            connection.commit();//transaction
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
    }
}
