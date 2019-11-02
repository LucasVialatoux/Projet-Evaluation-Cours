package business;

import java.util.Map;

public class ResultatSondage {
    private int idSondage;
    private long dateSondage;
    private Map<Ressenti, Integer> resultats;
    
    public int getIdSondage() {
        return idSondage;
    }
    
    public void setIdSondage(int idSondage) {
        this.idSondage = idSondage;
    }
    
    public long getDateSondage() {
        return dateSondage;
    }
    
    public void setDateSondage(long dateSondage) {
        this.dateSondage = dateSondage;
    }
    
    public Map<Ressenti, Integer> getResultats() {
        return resultats;
    }
    
    public void setResultats(Map<Ressenti, Integer> resultats) {
        this.resultats = resultats;
    }
    
    

}
