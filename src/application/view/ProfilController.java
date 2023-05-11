package application.view;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.print.attribute.standard.OutputDeviceAssigned;
import javax.swing.JFileChooser;

import application.model.Apprenant;
import application.model.Classe;
import application.model.Section;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProfilController implements Initializable{

    @FXML
    private TableView<Apprenant> tableau_apprenant_view;

    @FXML
    private TableColumn<Apprenant, Integer> tab;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Nom;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Prenom;

    @FXML
    private TableColumn<Apprenant, Date> addApprenant_Col_Date;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Lieu;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Genre;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Classe;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Section;

    @FXML
    private TextField txt_prenom;

    @FXML
    private TextField nom;


    @FXML
    private ComboBox<String> nomSection;

     
    @FXML
    private TextField txt_lieu;

    @FXML
    private DatePicker date;

    @FXML
    private Button ajouterApprenant;

    @FXML
    private ComboBox<String> nomsClasse;
    

    @FXML
    private Button fichier;
    
    @FXML
    void Select1(ActionEvent event) 
    {	
        List<String> nomsClasses = Classe.getNomsClasses();
        ObservableList<String> options = FXCollections.observableArrayList(nomsClasses);
        nomsClasse.setItems(options);
    }
    @FXML
    private ComboBox<String> genre;
    private String [] sexe = new String[] {"M", "F"};

    @FXML
    void Select(ActionEvent event) 
    {
    	 String sexe = ((String)this.genre.getSelectionModel().getSelectedItem()).toString();
    }
    
    @FXML
    private TextField filterfield;



    @FXML
    void Select2(ActionEvent event) 
    {
    	List<String> nomsSection = Classe.getNomsSection();
    	 ObservableList<String> options = FXCollections.observableArrayList(nomsSection);
    	 nomSection.setItems(options); 
    }

    @FXML
    private Button modifierApprenant;

    
    
    @FXML
    private TextField txt_search;
    @FXML
    void editApprenant() throws Exception
    {
    	if (!verifierChamp()) {
    	    Alert alert = new Alert(AlertType.ERROR);
    	    alert.setTitle("Modification impossible");
    	    alert.setHeaderText("");
    	    alert.setContentText("Mettez l'id de l'apprenant sur la barre de recherche tout d'abord");
    	    alert.showAndWait();
    	    return ; 
    	}

    	String nom = this.nom.getText();
    	String prenom = this.txt_prenom.getText();

    	if (!nom.matches("[a-zA-Z]+") || !prenom.matches("[a-zA-Z]+")) {
    	    Alert alert = new Alert(AlertType.ERROR);
    	    alert.setTitle("Erreur");
    	    alert.setHeaderText("");
    	    alert.setContentText("Les noms et prénoms doivent être des chaînes de caractères");
    	    alert.showAndWait();
    	    return;
    	}

    	if (this.date.getValue() == null) {
    	    // Afficher une alerte pour indiquer que la date est requise
    	    Alert alert = new Alert(Alert.AlertType.ERROR);
    	    alert.setTitle("Erreur");
    	    alert.setHeaderText("Date requise");
    	    alert.setContentText("Veuillez entrer une date");
    	    alert.showAndWait();
    	    return;
    	}
    	   LocalDate localDate;

    	try {
    	      localDate = this.date.getValue();
    	     Date dateSQL = Date.valueOf(localDate);
    	} catch (Exception e) {
    	    // Afficher une alerte pour indiquer que le format de date n'est pas correct
    	    Alert alert = new Alert(Alert.AlertType.ERROR);
    	    alert.setTitle("Erreur");
    	    alert.setHeaderText("Format de date incorrect");
    	    alert.setContentText("Veuillez entrer une date au format correct (YYYY-MM-DD)");
    	    alert.showAndWait();
    	    return;
    	}

    	String lieu = this.txt_lieu.getText();
    	String sexe = ((String)this.genre.getSelectionModel().getSelectedItem()).toString();
    	String salle = (String)this.nomsClasse.getSelectionModel().getSelectedItem();
    	String sect = (String)this.nomSection.getSelectionModel().getSelectedItem();

    
    String sql = "update apprenantprofil set nom=?, prenom=?, dateNaissance=?, lieuNaissance=?, genre=?, classe=?, section=? where id='" + txt_search.getText() + "'";
    try {
    Connection connection = DataBaseConnection.getConnection();
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setString(1, nom);
    statement.setString(2, prenom);
    statement.setDate(3, Date.valueOf(localDate));
    statement.setString(4, lieu);
    statement.setString(5, sexe);
    statement.setString(6, salle);
    statement.setString(7, sect);
    int rowsInserted = statement.executeUpdate();
    if (rowsInserted > 0) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Insertion réussi");
    alert.setHeaderText("");
    alert.setContentText("Les informations de l'apprenant ont bien été modifiés");
    alert.showAndWait();
    new TableView();
    this.refreshTableApprenant();
    }
    } catch (SQLException var16) {
    var16.printStackTrace();
    }
    }
    @FXML
    void searchApprenant() throws Exception 
    {
    	String sql ="SELECT nom, prenom, dateNaissance, lieuNaissance,genre, classe, section from apprenantprofil WHERE ID ='"+txt_search.getText()+"'";
    	try 
    	{
    		Connection connection = DataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
            {
            	nom.setText( resultSet.getString("nom"));
            	txt_prenom.setText( resultSet.getString("prenom"));
            	Date date = resultSet.getDate("dateNaissance");
            	txt_lieu.setText( resultSet.getString("lieuNaissance"));
            	String genreValue = (String) genre.getValue();
            	String classeValue = (String) nomsClasse.getValue();
            	String sectionValue =(String) nomSection.getValue();
            	Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Bingo");
                alert.setHeaderText("Resultat");
                alert.setContentText("Cet apprenant est bien inscrit");
                alert.show();
                
            }
            else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de recherche");
                alert.setHeaderText("Aucun résultat");
                alert.setContentText("Aucun apprenant ne correspond à la recherche");
                alert.show();
            }
            
    	}catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    }
    private boolean verifierChamp() {
        String nom = this.nom.getText();
        String prenom = this.txt_prenom.getText();
        LocalDate dateNaissance = this.date.getValue();
        String lieuNaissance = this.txt_lieu.getText();
        String genre = (String)this.genre.getSelectionModel().getSelectedItem();
        String salle = (String)this.nomsClasse.getSelectionModel().getSelectedItem();
        String section = (String)this.nomSection.getSelectionModel().getSelectedItem();

        if (nom.isEmpty() || prenom.isEmpty() || dateNaissance == null || lieuNaissance.isEmpty() || genre.isEmpty() || salle.isEmpty() || section.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs non remplis");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    public void resetField() {
        this.txt_prenom.setText("");
        this.nom.setText("");
        this.nomsClasse.setValue(null);
        this.nomSection.setValue(null);
        this.genre.setValue(null);
        this.txt_lieu.setText("");
        this.date.setValue(null);
      }
    @FXML
    void ajouterApprenantOnAction(ActionEvent event) throws Exception 
    {
       if (!verifierChamp()) {
             return;
         }
       String nom = this.nom.getText();
       String prenom = this.txt_prenom.getText();
       Date dateSQL = Date.valueOf((LocalDate)this.date.getValue());
       LocalDate localDate = dateSQL.toLocalDate(); 
       String lieu = this.txt_lieu.getText();
       String sexe = ((String)this.genre.getSelectionModel().getSelectedItem()).toString();
       String salle = (String)this.nomsClasse.getSelectionModel().getSelectedItem();
          String sect = (String)this.nomSection.getSelectionModel().getSelectedItem();
           
          String sql = "INSERT INTO apprenantprofil (nom, prenom, dateNaissance, lieuNaissance, genre, classe, section) values(?, ?, ?, ?, ?, ?, ?)";
          try {
              Connection connection = DataBaseConnection.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql);
              statement.setString(1, nom);
              statement.setString(2, prenom);
              if (!nom.matches("[a-zA-Z]+") || !prenom.matches("[a-zA-Z]+")) {
                  Alert alert = new Alert(AlertType.ERROR);
                  alert.setTitle("Erreur");
                  alert.setHeaderText("");
                  alert.setContentText("Les noms et prénoms doivent être des chaînes de caractères");
                  alert.showAndWait();
                  resetField();
                  return;
              }
              if (this.date.getValue() == null) {
                 // Afficher une alerte pour indiquer que la date est requise
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Erreur");
                 alert.setHeaderText("Date requise");
                 alert.setContentText("Veuillez entrer une date");
                 alert.showAndWait();
                 resetField();
                 return;
             }

             try {
                  dateSQL = Date.valueOf((LocalDate)this.date.getValue());
             } catch (Exception e) {
                 // Afficher une alerte pour indiquer que le format de date n'est pas correct
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Erreur");
                 alert.setHeaderText("Format de date incorrect");
                 alert.setContentText("Veuillez entrer une date au format correct (YYYY-MM-DD)");
                 alert.showAndWait();
                 resetField();
                 return;
             }

              statement.setDate(3, Date.valueOf(localDate));
              statement.setString(4, lieu);
              statement.setString(5, sexe);
              statement.setString(6, salle);
              statement.setString(7, sect);
              int rowsInserted = statement.executeUpdate();
              if (rowsInserted > 0) {
                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Insertion réussi");
                  alert.setHeaderText("");
                  alert.setContentText("L'ajout de l'apprenant a été tés bien éffectué");
                  alert.showAndWait();
                  new TableView();
                  resetField();
                  this.refreshTableApprenant();
              }
          } catch (SQLException var16) {
              var16.printStackTrace();
          }
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		  this.genre.getItems().addAll(this.sexe);
		  this.nomsClasse.getItems().addAll(Classe.getNomsClasses());
		  this.nomSection.getItems().addAll(Classe.getNomsSection());
	        
	        try {
	            this.showApprenant();
	        } catch (Exception var4) {
	            var4.printStackTrace();
	        }

	    }



	private void showApprenant() throws Exception 
	{
		 String sql1 = "SELECT * from apprenantprofil";

	        try {
	            Connection connection = DataBaseConnection.getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(sql1);
	            ObservableList<Apprenant> apprenants = FXCollections.observableArrayList();
	        
	            while(resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String nom = resultSet.getString("nom");
	                String prenom = resultSet.getString("prenom");
	                Date dateNaissance = resultSet.getDate("dateNaissance");
	                String lieuNaissance = resultSet.getString("lieuNaissance");
	                String genre = resultSet.getString("genre");
	                String classe = resultSet.getString("classe");
	                String section = resultSet.getString("section");
	                apprenants.add(new Apprenant(id, nom, prenom, dateNaissance, lieuNaissance, genre, classe, section));
	            }

	            this.tableau_apprenant_view.setItems(apprenants);
	            this.tab.setCellValueFactory(new PropertyValueFactory("id"));
	            this.addApprenant_Col_Nom.setCellValueFactory(new PropertyValueFactory("nom"));
	            this.addApprenant_Col_Prenom.setCellValueFactory(new PropertyValueFactory("prenom"));
	            this.addApprenant_Col_Date.setCellValueFactory(new PropertyValueFactory("dateNaissance"));
	            this.addApprenant_Col_Lieu.setCellValueFactory(new PropertyValueFactory("lieuNaissance"));
	            this.addApprenant_Col_Genre.setCellValueFactory(new PropertyValueFactory("genre"));
	            this.addApprenant_Col_Classe.setCellValueFactory(new PropertyValueFactory("classe"));
	            this.addApprenant_Col_Section.setCellValueFactory(new PropertyValueFactory("section"));
	            this.refreshTableApprenant();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }		
	}
	 
     ObservableList<Apprenant> apprenant1s = FXCollections.observableArrayList();
     Integer index; 
	
	@FXML
    void handleMouseAction(MouseEvent mouseEvent)
    {
    	index = tableau_apprenant_view.getSelectionModel().getSelectedIndex();  
    	if (index <= -1)
    	{
    		return; 
    	}
    	txt_prenom.setText(addApprenant_Col_Prenom.getCellData(index).toString());
    	nom.setText(addApprenant_Col_Nom.getCellData(index).toString());
    
   }

	  public void refreshTableApprenant() throws Exception {
	        String sqlRefresh = "SELECT * from apprenantprofil";

	        try {
	            Connection connection = DataBaseConnection.getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(sqlRefresh);
	            ObservableList<Apprenant> apprenants = FXCollections.observableArrayList();

	            while(resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String nom = resultSet.getString("nom");
	                String prenom = resultSet.getString("prenom");
	                Date dateNaissance = resultSet.getDate("dateNaissance");
	                String lieuNaissance = resultSet.getString("lieuNaissance");
	                String genre = resultSet.getString("genre");
	                String classe = resultSet.getString("classe");
	                String section = resultSet.getString("section");
	                apprenants.add(new Apprenant(id, nom, prenom, dateNaissance, lieuNaissance, genre, classe, section));
	            }

	            this.tableau_apprenant_view.setItems(apprenants);
	        } catch (SQLException v) {
	            v.printStackTrace();
	        }

	    }
	  @FXML
	  void ActionFichier(ActionEvent event) throws Exception {
	      FileChooser fileChooser = new FileChooser();
	      fileChooser.setTitle("Choisir un fichier");

	      // Filtre pour n'afficher que les fichiers CSV
	      FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv");
	      fileChooser.getExtensionFilters().add(extFilter);

	      // Ouvre la boîte de dialogue pour choisir un fichier
	      File selectedFile = fileChooser.showOpenDialog(null);

	      if (selectedFile != null) {
	          System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());
	          try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javadata?serverTimezone=UTC&useSSL=false", "root", "")) {
	              try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
	                  String line;
	                  while ((line = br.readLine()) != null) {
	                      String[] values = line.split(";");
	                      String sql = "INSERT INTO apprenantprofil (nom, prenom, dateNaissance, lieuNaissance, genre, classe, section ) VALUES (?, ?, ?, ?, ?, ?, ?)";
	                      try (PreparedStatement statement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	                          statement.setString(1, values[1]);
	                          statement.setString(2, values[2]); 
	                          SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	                          java.util.Date dateNaissance = formatter.parse(values[3]);
	                          statement.setDate(3, new java.sql.Date(dateNaissance.getTime()));
	                          statement.setString(4, values[4]); 
	                          statement.setString(5, values[5]);
	                          statement.setString(6, values[6]); 
	                          statement.setString(7, values[7]);
	                          statement.executeUpdate();
	                          System.out.println("Insertion réussie.");

	                          // Récupère l'ID auto-incrémenté généré par la base de données
	                          ResultSet rs = statement.getGeneratedKeys();
	                          if (rs.next()) {
	                              int id = rs.getInt(1);
	                              System.out.println("ID auto-incrémenté : " + id);
	                          }
	                          
	                      } catch (SQLException e) {
	                          Alert alert = new Alert(AlertType.ERROR);
	                          alert.setTitle("Erreur SQL");
	                          alert.setHeaderText("Une erreur est survenue lors de l'insertion des données");
	                          alert.setContentText(e.getMessage());
	                          alert.showAndWait();
	                      } catch (ParseException e) {
	                          Alert alert = new Alert(AlertType.ERROR);
	                          alert.setTitle("Erreur de conversion de date");
	                          alert.setHeaderText("Une erreur est survenue lors de la conversion de la date");
	                          alert.setContentText(e.getMessage());
	                          alert.showAndWait();
	                      }
	                  }
	                  Alert alert = new Alert(AlertType.CONFIRMATION);
                      alert.setTitle("Succés");
                      alert.setHeaderText("L'insertion de données est un succés");
                      alert.showAndWait();
                      this.refreshTableApprenant();
	              } catch (IOException e) {
	                  Alert alert = new Alert(AlertType.ERROR);
	                  alert.setTitle("Erreur de lecture de fichier");
	                  alert.setHeaderText("Une erreur est survenue lors de la lecture du fichier CSV");
	                  alert.setContentText(e.getMessage());
	                  alert.showAndWait();
	              }
	          } catch (SQLException e) {
	              Alert alert = new Alert(AlertType.ERROR);
	              alert.setTitle("Erreur de connexion à la base de données");
	              alert.setHeaderText("Une erreur est survenue lors de la connexion à la base de données");
	              alert.setContentText(e.getMessage());
	              alert.showAndWait();
	          }
	      }
	  }

}