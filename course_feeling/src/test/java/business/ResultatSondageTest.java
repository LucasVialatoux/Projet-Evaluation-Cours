package business;

import business.Ressenti;
import business.ResultatSondage;

import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ResultatSondageTest {

    ResultatSondage ResSond;

    public ResultatSondageTest(){
        ResSond = new ResultatSondage();
    }

    @Test
    public void TestFonctionsMembres() {
        ResSond.setIdSondage(100);
        assertEquals(100,ResSond.getIdSondage());


        long time = System.currentTimeMillis();
        ResSond.setDateSondage(time);
        assertEquals(time, ResSond.getDateSondage());

        Map<Ressenti,Integer> monSondage = new HashMap<>();
        monSondage.put(Ressenti.Complique, 125);
        monSondage.put(Ressenti.Accessible, 134);
        ResSond.setResultats(monSondage);
        assertEquals(monSondage, ResSond.getResultats());
    }    
}
