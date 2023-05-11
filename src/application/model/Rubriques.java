package application.model;

public class Rubriques {
	private int idRubriques;
	private String classe;
	private int droitDinscription;
	private int scolarite;
	private int albums;
	private int tenues;
	private int fraisGeneraux;
	private int cotisationAPE;
	private int total;
	
	
	
	
	
	public Rubriques(int idRubriques, String classe, int droitDinscription, int scolarite, int albums, int tenues,
			int fraisGeneraux, int cotisationAPE,int total) {
		super();
		this.idRubriques = idRubriques;
		this.classe = classe;
		this.droitDinscription = droitDinscription;
		this.scolarite = scolarite;
		this.albums = albums;
		this.tenues = tenues;
		this.fraisGeneraux = fraisGeneraux;
		this.cotisationAPE = cotisationAPE;
		this.total=total;
	}
	
	public Rubriques(String classe,int total) {
		super();
		this.classe = classe;
		this.total=total;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getIdRubriques() {
		return idRubriques;
	}
	public void setIdRubriques(int idRubriques) {
		this.idRubriques = idRubriques;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public int getDroitDinscription() {
		return droitDinscription;
	}
	public void setDroitDinscription(int droitDinscription) {
		this.droitDinscription = droitDinscription;
	}
	public int getScolarite() {
		return scolarite;
	}
	public void setScolarite(int scolarite) {
		this.scolarite = scolarite;
	}
	public int getAlbums() {
		return albums;
	}
	public void setAlbums(int albums) {
		this.albums = albums;
	}
	public int getTenues() {
		return tenues;
	}
	public void setTenues(int tenues) {
		this.tenues = tenues;
	}
	public int getFraisGeneraux() {
		return fraisGeneraux;
	}
	public void setFraisGeneraux(int fraisGeneraux) {
		this.fraisGeneraux = fraisGeneraux;
	}
	public int getCotisationAPE() {
		return cotisationAPE;
	}
	public void setCotisationAPE(int cotisationAPE) {
		this.cotisationAPE = cotisationAPE;
	}
	
	
}
