package application.model;

public class Matiere {

    private String nomProf;
    private String nomMatiere;
    private double coeff;
    private double noteMath;
    private double noteSVT;
    private double noteFrancais;
    private double noteAnglais;
    private double noteHistoireGeographie;
    private double noteSciencesPhysiques;
    private double noteEPS;
    private double notePhilosophie;

    public Matiere(String nomProf, String nomMatiere, double coeff) {
        this.nomProf = nomProf;
        this.nomMatiere = nomMatiere;
        this.coeff = coeff;
    }

    public String getNomProf() {
        return nomProf;
    }

    public void setNomProf(String nomProf) {
        this.nomProf = nomProf;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public double getCoeff() {
        return coeff;
    }

    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }

    public double getNoteMath() {
        return noteMath;
    }

    public void setNoteMath(double noteMath) {
        this.noteMath = noteMath;
    }

    public double getNoteSVT() {
        return noteSVT;
    }

    public void setNoteSVT(double noteSVT) {
        this.noteSVT = noteSVT;
    }

    public double getNoteFrancais() {
        return noteFrancais;
    }

    public void setNoteFrancais(double noteFrancais) {
        this.noteFrancais = noteFrancais;
    }

    public double getNoteAnglais() {
        return noteAnglais;
    }

    public void setNoteAnglais(double noteAnglais) {
        this.noteAnglais = noteAnglais;
    }

    public double getNoteHistoireGeographie() {
        return noteHistoireGeographie;
    }

    public void setNoteHistoireGeographie(double noteHistoireGeographie) {
        this.noteHistoireGeographie = noteHistoireGeographie;
    }

    public double getNoteSciencesPhysiques() {
        return noteSciencesPhysiques;
    }

    public void setNoteSciencesPhysiques(double noteSciencesPhysiques) {
        this.noteSciencesPhysiques = noteSciencesPhysiques;
    }

    public double getNoteEPS() {
        return noteEPS;
    }

    public void setNoteEPS(double noteEPS) {
        this.noteEPS = noteEPS;
    }

    public double getNotePhilosophie() {
        return notePhilosophie;
    }

    public void setNotePhilosophie(double notePhilosophie) {
        this.notePhilosophie = notePhilosophie;
    }

}
