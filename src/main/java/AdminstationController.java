import dal.DAO.IDAO;
import dal.DAO.IUserDAO;
import dal.DAO.UserDAO;
import dal.DTO.MaybeUseless.IUser;
import dal.DTO.UserDTO;

public class AdminstationController {
    IDAO<IUser> brugerForbindelse = new UserDAO();
    IUser bruger = new UserDTO();

    public void tjek(int id) throws IDAO.DALException{
        brugerForbindelse.get(id);

    }
}
