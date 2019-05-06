package dal;

import dal.Objekter.Opskrift;
import dal.Objekter.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpskriftDAO {

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?"
                + "user=s185103&password=A6fE9rT4KIhs53G05jsqL");
    }

    void createOpskrift(Opskrift opskrift) throws IDAO.DALException{
        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO opskrifter (navn, opbevaringstid) VALUES (?, ?);");

            statement.setString(1, opskrift.getNavn());
            statement.setInt(2, opskrift.getOpbevaringstid());

            statement.executeUpdate();


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }

    public Test getOpskrift(int id) throws IDAO.DALException{

        Opskrift opskrift = null;

        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM opskrifter WHERE opskriftID = ?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();


            String name = resultSet.getString("navn");

            int expdate = resultSet.getInt("opbevaringstid");



            PreparedStatement statement1 = c.prepareStatement("SELECT * FROM opskrif_indhold WHERE  opskriftID = ?");

            statement1.setInt(1, id);

            ResultSet resultSet1 = statement1.executeQuery();

            ArrayList<indholdsstof> stof = new ArrayList<indholdsstof>();

            while (resultSet1.next()){
                stof.add(getindholdsstof());
            }

            ArrayList<Boolean> active = new ArrayList<>();

            while (resultSet1.next()){
                active.add(getAktiv);
            }

            ArrayList<Integer> amount = new ArrayList<>();

            while (resultSet1.next()){
                amount.add(getMeangde);
            }

            opskrift = new Opskrift(id, name, stof, amount,active, expdate);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

        return opskrift;
    }

    public List<Test> getOpskriftList() throws IDAO.DALException{

        try (Connection c = createConnection()){
            Statement statement = c.createStatement();

            String sqlGetOpskriftList = String.format(" rghtjykyl");

            statement.executeUpdate(sqlGetOpskriftList);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

        return ;
    }

    void updateOpskrift(Test objekt) throws IDAO.DALException{

        try (Connection c = createConnection()){
            Statement statement = c.createStatement();

            String sqlUpdateOpskrift = String.format("vrgdsythufyåæp");

            statement.executeUpdate(sqlUpdateOpskrift);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

    }

    void deleteOpskrift(int id) throws IDAO.DALException{

        try (Connection c = createConnection()){
            Statement statement = c.createStatement();

            String sqlDeleteOpskrift = String.format("fgdstuhyijkl");

            statement.executeUpdate(sqlDeleteOpskrift);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

    }


}
