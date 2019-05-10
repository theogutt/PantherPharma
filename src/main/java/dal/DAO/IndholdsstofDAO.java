package dal.DAO;

import dal.ConnectionController;
import dal.DTO.Indholdsstof;
import dal.DTO.MaybeUseless.IIndholdsstof;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IndholdsstofDAO implements IDAO<IIndholdsstof> {

    ConnectionController connectionController = new ConnectionController();


    @Override
    public int create(IIndholdsstof stof) throws DALException, SQLException {
        Connection connection = connectionController.createConnection();
        int id = -1;

        try {
            connection.setAutoCommit(false);//transaction
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO indholdsstoffer (navn, genbestil) VALUES (?,?);");
            statement.setString(1, stof.getName());
            statement.setBoolean(2, stof.getGenbestil());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();

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
    public IIndholdsstof get(int id) throws DALException, SQLException {

        IIndholdsstof stof = new Indholdsstof();
        Connection connection = connectionController.createConnection();
        try {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM indholdsstoffer WHERE stofID = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                stof = new Indholdsstof(id, resultSet.getString(2), resultSet.getBoolean(3));

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
        return stof;
    }

    @Override
    public List<IIndholdsstof> getList() throws DALException, SQLException {
        Connection connection = connectionController.createConnection();
        List<IIndholdsstof> stoffer = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM indholdsstoffer;");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                stoffer.add(
                        new Indholdsstof(
                                resultSet.getInt(1),
                                resultSet.getString(2), resultSet.getBoolean(3)));
            }

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();

        return stoffer;
    }

    @Override
    public void update(IIndholdsstof stof) throws DALException, SQLException {

        Connection connection = connectionController.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE indholdsstoffer SET navn = ?, genbestil = ? WHERE stofID = ?;");

            statement.setString(1, stof.getName());
            statement.setBoolean(2, stof.getGenbestil());
            statement.setInt(3, stof.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
    }

    @Override
    public void delete(int id) throws DALException, SQLException {
        Connection connection = connectionController.createConnection();
        try {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM indholdsstoffer WHERE stofID = ?;");

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.close();
    }
}
