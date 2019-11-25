package business;

import org.junit.Test;

import business.Matiere;
import business.Sondage;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

public class MatiereTest {

    Matiere matiereTest1;

    public MatiereTest(){
        String nameMatiere1 = "UE-Test1";
        matiereTest1 = new Matiere();
        
        matiereTest1.setName(nameMatiere1);
    }

    @Test 
    public void MatiereTestGetName() {
        assertEquals("UE-Test1", matiereTest1.getName());
    }

    @Test
    public void MatiereTestSetGetName() {
        matiereTest1.setName("UE-Test");
        assertEquals("UE-Test", matiereTest1.getName());
    }

    @Test
    public void MatiereTestGetSondageEtSetSondage() {
        List<Sondage> listeDeSondageTest = new ArrayList<Sondage>();

        Sondage s = new Sondage();
        s.setId(154);
        s.setDate(173837829);
        listeDeSondageTest.add(s);

        Sondage s2 = new Sondage();
        s.setId(345);
        s.setDate(89787897);
        listeDeSondageTest.add(s2);

        matiereTest1.setSondages(listeDeSondageTest);

        assertEquals(listeDeSondageTest, matiereTest1.getSondages());
    }
}
