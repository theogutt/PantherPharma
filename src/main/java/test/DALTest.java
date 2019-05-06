package test;

import dal.DAO.*;
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


        }
        catch (IDAO.DALException e) {
            e.printStackTrace();
            fail();
        }
    }
}
