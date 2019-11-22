package business;

import org.junit.Test;

import business.Ressenti;
import business.Sondage;

import static org.junit.Assert.assertEquals;

public class SondageTest {

    Sondage sondage1;

    public  SondageTest() {
        sondage1 = new Sondage();
        sondage1.setId(1);
        sondage1.setDate(22112019);
    }

    @Test
    public void SondageTestGetId() {
        assertEquals(1, sondage1.getId());
    }

    @Test
    public void SondageTestGetDate() {
        assertEquals(22112019, sondage1.getDate());
    }
}