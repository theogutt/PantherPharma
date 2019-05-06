package test;

import dal.DAO.*;
import dal.DTO.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DALTest {

    //TODO lav råvarebatch test, update tests, en system test og få programmet til at overholde tests.


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
            fullTest();
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
    public void produktBatch() throws IDAO.DALException {
        //Laver indholsstoffer til råvarer
        Indholdsstof Croscarmellosenatrium  = new Indholdsstof(4,"Croscarmellosenatrium");
        Indholdsstof Magnesiumstearat  = new Indholdsstof(5,"Magnesiumstearat");
        //Laver råvarer til lister
        RåvareBatch batch1 = new RåvareBatch(99,4,2,"Sanofi",true);
        RåvareBatch batch2 = new RåvareBatch(100,5,30,"Eli Lilly", false);
        //Laver lister til produktbatch
        List<Integer> råvareBatchList = new ArrayList<>();
        råvareBatchList.add(100);
        råvareBatchList.add(99);
        List<Integer>råvareMængdeList = new ArrayList<>();
        råvareMængdeList.add(10);
        råvareMængdeList.add(20);
        //Opretter ProduktBatch
        ProduktBatch test = new ProduktBatch(10,"06-05-2019", 13, råvareBatchList, råvareMængdeList);
        ProduktBatch test = new ProduktBatch();
        //Indsætter det i databasen
        råvareBatchDAO.create(test);
        //henter det ned fra databasen
        IRåvareBatch receivedRåvareBatch = råvareBatchDAO.get(10);

        assertEquals(test.getId(),receivedRåvareBatch.getId());
        assertEquals(test.getDato(),receivedRåvareBatch.getDato());
        assertEquals(test.getOpskriftID(),receivedRåvareBatch.getOpskriftID());
        for(int i = 0; i<test.getRavareMengde().size(); i++) {
            assertEquals(test.getRavareMengde().get(i), receivedRåvareBatch.getRavareMengde().get(i));
        }
        for(int j = 0; j<test.getRavareBatchIDs().size();j++) {
            assertEquals(test.getRavareBatchIDs().get(j), receivedRåvareBatch.getRavareBatchIDs().get(j));
        }
        //sletter oprettet data
        råvareBatchDAO.delete(test);
        //---------nedenstående skal lige verificeres------------------------------------------
        //tester om dataen er blevet slettet
        if(råvareBatchDAO.get(10)!=null){
            fail();
        }
        //---------ovenstående skal lige verificeres------------------------------------------
    }
    public void råvareBatch(){

    }
    public void fullTest(){

    }
}
