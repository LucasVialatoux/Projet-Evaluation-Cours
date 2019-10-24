package course_feeling.business;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SondageTest {

    Sondage sondage1;

    public  SondageTest() {
        sondage1 = new Sondage(1);
    }

    @Test
    public void SondageTestGetId() {
        assertEquals(1, sondage1.getId());
    }

    @Test
    public void SondageTestAddRessenti() {
        // On vérifie qu'on a aucun ressenti
        assertEquals(0, sondage1.getRessentisNumber());

        // On ajoute un ressenti
        Ressenti ressenti1 = Ressenti.Interessant;
        sondage1.addRessenti(ressenti1);
        
        //On vérifie son ajout
        assertEquals(1, sondage1.getRessentisNumber());

        //On ajoute un second ressenti
        Ressenti ressenti2 = Ressenti.Complique;
        sondage1.addRessenti(ressenti2);
        assertEquals(2, sondage1.getRessentisNumber());

        //On vérifie que l'ajout s'est fait correctement
        assertEquals("Interessant", sondage1.getRessenti(0).toString());
        assertEquals("Complique", sondage1.getRessenti(1).toString());
    }

    @Test
    public void SondageTestGrandId() {
        Sondage sondage999 = new Sondage(2147483647); 
        for (int i= 0 ; i < 1000 ; i++) 
            sondage999.addRessenti(Ressenti.Accessible);
        assertEquals(1000, sondage999.getRessentisNumber());
    }

    @Test
    public void SondageTestPetitId() {
        Sondage sondage0 = new Sondage(0);
        assertEquals(0, sondage0.getRessentisNumber());
    }
      
    @Test
    public void SondageTestNegatifId() {
        Sondage sondageMoins1000 = new Sondage(-1000);
        assertEquals(0, sondageMoins1000.getRessentisNumber());
    }
}