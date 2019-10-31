package business;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.ArrayList;

@Entity
@Table(name = "MATIERES")
public class Matiere {
    
    @Id
    private int id;
    private String name;
    private List<Sondage> sondages = new ArrayList<Sondage>();
    
    public Matiere(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Ajoute un sondage à la matière.
     * @param sondage à ajouter
     */
    public void addSondage(Sondage sondage) {
        sondages.add(sondage);
    }
    
    /**
     * Renvoie le nombre de sondage de la matière.
     * @return int
     */
    public int getSondageNumber() {
        return sondages.size();
    }
    
    /**
     * Renvoie un sondage de la liste.
     * @param i : int, l'indice du sondage à récupérer.
     * @return Sondage
     */
    public Sondage getSondage(int i) {
        return sondages.get(i);
    }
    
    /*
     * Getters and setters
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sondage> getSondages() {
        return sondages;
    }

    public void setSondages(List<Sondage> sondages) {
        this.sondages = sondages;
    }
}
