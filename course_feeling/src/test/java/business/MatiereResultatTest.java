package business;

import org.junit.Test;

import business.MatiereResultat;
import business.ResultatSondage;
import business.Ressenti;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MatiereResultatTest {
    @Test
    public void SetterGetter() {
        MatiereResultat mrTest = new MatiereResultat();
        mrTest.setNom("Test");

        Map<Ressenti,Integer> monSondage = new HashMap<>();
        monSondage.put(Ressenti.Complique, 125);
        monSondage.put(Ressenti.Accessible, 134);
        //ResSond.setResultats(monSondage);

        List<ResultatSondage> listeDeSondageTest = new ArrayList<ResultatSondage>();
        ResultatSondage RS = new ResultatSondage();
        RS.setIdSondage(2424);
        RS.setDateSondage(23112019);
        RS.setResultats(monSondage);
        listeDeSondageTest.add(RS);

        mrTest.setResultatsSondage(listeDeSondageTest);

        assertEquals("Test", mrTest.getNom());
        assertEquals(23112019, RS.getDateSondage());
        assertEquals(listeDeSondageTest, mrTest.getResultatsSondage());

    }
}