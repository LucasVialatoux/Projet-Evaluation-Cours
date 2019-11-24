package dao;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import business.Ressenti;
import business.ResultatSondage;
import business.MatiereResultat;
import business.Sondage;
import business.Matiere;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

public class MatiereDaoMockImpl implements MatiereDao {

    @Override
    public void ajoutMatiere(String idProf, String nomMat)
            throws MatiereDaoException {
        
    }

    @Override
    public void supprimerMatiere(String idProf, String nomMat)
            throws MatiereDaoException {
    }

    @Override
    public List<Matiere> getMatieres(String idProf) throws MatiereDaoException {
        if (idProf == "test@test.com") {
            //Création d'une matière
            Matiere maMatiere = new Matiere();
            maMatiere.setName("LIFAP1");
            List<Sondage> listeDeSondageVide = new ArrayList<Sondage>();
            maMatiere.setSondages(listeDeSondageVide);

            Matiere maMatiere2 = new Matiere();
            maMatiere2.setName("LIFAP2");
            List<Sondage> listeDeSondageVide2 = new ArrayList<Sondage>();
            maMatiere2.setSondages(listeDeSondageVide2);

            List<Matiere> maListeTest = new ArrayList<Matiere>();
            maListeTest.add(maMatiere);
            maListeTest.add(maMatiere2);
            return maListeTest;
        } else
            return null;
    }

    private ResultatSondage getResultatSondage(List<ResultatSondage> resultats,
            int id) {
        return null;
    }

    @Override
    public MatiereResultat getResultat(String idProf, String nomMat)
            throws MatiereDaoException {
        if (idProf == "test@test2.com") {
            //Creation d'une matiereResultat
            MatiereResultat mr = new MatiereResultat();
            mr.setNom("WEB");
            //On lui ajoute un ResultatSondage
            ResultatSondage rs = new ResultatSondage();
            rs.setIdSondage(666);
            rs.setDateSondage(24112019);
            //On ajouter une map de ressenti au ResultatSondage de matiereResultat
            Map<Ressenti,Integer> monSondage = new HashMap<>();
        	monSondage.put(Ressenti.Complique, 125);
            monSondage.put(Ressenti.Accessible, 134);
            
            rs.setResultats(monSondage);
            List<ResultatSondage> listeDeResultatSondage = new ArrayList<>();
            listeDeResultatSondage.add(rs);
            
            mr.setResultatsSondage(listeDeResultatSondage);

            return mr;
        } else if (idProf == "test@test3.com") {
            //Creation d'une matiereResultat
            MatiereResultat mr = new MatiereResultat();
            mr.setNom("WEB");
            //On lui ajoute un ResultatSondage
            ResultatSondage rs = new ResultatSondage();
            rs.setIdSondage(666);
            rs.setDateSondage(24112019);
            //On ajouter une map de ressenti au ResultatSondage de matiereResultat
            Map<Ressenti,Integer> monSondage = new HashMap<>();
        	monSondage.put(Ressenti.Complique, 125);
            monSondage.put(Ressenti.Accessible, 134);
            
            rs.setResultats(monSondage);
            List<ResultatSondage> listeDeResultatSondage = new ArrayList<>();
            listeDeResultatSondage.add(rs);
            
            mr.setResultatsSondage(listeDeResultatSondage);

            return mr;
        } else
            return null;
    }
}
