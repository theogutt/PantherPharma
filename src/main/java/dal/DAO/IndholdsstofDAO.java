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
    public void create(IIndholdsstof stof) throws DALException {

        try (Connection connection = connectionController.createConnection()) {
            connection.setAutoCommit(false);//transaction
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO indholdsstoffer (navn, genbestil) VALUES (?,?);");
            statement.setString(1, stof.getName());
            statement.setBoolean(2,stof.getGenbestil());
            statement.executeUpdate();
            connection.commit();//transaction

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IIndholdsstof get(int id) throws DALException {

        IIndholdsstof stof = new Indholdsstof();

        try (Connection connection = connectionController.createConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM indholdsstoffer WHERE stofID = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                stof = new Indholdsstof(id, resultSet.getString(2), resultSet.getBoolean(3));
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stof;
    }

    @Override
    public List<IIndholdsstof> getList() throws DALException {

        List<IIndholdsstof> stoffer = new ArrayList<>();

        try (Connection connection = connectionController.createConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM indholdsstoffer;");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                stoffer.add(
                        new Indholdsstof(
                        resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getBoolean(3)));
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stoffer;
    }

    @Override
    public void update(IIndholdsstof object) throws DALException {

        Indholdsstof stof = (Indholdsstof) object;

        try (Connection connection = connectionController.createConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE indholdsstoffer SET navn = ?, genbestil = ? WHERE stofID = ?;");

            statement.setString(1, stof.getName());
            statement.setBoolean(2, stof.getGenbestil());
            statement.setInt(3, stof.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) throws DALException {

        try (Connection connection = connectionController.createConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM indholdsstoffer WHERE stofID = ?;");

            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
