package dao;

import business.Matiere;
import business.MatiereResultat;

public interface MatiereDao {
    // Ajout matière (idprof, nommat)
    public void ajoutMatiere(String idProf, String nomMat) throws MatiereDaoException;
    
    // Supprimer une matière
    public void supprimerMatiere(String idProf, String numMat) throws MatiereDaoException;
    
    // Récupérer les matières (avec sondages dedans) (idprof)
    public Matiere getMatieres(String idProf) throws MatiereDaoException;
    
    // Récupérer resultat des matières
    public MatiereResultat getResultat() throws MatiereDaoException;

}
