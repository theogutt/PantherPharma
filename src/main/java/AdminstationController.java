import dal.DAO.IDAO;
import dal.DAO.UserDAO;
import dal.DTO.MaybeUseless.IUser;
import dal.DTO.UserDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminstationController {

    IDAO<IUser> brugerForbindelse = new UserDAO();
    IUser bruger = new UserDTO();
    List<String> roler = new ArrayList<>();
    Boolean[] tilgang = new Boolean[4];

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185118?"
                + "user=s185118&password=SNX64wUCCqEHKNVwEwumg");
    }

    public Boolean[] tjek(int id) throws IDAO.DALException{

        try (Connection connection = createConnection()) {

            bruger = brugerForbindelse.get(id, connection);
            roler = bruger.getRoles();
            for (int i = 0; i < roler.size(); i++){
                if (roler.get(i).equals("Admin")) tilgang[0] = true;
                if (roler.get(i).equals("Farmaceut")) tilgang[1] = true;
                if (roler.get(i).equals("Produktionsleder")) tilgang[2] = true;
                if (roler.get(i).equals("Laborant")) tilgang[3] = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tilgang;
    }
}
