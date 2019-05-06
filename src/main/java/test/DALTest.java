package test;

import dal.DAO.*;
import dal.DTO.Indholdsstof;
import dal.DTO.MaybeUseless.IIndholdsstof;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DALTest {
    IDAO indholdsstofDAO = new IndholdsstofDAO();
    IDAO OpskriftDAO = new OpskriftDAO();
    IDAO produktBatchDAO = new ProduktBatchDAO();
    IDAO råvareBatchDAO = new RåvareBatchDAO();

    @Test
    public void test(){
        try{
            //Opretter Indholdsstof
            Indholdsstof Calciumhydrogenphospath = new Indholdsstof(1,"Calciumhydrogenphosphat dihydrat");
            //Indsætter indholdstofferne i databasen
            indholdsstofDAO.create(Calciumhydrogenphospath);
            //Prøver at hente det ned fra databasen
            IIndholdsstof received = indholdsstofDAO.get(1);
            //Tester om navnene stemmer overens
            assertEquals(Calciumhydrogenphospath.getName(), received.getName());



            //sletter i testen oprettede data
            indholdsstofDAO.delete(1);

        }
        catch (IDAO.DALException e) {
            e.printStackTrace();
            fail();
        }
    }
}
