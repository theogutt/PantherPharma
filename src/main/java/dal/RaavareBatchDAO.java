package dal;

import dal.Objekter.Indholdsstof;
import dal.Objekter.RåvareBatch;
import dal.Objekter.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaavareBatchDAO implements IDAO {

    private Connection createConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?"
            + "user=s185103&password=A6fE9rT4KIhs53G05jsqL");
}

    @Override
    public void create(Test objekt) throws IDAO.DALException {
        RåvareBatch batch = (RåvareBatch) objekt;

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO råvareBatch (råvareBacthID, mængde, producent, genbestil) VALUES (? ,? , ?, ?);");
            statement.setInt(1, batch.getIndholdsstof());
            statement.setInt(2, batch.getMængde());
            statement.setString(3, batch.getProducent());
            statement.setBoolean(4, batch.isGenbestil());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Test get(int id) throws IDAO.DALException {
        RåvareBatch råvareBatch = null;

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM råvareBatch WHERE råvareBacthID = ?;");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            råvareBatch = new RåvareBatch(
                    id,
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getBoolean(5));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return råvareBatch;
    }

    @Override
    public List<Test> getList() throws IDAO.DALException {

        List<RåvareBatch> stoffer = new ArrayList<>();

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM råvareBatch;");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                stoffer.add(new RåvareBatch(
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                resultSet.getInt(3),
                                resultSet.getString(4),
                                resultSet.getBoolean(5)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stoffer;
    }

    @Override
    public void update(Test object) throws IDAO.DALException {

        Indholdsstof stof = (Indholdsstof) object;

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE indholdsstoffer SET navn = ? WHERE stofID = ?;");

            statement.setString(1, stof.getName());
            statement.setInt(2, stof.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) throws IDAO.DALException {

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE indholdsstoffer WHERE stofID = ?;");

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
