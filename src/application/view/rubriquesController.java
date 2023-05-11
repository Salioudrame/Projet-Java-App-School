package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.Paiement;
import application.model.Rubriques;
import application.model.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class rubriquesController implements Initializable{
	@FXML
	private TableView rubriqueTable;
	@FXML
	private TableView totalTable;
	@FXML
	private TextField droitDinscription;
	@FXML
	private TextField scolarite;
	@FXML
	private TextField albums;
	@FXML
	private TextField tenues;
	@FXML
	private TextField fraisGeneraux;
	@FXML
	private TextField cotisationAPE;
	@FXML
	private Button editButton;
	
	
	@FXML
	private TableColumn<Rubriques, Integer> idRubriquesColumn;
	@FXML
	private TableColumn<Rubriques, String> classeColumn;
	@FXML
	private TableColumn<Rubriques, Integer> droitColumn;
	@FXML
	private TableColumn<Rubriques, Integer> scolariteColumn;
	@FXML
	private TableColumn<Rubriques, Integer> albumsColumn;
	@FXML
	private TableColumn<Rubriques, Integer> tenuesColumn;
	@FXML
	private TableColumn<Rubriques, Integer> fraisColumn;
	@FXML
	private TableColumn<Rubriques, Integer> cotisationColumn;
	@FXML
	private TableColumn<Rubriques, Integer> totalColumn;

	
	private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        // Vérifier si la chaîne ne contient que des chiffres
        return str.matches("\\d+");
    }
	// Event Listener on Button[#editButton].onMouseClicked
	@FXML
	public void editRubrique(MouseEvent event) throws Exception {
		String sql1="UPDATE rubriques SET droitInscription = ?, scolarite = ?, albums = ?, tenues = ?, fraisGeneraux = ?,cotisationAPE=? "
				+ "WHERE idRubriques= ?";
    			
		
		try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql1);
            Rubriques selectedRubrique = (Rubriques) rubriqueTable.getSelectionModel().getSelectedItem();
            
            if(selectedRubrique == null) {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
            	alert.setTitle("aucune rubrique selectionné");
            	alert.setContentText("Veuillez selectionner une rubrique");
            	alert.setHeaderText("Aucune rubrique selectionnée ");
            	alert.showAndWait();
            	
            }else if(this.albums.getText()=="" || this.cotisationAPE.getText()=="" || this.droitDinscription.getText()=="" || this.fraisGeneraux.getText()=="" || this.scolarite.getText()=="" || this.tenues.getText()=="") {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
            	alert.setTitle("rubriques vides");
            	alert.setContentText("Veuillez remplir les rubriques");
            	alert.setHeaderText("champs vides");
            	alert.showAndWait();
            	droitDinscription.setText("");
                scolarite.setText("");
                albums.setText("");
                tenues.setText("");
                fraisGeneraux.setText("");
                cotisationAPE.setText("");
            }else {
            	statement.setInt(1, Integer.valueOf(droitDinscription.getText()));
                statement.setInt(2, Integer.valueOf(scolarite.getText()));
                statement.setInt(3, Integer.valueOf(albums.getText()));
                statement.setInt(4, Integer.valueOf(tenues.getText()));
                statement.setInt(5, Integer.valueOf(fraisGeneraux.getText()));
                statement.setInt(6, Integer.valueOf(cotisationAPE.getText()));
                statement.setInt(7, selectedRubrique.getIdRubriques());
                
                Alert alert = new Alert(AlertType.CONFIRMATION);
      	      	alert.setTitle("modification");
      	      	alert.setHeaderText("Etes vous sur de vouloir modifier l'utilisateur ?");
      	      	Stage stage=(Stage) editButton.getScene().getWindow();
      	      	
      	      	Optional<ButtonType> result =alert.showAndWait();
      	      	
      	      if(result.isPresent() && result.get() == ButtonType.OK) {
      	    	statement.execute();
                droitDinscription.setText("");
                scolarite.setText("");
                albums.setText("");
                tenues.setText("");
                fraisGeneraux.setText("");
                cotisationAPE.setText("");
                
                int rowsEdited = statement.executeUpdate();
                if (rowsEdited > 0) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("modification réussi");
                    alert2.setHeaderText("");
                    alert2.setContentText("La modification a bien ete effectue");
                    alert2.showAndWait();
                    new TableView();
                    
                }
                showRubriques();
                
      	      }
            }
            
        } catch (SQLException var16) {
            var16.printStackTrace();
        }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
            this.showRubriques();
        } catch (Exception var4) {
            var4.printStackTrace();
        }
		
	}

	
    private void showRubriques() throws Exception 
	{
    	 
		 String sql1 = "SELECT * from rubriques";
	            
				try {
					Connection connection = DataBaseConnection.getConnection();
					Statement statement = connection.createStatement();
		            ResultSet resultSet = statement.executeQuery(sql1);
		            ObservableList<Rubriques> rubriques = FXCollections.observableArrayList();
		            //List<Rubriques> rubrique= new ArrayList<>();

		            while(resultSet.next()) { 
		            	int idRubriques=resultSet.getInt("idRubriques");
		                String classe = resultSet.getString("classe");
		                int droitDinscription=resultSet.getInt("droitInscription");
		                int scolarite=resultSet.getInt("scolarite");
		                int albums=resultSet.getInt("albums");
		                int tenues=resultSet.getInt("tenues");
		                int fraisGeneraux=resultSet.getInt("fraisGeneraux");
		                int cotisationAPE=resultSet.getInt("cotisationAPE");
		                int total=droitDinscription+scolarite+albums+tenues+fraisGeneraux+cotisationAPE;
		                		                
		                rubriques.add(new Rubriques(idRubriques,classe, droitDinscription, scolarite,albums,tenues,fraisGeneraux,cotisationAPE,total));
		                //rubrique.add(new Rubriques(idRubriques,classe, droitDinscription, scolarite,albums,tenues,fraisGeneraux,cotisationAPE,total));
		            }

		            this.rubriqueTable.setItems(rubriques);
		            this.idRubriquesColumn.setCellValueFactory(new PropertyValueFactory("idRubriques"));
		            this.classeColumn.setCellValueFactory(new PropertyValueFactory("classe"));
		            this.droitColumn.setCellValueFactory(new PropertyValueFactory("droitDinscription"));
		            this.scolariteColumn.setCellValueFactory(new PropertyValueFactory("scolarite"));
		            this.albumsColumn.setCellValueFactory(new PropertyValueFactory("albums"));
		            this.tenuesColumn.setCellValueFactory(new PropertyValueFactory("tenues"));
		            this.fraisColumn.setCellValueFactory(new PropertyValueFactory("fraisGeneraux"));
		            this.cotisationColumn.setCellValueFactory(new PropertyValueFactory("cotisationAPE"));
		            this.totalColumn.setCellValueFactory(new PropertyValueFactory("total"));

		            insertUpdateTotal(rubriques);
		            //return rubriques;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//return null;
				}
				
	            	        
	}
    
    public void updateTotalSection(int total,String classe) {
    	
    	
    		String sql = "UPDATE sections SET total = ? WHERE nom_classe= '"+classe+"' ";
    		
        		try {
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                
                statement.setInt(1, total);
                statement.execute();
                
                
                
    		}catch (Exception e) {
				e.getMessage();
			}
    	
  }
    public void insertUpdateTotal(ObservableList<Rubriques> rubriques) {
    	
    	for (Rubriques rubriques1 : rubriques) {
    		String sql = "UPDATE rubriques SET classe = ?, droitInscription = ?, scolarite= ?,albums= ?, tenues= ?,fraisGeneraux= ?,cotisationAPE=?,total= ? WHERE idRubriques= ?";
    		
        		try {
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, rubriques1.getClasse());
                statement.setInt(2, rubriques1.getDroitDinscription());
                statement.setInt(3, rubriques1.getScolarite());
                statement.setInt(4, rubriques1.getAlbums());
                statement.setInt(5, rubriques1.getTenues());
                statement.setInt(6, rubriques1.getFraisGeneraux());
                statement.setInt(7, rubriques1.getCotisationAPE());
                statement.setInt(8, rubriques1.getTotal());
                
                statement.setInt(9, rubriques1.getIdRubriques());
                
                updateTotalSection(rubriques1.getTotal(),rubriques1.getClasse());
                statement.execute();
                //int rowsInserted = statement.executeUpdate();
                
                
    		}catch (Exception e) {
				e.getMessage();
			}
    	}
  }
        
    @FXML
    public void rowSelection() {
    	Rubriques selectedRubriques = (Rubriques) rubriqueTable.getSelectionModel().getSelectedItem();
    	
    	if(selectedRubriques==null) {
    		Alert alert =new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("aucun rubrique selectionnée");
        	alert.setContentText("Veuillez selectionner une rubrique");
        	alert.setHeaderText("Aucune rubrique selectionnée ");
        	alert.showAndWait();
        	
    	}else {
    		droitDinscription.setText(String.valueOf(selectedRubriques.getDroitDinscription()));
        	scolarite.setText(String.valueOf(selectedRubriques.getScolarite()));
        	albums.setText(String.valueOf(selectedRubriques.getAlbums()));
        	tenues.setText(String.valueOf(selectedRubriques.getTenues()));
        	fraisGeneraux.setText(String.valueOf(selectedRubriques.getFraisGeneraux()));
        	cotisationAPE.setText(String.valueOf(selectedRubriques.getCotisationAPE()));
    	}
    	
    }
    
	
}
