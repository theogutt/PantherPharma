package dal.DAO;

import dal.DTO.MaybeUseless.IIndholdsstof;
import dal.DTO.MaybeUseless.IRåvareBatch;
import dal.DTO.MaybeUseless.IUser;
import dal.DTO.RåvareBatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RåvareBatchDAO implements IDAO<IRåvareBatch> {

    private static final String url =
            "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";

    @Override
    public void create(IRåvareBatch objekt, Connection connection) throws SQLException {
        RåvareBatch batch = (RåvareBatch) objekt;


        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO råvareBatch (stofID, mængde, producent) VALUES (? ,? , ?);");
        statement.setInt(1, batch.getIndholdsstof());
        statement.setDouble(2, batch.getMængde());
        statement.setString(3, batch.getProducent());
        statement.executeUpdate();

    }

    @Override
    public IRåvareBatch get(int id, Connection connection) throws SQLException {
        RåvareBatch råvareBatch = null;

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM råvareBatch WHERE råvareBacthID = ?;");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        råvareBatch = new RåvareBatch(
                id,
                resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getString(4));

        return råvareBatch;
    }

    @Override
    public List<IRåvareBatch> getList(Connection connection) throws SQLException {

        List<IRåvareBatch> stoffer = new ArrayList<>();


        PreparedStatement statement = connection.prepareStatement("SELECT * FROM råvareBatch;");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            stoffer.add(new RåvareBatch(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4)));
        }

        return stoffer;
    }

    @Override
    public void update(IRåvareBatch object, Connection connection) throws SQLException {

        RåvareBatch råvareBatch = (RåvareBatch) object;


        PreparedStatement statement = connection.prepareStatement(
                "UPDATE råvareBatch SET stofID = ?, mængde = ?, producent = ? WHERE råvareBacthID = ?;");

        statement.setInt(1, råvareBatch.getIndholdsstof());
        statement.setDouble(2, råvareBatch.getMængde());
        statement.setString(3, råvareBatch.getProducent());
        statement.setInt(4, råvareBatch.getId());
        statement.executeUpdate();

    }

    @Override
    public void delete(int id, Connection connection) throws SQLException {


        PreparedStatement statement = connection.prepareStatement(
                "DELETE råvareBatch WHERE råvareBacthID = ?;");

        statement.setInt(1, id);
        statement.executeUpdate();

    }
}
