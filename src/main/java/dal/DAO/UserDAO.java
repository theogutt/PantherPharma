package dal.DAO;

import dal.ConnectionController;
import dal.DTO.MaybeUseless.IUser;
import dal.DTO.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODONE Rename class so it matches your study-number
public class UserDAO implements IDAO<IUser>{
    //TODONE Make a connection to the database
    ConnectionController connectionController = new ConnectionController();


    @Override
    public void create(IUser user) throws DALException, SQLException {
        Connection c = connectionController.createConnection();
        try {
            c.setAutoCommit(false);//transaction

            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO bruger (brugerNavn) VALUES (?);");
            statement.setString(1, user.getUserName());
            statement.executeUpdate();

            for (int n=0 ; n < user.getRoles().size() ; n++) {
                statement = c.prepareStatement(
                        "INSERT INTO roller (brugerID, rolle) VALUES (LAST_INSERT_ID(), ?);");
                statement.setString(1, user.getRoles().get(n));
                statement.executeUpdate();
            }
            c.commit();//transaction
        } catch (SQLException e) {
            c.rollback();
            e.printStackTrace();
        }
        c.close();
    }

    @Override
    public IUser get(int userId) throws DALException, SQLException {
        Connection c = connectionController.createConnection();
        IUser user = new UserDTO();

        try {
            c.setAutoCommit(false);//transaction

            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM bruger NATURAL JOIN roller WHERE brugerID = ?;");
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();



            if(resultSet.next())
                user = createUserDTO(resultSet);
            c.commit();//transaction

        } catch (SQLException e) {
            c.rollback();
            throw new DALException(e.getMessage());
        }
        c.close();
        return user;
    }



    @Override
    public List<IUser> getList() throws DALException, SQLException {
        List<IUser> userList = new ArrayList<>();

        Connection c = connectionController.createConnection();
        try {
            c.setAutoCommit(false);//transaction

            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM bruger NATURAL JOIN roller ORDER BY brugerID;");
            ResultSet resultSet = statement.executeQuery();



            while(resultSet.next()){
                userList.add(createUserDTO(resultSet));
            }
            c.commit();//transaction


        } catch (SQLException e) {
            c.rollback();
            throw new DALException(e.getMessage());
        }
        c.close();
        return userList;
    }


    @Override
    public void update(IUser user) throws DALException, SQLException {
        Connection c = connectionController.createConnection();
        try {
            c.setAutoCommit(false);//transaction

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE bruger SET brugerNavn = ? WHERE brugerID = ?;");
            statement.setString(1, user.getUserName());
            statement.setInt(2, user.getUserId());
            statement.executeUpdate();

            statement = c.prepareStatement(
                    "DELETE FROM roller WHERE brugerID = ?;");
            statement.setInt(1, user.getUserId());
            statement.executeUpdate();

            for (int n=0 ; n < user.getRoles().size() ; n++) {
                statement = c.prepareStatement(
                        "INSERT INTO roller (brugerID, rolle) VALUES (?, ?);");
                statement.setInt(1, user.getUserId());
                statement.setString(2, user.getRoles().get(n));
                statement.executeUpdate();
            }
            c.commit();//transaction

        } catch (SQLException e) {
            c.rollback();
            e.printStackTrace();
        }
        c.close();
    }

    @Override
    public void delete(int userId) throws DALException, SQLException {
        Connection c = connectionController.createConnection();
        try {
            c.setAutoCommit(false);//transaction

            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM bruger WHERE brugerID = ?;");
            statement.setInt(1, userId);
            statement.executeUpdate();
            c.commit();//transaction
        } catch (SQLException e) {
            c.rollback();
            e.printStackTrace();
        }
        c.close();
    }

    private UserDTO createUserDTO(ResultSet resultSet) throws SQLException {
        UserDTO newUser = new UserDTO();

        int newUserID = resultSet.getInt("userID");
        newUser.setUserId(newUserID);

        String userName = resultSet.getString("userName");
        newUser.setUserName(userName);

        List<String> jobArray = new ArrayList<>();
        String job;

        while(!resultSet.isAfterLast() && (resultSet.getInt("userID") == newUserID)){
            job = resultSet.getString("job");
            jobArray.add(job);

            resultSet.next();
        }
        resultSet.previous(); // metoden starter med resultSet.next(), så hvis den køres flere gange i træk skal cursoren en gang tilbage her.

        newUser.setRoles(jobArray);

        return newUser;
    }
}
