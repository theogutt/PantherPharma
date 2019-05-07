package test;

import dal.DAO.*;
import dal.DTO.*;
import dal.DTO.MaybeUseless.*;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DALTest {

    //TODO update tests, en system test og få programmet til at overholde tests.


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
        IIndholdsstof Calciumhydrogenphospath = new Indholdsstof(1,"Calciumhydrogenphosphat dihydrat");
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
        OpskriftDAO.delete(1);
        //---------nedenstående skal lige verificeres------------------------------------------
        //tester om dataen er blevet slettet
        if(OpskriftDAO.get(1)!=null){
            fail();
        }
        //---------ovenstående skal lige verificeres------------------------------------------
    }
    public void produktBatch() throws IDAO.DALException {
        //Laver indholsstoffer til råvarer
        IIndholdsstof Croscarmellosenatrium  = new Indholdsstof(4,"Croscarmellosenatrium");
        IIndholdsstof Magnesiumstearat  = new Indholdsstof(5,"Magnesiumstearat");
        //Laver råvarer til lister
        IRåvareBatch batch1 = new RåvareBatch(99,4,2,"Sanofi",true);
        IRåvareBatch batch2 = new RåvareBatch(100,5,30,"Eli Lilly", false);
        //Laver lister til produktbatch
        List<Integer> råvareBatchList = new ArrayList<>();
        råvareBatchList.add(100);
        råvareBatchList.add(99);
        List<Integer>råvareMængdeList = new ArrayList<>();
        råvareMængdeList.add(10);
        råvareMængdeList.add(20);
        //Opretter ProduktBatch
        ProduktBatch test = new ProduktBatch(10,"06-05-2019", 13, råvareBatchList, råvareMængdeList);
        //Indsætter det i databasen
        produktBatchDAO.create(test);
        //henter det ned fra databasen
        IProduktBatch receivedProduktBatch = produktBatchDAO.get(10);

        assertEquals(test.getId(),receivedProduktBatch.getId());
        assertEquals(test.getDato(),receivedProduktBatch.getDato());
        assertEquals(test.getOpskriftID(),receivedProduktBatch.getOpskriftID());
        for(int i = 0; i<test.getRavareMengde().size(); i++) {
            assertEquals(test.getRavareMengde().get(i), receivedProduktBatch.getRavareMengde().get(i));
        }
        for(int j = 0; j<test.getRavareBatchIDs().size();j++) {
            assertEquals(test.getRavareBatchIDs().get(j), receivedProduktBatch.getRavareBatchIDs().get(j));
        }
        //sletter oprettet data
        produktBatchDAO.delete(10);
        //---------nedenstående skal lige verificeres------------------------------------------
        //tester om dataen er blevet slettet
        if(produktBatchDAO.get(10)!=null){
            fail();
        }
        //---------ovenstående skal lige verificeres------------------------------------------
    }
    public void råvareBatch() throws IDAO.DALException {
        //opretter råvarebatch
        IRåvareBatch test = new RåvareBatch(21,10,1100,"Lundbeck",false);
        //Indsætter det i databasen
        råvareBatchDAO.create(test);
        //Henter det ned fra databasen og sammenligner
        IRåvareBatch receivedRåvareBatch = råvareBatchDAO.get(21);
        //tester om dataen er den samme
        assertEquals(test.getId(),receivedRåvareBatch.getId());
        assertEquals(test.getIndholdsstof(),receivedRåvareBatch.getIndholdsstof());
        assertEquals(test.getMængde(),receivedRåvareBatch.getMængde());
        assertEquals(test.getProducent(),receivedRåvareBatch.getProducent());
        assertEquals(test.isGenbestil(),receivedRåvareBatch.isGenbestil());
        //sletter oprettet data
        råvareBatchDAO.delete(21);
        //---------nedenstående skal lige verificeres------------------------------------------
        //tester om dataen er blevet slettet
        if(råvareBatchDAO.get(21)!=null){
            fail();
        }
        //---------ovenstående skal lige verificeres------------------------------------------
    }
    public void fullTest() throws IDAO.DALException {
        //Opretter Indholdsstoffer til Estrogen
        IIndholdsstof Estradiol = new Indholdsstof(1,"Estradiol");
        IIndholdsstof Norethisteronacetat = new Indholdsstof(2, "Norethisteronacetat");
        IIndholdsstof Opovidon = new Indholdsstof(3, "Opovidon");
        IIndholdsstof Laktosemonohydrat = new Indholdsstof(4,"Laktosemonohydrat");
        IIndholdsstof Magnesiumstearat = new Indholdsstof(5,"Magnesiumstearat");
        IIndholdsstof Majsstivelse = new Indholdsstof(6,"Majsstivelse");
        //Indsætter indholdsstoffer
        indholdsstofDAO.create(Estradiol);
        indholdsstofDAO.create(Norethisteronacetat);
        indholdsstofDAO.create(Opovidon);
        indholdsstofDAO.create(Laktosemonohydrat);
        indholdsstofDAO.create(Magnesiumstearat);
        indholdsstofDAO.create(Majsstivelse);
        //henter indholdstof fra databasen 'r' står får 'received'
        IIndholdsstof rEstradiol = indholdsstofDAO.get(1);
        IIndholdsstof rNorethisteronacetat = indholdsstofDAO.get(2);
        IIndholdsstof rOpovidon = indholdsstofDAO.get(3);
        IIndholdsstof rLaktosemonohydrat = indholdsstofDAO.get(4);
        IIndholdsstof rMagnesiumstearat = indholdsstofDAO.get(5);
        IIndholdsstof rMajsstivelse = indholdsstofDAO.get(6);
        //laver stikprøve på hentet data
        assertEquals(rOpovidon.getId(),Opovidon.getId());
        assertEquals(rOpovidon.getName(),Opovidon.getName());
        assertEquals(rLaktosemonohydrat.getId(),Laktosemonohydrat.getId());
        assertEquals(rLaktosemonohydrat.getName(),Laktosemonohydrat.getName());
        //Prøver at ændre navne i et par indholdsstoffer
        rMajsstivelse.setName("Talcum");
        rEstradiol.setName("Macrogol");
        //tester om ændringen blev registreret
        assertEquals(rMajsstivelse.getName(),"Talcum");
        assertEquals(rEstradiol.getName(),"Macrogol");
        //Opretter lister til opskrift
        ArrayList<Integer>indholdsstofList = new ArrayList<>();
        ArrayList<Integer>mængdeList = new ArrayList<>();
        ArrayList<Boolean>aktivList = new ArrayList<>();
        for(int i = 1; i>=6; i++){
            indholdsstofList.add(i);
        }
        mængdeList.add(1);
        mængdeList.add(0.5);
        mængdeList.add(50);
        mængdeList.add(10);
        mængdeList.add(15);
        mængdeList.add(120);
        for(int i = 1; i>=6; i++){
            if(i==1||i==2){
                aktivList.add(true);
            }
            else{
                aktivList.add(false);
            }
        }
        //Opretter en opskrift
        IOpskrift Estrogen = new Opskrift(1,"Estrogen",indholdsstofList,mængdeList,aktivList,36);
        //Indsætter opskriften i databasen
        OpskriftDAO.create(Estrogen);
        //henter opskriften fra databasen
        IOpskrift rEstrogen = OpskriftDAO.get(1);
        //tester opskriften
        assertEquals(Estrogen.getId(),rEstrogen.getId());
        assertEquals(Estrogen.getNavn(),rEstrogen.getNavn());
        for(int i = 1; i>=6; i++) {
            assertEquals(Estrogen.getIndholdsStoffer().get(i), rEstrogen.getIndholdsStoffer().get(i));
        }
        for(int i = 1; i>=6; i++) {
            assertEquals(Estrogen.getMaengde().get(i), rEstrogen.getMaengde().get(i));
        }
        for(int i = 1; i>=6; i++) {
            assertEquals(Estrogen.getAktiv().get(i), rEstrogen.getAktiv().get(i));
        }
        assertEquals(Estrogen.getOpbevaringstid(),rEstrogen.getOpbevaringstid());
        //prøver at lave ændringer i opskriften
        Estrogen.setId(2);
        Estrogen.setNavn("Østrogen");
        Estrogen.setIndholdsStoffer();
        Estrogen.setMaengde();
        Estrogen.setAktiv();
        Estrogen.setOpbevaringstid();
        OpskriftDAO.update(Estrogen);
        //henter opskrift ned fra databasen
        IOpskrift rØstrogen = OpskriftDAO.get(2);
        //tester om ændringer

    }
}
