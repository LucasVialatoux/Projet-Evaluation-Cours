package business;

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
}
