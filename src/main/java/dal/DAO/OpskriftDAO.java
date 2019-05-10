package dal.DAO;


import dal.DTO.MaybeUseless.IOpskrift;
import dal.DTO.Opskrift;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpskriftDAO implements IDAO<IOpskrift> {

    ConnectionController connectionController = new ConnectionController();


    public void create(IOpskrift opskrift) throws IDAO.DALException {

        try (Connection c = connectionController.createConnection()) {
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO opskrifter (navn, opbevaringstid, ibrug) VALUES (?,?,?);");

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

        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }
    @Override
    public IOpskrift get(int id) throws IDAO.DALException {

        IOpskrift opskrift;

        try (Connection c = connectionController.createConnection()) {

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


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

        return opskrift;
    }

    @Override
    public List<IOpskrift> getList() throws IDAO.DALException {

        List<IOpskrift> opskrifter = new ArrayList<>();
        ArrayList<Integer> stof = new ArrayList<>();
        ArrayList<Double> amount = new ArrayList<>();
        ArrayList<Boolean> active = new ArrayList<>();

        try (Connection c = connectionController.createConnection()) {
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

        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

        return opskrifter;
    }

    public void update(IOpskrift opskrift) throws IDAO.DALException {

        ArrayList<Integer> indholdsStoffer = opskrift.getIndholdsStoffer();
        ArrayList<Double> maengde = opskrift.getMaengde();
        ArrayList<Boolean> aktiv = opskrift.getAktiv();

        try (Connection c = connectionController.createConnection()) {
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

        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }

    public void delete(int id) throws IDAO.DALException {

        try (Connection c = connectionController.createConnection()) {

            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM opskrifer WHERE opskriftID = ?;");
            PreparedStatement statement2 = c.prepareStatement(
                    "DELETE FROM opskrift_indhold WHERE opskriftID = ?;");
            statement.setInt(1, id);
            statement2.setInt(1, id);
            statement.executeUpdate();
            statement2.executeUpdate();

        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }
}
