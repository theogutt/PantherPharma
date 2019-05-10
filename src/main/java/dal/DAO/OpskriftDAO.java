package dal.DAO;


import dal.ConnectionController;
import dal.DTO.MaybeUseless.IOpskrift;
import dal.DTO.Opskrift;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpskriftDAO implements IDAO<IOpskrift> {

    ConnectionController connectionController = new ConnectionController();


    public int create(IOpskrift opskrift) throws IDAO.DALException, SQLException {
        Connection c = connectionController.createConnection();
        int id = -1;
        try {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO opskrifter (navn, opbevaringstid, ibrug) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, opskrift.getNavn());
            statement.setInt(2, opskrift.getOpbevaringstid());
            statement.setBoolean(3,opskrift.getIbrug());
            statement.executeUpdate();

            ArrayList<Integer> ihs = opskrift.getIndholdsStoffer();
            ArrayList<Double> maengde = opskrift.getMaengde();
            ArrayList<Boolean> aktiv = opskrift.getAktiv();
            PreparedStatement statement1 = c.prepareStatement(
                    "INSERT INTO opskrift_indhold (opskriftID, stofID, mængde, aktiv) VALUES (LAST_INSERT_ID(),?,?,?);");

            for (int i = 0; i < ihs.size(); i++) {
                statement1.setInt(1, ihs.get(i));
                statement1.setDouble(2, maengde.get(i));
                statement1.setBoolean(3, aktiv.get(i));
                statement1.executeUpdate();
            }
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();

            id = rs.getInt(1);
            c.commit();
        } catch (SQLException e) {
            c.rollback();
            throw new IDAO.DALException(e.getMessage());
        }
        c.close();
        return id;
    }
    @Override
    public IOpskrift get(int id) throws IDAO.DALException, SQLException {

        IOpskrift opskrift;
        Connection c = connectionController.createConnection();
        try  {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM opskrifter WHERE opskriftID = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            String name = resultSet.getString("navn");
            int expdate = resultSet.getInt("opbevaringstid");
            Boolean ibrug = resultSet.getBoolean(3);

            PreparedStatement statement1 = c.prepareStatement(
                    "SELECT * FROM opskrift_indhold WHERE opskriftID = ?;");
            statement1.setInt(1, id);
            ResultSet resultSet1 = statement1.executeQuery();

            ArrayList<Integer> stof = new ArrayList<>();
            ArrayList<Boolean> active = new ArrayList<>();
            ArrayList<Double> amount = new ArrayList<>();

            while (resultSet1.next()) {
                stof.add(resultSet1.getInt("indholdsStoffer"));
                active.add(resultSet1.getBoolean("aktiv"));
                amount.add(resultSet1.getDouble("mængde"));
            }

            opskrift = new Opskrift(id, name, stof, amount, active, expdate, ibrug);
            c.commit();

        } catch (SQLException e) {
            c.rollback();
            throw new IDAO.DALException(e.getMessage());
        }
        c.close();
        return opskrift;
    }

    @Override
    public List<IOpskrift> getList() throws IDAO.DALException, SQLException {

        List<IOpskrift> opskrifter = new ArrayList<>();
        ArrayList<Integer> stof = new ArrayList<>();
        ArrayList<Double> amount = new ArrayList<>();
        ArrayList<Boolean> active = new ArrayList<>();
        Connection c = connectionController.createConnection();
        try  {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM opskrifer;");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PreparedStatement statement1 = c.prepareStatement(
                        "SELECT * FROM opskrift_indhold WHERE opskriftID = ?;");
                statement1.setInt(1, resultSet.getInt(1));
                ResultSet resultSet1 = statement1.executeQuery();

                while (resultSet1.next()) {
                    stof.add(resultSet1.getInt(2));
                    amount.add(resultSet1.getDouble(3));
                    active.add(resultSet1.getBoolean(4));
                }

                opskrifter.add(new Opskrift(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        stof, amount, active,
                        resultSet.getInt(3),resultSet.getBoolean("ibrug")));

                //Tømmer arraylists
                stof.clear(); amount.clear(); active.clear();
            }
            c.commit();
        } catch (SQLException e) {
            c.rollback();
            throw new IDAO.DALException(e.getMessage());
        }
        c.close();
        return opskrifter;
    }

    public void update(IOpskrift opskrift) throws IDAO.DALException, SQLException {

        ArrayList<Integer> indholdsStoffer = opskrift.getIndholdsStoffer();
        ArrayList<Double> maengde = opskrift.getMaengde();
        ArrayList<Boolean> aktiv = opskrift.getAktiv();
        Connection c = connectionController.createConnection();
        try  {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO opskrifter (navn, opbevaringstid, ibrug) VALUES (?,?,?);");
            statement.setString(1, opskrift.getNavn());
            statement.setInt(2, opskrift.getOpbevaringstid());
            statement.setBoolean(3, opskrift.getIbrug());
            statement.executeUpdate();

            for (int i = 0; i < indholdsStoffer.size(); i++) {
                PreparedStatement statement1 = c.prepareStatement(
                        "INSERT INTO opskrift_indhold (opskriftID, stofID, mængde, aktiv) VALUES (LAST_INSERT_ID(), ?, ?, ?);");
                statement1.setInt(1, indholdsStoffer.get(i));
                statement1.setDouble(2, maengde.get(i));
                statement1.setBoolean(3, aktiv.get(i));
                statement1.executeUpdate();
            }

            PreparedStatement statement2 = c.prepareStatement(
                    "UPDATE opskrifter SET aktiv = FALSE WHERE opskriftID = ?;");
            statement2.setInt(1, opskrift.getId());
            statement2.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            c.rollback();
            throw new IDAO.DALException(e.getMessage());
        }
        c.close();
    }

    public void delete(int id) throws IDAO.DALException, SQLException {
        Connection c = connectionController.createConnection();
        try  {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM opskrifer WHERE opskriftID = ?;");
            PreparedStatement statement2 = c.prepareStatement(
                    "DELETE FROM opskrift_indhold WHERE opskriftID = ?;");
            statement.setInt(1, id);
            statement2.setInt(1, id);
            statement.executeUpdate();
            statement2.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            c.rollback();
            throw new IDAO.DALException(e.getMessage());
        }
        c.close();
    }
}
