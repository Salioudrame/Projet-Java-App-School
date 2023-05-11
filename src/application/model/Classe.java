package application.model;

import java.util.ArrayList;
import java.util.List;

public class Classe {
    private String nomSection;
    private String nomClasse;
    private int droitInscription;
    private int scolarite;
    private int tenues;
    private int fraisGeneraux;
    private int cotisationAPE;
    private int albums;
    private int totalAPayer;
    private ArrayList<Apprenant> listeDesApprenants;

    public Classe(String nomSection, String nomClasse, int droitInscription, int scolarite, int tenues, int fraisGeneraux, int cotisationAPE, int albums) 
    {
        this.nomSection = nomSection;
        this.nomClasse = nomClasse;
        this.droitInscription = droitInscription;
        this.scolarite = scolarite;
        this.tenues = tenues;
        this.fraisGeneraux = fraisGeneraux;
        this.cotisationAPE = cotisationAPE;
        this.albums = albums;
        this.totalAPayer = droitInscription + (scolarite * 10) + tenues + fraisGeneraux + cotisationAPE + albums;
        this.listeDesApprenants = new ArrayList<Apprenant>();
    }
    public Classe(String nomClasse)
    {
        this.nomClasse = nomClasse;
    }

    public String getNomSection() 
    {
        return nomSection;
    }

    public String getNomClasse() 
    {
        return nomClasse;
    }

    public int getDroitInscription() 
    {
        return droitInscription;
    }

    public int getScolarite() 
    {
        return scolarite;
    }

    public int getTenues() 
    {
        return tenues;
    }

    public int getFraisGeneraux() 
    {
        return fraisGeneraux;
    }

    public int getCotisationAPE() 
    {
        return cotisationAPE;
    }

    public int getAlbums() 
    {
        return albums;
    }

    public int getTotalAPayer() 
    {
        return totalAPayer;
    }

    public ArrayList<Apprenant> getListeDesApprenants() 
    {
        return listeDesApprenants;
    }

    public void ajouterApprenant(Apprenant apprenant) 
    {
        listeDesApprenants.add(apprenant);
    }
    public static List<String> getNomsClasses() {
        List<String> nomsClasses = new ArrayList<>();
        nomsClasses.add("CI");
        nomsClasses.add("CP");
        nomsClasses.add("CE1");
        nomsClasses.add("CE2");
        nomsClasses.add("CM1");
        nomsClasses.add("CM2");
        nomsClasses.add("CRECHE");
        nomsClasses.add("TPS");
        nomsClasses.add("PS");
        nomsClasses.add("MS");
        nomsClasses.add("6e");
        nomsClasses.add("5e");
        nomsClasses.add("4e");
        nomsClasses.add("3e");
        nomsClasses.add("CAP_BEP1");
        nomsClasses.add("CAP_BEP2");
        nomsClasses.add("CAP_BEP3");
        return nomsClasses;
    }
    public static List<String> getNomsSection()
    { 
    	List<String> nomSection = new ArrayList<>();
        nomSection.add("elementaire");
        nomSection.add("maternelle");
        nomSection.add("college");
        nomSection.add("froidclimatisation");
		return nomSection;
    }
     

}
 