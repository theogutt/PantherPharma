package dal;

import dal.Objekter.Indholdsstof;
import dal.Objekter.Test;

import java.sql.*;
import java.util.List;

public class IndholdsstofDAO implements IDAO{

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?"
                + "user=s185103&password=A6fE9rT4KIhs53G05jsqL");
    }

    public void create(Test objekt) throws DALException {
        Indholdsstof stof = (Indholdsstof)objekt;

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO indholdsstoffer (navn) VALUES (?);");
            statement.setString(1, stof.getName());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Test get(int id) throws DALException {
        Test stof;

        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM indholdsstoffer WHERE stofID = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            stof = new Indholdsstof(id, resultSet.getString(2));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stof;
    }

    public List<Test> getList() throws DALException {
        return null;
    }

    public void update(Test object) throws DALException {

    }

    public void delete(int id) throws DALException {

    }
}
