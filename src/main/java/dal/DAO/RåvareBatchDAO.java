package dal.DAO;

import dal.DTO.MaybeUseless.IRåvareBatch;
import dal.DTO.RåvareBatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RåvareBatchDAO implements IRåvareBatchDAO {

    private static final String url = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(url);
    }

    @Override
    public void create(IRåvareBatch objekt) throws IDAO.DALException {
        RåvareBatch batch = (RåvareBatch) objekt;

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO råvareBatch (stofID, mængde, producent, genbestil) VALUES (? ,? , ?, ?);");
            statement.setInt(1, batch.getIndholdsstof());
            statement.setDouble(2, batch.getMængde());
            statement.setString(3, batch.getProducent());
            statement.setBoolean(4, batch.isGenbestil());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IRåvareBatch get(int id) throws IDAO.DALException {
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
    public List<IRåvareBatch> getList() throws IDAO.DALException {

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

        return null;
        //return stoffer;
    }

    @Override
    public void update(IRåvareBatch object) throws IDAO.DALException {

        RåvareBatch råvareBatch = (RåvareBatch) object;

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE råvareBatch SET stofID = ?, mængde = ?, producent = ?, genbestil = ? WHERE råvareBacthID = ?;");

            statement.setInt(1,råvareBatch.getIndholdsstof());
            statement.setDouble(2, råvareBatch.getMængde());
            statement.setString(3, råvareBatch.getProducent());
            statement.setBoolean(4, råvareBatch.isGenbestil());
            statement.setInt(5, råvareBatch.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) throws IDAO.DALException {

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE råvareBatch WHERE råvareBacthID = ?;");

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
