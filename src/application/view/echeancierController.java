package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import application.model.Paiement;
import application.model.Rubriques;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.DatePicker;

public class echeancierController implements Initializable {
	@FXML
	private DatePicker echeanceInput;
	@FXML
	private Button echeanceBtn;
	@FXML
	private Label echeanceText;

	// Event Listener on Button[#echeanceBtn].onMouseClicked
    @SuppressWarnings("unused")
	private void changeEcheance() throws Exception 
	{
    	 
		 String sql1 = "SELECT * from payement";
	            
				try {
					Connection connection = DataBaseConnection.getConnection();
					Statement statement = connection.createStatement();
		            ResultSet resultSet = statement.executeQuery(sql1);
		            ObservableList<Paiement> paiements = FXCollections.observableArrayList();

		            while(resultSet.next()) { 
		            	int idPayement=resultSet.getInt("idPayement");
		                String prenom = resultSet.getString("prenom");
		                String nom = resultSet.getString("prenom");
		                String classe = resultSet.getString("classe");
		                int montant=resultSet.getInt("montant");
		                Date datePayement=resultSet.getDate("datePayement");
		                Date dateEcheance=resultSet.getDate("dateEcheance");
		                String statut = resultSet.getString("statut");
		                int montantPaye=resultSet.getInt("montantPaye");
		          
		                		                
		                paiements.add(new Paiement(idPayement,prenom,nom,classe,montant,datePayement,dateEcheance,statut,montantPaye));
		                //rubrique.add(new Rubriques(idRubriques,classe, droitDinscription, scolarite,albums,tenues,fraisGeneraux,cotisationAPE,total));
		            }

		            

		            insertUpdatedDate(paiements);
		            
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//return null;
				}
				
	            	        
	}
    
    public Date selectUpdatedDate() {
    	String sql="SELECT dateEcheance FROM payement";
    	try {
    		Connection connection=DataBaseConnection.getConnection();
    		Statement statement=connection.createStatement();
    		ResultSet resultSet = statement.executeQuery(sql);
    		ObservableList<Paiement> paiements = FXCollections.observableArrayList();
    		while(resultSet.next()) { 
            	Date dateEcheance=resultSet.getDate("dateEcheance");
                		                
                paiements.add(new Paiement(dateEcheance));
                //rubrique.add(new Rubriques(idRubriques,classe, droitDinscription, scolarite,albums,tenues,fraisGeneraux,cotisationAPE,total));
            }
    		Date dateLimite=null;
    		for (Paiement paiement : paiements) {
				dateLimite=paiement.getDateEcheance();
			}
    		
    		return dateLimite;
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return null;
		}
    }
    
    public void insertUpdatedDate(ObservableList<Paiement> paiements) {
    	
    	for (Paiement paiement1 : paiements) {
    		String sql = "UPDATE payement SET dateEcheance= ? WHERE idPayement= ?";
        		try {
        		LocalDate dateEcheance= this.echeanceInput.getValue();
        			
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setDate(1, java.sql.Date.valueOf(dateEcheance));
                statement.setInt(2, paiement1.getIdPaiement());
                
                
                statement.execute();
                //int rowsInserted = statement.executeUpdate();
                
                
    		}catch (Exception e) {
				e.getMessage();
			}
    	}
  }
    public void resetField() {
    	this.echeanceInput.setValue(null);
    }
	@FXML
	public void editEcheance(MouseEvent event) {
		try {
			
			changeEcheance();
			echeanceText.setText(""+selectUpdatedDate());
			resetField();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		echeanceText.setText(""+selectUpdatedDate());
		
	}
}
