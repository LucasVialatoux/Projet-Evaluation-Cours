package business;

import java.util.List;

import java.util.ArrayList;

public class Matiere {
    
    private String name;
    private List<Sondage> sondages = new ArrayList<>();
    
    public Matiere() {
        // Les données membres sont modifiés ensuite
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
