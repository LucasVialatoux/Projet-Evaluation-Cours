package business;

import java.util.List;

public class MatiereResultat {
    private String nom;
    private List<ResultatSondage> resultatsSondage;
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public List<ResultatSondage> getResultatsSondage() {
        return resultatsSondage;
    }
    
    public void setResultatsSondage(List<ResultatSondage> resultatsSondage) {
        this.resultatsSondage = resultatsSondage;
    }
    
}
