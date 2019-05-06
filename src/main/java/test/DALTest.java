package test;

import dal.DAO.*;
import dal.DTO.Indholdsstof;
import dal.DTO.MaybeUseless.IIndholdsstof;
import dal.DTO.Opskrift;
import org.junit.Test;

import java.util.ArrayList;

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
            indholdsstof();
            //Opretter Indholdsstof
            IIndholdsstof Hypromellose  = new Indholdsstof(2,"Hypromellose ");
            IIndholdsstof Talcum = new Indholdsstof(3,"Talcum");
            //Indsætter indholdstofferne i databasen
            indholdsstofDAO.create(Hypromellose);
            indholdsstofDAO.create(Talcum);
            //sletter i testen oprettede data
            indholdsstofDAO.delete(1); //sletter i testen oprettede data
            indholdsstofDAO.delete(1);
            ArrayList<IIndholdsstof>indholdsstofList = new ArrayList<>();
            indholdsstofList.add(Hypromellose);
            indholdsstofList.add(Talcum);
            ArrayList<Double>mængdeList = new ArrayList<>();
            mængdeList.add(1.3);
            mængdeList.add(1.0);
            ArrayList<Boolean>aktivList = new ArrayList<>();
            aktivList.add(false);
            aktivList.add(false);
            //Opretter opskrift
            Opskrift Sildenafil = new Opskrift(1,"Sildenafil",indholdsstofList,mængdeList,aktivList,30);
        }
        catch (IDAO.DALException e) {
            e.printStackTrace();
            fail();
        }
    }
    public void indholdsstof() throws IDAO.DALException {
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
}
