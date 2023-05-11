package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.Apprenant;
import application.model.Notes;
import application.model.Paiement;
import application.model.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class operationsController implements Initializable {
	@FXML
	private TableView<Paiement> paiementTable;
	@FXML
	private TableColumn<Paiement,Integer> idColumn;
	@FXML
	private TableColumn<Paiement,String> prenomColumn;
	@FXML
	private TableColumn<Paiement,String> nomColumn;
	@FXML
	private TableColumn<Paiement,Integer> montantColumn;
	@FXML
	private TableColumn<Paiement,Date> dateColumn;
	@FXML
	private TableColumn<Paiement,String> statutColumn;
	@FXML
	private Button deleteBtn1;
	@FXML
	private Button deleteBtn2;
	@FXML
	private Button deleteBtn3;
	
	
	
	@FXML
	private TableView<Apprenant> apprenantTable;
	@FXML
	private TableColumn<Apprenant,Integer> idAColumn;
	@FXML
	private TableColumn<Apprenant,String> prenomAColumn;
	@FXML
	private TableColumn<Apprenant,String> nomAColumn;
	@FXML
	private TableColumn<Apprenant,Date> dateAColumn;
	@FXML
	private TableColumn<Apprenant,String> lieuAColumn;
	@FXML
	private TableColumn<Apprenant,String> classeAColumn;
	
	
	@FXML
	private TableView<Notes> noteTable;
	@FXML
	private TableColumn<Notes,Integer> idNColumn;
	@FXML
	private TableColumn<Notes,String> matiereNColumn;
	@FXML
	private TableColumn<Notes,Integer> noteNColumn;

    @FXML
    private TableColumn<Notes, Integer> id_apprenantcolumn;
    
    @FXML
    private TextField txt_search;
	
	@FXML
	public void note() {
	 
	}
	@FXML
	public void deleteNote3() throws Exception {
	    String sql = "DELETE FROM notes WHERE id_note = ?";
	    String sql2 = "DELETE FROM notes WHERE id_apprenant = ?";
	    String sql3 = "UPDATE apprenantprofil SET moyenne_semestre_1 = NULL, moyenne_semestre_2 = NULL, rang = NULL WHERE id = ?";
	    
	    try {
	        Connection connection = DataBaseConnection.getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql);
	        PreparedStatement statement2 = connection.prepareStatement(sql2);
	        PreparedStatement statement3 = connection.prepareStatement(sql3);
	        Notes selectedNotes = noteTable.getSelectionModel().getSelectedItem();
	        
	        
	        if(selectedNotes == null) {
	            Alert alert =new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("aucune note selectionnée");
	            alert.setContentText("Veuillez selectionner une note");
	            alert.setHeaderText("Aucun note selectionnée ");
	            alert.showAndWait();
	        } else {
	            statement.setInt(1, selectedNotes.getId_note());
	            statement2.setInt(1, selectedNotes.getId_apprenant());
	            statement3.setInt(1, selectedNotes.getId_apprenant());
	            Alert alert = new Alert(AlertType.CONFIRMATION);
	            alert.setTitle("Suppression de toutes les notes");
	            alert.setHeaderText("Etes-vous sûr de vouloir supprimer toutes les notes de cet apprenant ?");
	            alert.setContentText("Cette action va également réinitialiser la moyenne et le rang de l'apprenant. Voulez-vous continuer ?");
	            
	            Optional<ButtonType> result = alert.showAndWait();
	            if(result.isPresent() && result.get() == ButtonType.OK) {
	                statement2.execute();
	                statement3.executeUpdate();
	                
	                int rowsDeleted = statement.executeUpdate();
	                if (rowsDeleted > 0) {
	                    Alert alerte = new Alert(AlertType.INFORMATION);
	                    alerte.setTitle("suppression réussie");
	                    alerte.setHeaderText("");
	                    alerte.setContentText("La suppression a bien été effectuée");
	                    alerte.showAndWait();
	                    new TableView();
	                }
	                refreshTableNotes();
	            }
	        }     
	    } catch (SQLException var16) {
	        var16.printStackTrace();
	    }
	}

	
	@FXML
	public void apprenant(){
		Apprenant selectedApprenant = apprenantTable.getSelectionModel().getSelectedItem();
    	if(selectedApprenant==null) {
    		Alert alert =new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("aucun apprenant selectionné");
        	alert.setContentText("Veuillez selectionner un apprenant");
        	alert.setHeaderText("Aucun apprenant selectionné ");
        	alert.showAndWait();
    	}
	}
	
	@FXML
	public void deleteApprenant2() throws Exception {
		String sql = "DELETE FROM apprenantprofil WHERE ID= ?";
        
		try {
        Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        Apprenant selectedApprenant = apprenantTable.getSelectionModel().getSelectedItem();
        
        
        if(selectedApprenant == null) {
        	Alert alert =new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("aucun apprenant selectionné");
        	alert.setContentText("Veuillez selectionner un apprenant");
        	alert.setHeaderText("Aucun apprenant selectionné ");
        	alert.showAndWait();
        }else {
        	statement.setInt(1, selectedApprenant.getId());
        	Alert alert = new Alert(AlertType.CONFIRMATION);
  	      	alert.setTitle("suppression");
  	      	alert.setHeaderText("Etes vous sur de vouloir supprimer l'apprenant ?");
  	      	Stage stage=(Stage) deleteBtn2.getScene().getWindow();
  		
  		
  	      	Optional<ButtonType> result =alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
            
            statement.execute();
            
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                Alert alerte = new Alert(AlertType.INFORMATION);
                alerte.setTitle("suppression réussie");
                alerte.setHeaderText("");
                alerte.setContentText("La suppression a bien ete effectuée");
                alerte.showAndWait();
                new TableView();
                
            }
            refreshTableApprenant();
           }
        }     
    } catch (SQLException var16) {
        var16.printStackTrace();
    }

	}
	@FXML
	private TextField notefield;


    @FXML
    void searchNote(MouseEvent event) throws Exception 
    {
    	String sql ="SELECT note from notes WHERE id_note ='"+txt_search.getText()+"'";
    	try 
    	{
    		Connection connection = DataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
            {
            	 
            	notefield.setText(String.valueOf(resultSet.getInt("note"))); 
            	Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Bingo");
                alert.setHeaderText("Resultat");
                alert.setContentText("Cette note est bien disponible");
                alert.show();
                
            }
            else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de recherche");
                alert.setHeaderText("Aucun résultat");
                alert.setContentText("Aucune note correspond à la recherche");
                alert.show();
            }
            
    	}catch(SQLException e)
    	{
    		e.printStackTrace();
    	}

    }
    
    @FXML
    private Button modifierNotes;
     

    @FXML
    void editNote(MouseEvent event) throws Exception {
    double note = Double.parseDouble(notefield.getText()); // Correction 1: Convertir la chaîne de caractères en double
    String sql = "UPDATE notes SET note = ? WHERE id_note = ?"; // Correction 2: utiliser un point d'interrogation pour les paramètres de la requête
    try {
    Connection connection = DataBaseConnection.getConnection();
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setDouble(1, note);
    statement.setString(2, txt_search.getText()); // Correction 3: ajouter la valeur de l'id_note en tant que deuxième paramètre de la requête
    int rowsUpdated = statement.executeUpdate(); // Correction 4: utiliser la méthode executeUpdate() pour les requêtes de mise à jour
    if (rowsUpdated > 0) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Modification réussie");
    alert.setHeaderText("");
    alert.setContentText("La note de l'apprenant a bien été modifiée");
    alert.showAndWait();
    refreshTableNotes();
    }
    } catch (SQLException e) {
    e.printStackTrace();
    }
    }
    
 

	
	// Event Listener on TableView[#paiementTable].onMouseClicked
	@FXML
	public void paiement(MouseEvent event) {
		// TODO Autogenerated
		Paiement selectedPaiement = paiementTable.getSelectionModel().getSelectedItem();
    	if(selectedPaiement==null) {
    		Alert alert =new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("aucun paiement selectionné");
        	alert.setContentText("Veuillez selectionner un paiement");
        	alert.setHeaderText("Aucun paiement selectionné ");
        	alert.showAndWait();
    	}

	}
	// Event Listener on Button[#deleteBtn1].onMouseClicked
	@FXML
	public void deletePayement1(MouseEvent event) throws Exception {
		// TODO Autogenerated
			String sql = "DELETE FROM payement WHERE idPayement= ?";
        
			try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            Paiement selectedPaiement = paiementTable.getSelectionModel().getSelectedItem();
            
            
            if(selectedPaiement == null) {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
            	alert.setTitle("aucun utilisateur selectionné");
            	alert.setContentText("Veuillez selectionner un utilisateur");
            	alert.setHeaderText("Aucun utilisateur selectionné ");
            	alert.showAndWait();
            }else {
            	statement.setInt(1, selectedPaiement.getIdPaiement());
            	Alert alert = new Alert(AlertType.CONFIRMATION);
      	      	alert.setTitle("suppression");
      	      	alert.setHeaderText("Etes vous sur de vouloir supprimer le paiement ?");
      	      	Stage stage=(Stage) deleteBtn1.getScene().getWindow();
      		
      		
      	      	Optional<ButtonType> result =alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                
                statement.execute();
                
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    Alert alerte = new Alert(AlertType.INFORMATION);
                    alerte.setTitle("suppression réussie");
                    alerte.setHeaderText("");
                    alerte.setContentText("La suppression a bien ete effectuée");
                    alerte.showAndWait();
                    new TableView();
                    
                }
                refreshTablePaiement();
               }
            }     
        } catch (SQLException var16) {
            var16.printStackTrace();
        }
	
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.refreshTablePaiement();
		this.refreshTableApprenant();
		this.refreshTableNotes();
	}
	public void refreshTablePaiement(){
		String sql1 = "SELECT * from payement";
        
		try {
			Connection connection = DataBaseConnection.getConnection();
			Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            ObservableList<Paiement> paiements = FXCollections.observableArrayList();
            
            while(resultSet.next()) { 
            	int idPaiement=resultSet.getInt("idPayement");
                String prenom = resultSet.getString("prenom");
                String nom = resultSet.getString("nom"); 
                int montant = resultSet.getInt("montant");
                Date datePaiement = resultSet.getDate("datePayement");
                String statut=resultSet.getString("statut");
                
                paiements.add(new Paiement(idPaiement,prenom, nom, montant,datePaiement,statut));
            }

            this.paiementTable.setItems(paiements);
            this.idColumn.setCellValueFactory(new PropertyValueFactory<>("idPaiement"));
            this.prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            this.nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            this.montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
            this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("datePayement"));
            this.statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
            //this.refreshTablePaiement();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void refreshTableApprenant(){
		String sql1 = "SELECT * from apprenantprofil";
        
		try {
			Connection connection = DataBaseConnection.getConnection();
			Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            ObservableList<Apprenant> apprenants = FXCollections.observableArrayList();

            while(resultSet.next()) { 
            	int ID=resultSet.getInt("ID");
                String prenom = resultSet.getString("prenom");
                String nom = resultSet.getString("nom"); 
                Date dateNaissance = resultSet.getDate("dateNaissance");
                String lieuNaissance=resultSet.getString("lieuNaissance");
                String classe=resultSet.getString("classe");
                
                
                apprenants.add(new Apprenant(ID,prenom, nom, dateNaissance,lieuNaissance,classe));
            }

            this.apprenantTable.setItems(apprenants);
            this.idAColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            this.prenomAColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            this.nomAColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            this.dateAColumn.setCellValueFactory(new PropertyValueFactory<>("DateNaissance"));
            this.lieuAColumn.setCellValueFactory(new PropertyValueFactory<>("lieuNaissance"));
            this.classeAColumn.setCellValueFactory(new PropertyValueFactory<>("classe"));
            //this.refreshTablePaiement();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	public void refreshTableNotes(){
		String sql1 = "SELECT * from notes";
        
		try {
			Connection connection = DataBaseConnection.getConnection();
			Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            ObservableList<Notes> notes = FXCollections.observableArrayList();
            
            while(resultSet.next()) { 
            	int id_note=resultSet.getInt("id_note");
                String matiere = resultSet.getString("matiere");
                int note = resultSet.getInt("note");
                int id_apprenant=resultSet.getInt("id_apprenant");
                notes.add(new Notes(id_note,matiere,note, id_apprenant));
            }

            this.noteTable.setItems(notes);
            this.idNColumn.setCellValueFactory(new PropertyValueFactory<>("id_note"));
            this.matiereNColumn.setCellValueFactory(new PropertyValueFactory<>("matiere"));
            this.noteNColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
            this.id_apprenantcolumn.setCellValueFactory(new PropertyValueFactory<>("id_apprenant"));
            //this.refreshTablePaiement();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


}
