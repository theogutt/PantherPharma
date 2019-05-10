import dal.DAO.ConnectionController;
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

    ConnectionController connectionController = new ConnectionController();

    public Boolean[] tjek(int id) throws IDAO.DALException {

        bruger = brugerForbindelse.get(id);
        roler = bruger.getRoles();
        for (int i = 0; i < roler.size(); i++) {
            if (roler.get(i).equals("Admin")) tilgang[0] = true;
            if (roler.get(i).equals("Farmaceut")) tilgang[1] = true;
            if (roler.get(i).equals("Produktionsleder")) tilgang[2] = true;
            if (roler.get(i).equals("Laborant")) tilgang[3] = true;
        }

        return tilgang;
    }
}
