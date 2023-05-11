package application.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

import application.view.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class Paiement {
	private int idPaiement;
	private String prenom;
	private String nom;
	private String classe;
	private int montant;
	private Date datePayement;
	private Date dateEcheance;
	private String statut;
	private int montantPaye;
	
	
	
	public Paiement(int montant) {
		super();
		this.montant = montant;
	}
	public Paiement(int idPaiement,String prenom,String nom,int montant,Date datePaiement,String statut) {
		super();
		this.idPaiement=idPaiement;
		this.prenom=prenom;
		this.nom=nom;
		this.montant=montant;
		this.datePayement=datePaiement;
		this.statut=statut;
	}
	public Paiement(int idPaiement, String prenom, String nom, String classe, int montant, Date datePayement,
			Date dateEcheance, String statut, int montantPaye) {
		super();
		this.idPaiement = idPaiement;
		this.prenom = prenom;
		this.nom = nom;
		this.classe = classe;
		this.montant = montant;
		this.datePayement = datePayement;
		this.dateEcheance = dateEcheance;
		this.statut = statut;
		this.montantPaye = montantPaye;
	}
	
	
	public Paiement(Date dateEcheance) {
		super();
		this.dateEcheance = dateEcheance;
	}
	public Paiement(int idPayement, Date date, int montant) {
		// TODO Auto-generated constructor stub
		this.idPaiement = idPayement;
		this.datePayement = date;
		this.montant = montant;
	}
	public int getIdPaiement() {
		return idPaiement;
	}
	public void setIdPaiement(int idPaiement) {
		this.idPaiement = idPaiement;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public int getMontant() {
		return montant;
	}
	public void setMontant(int montant) {
		this.montant = montant;
	}
	public Date getDatePayement() {
		return datePayement;
	}
	public void setDatePayement(Date datePayement) {
		this.datePayement = datePayement;
	}
	public Date getDateEcheance() {
		return dateEcheance;
	}
	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public int getMontantPaye() {
		return montantPaye;
	}
	public void setMontantPaye(int montantPaye) {
		this.montantPaye = montantPaye;
	}
	
	
	
	
}
















//
//
//LocalDate today=LocalDate.now();
//String sql="SELECT * FROM payement WHERE datePayement= "+today+" ";
//
//
//try {
//	Connection connection= DataBaseConnection.getConnection();
//	Statement statement=connection.createStatement();
//	ResultSet resultSet=statement.executeQuery(sql);
//	ObservableList<Paiement> paiements=FXCollections.observableArrayList();
//	
//	while(resultSet.next()) {
//		int idPayement=resultSet.getInt("idPayement");
//		Date date=resultSet.getDate("datePayement");
//		int montant=resultSet.getInt("montant");
//		
//		paiements.add(new Paiement(idPayement,date,montant));
//	}
//	this.dayPaiementTable.setItems(paiements);
//    this.idPayementColumn.setCellValueFactory(new PropertyValueFactory<>("idPayement"));
//    this.datePayementColumn.setCellValueFactory(new PropertyValueFactory<>("datePayement"));
//    this.montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
//    
//    System.out.println("today c est "+today);
//    System.out.println("le montant est ");
//}catch (Exception e) {
//	// TODO: handle exception
//	e.printStackTrace();
//}
