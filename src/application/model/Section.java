package application.model;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private List<Matiere> matieresCollege;
    private List<Matiere> matieresElementaire;
    private List<Matiere> matieresFroidClimatisation;

    public List<Matiere> getMatieresCollege() {
        return matieresCollege;
    }

    public void setMatieresCollege(List<Matiere> matieresCollege) {
        this.matieresCollege = matieresCollege;
    }

    public List<Matiere> getMatieresElementaire() {
        return matieresElementaire;
    }

    public void setMatieresElementaire(List<Matiere> matieresElementaire) {
        this.matieresElementaire = matieresElementaire;
    }

    public List<Matiere> getMatieresFroidClimatisation() {
        return matieresFroidClimatisation;
    }

    public void setMatieresFroidClimatisation(List<Matiere> matieresFroidClimatisation) {
        this.matieresFroidClimatisation = matieresFroidClimatisation;
    }
 
}
