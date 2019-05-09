import dal.DAO.IDAO;
import dal.DAO.IIndholdsstofDAO;
import dal.DAO.IndholdsstofDAO;
import dal.DTO.*;
import dal.DTO.MaybeUseless.IIndholdsstof;

public class Constructor {
    IDAO<IIndholdsstof> indholdsstofDAO = new IndholdsstofDAO();


    public void opstart() throws IDAO.DALException {

        //Opretter Indholdsstof
        IIndholdsstof Calciumhydrogenphospath = new Indholdsstof(1, "Calciumhydrogenphosphat dihydrat", false);
        //Indsætter indholdstofferne i databasen
        indholdsstofDAO.create(Calciumhydrogenphospath);
        //Prøver at hente det ned fra databasen
        IIndholdsstof received = indholdsstofDAO.get(1);
        //Tester om navnene stemmer overens
        System.out.println("pls" + Calciumhydrogenphospath.getName() + received.getName());

        //sletter i testen oprettede data
        indholdsstofDAO.delete(1);

    }
}
