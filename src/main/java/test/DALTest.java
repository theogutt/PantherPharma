package test;

import dal.DAO.*;
import dal.DTO.Indholdsstof;
import dal.DTO.MaybeUseless.IIndholdsstof;
import dal.DTO.MaybeUseless.IOpskrift;
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
            //create, read, update, delete tests
            indholdsstof();
            opskrift();
            produktBatch();
            råvareBatch();
            //Den store test
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
        //tester om dataen er blevet slettet
        if(indholdsstofDAO.get(1)!=null){
            fail();
        }
    }
    public void opskrift() throws IDAO.DALException {
        //Opretter Indholdsstof
        IIndholdsstof Hypromellose  = new Indholdsstof(2,"Hypromellose ");
        IIndholdsstof Talcum = new Indholdsstof(3,"Talcum");
        //Indsætter indholdstofferne i databasen
        indholdsstofDAO.create(Hypromellose);
        indholdsstofDAO.create(Talcum);
        //laver lister til opskrift
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
        //Indsætter opskrift i databasen
        IOpskrift receivedOpskrift = OpskriftDAO.create(Sildenafil);
        //tester om vi får samme data tilbage
        assertEquals(Sildenafil.getId(),receivedOpskrift.getId());
        assertEquals(Sildenafil.getNavn(), receivedOpskrift.getNavn());
        for(int i = 0; i<Sildenafil.getIndholdsStoffer().size();i++){
            assertEquals(Sildenafil.getIndholdsStoffer().get(i),receivedOpskrift.getIndholdsStoffer().get(i));
        }
        for(int i = 0; i<Sildenafil.getMaengde().size();i++){
            assertEquals(Sildenafil.getMaengde().get(i),receivedOpskrift.getMaengde().get(i));
        }
        for(int i = 0; i<Sildenafil.getAktiv().size();i++){
            assertEquals(Sildenafil.getAktiv().get(i),receivedOpskrift.getAktiv().get(i));
        }
        assertEquals(Sildenafil.getOpbevaringstid(),receivedOpskrift.getOpbevaringsstid());

        //sletter i testen oprettede data
        indholdsstofDAO.delete(2);
        indholdsstofDAO.delete(3);
        OpskriftDAO.delete(1);
        //---------nedenstående skal lige verificeres------------------------------------------
        //tester om dataen er blevet slettet
        if(indholdsstofDAO.get(2)!=null){
            fail();
        }
        if(indholdsstofDAO.get(2)!=null){
            fail();
        }
        if(OpskriftDAO.get(1)!=null){
            fail();
        }
        //---------ovenstående skal lige verificeres------------------------------------------
    }
    public void produktBatch(){

    }
    public void råvareBatch(){

    }
}
