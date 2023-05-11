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


public class BulletinsElementaireController implements Initializable
{

    @FXML
    private ComboBox<String> classeElementaire;
   
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
    private TableView<Apprenant> listeelementaireview;

    @FXML
    private TextField arithmetiquefield;

    @FXML
    private TextField coefarithmetique;
    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label classeLabel;
    
    @FXML
    private TextField problemefield;

    @FXML
    private TextField coefprobleme;
    
    @FXML
    private TextField frfield;

    @FXML
    private TextField coeffrfield;
    
    @FXML
    private TextField sciencesfield;

    @FXML
    private TextField coefsciences;
 
    @FXML
    private TextField hgfield;

    @FXML
    private TextField coeffhg;

    @FXML
    private TextField artfield;

    @FXML
    private TextField coefart;

    @FXML
    private Button save;
    
    @FXML
    private Button save2;
 
    @FXML
    private TextField arithmetiquefield2;

    @FXML
    private TextField coefarithmetique2;
    
    @FXML
    private TextField problémes2;

    @FXML
    private TextField coeffproblémes2;
    

    @FXML
    private TextField langfr2;

    @FXML
    private TextField coeflangfr2;
   

    @FXML
    private TextField artieldss;

    @FXML
    private TextField coeffart2;
    

    @FXML
    private TextField hgfield2;

    @FXML
    private TextField coeffhg2;
    
    @FXML
    private TextField istfield2;

    @FXML
    private TextField coefist2;
    
    @FXML
    private Button rang;

    

    
    private int previousSelectedApprenantId;

