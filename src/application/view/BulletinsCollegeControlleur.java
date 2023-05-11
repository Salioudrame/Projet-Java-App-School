package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
  

public class BulletinsCollegeControlleur implements Initializable {

    @FXML
    private TableView<Apprenant> liste_collegien_view;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Nom21;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Prenom21;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Classe21;
    
    @FXML
    private TableColumn<Apprenant, Integer> addApprenant_Col_Rang22;

    @FXML
    private ComboBox<String> classecollege;
    
    @FXML
    private GridPane gridPane;

    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label classeLabel;

    @FXML
    private TextField coeffmath;

    @FXML
    private TextField coeffpc;

    @FXML
    private TextField coeffsvt;

    @FXML
    private TextField coefffr;

    @FXML
    private TextField coeffang;

    @FXML
    private TextField physiqueChimiefield;

    @FXML
    private TextField svtfield;

    @FXML
    private TextField francaisfield;

    @FXML
    private TextField anglaisfield;
 

    @FXML
    private TextField mathField;
    

    @FXML
    private TextField histogeofield;

    @FXML
    private TextField coefhistogeo;
    
    @FXML
    private TextField epsfield;

    @FXML
    private TextField coefeps;
    

    @FXML
    private TextField francaisfield2;

    @FXML
    private TextField coeffr2;

    @FXML
    private Button save;
   
    @FXML
    private TableColumn<Apprenant, Double> addApprenant_Col_Moy21;
    
    @FXML
    private TableColumn<Apprenant, Double> addApprenant_Col_Moy22;

    @FXML
    private TableColumn<Apprenant, Integer> addApprenant_Col_Rang21;
    
    
    @FXML
    private TextField mathField2;

    @FXML
    private TextField coeffmath2;
    
    @FXML
    private Button save2;
    
    @FXML
    private TextField pcfield2;

    @FXML
    private TextField coeffpc2;
    
    @FXML
    private TextField svtfield2;

    @FXML
    private TextField coeffsvt2;
    

    @FXML
    private TextField anglaisfield2;

    @FXML
    private TextField coeffang2;
    
    @FXML
    private TextField hg;

    @FXML
    private TextField coefhg;

    @FXML
    private TextField epsfield2;

    @FXML
    private TextField coefeps2;
    
