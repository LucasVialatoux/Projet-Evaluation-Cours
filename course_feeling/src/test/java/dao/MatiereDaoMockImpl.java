package dao;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import business.Ressenti;
import business.ResultatSondage;
import business.MatiereResultat;
import business.Sondage;
import business.Matiere;

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
        return null;
    }

    private ResultatSondage getResultatSondage(List<ResultatSondage> resultats,
            int id) {
        return null;
    }

    @Override
    public MatiereResultat getResultat(String idProf, String nomMat)
            throws MatiereDaoException {
        return null;
    }
}
