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
    public void create(IRåvareBatch batch) throws IDAO.DALException, SQLException {
        Connection connection = connectionController.createConnection();
        try {
            connection.setAutoCommit(false);//transaction

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO råvareBatch (stofID, mængde, producent) VALUES (?,?,?);");
            statement.setInt(1, batch.getIndholdsstof());
            statement.setDouble(2, batch.getMængde());
            statement.setString(3, batch.getProducent());
            statement.executeUpdate();
            connection.commit();//transaction
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    @Override
    public IRåvareBatch get(int id) throws IDAO.DALException, SQLException {
        RåvareBatch råvareBatch = null;
        Connection connection = connectionController.createConnection();
        try {
            connection.setAutoCommit(false);//transaction

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM råvareBatch WHERE råvareBacthID = ?;");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            råvareBatch = new RåvareBatch(
                    id,
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4));
            connection.commit();//transaction
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
        return råvareBatch;
    }

    @Override
    public List<IRåvareBatch> getList() throws IDAO.DALException,SQLException {

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
            e.printStackTrace();
        }
        connection.close();

        return stoffer;
    }

    @Override
    public void update(IRåvareBatch råvareBatch) throws IDAO.DALException, SQLException {

        Connection connection = connectionController.createConnection();

        try {
            connection.setAutoCommit(false);//transaction

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE råvareBatch SET stofID = ?, mængde = ?, producent = ? WHERE råvareBacthID = ?;");

            statement.setInt(1,råvareBatch.getIndholdsstof());
            statement.setDouble(2, råvareBatch.getMængde());
            statement.setString(3, råvareBatch.getProducent());
            statement.setInt(4, råvareBatch.getId());
            statement.executeUpdate();
            connection.commit();//transaction
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    @Override
    public void delete(int id) throws IDAO.DALException, SQLException {

        Connection connection = connectionController.createConnection();

        try {
            connection.setAutoCommit(false);//transaction

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE råvareBatch WHERE råvareBacthID = ?;");

            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();//transaction
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }
}
