package test;
import dal.DAO.*;
import dal.DTO.*;
import dal.DTO.MaybeUseless.*;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DALTest {

    //TODO få programmet til at overholde tests.


    IDAO<IIndholdsstof> indholdsstofDAO = new IndholdsstofDAO();
    IDAO<IOpskrift> OpskriftDAO = new OpskriftDAO();
    IDAO<IProduktBatch> produktBatchDAO = new ProduktBatchDAO();
    IDAO<IRåvareBatch> råvareBatchDAO = new RåvareBatchDAO();

    @Test
    public void test(){
        try{
            indholdsstof();
            opskrift();
            produktBatch();
            råvareBatch();
            fullTest();
        }
        catch (IDAO.DALException e) {
            e.printStackTrace();
            fail();
        }
    }
    public void indholdsstof() throws IDAO.DALException {
        //Opretter Indholdsstof
        IIndholdsstof Calciumhydrogenphospath = new Indholdsstof(1,"Calciumhydrogenphosphat dihydrat",false);
        //Indsætter indholdstofferne i databasen
        indholdsstofDAO.create(Calciumhydrogenphospath);
        //Prøver at hente det ned fra databasen
        IIndholdsstof received = indholdsstofDAO.get(1);
        //Tester om navnene stemmer overens
        assertEquals(Calciumhydrogenphospath.getName(), received.getName());
        //sletter i testen oprettede data
        indholdsstofDAO.delete(Calciumhydrogenphospath.getId());
        //tester om dataen er blevet slettet
        List<IIndholdsstof>alleIndholdsstoffer = indholdsstofDAO.getList();
        for (IIndholdsstof indholdsstof: alleIndholdsstoffer) {
            if (indholdsstof.getId()==Calciumhydrogenphospath.getId()) {
                fail();
            }
        }
    }
    public void opskrift() throws IDAO.DALException {
        //Opretter Indholdsstof
        IIndholdsstof Hypromellose  = new Indholdsstof(2,"Hypromellose ", false);
        IIndholdsstof Talcum = new Indholdsstof(3,"Talcum",false);
        //laver lister til opskrift
        ArrayList<Integer>indholdsstofIDList = new ArrayList<>();
        indholdsstofIDList.add(2);
        indholdsstofIDList.add(3);
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
        Opskrift Sildenafil = new Opskrift(1,"Sildenafil",indholdsstofIDList,mængdeList,aktivList,30, true);
        //Indsætter opskrift i databasen
        OpskriftDAO.create(Sildenafil);
        //henter opskriften fra databasen
        IOpskrift receivedOpskrift = OpskriftDAO.get(1);
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
        assertEquals(Sildenafil.getOpbevaringstid(),receivedOpskrift.getOpbevaringstid());
        //sletter i testen oprettede data
        OpskriftDAO.delete(Sildenafil.getId());
        //tester om dataen er blevet slettet
        List<IOpskrift>alleOpskrifter = OpskriftDAO.getList();
        for(IOpskrift opskrift: alleOpskrifter) {
            if (opskrift.getId()==Sildenafil.getId()) {
                fail();
            }
        }
    }
    public void produktBatch() throws IDAO.DALException {
        //Laver lister til produktbatch
        List<Integer> råvareBatchList = new ArrayList<>();
        råvareBatchList.add(100);
        råvareBatchList.add(99);
        List<Double>råvareMængdeList = new ArrayList<>();
        råvareMængdeList.add(10.0);
        råvareMængdeList.add(20.0);
        //Opretter ProduktBatch
        ProduktBatch test = new ProduktBatch(10,"06-05-2019", 13, råvareBatchList, råvareMængdeList,10);
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
        produktBatchDAO.delete(test.getId());
        //tester om dataen er blevet slettet
        List<IProduktBatch>alleProduktBatches = produktBatchDAO.getList();
        for(IProduktBatch produktBatch: alleProduktBatches) {
            if (produktBatch.getId()==test.getId()) {
                fail();
            }
        }
    }
    public void råvareBatch() throws IDAO.DALException {
        //opretter råvarebatch
        IRåvareBatch test = new RåvareBatch(21,10,1100,"Lundbeck");
        //Indsætter det i databasen
        råvareBatchDAO.create(test);
        //Henter det ned fra databasen og sammenligner
        IRåvareBatch receivedRåvareBatch = råvareBatchDAO.get(21);
        //tester om dataen er den samme
        assertEquals(test.getId(),receivedRåvareBatch.getId());
        assertEquals(test.getIndholdsstof(),receivedRåvareBatch.getIndholdsstof());
        assertEquals(test.getProducent(),receivedRåvareBatch.getProducent());
        //sletter oprettet data
        råvareBatchDAO.delete(test.getId());
        //tester om dataen er blevet slettet
        List<IRåvareBatch>alleRåvareBatches = råvareBatchDAO.getList();
        for(IRåvareBatch råvareBatch: alleRåvareBatches) {
            if (råvareBatch.getId()==test.getId()) {
                fail();
            }
        }
    }
    public void fullTest() throws IDAO.DALException {
        //Opretter Indholdsstoffer til Estrogen
        IIndholdsstof Estradiol = new Indholdsstof(1,"Estradiol",false);
        IIndholdsstof Norethisteronacetat = new Indholdsstof(2, "Norethisteronacetat",false);
        IIndholdsstof Opovidon = new Indholdsstof(3, "Opovidon",false);
        IIndholdsstof Laktosemonohydrat = new Indholdsstof(4,"Laktosemonohydrat",false);
        IIndholdsstof Magnesiumstearat = new Indholdsstof(5,"Magnesiumstearat",false);
        IIndholdsstof Majsstivelse = new Indholdsstof(6,"Majsstivelse", false);
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
        ArrayList<Double>mængdeList = new ArrayList<>();
        ArrayList<Boolean>aktivList = new ArrayList<>();
        for(int i = 1; i>=6; i++){
            indholdsstofList.add(i);
        }
        mængdeList.add(1.0);
        mængdeList.add(0.5);
        mængdeList.add(50.0);
        mængdeList.add(10.0);
        mængdeList.add(15.0);
        mængdeList.add(120.0);
        for(int i = 1; i>=6; i++){
            if(i==1||i==2){
                aktivList.add(true);
            }
            else{
                aktivList.add(false);
            }
        }
        //Opretter en opskrift
        IOpskrift Estrogen = new Opskrift(1,"Estrogen",indholdsstofList,mængdeList,aktivList,36, true);
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
        Estrogen.setOpbevaringstid(40);
        OpskriftDAO.update(Estrogen);
        //henter opskrift ned fra databasen
        IOpskrift rØstrogen = OpskriftDAO.get(2);
        //verificerer ændringer
        assertEquals(rØstrogen.getId(),2);
        assertEquals(rØstrogen.getNavn(),"Østrogen");
        assertEquals(rØstrogen.getOpbevaringstid(),40);
        //tester om ændringer gik igennem
        assertEquals(Estrogen.getId(),rØstrogen.getId());
        assertEquals(Estrogen.getNavn(),rØstrogen.getNavn());
        assertEquals(Estrogen.getOpbevaringstid(),rØstrogen.getOpbevaringstid());
        //Opretter råvareBatches
        IRåvareBatch råvareBatchEstradiol = new RåvareBatch(1,1,1000,"Teva");
        IRåvareBatch råvareBatchNorethisteronacetat = new RåvareBatch(2,2,500,"Teva");
        IRåvareBatch råvareBatchOpovidon = new RåvareBatch(3,3,50000,"Teva");
        IRåvareBatch råvareBatchLaktosemonohydrat = new RåvareBatch(4,4,10000,"Teva");
        IRåvareBatch råvareBatchMagnesiumstearat = new RåvareBatch(5,5,15000,"Teva");
        IRåvareBatch råvareBatchMajsstivelse = new RåvareBatch(6,6,120000,"Teva");
        //Laver lister med råvarebatches til test
        List<IRåvareBatch>råvareBatchList = new ArrayList<>();
        råvareBatchList.add(råvareBatchEstradiol);
        råvareBatchList.add(råvareBatchNorethisteronacetat);
        råvareBatchList.add(råvareBatchOpovidon);
        råvareBatchList.add(råvareBatchLaktosemonohydrat);
        råvareBatchList.add(råvareBatchMagnesiumstearat);
        råvareBatchList.add(råvareBatchMajsstivelse);
        //indsætter råvarebatches i databasen
        for(int m = 0; m<6; m++){
            råvareBatchDAO.create(råvareBatchList.get(m));
        }
        //henter råvarebatch liste til test og tester
        for(int k = 0; k<råvareBatchDAO.getList().size(); k++){
            IRåvareBatch rinstance = råvareBatchDAO.getList().get(k);
            IRåvareBatch instance = råvareBatchList.get(k);
            assertEquals(instance.getIndholdsstof(), rinstance.getIndholdsstof());
            assertEquals(instance.getProducent(), rinstance.getProducent());
        }
        //laver ændringer i råvareBatches
        råvareBatchMagnesiumstearat.setId(7);
        råvareBatchMagnesiumstearat.setIndholdsstof(7);
        råvareBatchMagnesiumstearat.setMængde(7);
        råvareBatchMagnesiumstearat.setProducent("7");
        råvareBatchDAO.update(råvareBatchMagnesiumstearat);
        //henter det ned fra databasen
        IRåvareBatch r7 = råvareBatchDAO.get(7);
        //tester om ændringer passer
        assertEquals(r7.getId(),7);
        assertEquals(r7.getIndholdsstof(),7);
        assertEquals(r7.getProducent(),"7");
        //Opretter mængdeliste og råvare liste
        List<Integer>råvareBatchIDList = new ArrayList<>();
        råvareBatchIDList.add(1);
        råvareBatchIDList.add(2);
        råvareBatchIDList.add(3);
        råvareBatchIDList.add(4);
        råvareBatchIDList.add(5);
        råvareBatchIDList.add(7);
        List<Double>PBmængdeList = new ArrayList<>();
        PBmængdeList.add(100.0);
        PBmængdeList.add(50.0);
        PBmængdeList.add(5000.0);
        PBmængdeList.add(1000.0);
        PBmængdeList.add(1500.0);
        PBmængdeList.add(12000.0);
        //Opretter produktBatches
        IProduktBatch PBEstrogen = new ProduktBatch(1,"07-05-2019",2,råvareBatchIDList,PBmængdeList,100);
        //Indsætter produktBatch i databasen
        produktBatchDAO.create(PBEstrogen);
        //henter produktbatch ned fra databasen
        IProduktBatch rPBEstrogen = produktBatchDAO.get(1);
        //tester
        assertEquals(rPBEstrogen.getId(),1);
        assertEquals(rPBEstrogen.getDato(),"07-05-2019");
        assertEquals(rPBEstrogen.getOpskriftID(),2);
        for(int n=0; n<råvareBatchIDList.size();n++) {
            assertEquals(rPBEstrogen.getRavareBatchIDs().get(n), råvareBatchIDList.get(n));
            assertEquals(rPBEstrogen.getRavareMengde().get(n), PBmængdeList.get(n));
        }
        assertEquals(rPBEstrogen.getAntal(),100);
        //laver ændringer og opdaterer
        PBEstrogen.setDato("22-05-2019");
        PBEstrogen.setAntal(50);
        PBEstrogen.setId(2);
        PBEstrogen.setOpskriftID(3);
        produktBatchDAO.update(PBEstrogen);
        //tester ændringer
        IProduktBatch r2PBEstrogen = produktBatchDAO.get(2);
        assertEquals(r2PBEstrogen.getDato(),"22-05-2019");
        assertEquals(r2PBEstrogen.getAntal(),50);
        assertEquals(r2PBEstrogen.getId(),2);
        assertEquals(r2PBEstrogen.getOpskriftID(),3);
        //sletter i testen oprettede data
        indholdsstofDAO.delete(Estradiol.getId());
        indholdsstofDAO.delete(Norethisteronacetat.getId());
        indholdsstofDAO.delete(Opovidon.getId());
        indholdsstofDAO.delete(Laktosemonohydrat.getId());
        indholdsstofDAO.delete(Magnesiumstearat.getId());
        indholdsstofDAO.delete(Majsstivelse.getId());
        OpskriftDAO.delete(Estrogen.getId());
        råvareBatchDAO.delete(råvareBatchEstradiol.getId());
        råvareBatchDAO.delete(råvareBatchNorethisteronacetat.getId());
        råvareBatchDAO.delete(råvareBatchOpovidon.getId());
        råvareBatchDAO.delete(råvareBatchLaktosemonohydrat.getId());
        råvareBatchDAO.delete(råvareBatchMagnesiumstearat.getId());
        råvareBatchDAO.delete(råvareBatchMajsstivelse.getId());
        produktBatchDAO.delete(PBEstrogen.getId());
        //tester om dataen er blevet slettet
        List<IIndholdsstof>alleIndholdsstoffer = indholdsstofDAO.getList();
        for(IIndholdsstof indholdsstof: alleIndholdsstoffer){
            if((Estradiol.getId()== indholdsstof.getId()|| Norethisteronacetat.getId()== indholdsstof.getId() || Opovidon.getId()== indholdsstof.getId() || Laktosemonohydrat.getId()== indholdsstof.getId() || Magnesiumstearat.getId()== indholdsstof.getId() || Majsstivelse.getId()== indholdsstof.getId())){
                fail();
            }
        }
        List<IOpskrift>alleOpskrifter = OpskriftDAO.getList();
        for(IOpskrift opskrift: alleOpskrifter){
            if(opskrift.getId()==Estrogen.getId()){
                fail();
            }
        }
        List<IRåvareBatch>alleRåvareBatches = råvareBatchDAO.getList();
        for(IRåvareBatch råvareBatch: alleRåvareBatches){
            if((råvareBatch.getId()==råvareBatchEstradiol.getId()||råvareBatch.getId()==råvareBatchNorethisteronacetat.getId()||råvareBatch.getId()==råvareBatchOpovidon.getId()||råvareBatch.getId()==råvareBatchLaktosemonohydrat.getId()||råvareBatch.getId()==råvareBatchMagnesiumstearat.getId()||råvareBatch.getId()==råvareBatchMajsstivelse.getId())){
                fail();
            }
        }
        List<IProduktBatch>alleProduktBatches = produktBatchDAO.getList();
        for(IProduktBatch produktBatch: alleProduktBatches){
            if(produktBatch.getId()==PBEstrogen.getId()){
                fail();
            }
        }
    }
}
