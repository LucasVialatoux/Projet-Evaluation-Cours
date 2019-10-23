package course_feeling.business;

import java.util.ArrayList;
import java.util.List;

public class Sondage {
    private int id;
    private List<Ressenti> ressentis = new ArrayList<Ressenti>();
    
    public Sondage(int id) {
        this.id = id;
    }

    /**
     * Ajoute un ressenti au sondage.
     * @param ressenti : Ressenti à ajouter.
     */
    public void addRessenti(Ressenti ressenti) {
        ressentis.add(ressenti);
    }
    
    /**
     * Renvoie le nombres de ressenti d'un sondage.
     * @return int
     */
    public int getRessentisNumber() {
        return ressentis.size();
    }
    
    /**
     * Renvoie un ressenti du sondage.
     * @param i : int, indice du ressenti à renvoyer
     * @return Ressenti
     */
    public Ressenti getRessenti(int i) {
        return ressentis.get(i);
    }

    /*
     * Getters and setters
     */
    public int getId() {
        return id;
    }

}
