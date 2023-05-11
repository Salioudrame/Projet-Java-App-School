package application.model;

public class Notes {
	private int id_note;
	private String matiere;
	private int note;
	private int id_apprenant; 
	
	
	public Notes(int id_note, String matiere, int note, int id_apprenant) {
		super();
		this.id_note = id_note;
		this.matiere = matiere;
		this.note = note;
		this.id_apprenant=id_apprenant;
	}
	
	
	
	public int getId_apprenant() {
		return id_apprenant;
	}
	public void setId_apprenant(int id_apprenant) {
		this.id_apprenant = id_apprenant;
	}
	public int getId_note() {
		return id_note;
	}
	public void setId_note(int id_note) {
		this.id_note = id_note;
	}
	public String getMatiere() {
		return matiere;
	}
	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	
	
	
}
