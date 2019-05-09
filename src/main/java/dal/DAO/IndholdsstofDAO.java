package dal.DAO;

import dal.DTO.Indholdsstof;
import dal.DTO.MaybeUseless.IIndholdsstof;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IndholdsstofDAO implements IDAO<IIndholdsstof> {

    private static final String url =
            "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";

    @Override
    public void create(IIndholdsstof stof, Connection connection) throws SQLException {


        PreparedStatement statement = connection.prepareStatement("INSERT INTO indholdsstoffer (navn, genbestil) VALUES (?,?);");
        statement.setString(1, stof.getName());
        statement.setBoolean(2, stof.getGenbestil());
        statement.executeUpdate();

    }

    @Override
    public IIndholdsstof get(int id, Connection connection) throws SQLException {

        IIndholdsstof stof;

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM indholdsstoffer WHERE stofID = ?;");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        stof = new Indholdsstof(id, resultSet.getString(2), resultSet.getBoolean(3));

        return stof;
    }

    @Override
    public List<IIndholdsstof> getList(Connection connection) throws SQLException {

        List<IIndholdsstof> stoffer = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM indholdsstoffer;");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            stoffer.add(
                    new Indholdsstof(
                            resultSet.getInt(1),
                            resultSet.getString(2), resultSet.getBoolean(3)));
        }

        return stoffer;
    }

    @Override
    public void update(IIndholdsstof object, Connection connection) throws SQLException {

        Indholdsstof stof = (Indholdsstof) object;


        PreparedStatement statement = connection.prepareStatement(
                "UPDATE indholdsstoffer SET navn = ?, genbestil = ? WHERE stofID = ?;");

        statement.setString(1, stof.getName());
        statement.setBoolean(2, stof.getGenbestil());
        statement.setInt(3, stof.getId());
        statement.executeUpdate();
    }

    @Override
    public void delete(int id, Connection connection) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "DELETE indholdsstoffer WHERE stofID = ?;");

        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
