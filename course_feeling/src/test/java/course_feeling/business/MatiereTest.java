package course_feeling.business;

import org.junit.Test;

import business.Matiere;
import business.Ressenti;
import business.Sondage;

import static org.junit.Assert.assertEquals;

public class MatiereTest {

    Matiere matiereTest1;

    public MatiereTest(){
        int idMatiere1 = 1;
        String nameMatiere1 = "UE-Test1";
        matiereTest1 = new Matiere(idMatiere1, nameMatiere1);
    }

    @Test
    public void MatiereTestGetId() {
        assertEquals(1, matiereTest1.getId());
    }

    @Test 
    public void MatiereTestGetName() {
        assertEquals("UE-Test1", matiereTest1.getName());
    }

    @Test
    public void MatiereTestSetIdGetId() {
        matiereTest1.setId(2);
        assertEquals(2, matiereTest1.getId());

        matiereTest1.setId(1);
    }

    @Test
    public void MatiereTestSetGetName() {
        matiereTest1.setName("UE-Test");
        assertEquals("UE-Test", matiereTest1.getName());
    }

    @Test
    public void MatiereTestSondage() {
        Sondage sondage1 = new Sondage(1);
        sondage1.addRessenti(Ressenti.Accessible);
        sondage1.addRessenti(Ressenti.Fatiguant);

        assertEquals(0, matiereTest1.getSondageNumber());
        matiereTest1.addSondage(sondage1);
        assertEquals(1, matiereTest1.getSondageNumber());

        Sondage sondage2 = new Sondage(2);
        sondage2.addRessenti(Ressenti.Fatiguant);
        sondage2.addRessenti(Ressenti.Complique);
        matiereTest1.addSondage(sondage2);
        assertEquals(2, matiereTest1.getSondageNumber());

        assertEquals("Complique", matiereTest1.getSondage(1).getRessenti(1).toString());
    }
    
    @Test 
    public void MatiereTestBeaucoupSondages() {
        Matiere matiereTest2 = new Matiere(2147483647, "AZFNVJFcvgfdse(à(à(à12339292928");
        for (int i = 1 ; i <= 1000 ; i++) {
            Sondage sondageToAdd = new Sondage(-999);
            sondageToAdd.addRessenti(Ressenti.Accessible);
            sondageToAdd.addRessenti(Ressenti.Fatiguant);
            sondageToAdd.addRessenti(Ressenti.Complique);
            matiereTest2.addSondage(sondageToAdd);
        }
        Sondage sondageToAdd2 = new Sondage(0);
        sondageToAdd2.addRessenti(Ressenti.Indifferent);
        matiereTest2.addSondage(sondageToAdd2);
        for (int i = 0 ; i < 500 ; i++) {
            Sondage sondageToAdd = new Sondage(-999);
            sondageToAdd.addRessenti(Ressenti.Accessible);
            sondageToAdd.addRessenti(Ressenti.Fatiguant);
            sondageToAdd.addRessenti(Ressenti.Complique);
            matiereTest2.addSondage(sondageToAdd);
        }

        assertEquals("Indifferent", matiereTest2.getSondage(1000).getRessenti(0).toString());
    }
}
