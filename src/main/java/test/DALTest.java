package test;


import dal.DAO.*;
import dal.DTO.*;
import dal.DTO.MaybeUseless.*;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DALTest {

    //TODO få programmet til at overholde tests.

    IDAO<IUser> userDAO = new UserDAO();
    IDAO<IIndholdsstof> indholdsstofDAO = new IndholdsstofDAO();
    IDAO<IOpskrift> OpskriftDAO = new OpskriftDAO();
    IDAO<IProduktBatch> produktBatchDAO = new ProduktBatchDAO();
    IDAO<IRåvareBatch> råvareBatchDAO = new RåvareBatchDAO();

    @Test
    public void user() throws IDAO.DALException, SQLException {
        UserDTO userDTO = new UserDTO();
        //opretter user
        IUser testUser = userDTO.createTestDTO();
        //indsætter i databasen
        int brugerid = userDAO.create(testUser);
        //henter fra databasen
        IUser receivedUser = userDAO.get(brugerid);
        //tester om data er det samme
        assertEquals(receivedUser.getUserName(), testUser.getUserName());
        for (int i = 0; i < testUser.getRoles().size(); i++) {
            assertEquals(receivedUser.getRoles().get(i), testUser.getRoles().get(i));
        }
        //laver ændringer
        receivedUser.setUserId(brugerid);
        receivedUser.setUserName("tester");
        receivedUser.removeRole("Admin");
        receivedUser.addRole("Pharmaceut");
        userDAO.update(receivedUser);
        IUser receivedUser2 = userDAO.get(receivedUser.getUserId());
        assertEquals(receivedUser2.getUserId(), receivedUser.getUserId());
        assertEquals(receivedUser2.getUserName(), receivedUser.getUserName());
        for (int j = 0; j < receivedUser.getRoles().size(); j++) {
            assertEquals(receivedUser2.getRoles().get(j), receivedUser.getRoles().get(j));
        }
        //sletter fra databasen
        userDAO.delete(brugerid);
        //tester om sletningen virkede
        List<IUser> alleUsers = userDAO.getList();
        for (IUser user : alleUsers) {
            if (user.getUserId() == brugerid) {
                fail();
            }
        }

    }

    @Test
    public void indholdsstof() throws IDAO.DALException, SQLException {
        //Opretter Indholdsstof
        IIndholdsstof Calciumhydrogenphospath = new Indholdsstof(1, "Calciumhydrogenphosphat dihydrat", false);
        //Indsætter indholdstofferne i databasen
        int stofid = indholdsstofDAO.create(Calciumhydrogenphospath);
        Calciumhydrogenphospath.setId(stofid);
        //Prøver at hente det ned fra databasen
        IIndholdsstof received = indholdsstofDAO.get(Calciumhydrogenphospath.getId());
        //Tester om navnene stemmer overens
        assertEquals(Calciumhydrogenphospath.getName(), received.getName());
        //sletter i testen oprettede data
        indholdsstofDAO.delete(Calciumhydrogenphospath.getId());
        //tester om dataen er blevet slettet
        List<IIndholdsstof> alleIndholdsstoffer = indholdsstofDAO.getList();
        for (IIndholdsstof indholdsstof : alleIndholdsstoffer) {
            if (indholdsstof.getId() == Calciumhydrogenphospath.getId()) {
                fail();
            }
        }
    }

    @Test
    public void opskrift() throws IDAO.DALException, SQLException {
        //Opretter Indholdsstof
        IIndholdsstof Hypromellose = new Indholdsstof(0, "Hypromellose", false);
        IIndholdsstof Talcum = new Indholdsstof(0, "Talcum", false);
        int hypID = indholdsstofDAO.create(Hypromellose);
        int talumID = indholdsstofDAO.create(Talcum);

        //laver lister til opskrift
        ArrayList<Integer> indholdsstofIDList = new ArrayList<>();
        indholdsstofIDList.add(hypID);
        indholdsstofIDList.add(talumID);
        ArrayList<IIndholdsstof> indholdsstofList = new ArrayList<>();
        indholdsstofList.add(Hypromellose);
        indholdsstofList.add(Talcum);
        ArrayList<Double> mængdeList = new ArrayList<>();
        mængdeList.add(1.3);
        mængdeList.add(1.0);
        ArrayList<Boolean> aktivList = new ArrayList<>();
        aktivList.add(false);
        aktivList.add(false);
        //Opretter opskrift
        Opskrift Sildenafil = new Opskrift(
                1, "Sildenafil", indholdsstofIDList, mængdeList, aktivList, 30, true);
        //Indsætter opskrift i databasen
        int opskriftID = OpskriftDAO.create(Sildenafil);

        //henter opskriften fra databasen
        Sildenafil.setId(opskriftID);
        IOpskrift receivedOpskrift = OpskriftDAO.get(Sildenafil.getId());

        //tester om vi får samme data tilbage
        assertEquals(Sildenafil.getId(), receivedOpskrift.getId());
        assertEquals(Sildenafil.getNavn(), receivedOpskrift.getNavn());
        for (int i = 0; i < Sildenafil.getIndholdsStoffer().size(); i++) {
            assertEquals(Sildenafil.getIndholdsStoffer().get(i), receivedOpskrift.getIndholdsStoffer().get(i));
        }
        for (int i = 0; i < Sildenafil.getMaengde().size(); i++) {
            assertEquals(Sildenafil.getMaengde().get(i), receivedOpskrift.getMaengde().get(i));
        }
        for (int i = 0; i < Sildenafil.getAktiv().size(); i++) {
            assertEquals(Sildenafil.getAktiv().get(i), receivedOpskrift.getAktiv().get(i));
        }
        assertEquals(Sildenafil.getOpbevaringstid(), receivedOpskrift.getOpbevaringstid());

        //sletter oprettede data fra testen
        OpskriftDAO.delete(Sildenafil.getId());
        //tester om dataen er blevet slettet
        List<IOpskrift> alleOpskrifter = OpskriftDAO.getList();
        for (IOpskrift opskrift : alleOpskrifter) {
            if (opskrift.getId() == Sildenafil.getId()) {
                fail();
            }
        }
    }

    @Test
    public void produktBatch() throws IDAO.DALException, SQLException {
        //Laver lister til produktbatch
        List<Integer> råvareBatchList = new ArrayList<>();
        råvareBatchList.add(7);
        råvareBatchList.add(8);
        List<Double> råvareMængdeList = new ArrayList<>();
        råvareMængdeList.add(5.4);
        råvareMængdeList.add(20.0);

        //Opretter ProduktBatch
        ProduktBatch test = new ProduktBatch(0, "2019-05-10", 13, råvareBatchList, råvareMængdeList, 10, "bestilt");

        //Indsætter det i databasen
        int batchID = produktBatchDAO.create(test);
        //henter det ned fra databasen
        test.setId(batchID);
        IProduktBatch receivedProduktBatch = produktBatchDAO.get(test.getId());

        assertEquals(test.getId(), receivedProduktBatch.getId());
        assertEquals(test.getDato(), receivedProduktBatch.getDato());
        assertEquals(test.getOpskriftID(), receivedProduktBatch.getOpskriftID());
        for (int i = 0; i < test.getRavareMengde().size(); i++) {
            assertEquals(test.getRavareMengde().get(i), receivedProduktBatch.getRavareMengde().get(i));
        }
        for (int j = 0; j < test.getRavareBatchIDs().size(); j++) {
            assertEquals(test.getRavareBatchIDs().get(j), receivedProduktBatch.getRavareBatchIDs().get(j));
        }
        //sletter oprettet data
        produktBatchDAO.delete(test.getId());
        //tester om dataen er blevet slettet
        List<IProduktBatch> alleProduktBatches = produktBatchDAO.getList();
        for (IProduktBatch produktBatch : alleProduktBatches) {
            if (produktBatch.getId() == test.getId()) {
                fail();
            }
        }
    }

    @Test
    public void råvareBatch() throws IDAO.DALException, SQLException {
        //opretter råvarebatch
        IRåvareBatch batch1 = new RåvareBatch(0, 7, 400.11, "Lundbeck");
        IRåvareBatch batch2 = new RåvareBatch(0, 4, 50.22, "Lundbeck");
        IRåvareBatch batch3 = new RåvareBatch(0, 6, 40.5, "Novo Nordisk");
        IRåvareBatch batch4 = new RåvareBatch(0, 5, 5.78, "Lundbeck");

        //Indsætter det i databasen
        int id1 = råvareBatchDAO.create(batch1);
        batch1.setId(id1);

        batch2.setId(råvareBatchDAO.create(batch2));
        batch3.setId(råvareBatchDAO.create(batch3));
        batch4.setId(råvareBatchDAO.create(batch4));

        //Henter det ned fra databasen og sammenligner
        IRåvareBatch receivedRåvareBatch = råvareBatchDAO.get(batch1.getId());
        //tester om dataen er den samme
        assertEquals(batch1.getId(), receivedRåvareBatch.getId());
        assertEquals(batch1.getIndholdsstof(), receivedRåvareBatch.getIndholdsstof());
        assertEquals(batch1.getProducent(), receivedRåvareBatch.getProducent());

        //sletter oprettet data
        råvareBatchDAO.delete(batch1.getId());
        //tester om dataen er blevet slettet
        List<IRåvareBatch> alleRåvareBatches = råvareBatchDAO.getList();
        for (IRåvareBatch råvareBatch : alleRåvareBatches) {
            if (råvareBatch.getId() == batch1.getId()) {
                fail();
            }
        }
    }

    @Test
    public void fullTest() throws IDAO.DALException, SQLException {
        //Opretter Indholdsstoffer til Estrogen
        IIndholdsstof Estradiol = new Indholdsstof(1, "Estradiol", false);
        IIndholdsstof Norethisteronacetat = new Indholdsstof(2, "Norethisteronacetat", false);
        IIndholdsstof Opovidon = new Indholdsstof(3, "Opovidon", false);
        IIndholdsstof Laktosemonohydrat = new Indholdsstof(4, "Laktosemonohydrat", false);
        IIndholdsstof Magnesiumstearat = new Indholdsstof(5, "Magnesiumstearat", false);
        IIndholdsstof Majsstivelse = new Indholdsstof(6, "Majsstivelse", false);

        //Indsætter indholdsstoffer og får deres stofID
        int est = indholdsstofDAO.create(Estradiol);            Estradiol.setId(est);
        int nor = indholdsstofDAO.create(Norethisteronacetat);  Norethisteronacetat.setId(nor);
        int opo = indholdsstofDAO.create(Opovidon);             Opovidon.setId(opo);
        int lak = indholdsstofDAO.create(Laktosemonohydrat);    Laktosemonohydrat.setId(lak);
        int mag = indholdsstofDAO.create(Magnesiumstearat);     Magnesiumstearat.setId(mag);
        int maj = indholdsstofDAO.create(Majsstivelse);         Majsstivelse.setId(maj);

        //henter indholdstof fra databasen 'r' står får 'received'
        IIndholdsstof rEstradiol = indholdsstofDAO.get(Estradiol.getId());
        IIndholdsstof rNorethisteronacetat = indholdsstofDAO.get(Norethisteronacetat.getId());
        IIndholdsstof rOpovidon = indholdsstofDAO.get(Opovidon.getId());
        IIndholdsstof rLaktosemonohydrat = indholdsstofDAO.get(Laktosemonohydrat.getId());
        IIndholdsstof rMagnesiumstearat = indholdsstofDAO.get(Magnesiumstearat.getId());
        IIndholdsstof rMajsstivelse = indholdsstofDAO.get(Majsstivelse.getId());

        //laver stikprøve på hentet data
        assertEquals(rOpovidon.getName(), Opovidon.getName());
        assertEquals(rLaktosemonohydrat.getName(), Laktosemonohydrat.getName());

        //Prøver at ændre navne i et par indholdsstoffer
        Majsstivelse.setName("Talcum");
        Estradiol.setName("Macrogol");
        indholdsstofDAO.update(Majsstivelse);
        indholdsstofDAO.update(Estradiol);
        rMajsstivelse = indholdsstofDAO.get(Majsstivelse.getId());
        rEstradiol = indholdsstofDAO.get(Estradiol.getId());

        //tester om ændringen blev registreret
        assertEquals(rMajsstivelse.getName(), "Talcum");
        assertEquals(rEstradiol.getName(), "Macrogol");

        //Opretter lister til opskrift
        ArrayList<Integer> indholdsstofList = new ArrayList<>();
        ArrayList<Double> mængdeList = new ArrayList<>();
        ArrayList<Boolean> aktivList = new ArrayList<>();

        for (int i = 4; i <= 9; i++)
            indholdsstofList.add(i);
        mængdeList.add(1.0);
        mængdeList.add(0.5);
        mængdeList.add(50.0);
        mængdeList.add(10.0);
        mængdeList.add(15.0);
        mængdeList.add(120.0);
        for (int i = 0; i <= 5; i++) {
            if (i == 1 || i == 2) {
                aktivList.add(true);
            } else {
                aktivList.add(false);
            }
        }

        //Opretter en opskrift
        IOpskrift Estrogen = new Opskrift(1, "Estrogen", indholdsstofList, mængdeList, aktivList, 36, true);

        //Indsætter opskriften i databasen og modtager dens ID
        int opskriftID = OpskriftDAO.create(Estrogen);
        Estrogen.setId(opskriftID);

        //henter opskriften fra databasen
        IOpskrift rEstrogen = OpskriftDAO.get(Estrogen.getId());

        //tester opskriften
        assertEquals(Estrogen.getNavn(), rEstrogen.getNavn());
        for (int i = 1; i >= 6; i++) {
            assertEquals(Estrogen.getIndholdsStoffer().get(i), rEstrogen.getIndholdsStoffer().get(i));
            assertEquals(Estrogen.getMaengde().get(i), rEstrogen.getMaengde().get(i));
            assertEquals(Estrogen.getAktiv().get(i), rEstrogen.getAktiv().get(i)); }
        assertEquals(Estrogen.getOpbevaringstid(), rEstrogen.getOpbevaringstid());

        //prøver at lave ændringer i opskriften
        Estrogen.setNavn("Østrogen");
        Estrogen.setOpbevaringstid(40);
        int newOpskriftID = OpskriftDAO.update(Estrogen);

        //henter opskrift ned fra databasen og overrider den gamle rEstrogen
        rEstrogen = OpskriftDAO.get(newOpskriftID);

        //tester om ændringer gik igennem
        assertEquals(Estrogen.getNavn(), rEstrogen.getNavn());
        assertEquals(Estrogen.getOpbevaringstid(), rEstrogen.getOpbevaringstid());

        //Opretter råvareBatches
        IRåvareBatch råvareBatchEstradiol = new RåvareBatch(0, 4, 100.42, "Teva");
        IRåvareBatch råvareBatchNorethisteronacetat = new RåvareBatch(0, 5, 500.00, "Teva");
        IRåvareBatch råvareBatchOpovidon = new RåvareBatch(0, 6, 600.3, "Teva");
        IRåvareBatch råvareBatchLaktosemonohydrat = new RåvareBatch(0, 7, 900.12, "Teva");
        IRåvareBatch råvareBatchMagnesiumstearat = new RåvareBatch(0, 8, 100.5, "Teva");
        IRåvareBatch råvareBatchMajsstivelse = new RåvareBatch(0, 9, 120.88, "Teva");

        //Laver lister med råvarebatches til test
        List<IRåvareBatch> råvareBatchList = new ArrayList<>();
        råvareBatchList.add(råvareBatchEstradiol);
        råvareBatchList.add(råvareBatchNorethisteronacetat);
        råvareBatchList.add(råvareBatchOpovidon);
        råvareBatchList.add(råvareBatchLaktosemonohydrat);
        råvareBatchList.add(råvareBatchMagnesiumstearat);
        råvareBatchList.add(råvareBatchMajsstivelse);

        //indsætter råvarebatches i databasen
        List<Integer> ids = new ArrayList<>();
        List<IRåvareBatch> rRåvareBatches = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ids.add(råvareBatchDAO.create(råvareBatchList.get(i)));
            rRåvareBatches.add(råvareBatchDAO.get(ids.get(i)));
            råvareBatchList.get(i).setId(ids.get(i));
        }
        //henter råvarebatch liste til test og tester
        for (int i = 0; i < ids.size(); i++) {
            IRåvareBatch rinstance = rRåvareBatches.get(i);
            IRåvareBatch instance = råvareBatchList.get(i);
            assertEquals(instance.getIndholdsstof(), rinstance.getIndholdsstof());
            assertEquals(instance.getProducent(), rinstance.getProducent());
        }

        //laver ændringer i råvareBatches
        råvareBatchMagnesiumstearat.setIndholdsstof(7);
        råvareBatchMagnesiumstearat.setMængde(7.7);
        råvareBatchMagnesiumstearat.setProducent("7");
        råvareBatchDAO.update(råvareBatchMagnesiumstearat);

        //henter det ned fra databasen
        IRåvareBatch r7 = råvareBatchDAO.get(råvareBatchMagnesiumstearat.getId());

        //tester om ændringer passer
        assertEquals(r7.getIndholdsstof(), råvareBatchMagnesiumstearat.getIndholdsstof());
        assertEquals(r7.getProducent(), råvareBatchMagnesiumstearat.getProducent());

        //Opretter mængdeliste og råvare liste
        List<Integer> råvareBatchIDList = new ArrayList<>();
        råvareBatchIDList.add(råvareBatchEstradiol.getId());
        råvareBatchIDList.add(råvareBatchNorethisteronacetat.getId());
        råvareBatchIDList.add(råvareBatchOpovidon.getId());
        råvareBatchIDList.add(råvareBatchLaktosemonohydrat.getId());
        råvareBatchIDList.add(råvareBatchMagnesiumstearat.getId());
        råvareBatchIDList.add(råvareBatchMajsstivelse.getId());

        List<Double> PBmængdeList = new ArrayList<>();
        PBmængdeList.add(100.0);
        PBmængdeList.add(50.0);
        PBmængdeList.add(500.0);
        PBmængdeList.add(100.0);
        PBmængdeList.add(150.0);
        PBmængdeList.add(120.0);

        //Opretter produktBatches
        IProduktBatch PBEstrogen = new ProduktBatch(1, "2019-07-04", 9, ids, PBmængdeList, 100, "under produktion");

        //Indsætter produktBatch i databasen
        int pbe = produktBatchDAO.create(PBEstrogen);
        PBEstrogen.setId(pbe);

        //henter produktbatch ned fra databasen
        IProduktBatch rPBEstrogen = produktBatchDAO.get(PBEstrogen.getId());

        //tester
        assertEquals(rPBEstrogen.getDato(), PBEstrogen.getDato());
        assertEquals(rPBEstrogen.getOpskriftID(), PBEstrogen.getOpskriftID());

        for (int n = 0; n < råvareBatchIDList.size(); n++) {
            assertEquals(rPBEstrogen.getRavareBatchIDs().get(n), råvareBatchIDList.get(n));
            assertEquals(rPBEstrogen.getRavareMengde().get(n), PBmængdeList.get(n));
        }
        assertEquals(rPBEstrogen.getAntal(), PBEstrogen.getAntal());

        //laver ændringer og opdaterer
        PBEstrogen.setDato("1999-07-06");
        PBEstrogen.setAntal(50);
        produktBatchDAO.update(PBEstrogen);

        //tester ændringer
        IProduktBatch r2PBEstrogen = produktBatchDAO.get(PBEstrogen.getId());
        assertEquals(r2PBEstrogen.getDato(), PBEstrogen.getDato());
        assertEquals(r2PBEstrogen.getAntal(), 50);

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
        List<IIndholdsstof> alleIndholdsstoffer = indholdsstofDAO.getList();
        for (IIndholdsstof indholdsstof : alleIndholdsstoffer) {
            if ((Estradiol.getId() == indholdsstof.getId() || Norethisteronacetat.getId() == indholdsstof.getId() || Opovidon.getId() == indholdsstof.getId() || Laktosemonohydrat.getId() == indholdsstof.getId() || Magnesiumstearat.getId() == indholdsstof.getId() || Majsstivelse.getId() == indholdsstof.getId())) {
                fail();
            }
        }
        List<IOpskrift> alleOpskrifter = OpskriftDAO.getList();
        for (IOpskrift opskrift : alleOpskrifter) {
            if (opskrift.getId() == Estrogen.getId()) {
                fail();
            }
        }
        List<IRåvareBatch> alleRåvareBatches = råvareBatchDAO.getList();
        for (IRåvareBatch råvareBatch : alleRåvareBatches) {
            if ((råvareBatch.getId() == råvareBatchEstradiol.getId() || råvareBatch.getId() == råvareBatchNorethisteronacetat.getId() || råvareBatch.getId() == råvareBatchOpovidon.getId() || råvareBatch.getId() == råvareBatchLaktosemonohydrat.getId() || råvareBatch.getId() == råvareBatchMagnesiumstearat.getId() || råvareBatch.getId() == råvareBatchMajsstivelse.getId())) {
                fail();
            }
        }
        List<IProduktBatch> alleProduktBatches = produktBatchDAO.getList();
        for (IProduktBatch produktBatch : alleProduktBatches) {
            if (produktBatch.getId() == PBEstrogen.getId()) {
                fail();
            }
        }
    }
}