    @FXML
    private Button rang;
    
    


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
    public void showCollegien(String classe) throws Exception {
    	String sql1 = "SELECT nom, prenom, classe, moyenne_semestre_1, moyenne_semestre_2, rang "
                + "FROM apprenantprofil "
                + "WHERE classe IN ('6e', '5e', '4e', '3e') "
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

            this.liste_collegien_view.setItems(apprenants);
            this.addApprenant_Col_Nom21.setCellValueFactory(new PropertyValueFactory<>("nom"));
            this.addApprenant_Col_Prenom21.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            this.addApprenant_Col_Moy21.setCellValueFactory(new PropertyValueFactory<>("moyenne_semestre_1"));
            this.addApprenant_Col_Moy22.setCellValueFactory(new PropertyValueFactory<>("moyenne_semestre_2"));
            this.addApprenant_Col_Rang22.setCellValueFactory(new PropertyValueFactory<>("rang"));
            this.liste_collegien_view.getSelectionModel().selectedItemProperty().addListener(
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
   

     

    @FXML
    void SelectCollege(ActionEvent event) {
        String classe = classecollege.getSelectionModel().getSelectedItem();
        if (classe != null) {
            try {
                showCollegien(classe);
            } catch (Exception e) {
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
    @FXML
    void saveNotes(ActionEvent event) throws Exception
    {
    	 
    	    Apprenant selectedApprenant = liste_collegien_view.getSelectionModel().getSelectedItem();
    	    if (selectedApprenant != null) {
    	        try {
    	            Connection connection = DataBaseConnection.getConnection();
    	            String mathsNotes = mathField.getText();
    	            String physiqueChimieNotes = physiqueChimiefield.getText();
    	            String svtNotes = svtfield.getText();
    	            String francaisNotes = francaisfield.getText();
    	            String anglaisNotes = anglaisfield.getText();
    	            String histogeoNotes = histogeofield.getText();
    	            String epsNotes = epsfield.getText();
    	          
    	            // Récupérer l'ID de l'apprenant sélectionné
    	            String query1 = "SELECT id FROM apprenantprofil WHERE nom = ? AND prenom = ? ";
    	            PreparedStatement statement1 = connection.prepareStatement(query1);
    	            statement1.setString(1, selectedApprenant.getNom());
    	            statement1.setString(2, selectedApprenant.getPrenom());
    	            
    	            ResultSet resultSet = statement1.executeQuery();
    	            int id_apprenant = 0;
    	            if (resultSet.next()) {
    	                id_apprenant = resultSet.getInt("id");
    	                
    	                
    	            }
    	         // Vérifier s'il y a des cases vides
    	            if (mathsNotes.isEmpty() || physiqueChimieNotes.isEmpty() || svtNotes.isEmpty() || 
    	                francaisNotes.isEmpty() || anglaisNotes.isEmpty() || histogeoNotes.isEmpty() || epsNotes.isEmpty()) {
    	                Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir toutes les cases avant d'enregistrer les notes.");
    	                alert.showAndWait();
    	                return;
    	            }
    	            if (!isNumeric(mathsNotes) || !isNumeric(physiqueChimieNotes) || !isNumeric(svtNotes) ||
    	                    !isNumeric(francaisNotes) || !isNumeric(anglaisNotes) || !isNumeric(histogeoNotes) || 
    	                    !isNumeric(epsNotes)) {
    	                    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les notes.");
    	                    alert.showAndWait();
    	                    return;
    	                }
    	            if (!isValidNoteValue(mathsNotes) || !isValidNoteValue(physiqueChimieNotes) || !isValidNoteValue(svtNotes) ||
    	            	    !isValidNoteValue(francaisNotes) || !isValidNoteValue(anglaisNotes) || !isValidNoteValue(histogeoNotes) || 
    	            	    !isValidNoteValue(epsNotes)) {
    	            	    Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des notes valides entre 0 et 20.");
    	            	    alert.showAndWait();
    	            	    return;
    	            	}
    	            if (!isNumeric(coeffmath.getText()) || !isNumeric(coeffpc.getText()) || !isNumeric(coeffsvt.getText()) || !isNumeric(coefffr.getText()) || !isNumeric(coeffang.getText()) || !isNumeric(coefhistogeo.getText()) || !isNumeric(coefeps.getText())) {
    	                Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des nombres valides pour les coefficients.");
    	                alert.showAndWait();
    	                return;
    	            }

    	            if (Double.parseDouble(coeffmath.getText()) <= 0 || Double.parseDouble(coeffpc.getText()) <= 0 || Double.parseDouble(coeffsvt.getText()) <= 0 || Double.parseDouble(coefffr.getText()) <= 0 || Double.parseDouble(coeffang.getText()) <= 0 || Double.parseDouble(coefhistogeo.getText()) <= 0 || Double.parseDouble(coefeps.getText()) <= 0) {
    	                Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des coefficients supérieurs à 0.");
    	                alert.showAndWait();
    	                return;
    	            }


    	            // Insérer les nouvelles notes dans la table "notes"
    	            String query = "INSERT INTO notes (id_apprenant, matiere, note) VALUES (?, ?, ?)";
    	            PreparedStatement statement = connection.prepareStatement(query);
    	            statement.setInt(1, id_apprenant);
    	            statement.setString(2, "maths");
    	            statement.setString(3, mathsNotes);
    	            statement.executeUpdate();

    	            statement.setString(2, "physique-chimie");
    	            statement.setString(3, physiqueChimieNotes);
    	            statement.executeUpdate();

    	            statement.setString(2, "svt");
    	            statement.setString(3, svtNotes);
    	            statement.executeUpdate();

    	            statement.setString(2, "francais");
    	            statement.setString(3, francaisNotes);
    	            statement.executeUpdate();

    	            statement.setString(2, "anglais");
    	            statement.setString(3, anglaisNotes);
    	            statement.executeUpdate();
 
    	            statement.setString(2, "histoiregeo");
    	            statement.setString(3, histogeoNotes);
    	            statement.executeUpdate();

    	            statement.setString(2, "eps");
    	            statement.setString(3, epsNotes);
    	            statement.executeUpdate();
    	            // Calculer la moyenne et l'insérer dans la table "notes"
    	            int nb_notes = 0;
    	            double somme_notes = 0;
    	            double somme_coefficients = 0;
    	            double coeff_maths = Double.parseDouble(coeffmath.getText());
    	            double coeff_pc = Double.parseDouble(coeffpc.getText());
    	            double coeff_svt = Double.parseDouble(coeffsvt.getText());
    	            double coeff_francais = Double.parseDouble(coefffr.getText());
    	            double coeff_anglais = Double.parseDouble(coeffang.getText());
    	            double coeff_histogeo = Double.parseDouble(coefhistogeo.getText());
    	            double coeff_eps = Double.parseDouble(coefeps.getText());
    	            
    	            if (!mathsNotes.isEmpty()) {
    	                nb_notes++;
    	                somme_notes += Double.parseDouble(mathsNotes) * coeff_maths;
    	                somme_coefficients += coeff_maths;
    	            }
    	            if (!physiqueChimieNotes.isEmpty()) {
    	                nb_notes++;
    	                somme_notes += Double.parseDouble(physiqueChimieNotes) * coeff_pc;
    	                somme_coefficients += coeff_pc;
    	            }
    	            if (!svtNotes.isEmpty()) {
    	                nb_notes++;
    	                somme_notes += Double.parseDouble(svtNotes) * coeff_svt;
    	                somme_coefficients += coeff_svt;
    	            }
    	            if (!francaisNotes.isEmpty()) {
    	            	nb_notes++;
    	            	somme_notes += Double.parseDouble(francaisNotes) * coeff_francais;
    	            	somme_coefficients += coeff_francais;
    	            	}
    	            	if (!anglaisNotes.isEmpty()) {
    	            	nb_notes++;
    	            	somme_notes += Double.parseDouble(anglaisNotes) * coeff_anglais;
    	            	somme_coefficients += coeff_anglais;
    	            	}
    	            	if(!histogeoNotes.isEmpty())
    	            	{	nb_notes++;
    	            		somme_notes += Double.parseDouble(epsNotes)* coeff_histogeo;
    	            		somme_coefficients += coeff_histogeo; 
    	            	}
    	            	if(!epsNotes.isEmpty())
    	            	{
    	            		nb_notes++;
    	            		somme_notes += Double.parseDouble(epsNotes)* coeff_eps;
    	            		somme_coefficients += coeff_eps; 
    	            	}	
    	            	 
    	            	 if (nb_notes > 0) {
    	            		 double moyenne = somme_notes / somme_coefficients;
    	            		 moyenne = Double.parseDouble(String.format(Locale.US, "%.2f", moyenne).replaceAll(",", "."));
    	            		 String query2 = "UPDATE apprenantprofil SET moyenne_semestre_1 = ? WHERE id = ?";
    	            		 PreparedStatement statement2 = connection.prepareStatement(query2);
    	            		 statement2.setDouble(1, moyenne);
    	            		 statement2.setInt(2, id_apprenant);
    	            		 statement2.executeUpdate();

    	                    
    	                    
    	                     showCollegien(classecollege.getSelectionModel().getSelectedItem());
    	                          
    	                 }
    	          		 
    	             } catch (SQLException e) {
    	                 e.printStackTrace();
    	             }
    	         }
    	     
    	     }
    @FXML
    void saveNotes2(ActionEvent event) throws Exception 
    {
    	Apprenant selectedApprenant = liste_collegien_view.getSelectionModel().getSelectedItem();
	    if (selectedApprenant != null) 
	    {
	        try 
	        {
	            Connection connection = DataBaseConnection.getConnection();
	            String mathsNotes2 = mathField2.getText(); 
	            String pcNotes2 = pcfield2.getText();
	            String svtNotes2 = svtfield2.getText();
	            String françaisNotes2 = francaisfield2.getText();
	            String anglaisNotes2 = anglaisfield2.getText();
	            String hgNotes2 = hg.getText();
	            String epsNotes2 = epsfield2.getText();
	            int nb_notes = 0;
	            double somme_notes = 0;
	            double somme_coefficients = 0;
	            double coeff_maths = Double.parseDouble(coeffmath2.getText());
	            double coeff_pc = Double.parseDouble(coeffpc2.getText());
	            double coeff_svt = Double.parseDouble(coeffsvt2.getText());
	            double coeff_fr = Double.parseDouble(coeffr2.getText());
	            double coeff_ang = Double.parseDouble(coeffang2.getText());
	            double coeff_hg = Double.parseDouble(coefhg.getText());
	            double coeff_eps = Double.parseDouble(coefeps2.getText());
	            
	            if (!isNumeric(coeffmath2.getText()) || !isNumeric(coeffpc2.getText()) || !isNumeric(coeffsvt2.getText()) || !isNumeric(coeffr2.getText()) || !isNumeric(coeffang2.getText()) || !isNumeric(coefhg.getText()) || !isNumeric(coefeps2.getText())) {
	                Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des valeurs numériques pour les coefficients.");
	                alert.showAndWait();
	                return;
	            }

	            if (coeff_maths < 0 || coeff_maths > 20 || coeff_pc < 0 || coeff_pc > 20 || coeff_svt < 0 || coeff_svt > 20 ||
	                    coeff_fr < 0 || coeff_fr > 20 || coeff_ang < 0 || coeff_ang > 20 || coeff_hg < 0 || coeff_hg > 20 || coeff_eps < 0 || coeff_eps > 20) {
	                    Alert alert = new Alert(AlertType.ERROR, "Les coefficients doivent être des entiers compris entre 0 et 20.");
	                    alert.showAndWait();
	                    return;
	                }
	            
	           // Vérifier s'il y a des cases vides
	            if(!isValidNoteValue(mathsNotes2) || !isValidNoteValue(pcNotes2) || !isValidNoteValue(svtNotes2) ||
	            		!isValidNoteValue(françaisNotes2) || !isValidNoteValue(anglaisNotes2) || !isValidNoteValue(hgNotes2) ||
	            		!isValidNoteValue(epsNotes2)) {
	            		Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer des notes valides entre 0 et 20.");
	            		alert.showAndWait();
	            		return;
	            }
	            
	           

	
	            // Récupérer l'ID de l'apprenant sélectionné
	            String query2 = "SELECT ID FROM apprenantprofil WHERE nom = ? AND prenom = ? ";
	            PreparedStatement statement2 = connection.prepareStatement(query2);
	            statement2.setString(1, selectedApprenant.getNom());
	            statement2.setString(2, selectedApprenant.getPrenom());
	            
	            ResultSet resultSet = statement2.executeQuery();
	            int id_apprenant = 0;
	            if (resultSet.next()) 
	            {
	                id_apprenant = resultSet.getInt("id");      
	            }
	          
	            // Insérer les nouvelles notes dans la table "notes"
	            String query = "INSERT INTO notes (id_apprenant, matiere, note) VALUES (?, ?, ?)";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setInt(1, id_apprenant);
	            statement.setString(2, "maths");
	            statement.setString(3, mathsNotes2);
	            statement.executeUpdate();
	            
	            statement.setString(2, "physique-chimie");
	            statement.setString(3, pcNotes2);
	            statement.executeUpdate();
	           
	            statement.setString(2, "SVT");
	            statement.setString(3, svtNotes2);
	            statement.executeUpdate();
	            
	            statement.setString(2, "Français");
	            statement.setString(3, françaisNotes2);
	            statement.executeUpdate();
	            
	            statement.setString(2, "Anglais");
	            statement.setString(3, anglaisNotes2);
	            statement.executeUpdate();
	            
	            statement.setString(2, "HistoireGeo");
	            statement.setString(3, hgNotes2);
	            statement.executeUpdate();
	            
	            statement.setString(2,  "EPS");
	            statement.setString(3, epsNotes2);
	            statement.executeUpdate();
	            // Calculer la moyenne et l'insérer dans la table "notes"
	             
	            if (!mathsNotes2.isEmpty()) {
	                nb_notes++;
	                somme_notes += Double.parseDouble(mathsNotes2) * coeff_maths;
	                somme_coefficients += coeff_maths;
	            }
	            if (!pcNotes2.isEmpty())
	            {
	            	nb_notes++; 
	            	somme_notes += Double.parseDouble(pcNotes2)* coeff_pc;
	            	somme_coefficients +=coeff_pc;
	            }
	            if (!svtNotes2.isEmpty())
	            {
	            	nb_notes++; 
	            	somme_notes +=  Double.parseDouble(svtNotes2)*coeff_svt;
	            	somme_coefficients+=coeff_svt;
	            }
	             
	            if (!françaisNotes2.isEmpty())
	            {
	            	nb_notes++; 
	            	somme_notes += Double.parseDouble(françaisNotes2)*coeff_fr;
	            	somme_coefficients+=coeff_fr;
	            }
	            if (!anglaisNotes2.isEmpty())
	            {
	            	nb_notes++; 
	            	somme_notes +=Double.parseDouble(anglaisNotes2)*coeff_ang;
	            	somme_coefficients+=coeff_ang;
	            }
	            if (!hgNotes2.isEmpty())
	            {
	            	nb_notes++;
	            	somme_notes +=Double.parseDouble(hgNotes2)*coeff_hg; 
	            	somme_coefficients+=coeff_hg;
	            	
	            }
	            if(!epsNotes2.isEmpty())
	            {
	            	nb_notes++; 
	            	somme_notes+=Double.parseDouble(epsNotes2)*coeff_eps;
	            	somme_coefficients+=coeff_eps;
	            }
	            	 if (nb_notes > 0) {
	                     double moyenne = somme_notes / somme_coefficients;
	                     String query3 = "UPDATE apprenantprofil SET moyenne_semestre_2 = ? WHERE id = ?";
	                     PreparedStatement statement1= connection.prepareStatement(query3);
	                     statement1.setDouble(1, moyenne);
	                     statement1.setInt(2, id_apprenant);
	                     statement1.executeUpdate();
	                     showCollegien(selectedApprenant.getClasse());
	                     
	                     showCollegien(classecollege.getSelectionModel().getSelectedItem());        
	                 }
	             } catch (SQLException e) {
	                 e.printStackTrace();
	             }
	         }
	     }
    private int previousSelectedApprenantId;
    
    @FXML
    void Rangerlesapprenants(ActionEvent event) throws Exception 
    {
    	calculerMoyennesAnnuellesEtRangs();
    	showPersonDetails(null);
     
    }
    public void calculerMoyennesAnnuellesEtRangs() throws Exception {
        

        // Récupérer la liste des apprenants de la classe
        String classe = classecollege.getSelectionModel().getSelectedItem();
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
                
                showCollegien(classecollege.getSelectionModel().getSelectedItem());
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ObservableList<String> options = FXCollections.observableArrayList("6e", "5e", "4e", "3e");
        classecollege.setItems(options);
        classecollege.getSelectionModel().selectFirst();
        String classe = classecollege.getSelectionModel().getSelectedItem();

        try {
            showCollegien(classe);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mathField.setText("");
        physiqueChimiefield.setText("");
        svtfield.setText("");
        francaisfield.setText("");
        anglaisfield.setText("");
        histogeofield.setText("");
        epsfield.setText("");

        liste_collegien_view.getSelectionModel().selectedItemProperty().addListener(
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
