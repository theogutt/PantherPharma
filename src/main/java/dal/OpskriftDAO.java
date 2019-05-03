package dal;

import dal.Objekter.Opskrift;
import dal.Objekter.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OpskriftDAO {

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185103?"
                + "user=s185103&password=A6fE9rT4KIhs53G05jsqL");
    }

    void createOpskrift(Opskrift opskrift) throws IDAO.DALException{
        try (Connection c = createConnection()){
            Statement statement = c.createStatement();

            String sqlCreateOpskrift = String.format();

            statement.executeUpdate(sqlCreateOpskrift);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }
    }

    public Test getOpskrift(int id) throws IDAO.DALException{


        try (Connection c = createConnection()){
            Statement statement = c.createStatement();

            String sqlCreateOpskrift = String.format();

            statement.executeUpdate(sqlCreateOpskrift);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

        return ;
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

            String sqlCreateOpskrift = String.format();

            statement.executeUpdate(sqlCreateOpskrift);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

    }

    void deleteOpskrift(int id) throws IDAO.DALException{

        try (Connection c = createConnection()){
            Statement statement = c.createStatement();

            String sqlCreateOpskrift = String.format();

            statement.executeUpdate(sqlCreateOpskrift);


        } catch (SQLException e) {
            throw new IDAO.DALException(e.getMessage());
        }

    }


}
