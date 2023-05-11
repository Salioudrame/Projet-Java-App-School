package application.model;

import java.sql.Date;

public class Apprenant 
{
	private int id;
	private String nom;
	private String prenom;
	private Date DateNaissance;
	private String lieuNaissance;
	private String genre;
	private String classe;
	private String section;
	private double moyenne_semestre_1;
	private double moyenne_semestre_2;
	private double moyenneannuelle;
	private int rang;
 
	 
	public double getMoyenneannuelle() {
		return moyenneannuelle;
	}
	public void setMoyenneannuelle(double moyenneannuelle) {
		this.moyenneannuelle = moyenneannuelle;
	}
	public double getMoyenne_semestre_2() {
		return moyenne_semestre_2;
	}
	public void setMoyenne_semestre_2(double moyenne_semestre_2) {
		this.moyenne_semestre_2 = moyenne_semestre_2;
	}
	public double getMoyenne_semestre_1() {
		return moyenne_semestre_1;
	}
	public void setMoyenne_semestre_1(double moyenne_semestre_1) {
		this.moyenne_semestre_1 = moyenne_semestre_1;
	}
	public int getRang() {
		return rang;
	}
	public void setRang(int rang) {
		this.rang = rang;
	}
	public Apprenant(int id, String nom, String prenom, Date dateNaissance, String lieuNaissance, String genre,
			String classe, String section) 
	{
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.DateNaissance = dateNaissance;
		this.lieuNaissance = lieuNaissance;
		this.genre = genre;
		this.classe = classe;
		this.section = section;
	}
	public Apprenant(String nom, String prenom, String classe) 
	{
	        this.nom = nom;
	        this.prenom = prenom;
	        this.classe = classe;
	}
	public Apprenant(int id, String nom, String prenom, String classe) 
	{		
			this.id=id; 
	        this.nom = nom;
	        this.prenom = prenom;
	        this.classe = classe;
	}
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getNom() 
	{
		return nom;
	}
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	public String getPrenom() 
	{
		return prenom;
	}
	public void setPrenom(String prenom) 
	{
		this.prenom = prenom;
	}
	public Date getDateNaissance() 
	{
		return DateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) 
	{
		DateNaissance = dateNaissance;
	}
	public String getLieuNaissance() 
	{
		return lieuNaissance;
	}
	public void setLieuNaissance(String lieuNaissance) 
	{
		this.lieuNaissance = lieuNaissance;
	}
	public String getGenre() 
	{
		return genre;
	}
	public void setGenre(String genre) 
	{
		this.genre = genre;
	}
	public String getClasse() 
	{
		return classe;
	}
	public void setClasse(String classe) 
	{
		this.classe = classe;
	}
	public String getSection() 
	{
		return section;
	}
	public void setSection(String section) 
	{
		this.section = section;
	} 
	public Apprenant(String nom) {
	    this.nom = nom;
	}
	public Apprenant(String nom, String prenom, double moyenne_apprenant, double moyenne_apprenant_2, int rang) 
	{
		this.nom = nom;
		this.prenom = prenom;
		this.moyenne_semestre_1 = moyenne_apprenant;
		this.moyenne_semestre_2 = moyenne_apprenant_2; 
		this.rang=rang; 
	}
	public Apprenant(int id,String prenom,String nom,Date dateNaissance,String lieuNaissance,String classe) {
		this.id=id;
		this.prenom=prenom;
		this.nom=nom;
		this.DateNaissance=dateNaissance;
		this.lieuNaissance=lieuNaissance;
		this.classe=classe;
	}
	 
	   
	public Apprenant(int id, String nom, String prenom, String classe, double moyenne_semestre_1, double moyenne_semestre_2) 
	{	this.id=id; 
		this.nom = nom;
		this.prenom = prenom;
		this.moyenne_semestre_1 = moyenne_semestre_1;
		this.moyenne_semestre_2 = moyenne_semestre_2; 
	}
	public Apprenant(int id,String nom, String prenom, String classe, double moyenne_annuelle)
	{
		this.id=id; 
		this.nom = nom;
		this.prenom = prenom;
		this.moyenneannuelle=moyenne_annuelle;
	}
	public double getMoyenneSemestre1() {
		 
		return moyenne_semestre_1; 
	}
	public double getMoyenneSemestre2() {
		
		return moyenne_semestre_2;
	}
	 
}
