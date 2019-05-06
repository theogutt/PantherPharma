package dal.DAO;

import dal.DTO.Indholdsstof;
import dal.DTO.Opskrift;

import java.sql.*;
import java.util.ArrayList;

public class OpskriftDAO {

    private static final String url = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(url);
    }

    public void createOpskrift(Opskrift opskrift) throws IDAO.DALException {
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

    public Opskrift getOpskrift(int id) throws IDAO.DALException{

        Opskrift opskrift = null;

        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("SELECT * FROM opskrifter WHERE opskriftID = ?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            String name = resultSet.getString("navn");

            int expdate = resultSet.getInt("opbevaringstid");

            PreparedStatement statement1 = c.prepareStatement("SELECT * FROM opskrif_indhold WHERE  opskriftID = ?");

            statement1.setInt(1, id);

            ResultSet resultSet1 = statement1.executeQuery();

            ArrayList<Indholdsstof> stof = new ArrayList<>();
            ArrayList<Boolean> active = new ArrayList<>();
            ArrayList<Integer> amount = new ArrayList<>();

            while (resultSet1.next()){
                //stof.add(opskrift_indholdDAO.getindholdsstof());
                //active.add(opskrift_indholdDAO.getAktiv);
                //amount.add(opskrift_indholdDAO.getMeangde);
            }

            opskrift = new Opskrift(id, name, stof, amount,active, expdate);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

        return opskrift;
    }

    public ArrayList<Opskrift> getOpskriftList() throws IDAO.DALException{

        ArrayList<Opskrift> opskrifter = null;

        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("SELECT * FROM opskrifer");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                opskrifter.add(getOpskrift(resultSet.getInt("opskriftID")));
            }


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

        return opskrifter;
    }

    public void updateOpskrift(Opskrift opskrift) throws IDAO.DALException{

        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("UPDATE opskrifter SET navn = ? SET opbevaringstid = ? WHERE opskriftID = ?");

            statement.setString(1,opskrift.getNavn());
            statement.setInt(2,opskrift.getOpbevaringstid());
            statement.setInt(3,opskrift.getId());

            ResultSet resultSet = statement.executeQuery();


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }

    public void deleteOpskrift(int id) throws IDAO.DALException{

        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("DELETE * FROM opskrifer WHERE opskriftID = ?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            PreparedStatement statement1 = c.prepareStatement("INSERT INTO gamle_opskrifer");


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }
}
