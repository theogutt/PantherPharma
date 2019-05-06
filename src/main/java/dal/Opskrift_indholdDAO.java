package dal;

import dal.IDAO;
import dal.Objekter.Test;

import java.sql.*;
import java.util.List;

public class Opskrift_indholdDAO {



    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?"
                + "user=s185103&password=A6fE9rT4KIhs53G05jsqL");
    }

    void create(Test objekt) throws IDAO.DALException{
        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("INSERT INTO opskrift_indhold (opskriftID, stofID, mængde, aktiv) VALUES (?, ?, ?, ?)");

            statement.setInt(1,opskrift_indhold.getOpskriftID());
            statement.setInt(2,opskrift_indhold.getStofID());
            statement.setInt(3,opskrift_indhold.getMaengde());
            statement.setBoolean(4, opskrift_indhold.getAktiv);

            ResultSet resultSet = statement.executeQuery();


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }

    Test get(int id) throws IDAO.DALException{
        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("UPDATE opskrifter SET navn = ? SET opbevaringstid = ? WHERE opskriftID = ?");

            statement.setString(1,opskrift.getNavn());
            statement.setInt(2,opskrift.getOpbevaringstid());


            ResultSet resultSet = statement.executeQuery();


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
        return ;
    }

    List<Test> getList() throws IDAO.DALException{
        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("UPDATE opskrifter SET navn = ? SET opbevaringstid = ? WHERE opskriftID = ?");

            statement.setString(1,opskrift.getNavn());

            statement.setInt(3,opskrift.getId());

            ResultSet resultSet = statement.executeQuery();


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
        return ;
    }

    void update(Test objekt) throws IDAO.DALException{
        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("UPDATE opskrifter SET navn = ? SET opbevaringstid = ? WHERE opskriftID = ?");


            statement.setInt(2,opskrift.getOpbevaringstid());
            statement.setInt(3,opskrift.getId());

            ResultSet resultSet = statement.executeQuery();


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }

    void delete(int id) throws IDAO.DALException{
        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("UPDATE opskrifter SET navn = ? SET opbevaringstid = ? WHERE opskriftID = ?");

            statement.setString(1,opskrift.getNavn());
            statement.setInt(2,opskrift.getOpbevaringstid());
            statement.setInt(3,opskrift.getId());




        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }


}