    @FXML
    void selectclasseelementaire(ActionEvent event) 
    {
    	String classe = classeElementaire.getSelectionModel().getSelectedItem();
        if (classe !=null)
        {
            try 
            {
                showClasseElementaire(classe);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
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
        private void showClasseElementaire(String classe) throws Exception 
        {
        	String sql1 = "SELECT nom, prenom, classe, moyenne_semestre_1, moyenne_semestre_2, rang "
                    + "FROM apprenantprofil "
                    + "WHERE classe IN ('CI', 'CP', 'CE1', 'CE2', 'CM1', 'CM2') "
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

                    apprenants.add(new Apprenant(nom, prenom,  moyenne_apprenant,moyenne_apprenant2, rang));
                }

                this.listeelementaireview.setItems(apprenants);
                this.addApprenant_Col_Nom21.setCellValueFactory(new PropertyValueFactory<>("nom"));
                this.addApprenant_Col_Prenom21.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                this.addApprenant_Col_Moy21.setCellValueFactory(new PropertyValueFactory<>("moyenne_semestre_1"));
                this.addApprenant_Col_Moy22.setCellValueFactory(new PropertyValueFactory<>("moyenne_semestre_2"));
                this.addApprenant_Col_Rang22.setCellValueFactory(new PropertyValueFactory<>("rang"));
                this.listeelementaireview.getSelectionModel().selectedItemProperty().addListener(
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

        @FXML
        void saveNotes(ActionEvent event) throws Exception 
        {
        	Apprenant selectedApprenant = listeelementaireview.getSelectionModel().getSelectedItem();
    		if(selectedApprenant !=null)
    		{
    			try 
    			{  
    				Connection connection = DataBaseConnection.getConnection();
    				String arithmétiquesNotes = arithmetiquefield.getText();
    				String problemesNotes = problemefield.getText();
    				String frNotes = frfield.getText();
    				String sciencesNotes = sciencesfield.getText();
    				String hgNtes = hgfield.getText(); 
    				String artNotes = artfield.getText();
    				
    				String query1 = "SELECT id FROM apprenantprofil WHERE nom = ? AND prenom = ?"; 
    				PreparedStatement statement1 = connection.prepareStatement(query1);
                    statement1.setString(1, selectedApprenant.getNom());
                    statement1.setString(2, selectedApprenant.getPrenom());
                     
                     
                     ResultSet resultSet = statement1.executeQuery();
                     int id_apprenant = 0;
                     if (resultSet.next()) {
                         id_apprenant = resultSet.getInt("id");
                         
                     }
                     if (!isNumeric(arithmétiquesNotes) || !isNumeric(problemesNotes) || !isNumeric(frNotes) || !isNumeric(sciencesNotes) || !isNumeric(hgNtes) || !isNumeric(artNotes)) {
                    	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les notes.");
                    	    alert.showAndWait();
                    	    return;
                    	}

                    	if (!isValidNoteValue(arithmétiquesNotes) || !isValidNoteValue(problemesNotes) || !isValidNoteValue(frNotes) || !isValidNoteValue(sciencesNotes) || !isValidNoteValue(hgNtes) || !isValidNoteValue(artNotes)) {
                    	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des notes valides entre 0 et 20.");
                    	    alert.showAndWait();
                    	    return;
                    	}
                    	if (!isNumeric(coefarithmetique.getText()) || !isNumeric(coefprobleme.getText()) || !isNumeric(coeffrfield.getText()) || !isNumeric(coefsciences.getText()) || !isNumeric(coeffhg.getText()) || !isNumeric(coefart.getText())) {
                    	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les coefficients.");
                    	    alert.showAndWait();
                    	    return;
                    	}

                    	if (Double.parseDouble(coefarithmetique.getText()) <= 0 || Double.parseDouble(coefprobleme.getText()) <= 0 || Double.parseDouble(coeffrfield.getText()) <= 0 || Double.parseDouble(coefsciences.getText()) <= 0 || Double.parseDouble(coeffhg.getText()) <= 0 || Double.parseDouble(coefart.getText()) <= 0) {
                    	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des coefficients supérieurs à 0.");
                    	    alert.showAndWait();
                    	    return;
                    	}


                     //Insérer les nouvelles notes dans la base de données;
                     String query = "INSERT INTO notes (id_apprenant, matiere, note) VALUES (?, ?, ?)";
                     PreparedStatement statement = connection.prepareStatement(query);
                     
                     statement.setInt(1, id_apprenant); 
                     statement.setString(2,  "Arithmétique");
                     statement.setString(3, arithmétiquesNotes);
                     statement.executeUpdate();
                      
                    statement.setString(2, "Résolution probléme");
                    statement.setString(3, problemesNotes);
                    statement.executeUpdate();
                    
                    statement.setString(2, "Langue Française");
                    statement.setString(3, frNotes);
                    
                    statement.setString(2, "Sciences Naturelles");
                    statement.setString(3, sciencesNotes);
                    statement.executeUpdate();
                    
                    statement.setString(2, "Histoire Geographie");
                    statement.setString(3, hgNtes);
                    statement.executeUpdate();
                    
                    statement.setString(2,  "Arts");
                    statement.setString(3, artNotes);
                    statement.executeUpdate();
                    
                    //Calculer la moyenne et l'insérer dans la table notes
                     int nb_notes = 0; 
                     double somme_notes =0;
                     double somme_coefficients = 0; 
                     double coeff_arith = Double.parseDouble(coefarithmetique.getText());
                     double coeff_probleme = Double.parseDouble(coefprobleme.getText());
                     double coeff_fr = Double.parseDouble(coeffrfield.getText());
                     double coeff_sciences = Double.parseDouble(coefsciences.getText());
                     double coeff_hg = Double.parseDouble(coeffhg.getText());
                     double coeff_art = Double.parseDouble(coefart.getText());
                     
                     
                     if (!arithmétiquesNotes.isEmpty())
                     {
                    	 nb_notes++; 
                    	 somme_notes += Double.parseDouble(arithmétiquesNotes)*coeff_arith;
                    	 somme_coefficients +=coeff_arith; 
                    	 
                     }
                     
                     if (!problemesNotes.isEmpty())
                     {
                    	 nb_notes++; 
                    	 somme_notes +=Double.parseDouble(problemesNotes)*coeff_probleme;
                    	 somme_coefficients += coeff_probleme; 
                     }
                     if (!frNotes.isEmpty())
                     {
                    	 nb_notes++;
                    	 somme_notes +=Double.parseDouble(frNotes)*coeff_fr;
                    	 somme_coefficients += coeff_fr;
                     }
                     if (!sciencesNotes.isEmpty())
                     {
                    	 nb_notes++; 
                    	 somme_notes +=Double.parseDouble(sciencesNotes)*coeff_sciences;
                    	 somme_coefficients += coeff_sciences;
                     }
                     if (!hgNtes.isEmpty())
                     {
                    	 nb_notes++;
                    	 somme_notes += Double.parseDouble(hgNtes)*coeff_hg;
                    	 somme_coefficients += coeff_hg;
                     }
                     if (!artNotes.isEmpty())
                     {
                    	 nb_notes++;
                    	 somme_notes += Double.parseDouble(artNotes)*coeff_art;
                    	 somme_coefficients += coeff_art; 
                     }	 
                     
                     if (nb_notes > 0) 
                     {
                    	    try {
                    	        double moyenne = somme_notes / somme_coefficients;
                    	        String query2 = "UPDATE apprenantprofil SET moyenne_semestre_1 = ? WHERE id = ?";
                    	        PreparedStatement statement2 = connection.prepareStatement(query2);
                    	        statement2.setDouble(1, moyenne);
                    	        statement2.setInt(2, id_apprenant);
                    	        int rowsAffected = statement2.executeUpdate();
                    	        if (rowsAffected > 0) 
                    	        {
                    	            
                    	            showClasseElementaire(classeElementaire.getSelectionModel().getSelectedItem());
                    	        }
                    	    } 
                    	    catch (SQLException e) 
                    	    {
                    	        
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

        	Apprenant selectedApprenant = listeelementaireview.getSelectionModel().getSelectedItem();
    		if(selectedApprenant !=null)
    		{
    			try 
    			{  
    				Connection connection = DataBaseConnection.getConnection();
    				String arithmétiquesNotes2 = arithmetiquefield2.getText();
    				String problemes2Notes = problémes2.getText();  
    				String françaisNotes2 = langfr2.getText();
    				String istNotes2 = istfield2.getText(); 
    				String hgNotes2 = hgfield2.getText(); 
    				String artNotes2 = artieldss.getText();
    				 
    				
    				String query1 = "SELECT id FROM apprenantprofil WHERE nom = ? AND prenom = ?"; 
    				PreparedStatement statement1 = connection.prepareStatement(query1);
                    statement1.setString(1, selectedApprenant.getNom());
                    statement1.setString(2, selectedApprenant.getPrenom());
                     
                     
                     ResultSet resultSet = statement1.executeQuery();
                     int id_apprenant = 0;
                     if (resultSet.next()) {
                         id_apprenant = resultSet.getInt("id");
                         
                     }
                     if (!isNumeric(arithmétiquesNotes2) || !isNumeric(problemes2Notes) || !isNumeric(françaisNotes2) || !isNumeric(istNotes2) || !isNumeric(hgNotes2) || !isNumeric(artNotes2)) {
                    	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les notes.");
                    	    alert.showAndWait();
                    	    return;
                    	}

                    	if (!isValidNoteValue(arithmétiquesNotes2) || !isValidNoteValue(problemes2Notes) || !isValidNoteValue(françaisNotes2) || !isValidNoteValue(istNotes2) || !isValidNoteValue(hgNotes2) || !isValidNoteValue(artNotes2)) {
                    	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des notes valides entre 0 et 20.");
                    	    alert.showAndWait();
                    	    return;
                    	}
                    	if (!isNumeric(coefarithmetique2.getText()) || !isNumeric(coeffproblémes2.getText()) || !isNumeric(coeflangfr2.getText()) || !isNumeric(coefist2.getText()) || !isNumeric(coeffhg2.getText()) || !isNumeric(coeffart2.getText())) {
                    	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les coefficients.");
                    	    alert.showAndWait();
                    	    return;
                    	}

                    	if (Double.parseDouble(coefarithmetique2.getText()) <= 0 || Double.parseDouble(coeffproblémes2.getText()) <= 0 || Double.parseDouble(coeflangfr2.getText()) <= 0 || Double.parseDouble(coefist2.getText()) <= 0 || Double.parseDouble(coeffhg2.getText()) <= 0 || Double.parseDouble(coeffart2.getText()) <= 0) {
                    	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des coefficients supérieurs à 0.");
                    	    alert.showAndWait();
                    	    return;
                    	}


                     //Insérer les nouvelles notes dans la base de données;
                     String query = "INSERT INTO notes (id_apprenant, matiere, note) VALUES (?, ?, ?)";
                     PreparedStatement statement = connection.prepareStatement(query);
                     
                     statement.setInt(1, id_apprenant); 
                     statement.setString(2,  "Arithmétique2");
                     statement.setString(3, arithmétiquesNotes2);
                     statement.executeUpdate();
                     
                     statement.setString(2, "Résolution de problemes2");
                     statement.setString(3, problemes2Notes);
                     statement.executeUpdate();
                     
                     statement.setString(2, "Français");
                     statement.setString(2, françaisNotes2);
                     statement.executeUpdate();
                     
                     statement.setString(2, "Initiation Scientifiques et Techniques");
                     statement.setString(3, istNotes2);
                     statement.executeUpdate();
                     
                     statement.setString(2, "Histoire Géographie 2");
                     statement.setString(2, hgNotes2);
                     statement.executeUpdate();
                     
                     statement.setString(2,  "ART2");
                     statement.setString(3, artNotes2);
                     statement.executeUpdate(); 
                     
                    //Calculer la moyenne et l'insérer dans la table notes
                     int nb_notes = 0; 
                     double somme_notes =0;
                     double somme_coefficients = 0; 
                     double coeff_arith2 = Double.parseDouble(coefarithmetique2.getText());
                     double coeff_probleme2 = Double.parseDouble(coeffproblémes2.getText());
                     double coeff_fr2 = Double.parseDouble(coeflangfr2.getText());
                     double coeff_ist2 = Double.parseDouble(coefist2.getText());
                     double coeff_hg2 = Double.parseDouble(coeffhg2.getText());
                     double coeff_art2 = Double.parseDouble(coeffart2.getText());
                     
                    
                     
                     if (!arithmétiquesNotes2.isEmpty())
                     {
                    	 nb_notes++; 
                    	 somme_notes += Double.parseDouble(arithmétiquesNotes2)*coeff_arith2;
                    	 somme_coefficients +=coeff_arith2; 
                    	 
                     }
                     if (!problemes2Notes.isEmpty())
                     {
                    	 nb_notes++;
                    	 somme_notes += Double.parseDouble(problemes2Notes)*coeff_probleme2; 
                    	 somme_coefficients += coeff_probleme2; 
                     }
                     if(!françaisNotes2.isEmpty())
                     {
                    	 nb_notes++;
                    	 somme_notes += Double.parseDouble(françaisNotes2)*coeff_fr2;
                    	 somme_coefficients +=coeff_fr2;
                     }
                     if(!istNotes2.isEmpty())
                     {
                    	 nb_notes++; 
                    	 somme_notes += Double.parseDouble(istNotes2)*coeff_ist2; 
                    	 somme_coefficients +=coeff_ist2; 
                     } 	 
                     
                     if (!hgNotes2.isEmpty())
                     {
                    	 nb_notes++; 
                    	 somme_notes +=Double.parseDouble(hgNotes2)*coeff_hg2; 
                    	 somme_coefficients += coeff_hg2; 
                     }
                     if(!artNotes2.isEmpty())
                     {
                    	 nb_notes++; 
                    	 somme_notes += Double.parseDouble(artNotes2)*coeff_art2;
                    	 somme_coefficients +=coeff_art2; 
                     }
                      
                     if (nb_notes > 0) 
                     {
                    	    try {
                    	        double moyenne = somme_notes / somme_coefficients;
                    	        String query2 = "UPDATE apprenantprofil SET moyenne_semestre_2 = ? WHERE id = ?";
                    	        PreparedStatement statement2 = connection.prepareStatement(query2);
                    	        statement2.setDouble(1, moyenne);
                    	        statement2.setInt(2, id_apprenant);
                    	        int rowsAffected = statement2.executeUpdate();
                    	        if (rowsAffected > 0) 
                    	        {
                    	            
                    	            showClasseElementaire(classeElementaire.getSelectionModel().getSelectedItem());
                    	        }
                    	    } 
                    	    catch (SQLException e) 
                    	    {
                    	        
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
            String classe = classeElementaire.getSelectionModel().getSelectedItem();
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
                    
                    showClasseElementaire(classeElementaire.getSelectionModel().getSelectedItem());   
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) 
		{
			 ObservableList<String> options = FXCollections.observableArrayList("CI", "CP", "CE1", "CE2", "CM1", "CM2");
				classeElementaire.setItems(options);
		        classeElementaire.getSelectionModel().selectFirst();
		        String classe = classeElementaire.getSelectionModel().getSelectedItem();

		        try {
		            showClasseElementaire(classe);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        listeelementaireview.getSelectionModel().selectedItemProperty().addListener(
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

	 