package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.ResourceBundle;

import application.model.Apprenant;
import application.model.ApprenantComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BulletinsCAPBEPController implements Initializable{

    @FXML
    private TableView<Apprenant> listecapbepview;

	
    @FXML
    private ComboBox<String> classeCAP_BEP;
    
    @FXML
    private TableColumn<Apprenant, Double> addApprenant_Col_Nom21;

    @FXML
    private TableColumn<Apprenant, Double> addApprenant_Col_Prenom21;

    @FXML
    private TableColumn<Apprenant, Double> addApprenant_Col_Moy21;

    @FXML
    private TableColumn<Apprenant, Double> addApprenant_Col_Moy22;
    

    @FXML
    private TableColumn<Apprenant, Integer> addApprenant_Col_Rang22;
    
    @FXML
    private TextField thermofield2;

    @FXML
    private TextField coeffthermofield2;
    

    @FXML
    void selectCAP_BEP(ActionEvent event) 
    {
    	String classe = classeCAP_BEP.getSelectionModel().getSelectedItem();
    	if (classe !=null)
    	{
    		try 
    		{
    			showCapBep(classe);
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}

    }
    @FXML
    private TextField thermofield;

    @FXML
    private TextField coeffthermo;

    @FXML
    private Button save;
    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label classeLabel;
    
    @FXML
    private TextField electrofield;

    @FXML
    private TextField coeffelectro;
    
    @FXML
    private TextField réfrifield;

    @FXML
    private TextField coeffrefri;

    @FXML
    private TextField secfield;

    @FXML
    private TextField coefsec;
    
    @FXML
    private Button save2;

    @FXML
    private TextField electrofield2;

    @FXML
    private TextField coeffelectro2;
    
    @FXML
    private TextField coeffrefri2;
 
    @FXML
    private TextField réfrifield2;
    
    @FXML
    private TextField secfield2;

    @FXML
    private TextField coefsec2;
    
    @FXML
    private Button rang;


     

    private int previousSelectedApprenantId;

    public void showPersonDetails(Apprenant apprenant) throws Exception {
        if (apprenant != null) {
            this.nomLabel.setText(apprenant.getNom());
            this.prenomLabel.setText(apprenant.getPrenom());
            this.classeLabel.setText(apprenant.getClasse());
        } else {
            this.nomLabel.setText("");
            this.prenomLabel.setText("");
            this.classeLabel.setText("");
        }
    }


        // Afficher le nom, le prénom et la classe de l'apprenant
	public void showCapBep(String classe) throws Exception 
	{
		 String sql1 = "SELECT nom, prenom, classe, moyenne_semestre_1, moyenne_semestre_2, rang "
	                + "FROM apprenantprofil "
	                + "WHERE classe IN ('CAP_BEP1', 'CAP_BEP_2', 'CAP_BEP3') "
	                + "AND classe = '" + classe + "'";
		 
		 try {
	            Connection connection = DataBaseConnection.getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(sql1);
	            ObservableList<Apprenant> apprenants = FXCollections.observableArrayList();
	            while (resultSet.next()) {
	                String nom = resultSet.getString("nom");
	                String prenom = resultSet.getString("prenom");
	                double moyenne_apprenant = resultSet.getDouble("moyenne_semestre_1");
	                double moyenne_apprenant2 = resultSet.getDouble("moyenne_semestre_2");
	                DecimalFormat decimalFormat = new DecimalFormat("#.00");
	                String formattedMoyenne = decimalFormat.format(moyenne_apprenant);
	                String formattedMoyenne2 = decimalFormat.format(moyenne_apprenant2);
	                double moyenne_formatee;
	                int rang = resultSet.getInt("rang"); 


	                try {
	                    moyenne_formatee = Double.parseDouble(formattedMoyenne);
	                } catch (NumberFormatException nfe) {
	                    
	                    moyenne_formatee = 0.0;
	                }

	                double moyenne_formatee2;
	                try {
	                    moyenne_formatee2 = Double.parseDouble(formattedMoyenne2);
	                } catch (NumberFormatException nfe) {
	                    
	                    moyenne_formatee2 = 0.0;
	                }

	                apprenants.add(new Apprenant(nom, prenom,  moyenne_apprenant,moyenne_apprenant2, rang ));
	            }

	            this.listecapbepview.setItems(apprenants);
	            this.addApprenant_Col_Nom21.setCellValueFactory(new PropertyValueFactory<>("nom"));
	            this.addApprenant_Col_Prenom21.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	            this.addApprenant_Col_Moy21.setCellValueFactory(new PropertyValueFactory<>("moyenne_semestre_1"));
	            this.addApprenant_Col_Moy22.setCellValueFactory(new PropertyValueFactory<>("moyenne_semestre_2"));
	            this.addApprenant_Col_Rang22.setCellValueFactory(new PropertyValueFactory<>("rang"));
	            this.listecapbepview.getSelectionModel().selectedItemProperty().addListener(
	                    (observable, oldValue, newValue) -> {
	                        try {
	                            showPersonDetails(newValue);
	                        } catch (Exception e) {
	                        
	                            e.printStackTrace();
	                        }
	                    });
	        } catch (SQLException er) {
	            er.printStackTrace();
	        }
	    }
	
	  private boolean isNumeric(String str) {
	        if (str == null) {
	            return false;
	        }
	        // Vérifier si la chaîne ne contient que des chiffres
	        return str.matches("\\d+");
	    }

	  private boolean isValidNoteValue(String str) 
	  {
	        if (str == null) 
	        {
	            return false;
	        }
	        try 
	        {
	        double d = Double.parseDouble(str);
	        if (d < 0 || d > 20) 
	        {
	            return false;
	        }
	        
	        } 
	        catch (NumberFormatException nfe) 
	        {
	           return false;
	        }
	           return true;
	}
	@FXML
    void saveNotes(ActionEvent event) throws Exception 
	{
		Apprenant selectedApprenant = listecapbepview.getSelectionModel().getSelectedItem();
		if(selectedApprenant !=null)
		{
			try 
			{
				Connection connection = DataBaseConnection.getConnection();
				String thermoNotes = thermofield.getText();
				String electroNotes = electrofield.getText();
				String refriNotes = réfrifield.getText();
				String secNotes = secfield.getText();
			 
				
				String query1 = "SELECT id FROM apprenantprofil WHERE nom = ? AND prenom = ?"; 
				 PreparedStatement statement1 = connection.prepareStatement(query1);
                 statement1.setString(1, selectedApprenant.getNom());
                 statement1.setString(2, selectedApprenant.getPrenom());
                 
                 
                 ResultSet resultSet = statement1.executeQuery();
                 int id_apprenant = 0;
                 if (resultSet.next()) {
                     id_apprenant = resultSet.getInt("id");
                     
                 }
                 if (thermoNotes.isEmpty() || electroNotes.isEmpty() || refriNotes.isEmpty() || secNotes.isEmpty()) {
                	    Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir toutes les cases avant d'enregistrer les notes.");
                	    alert.showAndWait();
                	    return;
                	}
                 if (!isNumeric(thermoNotes) || !isNumeric(electroNotes) || !isNumeric(refriNotes) || !isNumeric(secNotes)) {
                	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les notes.");
                	    alert.showAndWait();
                	    return;
                	}

                	if (!isValidNoteValue(thermoNotes) || !isValidNoteValue(electroNotes) || !isValidNoteValue(refriNotes) || !isValidNoteValue(secNotes)) {
                	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des notes valides entre 0 et 20.");
                	    alert.showAndWait();
                	    return;
                	}
                	if (!isNumeric(coeffthermo.getText()) || !isNumeric(coeffelectro.getText()) || !isNumeric(coeffrefri.getText()) || !isNumeric(coefsec.getText())) {
                	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les coefficients.");
                	    alert.showAndWait();
                	    return;
                	}

                	if (Double.parseDouble(coeffthermo.getText()) <= 0 || Double.parseDouble(coeffelectro.getText()) <= 0 || Double.parseDouble(coeffrefri.getText()) <= 0 || Double.parseDouble(coefsec.getText()) <= 0) {
                	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des coefficients supérieurs à 0.");
                	    alert.showAndWait();
                	    return;
                	}


                 //Insérer les nouvelles notes dans la base de données;
                 String query = "INSERT INTO notes (id_apprenant, matiere, note) VALUES (?, ?, ?)";
                 PreparedStatement statement = connection.prepareStatement(query);
                 
                 statement.setInt(1, id_apprenant); 
                 statement.setString(2,  "Thermodynamique");
                 statement.setString(3, thermoNotes);
                 statement.executeUpdate();
                 
                 
                 statement.setString(2, "Electrotechnique");
                 statement.setString(3, electroNotes);
                 statement.executeUpdate();
                 
                 statement.setString(2, "Systéme de réfrigération");
                 statement.setString(3, refriNotes);
                 statement.executeUpdate();
                 
                 statement.setString(2, "Securite et prevention des risques");
                 statement.setString(3, secNotes);
                 statement.executeUpdate();
                 
                
                 //Calculer la moyenne et l'insérer dans la table notes
                 int nb_notes = 0; 
                 double somme_notes =0;
                 double somme_coefficients = 0; 
                 double coeff_thermo = Double.parseDouble(coeffthermo.getText());
                 double coeff_elec = Double.parseDouble(coeffelectro.getText());
                 double coeff_refri = Double.parseDouble(coeffrefri.getText());
                 double coeff_sec = Double.parseDouble(coefsec.getText());
                  
                 if (!thermoNotes.isEmpty())
                 {
                	 nb_notes++; 
                	 somme_notes += Double.parseDouble(thermoNotes)*coeff_thermo;
                	 somme_coefficients +=coeff_thermo; 
                	 
                 }
                 if (!electroNotes.isEmpty())
                 {
                	 nb_notes++; 
                	 somme_notes += Double.parseDouble(electroNotes)*coeff_elec;
                	 somme_coefficients += coeff_elec;
                 }
                 if (!refriNotes.isEmpty())
                 {
                	 nb_notes++; 
                	 somme_notes +=Double.parseDouble(refriNotes)*coeff_refri;
                	 somme_coefficients += coeff_refri;
                 }
                	 
                 if (!secNotes.isEmpty())
                 {
                	 nb_notes++; 
                	 somme_notes += Double.parseDouble(secNotes)*coeff_sec;
                	 somme_coefficients +=coeff_sec;
                 }
                 if (nb_notes > 0) {
                	    try {
                	        double moyenne = somme_notes / somme_coefficients;
                	        String query2 = "UPDATE apprenantprofil SET moyenne_semestre_1 = ? WHERE id = ?";
                	        PreparedStatement statement2 = connection.prepareStatement(query2);
                	        statement2.setDouble(1, moyenne);
                	        statement2.setInt(2, id_apprenant);
                	        int rowsAffected = statement2.executeUpdate();
                	        if (rowsAffected > 0) {
                	            
                	            showCapBep(classeCAP_BEP.getSelectionModel().getSelectedItem());
                	            
                	        }
                	    } catch (SQLException e) {
                	        
                	        e.printStackTrace();
                	    }
                	}

			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
		}

    }
	    @FXML
	    void saveNotes2(ActionEvent event) throws Exception 
	    {
	    	Apprenant selectedApprenant = listecapbepview.getSelectionModel().getSelectedItem();
			if(selectedApprenant !=null)
			{
				try 
				{  
					Connection connection = DataBaseConnection.getConnection();
					String thermoNotes2 = thermofield2.getText();
					String electroNotes2 = electrofield2.getText();
					String refriNotes2 = réfrifield2.getText();
					String secNotes2 = secfield2.getText();
				 
					
					String query1 = "SELECT id FROM apprenantprofil WHERE nom = ? AND prenom = ?"; 
					PreparedStatement statement1 = connection.prepareStatement(query1);
	                statement1.setString(1, selectedApprenant.getNom());
	                statement1.setString(2, selectedApprenant.getPrenom());
	                 
	                 
	                 ResultSet resultSet = statement1.executeQuery();
	                 int id_apprenant = 0;
	                 if (resultSet.next()) {
	                     id_apprenant = resultSet.getInt("id");
	                     
	                 }
	                 if (!isNumeric(thermoNotes2) || !isNumeric(electroNotes2) || !isNumeric(refriNotes2) || !isNumeric(secNotes2)) {
	                	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les notes.");
	                	    alert.showAndWait();
	                	    return;
	                	}

	                	if (!isValidNoteValue(thermoNotes2) || !isValidNoteValue(electroNotes2) || !isValidNoteValue(refriNotes2) || !isValidNoteValue(secNotes2)) {
	                	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des notes valides entre 0 et 20.");
	                	    alert.showAndWait();
	                	    return;
	                	}
	                	if (!isNumeric(coeffthermofield2.getText()) || !isNumeric(coeffelectro2.getText()) || !isNumeric(coeffrefri2.getText()) || !isNumeric(coefsec2.getText())) {
	                	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les coefficients.");
	                	    alert.showAndWait();
	                	    return;
	                	}

	                	if (Double.parseDouble(coeffthermofield2.getText()) <= 0 || Double.parseDouble(coeffelectro2.getText()) <= 0 || Double.parseDouble(coeffrefri2.getText()) <= 0 || Double.parseDouble(coefsec2.getText()) <= 0) {
	                	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des coefficients supérieurs à 0.");
	                	    alert.showAndWait();
	                	    return;
	                	}

	                 //Insérer les nouvelles notes dans la base de données;
	                 String query = "INSERT INTO notes (id_apprenant, matiere, note) VALUES (?, ?, ?)";
	                 PreparedStatement statement = connection.prepareStatement(query);
	                 
	                 statement.setInt(1, id_apprenant); 
	                 statement.setString(2,  "Thermodynamique");
	                 statement.setString(3, thermoNotes2);
	                 statement.executeUpdate();
	                 
	                 statement.setString(2,  "Electrotechnique");
	                 statement.setString(3, electroNotes2);
	                 statement.executeUpdate();
	                 
	                 statement.setString(2,  "Systéme de réfrigération");
	                 statement.setString(3, refriNotes2);
	                 statement.executeUpdate();
	                
	                 //Calculer la moyenne et l'insérer dans la table notes
	                 int nb_notes = 0; 
	                 double somme_notes =0;
	                 double somme_coefficients = 0; 
	                 double coeff_thermo2 = Double.parseDouble(coeffthermofield2.getText());
	                 double coeff_eletro2 = Double.parseDouble(electrofield2.getText());
	                 double coeff_refri2 = Double.parseDouble(coeffrefri2.getText()); 
	                 double coeff_sec2 = Double.parseDouble(coefsec2.getText());
	                 
	                 if (!thermoNotes2.isEmpty())
	                 {
	                	 nb_notes++; 
	                	 somme_notes += Double.parseDouble(thermoNotes2)*coeff_thermo2;
	                	 somme_coefficients +=coeff_thermo2; 
	                	 
	                 }
	                 if (!electroNotes2.isEmpty())
	                 {
	                	 nb_notes++; 
	                	 somme_notes += Double.parseDouble(electroNotes2)*coeff_eletro2;
	                	 somme_coefficients +=coeff_eletro2; 
	                	 
	                 }
	                 if (!refriNotes2.isEmpty())
	                 {
	                	 nb_notes++; 
	                	 somme_notes += Double.parseDouble(refriNotes2)*coeff_refri2;
	                	 somme_coefficients +=coeff_refri2; 
	                	 
	                 }
	                 if (!secNotes2.isEmpty())
	                 {
	                	 nb_notes++; 
	                	 somme_notes += Double.parseDouble(refriNotes2)*coeff_sec2;
	                	 somme_coefficients +=coeff_sec2; 
	                	 
	                 }
	                  
	                 if (nb_notes > 0) {
	                	    try {
	                	        double moyenne = somme_notes / somme_coefficients;
	                	        String query2 = "UPDATE apprenantprofil SET moyenne_semestre_2 = ? WHERE id = ?";
	                	        PreparedStatement statement2 = connection.prepareStatement(query2);
	                	        statement2.setDouble(1, moyenne);
	                	        statement2.setInt(2, id_apprenant);
	                	        int rowsAffected = statement2.executeUpdate();
	                	        if (rowsAffected > 0) {
	                	            
	                	            showCapBep(selectedApprenant.getClasse());
	                	            showCapBep(query2);
	                	            showCapBep(classeCAP_BEP.getSelectionModel().getSelectedItem());
	                	        } 
	                	    } catch (SQLException e) {
	                	        
	                	        e.printStackTrace();
	                	    }
	                	}

	                  
	     
	                 
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				
			}

	    }

	    @FXML
	    void Rangerlesapprenants(ActionEvent event) throws Exception 
	    {
	    	 calculerMoyennesAnnuellesEtRangs();
	    	 showPersonDetails(null);
	    }
	    public void calculerMoyennesAnnuellesEtRangs() throws Exception {
            

            // Récupérer la liste des apprenants de la classe
            String classe = classeCAP_BEP.getSelectionModel().getSelectedItem();
            if (classe == null) {
                // Afficher un message d'erreur ou une notification à l'utilisateur
                return;
            }

            ObservableList<Apprenant> apprenants = FXCollections.observableArrayList();

            try {
                // Établir une connexion à la base de données
                Connection connection = DataBaseConnection.getConnection();

                // Créer la requête SQL pour récupérer les apprenants de la classe sélectionnée
                String sql = "SELECT * FROM apprenantprofil WHERE classe = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, classe);

                // Exécuter la requête SQL et récupérer les résultats
                ResultSet result = statement.executeQuery();

                // Parcourir les résultats et ajouter les apprenants à la liste observable
                while (result.next()) {
                    int id = result.getInt("id");
                    String nom = result.getString("nom");
                    String prenom = result.getString("prenom");
                    double moyenneSemestre1 = result.getDouble("moyenne_semestre_1");
                    double moyenneSemestre2 = result.getDouble("moyenne_semestre_2");
                    double moyenneAnnuelle = (moyenneSemestre1 + moyenneSemestre2) / 2;
                    


                    Apprenant apprenant = new Apprenant(id, nom, prenom, classe, moyenneAnnuelle);
                    apprenant.setMoyenneannuelle(moyenneAnnuelle);
                    apprenants.add(apprenant);
                    
                }

                // Fermer la connexion
               
            } catch (SQLException e) {
                e.printStackTrace();
            }

             
            // Trier la liste des apprenants en fonction de leur moyenne annuelle
            Collections.sort(apprenants, new ApprenantComparator());

            
            // Mettre à jour les rangs des apprenants dans la base de données
            try {
                Connection connection = DataBaseConnection.getConnection();
                for (int i = 0; i < apprenants.size(); i++) {
                    int id = apprenants.get(i).getId();
                    int rang = i + 1;
                    String sql = "UPDATE apprenantprofil SET rang = ? WHERE id = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, rang);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                    
                    showCapBep(classeCAP_BEP.getSelectionModel().getSelectedItem());
                 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		 ObservableList<String> options = FXCollections.observableArrayList("CAP_BEP1", "CAP_BEP2", "CAP_BEP3");
		classeCAP_BEP.setItems(options);
        classeCAP_BEP.getSelectionModel().selectFirst();
        String classe = classeCAP_BEP.getSelectionModel().getSelectedItem();

        try {
            showCapBep(classe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listecapbepview.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        if (newValue.getId() != previousSelectedApprenantId) {
                            try {
                                showPersonDetails(newValue);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            previousSelectedApprenantId = newValue.getId();
                        }
                    }
                });
}
}


