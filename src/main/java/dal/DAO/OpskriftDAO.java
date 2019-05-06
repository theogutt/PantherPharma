package dal.DAO;


import dal.DTO.Indholdsstof;
import dal.DTO.MaybeUseless.IOpskrift;
import dal.DTO.Opskrift;
import dal.DTO.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpskriftDAO implements IOpskriftDAO{

    private static final String url = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?user=s185103&password=A6fE9rT4KIhs53G05jsqL";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(url);
    }

    public void create(IOpskrift objekt) throws IDAO.DALException {

        Opskrift opskrift = (Opskrift) objekt;

        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO opskrifter (navn, opbevaringstid) VALUES (?, ?);");

            statement.setString(1, opskrift.getNavn());
            statement.setInt(2, opskrift.getOpbevaringstid());

            statement.executeUpdate();

            ArrayList<Indholdsstof> ihs = opskrift.getIndholdsStoffer();
            ArrayList<Integer> maengde = opskrift.getMaengde();
            ArrayList<Boolean> aktiv = opskrift.getAktiv();

            for (int i = 0;i < ihs.size();i++){
                PreparedStatement statement1 = c.prepareStatement("INSERT INTO opskrift_indhold (opskriftID, stofID, maengde, aktiv) VALUES (?, ?, ?, ?)");
                statement1.setInt(1, opskrift.getId());
                statement1.setInt(2, ihs.get(i).getId());
                statement1.setInt(3, maengde.get(i));
                statement1.setBoolean(4, aktiv.get(i));
            }

        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }

    public IOpskrift get(int id) throws IDAO.DALException{

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

            }

            opskrift = new Opskrift(id, name, stof, amount,active, expdate);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

        return opskrift;
    }

    @Override
    public List<IOpskrift> getList() throws IDAO.DALException{

        List<Test> opskrifter = new ArrayList<>();

        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("SELECT * FROM opskrifer");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                opskrifter.add(get(resultSet.getInt("opskriftID")));
            }


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

        return opskrifter;
    }

    public void update(IOpskrift objekt) throws IDAO.DALException{

        Opskrift opskrift = (Opskrift) objekt;

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

    public void delete(int id) throws IDAO.DALException{

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
