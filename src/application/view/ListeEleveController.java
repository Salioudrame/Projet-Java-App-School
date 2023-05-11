package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.model.Apprenant;
import application.model.Classe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListeEleveController implements Initializable {

    

    @FXML
    private TableView<Apprenant> tablea_apprenant_liste;

    @FXML
    private TableColumn<Apprenant, Integer> tab1;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Nom1;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Prenom1;

    @FXML
    private TableColumn<Apprenant, Date> addApprenant_Col_Date1;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Lieu1;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Genre1;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Classe1;

    @FXML
    private TableColumn<Apprenant, String> addApprenant_Col_Section1;

    @FXML
    private ComboBox<String> listeeleve;
    
     
    @FXML
    void SelectEleve(ActionEvent event) 
    {
    	  listeeleve.getItems().clear();
    	    List<String> nomsClasses = Classe.getNomsClasses();
    	    ObservableList<String> options = FXCollections.observableArrayList(nomsClasses);
    	    listeeleve.setItems(options);
    	    listeeleve.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    	        try {
    	            show1Apprenant();
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	    });
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 this.listeeleve.getItems().addAll(Classe.getNomsClasses());
		 SelectEleve(null);
	        try {
	            this.show1Apprenant();
	        } catch (Exception var4) {
	            var4.printStackTrace();
	        }
		 
	}

	private void show1Apprenant() throws Exception {
	    String classeSelectionnee = listeeleve.getValue();
	    if (classeSelectionnee == null) {
	        return;
	    }

	    String sql = "SELECT * FROM apprenantprofil WHERE classe = ?";
	    try {
	        Connection connection = DataBaseConnection.getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, classeSelectionnee);
	        ResultSet resultSet = statement.executeQuery();
	        ObservableList<Apprenant> apprenants = FXCollections.observableArrayList();

	        while (resultSet.next()) {
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

	        this.tablea_apprenant_liste.setItems(apprenants);
	        this.tab1.setCellValueFactory(new PropertyValueFactory("id"));
	        this.addApprenant_Col_Nom1.setCellValueFactory(new PropertyValueFactory("nom"));
	        this.addApprenant_Col_Prenom1.setCellValueFactory(new PropertyValueFactory("prenom"));
	        this.addApprenant_Col_Date1.setCellValueFactory(new PropertyValueFactory("dateNaissance"));
	        this.addApprenant_Col_Lieu1.setCellValueFactory(new PropertyValueFactory("lieuNaissance"));
	        this.addApprenant_Col_Genre1.setCellValueFactory(new PropertyValueFactory("genre"));
	        this.addApprenant_Col_Classe1.setCellValueFactory(new PropertyValueFactory("classe"));
	        this.addApprenant_Col_Section1.setCellValueFactory(new PropertyValueFactory("section"));
	    } catch (SQLException e) 
	    {
	        e.printStackTrace();
	    }
	    listeeleve.setOnAction(event -> 
	    {
	        try {
	            show1Apprenant();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}


}
