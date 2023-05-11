package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class paiementsController implements Initializable{
	private int tmp ;
	
	@FXML
	private Label byDayLabel;
	@FXML
	private Label byHebdoLabel;
	@FXML
	private Label byMonthLabel;
	@FXML
	private Label byYearLabel;
	@FXML
	private TextField prenom;
	@FXML
	private TextField nom;
	@FXML
	private TextField montant;
	@FXML
	private DatePicker datePayement;
	@FXML
	private Button editButton;
	@FXML
	private TableView<Paiement> payementTable;
	@FXML
	private TableColumn<Paiement,Integer> idPayementColumn;
	@FXML
	private TableColumn<Paiement,String> prenomColumn;
	@FXML
	private TableColumn<Paiement,String> nomColumn;
	@FXML
	private TableColumn<Paiement,String> classeColumn;
	@FXML
	private TableColumn<Paiement,Integer> montantColumn;
	@FXML
	private TableColumn<Paiement,Date> datePayementColumn;
	@FXML
	private TableColumn<Paiement,Date> echeanceColumn;
	@FXML
	private TableColumn<Paiement,String> statutColumn;
	@FXML
	private TableColumn<Paiement,Integer> montantPayeColumn;
	@FXML
	private TableColumn<Paiement,Integer> montantAPayerColumn;
	@FXML
	private Button addButton;
	@FXML
	private Button resetButton;
	@FXML
	private ComboBox<String> classes;
	private String [] classe = new String[] {"CI","CP", "CE1","CE2","CM1","CM2","CRECHES","TPS","PS","MS","GS","6eme","5eme","4e","3e","CAP_B"};
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.classes.getItems().addAll(this.classe);
		this.byDayLabel.setText(String.valueOf(showDays()));
		this.byHebdoLabel.setText(String.valueOf(showHebdo()));
		this.byMonthLabel.setText(String.valueOf(showMonth()));
		this.byYearLabel.setText(String.valueOf(showYear()));
		try {
			refreshTablePaiement();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public int showYear() {
		String sql="SELECT * FROM payement WHERE datePayement >= DATE_SUB(NOW(), INTERVAL 1 YEAR)";
		
		try {
			Connection connection = DataBaseConnection.getConnection();
			PreparedStatement statement=connection.prepareStatement(sql);
			
			
			ResultSet resultSet = statement.executeQuery(sql);
			int i=0;
			while(resultSet.next()) {
				i++;
			}
			return i;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 1000;
		}
		
	}
	
	
	public int showMonth() {
		String sql="SELECT * FROM payement WHERE datePayement >= DATE_SUB(NOW(), INTERVAL 1 MONTH)";
		
		try {
			Connection connection = DataBaseConnection.getConnection();
			PreparedStatement statement=connection.prepareStatement(sql);
			
			
			ResultSet resultSet = statement.executeQuery(sql);
			int i=0;
			while(resultSet.next()) {
				i++;
			}
			return i;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 1000;
		}
		
	}
	
	
	public int showHebdo() {
		String sql="SELECT * FROM payement WHERE datePayement >= DATE_SUB(NOW(), INTERVAL 7 DAY)";
		
		try {
			Connection connection = DataBaseConnection.getConnection();
			PreparedStatement statement=connection.prepareStatement(sql);
			
			
			ResultSet resultSet = statement.executeQuery(sql);
			int i=0;
			while(resultSet.next()) {
				i++;
			}
			return i;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 1000;
		}
		
	}
	
	
	public int showDays() {
		String sql="SELECT * FROM payement WHERE datePayement >= DATE_SUB(NOW(), INTERVAL 1 DAY)";
		
		try {
			Connection connection = DataBaseConnection.getConnection();
			PreparedStatement statement=connection.prepareStatement(sql);
			
			
			ResultSet resultSet = statement.executeQuery(sql);
			int i=0;
			while(resultSet.next()) {
				i++;
			}
			return i;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 1000;
		}
		
	}
	
	public boolean isSignedUp(String prenom,String nom,String classe) {
		String sql="SELECT * FROM apprenantprofil WHERE prenom = '"+prenom+"' AND nom= '"+nom+"' AND classe LIKE '%"+classe+"%'";
		
		try {
			Connection connection = DataBaseConnection.getConnection();
			PreparedStatement statement=connection.prepareStatement(sql);
			
			
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.first()) {
				String prenom1=resultSet.getString("prenom");
				
				return true;
			}
			return false;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	public int totalAPayer(String classe) {
		String sql="SELECT total FROM sections WHERE nom_classe='"+classe+"' ";
		
		try {
			Connection connection = DataBaseConnection.getConnection();
			Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            

            while(resultSet.first()) { 
            	int total=resultSet.getInt(1);
                return total;
                  
            }
            return 0;
		}catch (Exception e) {
			// TODO: handle exception
			return 1000;
		}
	}
	
	public void updateStatus(String prenom,String nom) {
		String sql="UPDATE payement SET statut='Payé' WHERE prenom= ? AND nom= ?";
		
		try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, prenom);
            statement.setString(2, nom);
            statement.execute();
            
            
        } catch (SQLException var16) {
            var16.printStackTrace();
        }
   	 
	}
	
	public void removeStatus(String prenom,String nom) {
		String sql="UPDATE payement SET statut='Non payé' WHERE prenom= ? AND nom= ?";
		
		try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, prenom);
            statement.setString(2, nom);
            statement.execute();
            
            
        } catch (SQLException var16) {
            var16.printStackTrace();
        }
   	 
	}
	
	public void echeanceStatus(String prenom,String nom) {
		String sql="UPDATE payement SET statut='exclus' WHERE prenom= ? AND nom= ?";
		
		try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, prenom);
            statement.setString(2, nom);
            statement.execute();
            
            
        } catch (SQLException var16) {
            var16.printStackTrace();
        }
   	 
	}
	
	public Date getEcheance() {
		
		String sql="SELECT dateEcheance FROM payement";
		try {
			Connection connection = DataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            
            Date date=null;

            if(resultSet.first()) { 

            	date=resultSet.getDate(1);

            }
            
            return date;
            

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
	}
	private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        // Vérifier si la chaîne ne contient que des chiffres
        return str.matches("\\d+");
    }
	private boolean isString(String str) {
        if (str == null) {
            return false;
        }
        // Vérifier si la chaîne ne contient que des chiffres
        return str.matches("[a-zA-Z]+");
    }
	
	@FXML
    public void addPayement() throws Exception {
    	 
        if(this.prenom.getText() != "" && this.prenom.getText()!="" && this.classes.getValue()!= null && this.datePayement.getValue()!=null && this.montant.getText()!="" && !isString(montant.getText()) && Integer.valueOf(this.montant.getText()) != null
        		) {
        	String prenom = this.prenom.getText();
      	  	String nom = this.nom.getText();
      	  	String classe = ((String)this.classes.getSelectionModel().getSelectedItem()).toString();
      	  	String montant= this.montant.getText();
      	  	LocalDate datePayement= this.datePayement.getValue();
      	  	//LocalDate dateEcheance= LocalDate.now();
      	  	String statut="Non payé";
      	  	int refreshMontant=0;
      	  	
            if(datePayement.isAfter(LocalDate.now())) {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
            	alert.setTitle("Error");
            	alert.setContentText("Desolé date de paiement");
            	alert.setHeaderText("Erreur d'insertion");
            	alert.showAndWait();
            	resetPaiement();
            	return ;
            }else if(!isNumeric(montant)) {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
            	alert.setTitle("Error");
            	alert.setContentText("montant incorrect");
            	alert.setHeaderText("Erreur d'insertion");
            	alert.showAndWait();
            	resetPaiement();
            }
            else {
            	
            	String sql = "INSERT INTO payement (prenom, nom, classe,montant,datePayement,statut,montantPaye,dateEcheance) values(?, ?, ?, ?,?, ?, ?, ?)";
                
            	try {
            		
                    Connection connection = DataBaseConnection.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, prenom);
                    statement.setString(2, nom);
                    statement.setString(3, classe);
                    statement.setInt(4, Integer.valueOf(montant));
                    statement.setDate(5, java.sql.Date.valueOf(datePayement));
                    //statement.setDate(6, java.sql.Date.valueOf(dateEcheance));
                    statement.setString(6, statut);
                    statement.setInt(7, Integer.valueOf(montant)+calculMontant(prenom, nom, classe));
                    statement.setDate(8, (java.sql.Date) getEcheance());
                    
                    
                    
                    if(isSignedUp(prenom,nom,classe)==true ) {
                    	
                    	
                    	if(totalAPayer(classe) > Integer.valueOf(montant)+calculMontant(prenom, nom, classe)) {
                    		int rowsInserted = statement.executeUpdate();
                            refreshMontant=calculMontant(prenom, nom, classe);
                            if (rowsInserted > 0) {
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Insertion réussi");
                                alert.setHeaderText("");
                                alert.setContentText("L'ajout du payement a bien été effectué");
                                alert.showAndWait();
                                new TableView();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(getEcheance());

                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH) + 1; // Les mois commencent à 0, donc on ajoute 1
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                LocalDate localdate=LocalDate.of(year, month, day);
                                
                                if(localdate.isBefore(datePayement)) {
                                	echeanceStatus(prenom,nom);
                                }

                            }
                            resetPaiement();
                            this.byDayLabel.setText(String.valueOf(showDays()));
                            this.byHebdoLabel.setText(String.valueOf(showHebdo()));
                            this.byMonthLabel.setText(String.valueOf(showMonth()));
                            this.byYearLabel.setText(String.valueOf(showYear()));
                            //calculMontant(prenom,nom,classe);
                            
                            refreshTablePaiement();
                            connection.close();
                        }else if(totalAPayer(classe)==Integer.valueOf(montant)+calculMontant(prenom, nom, classe)) {
                        	int rowsInserted = statement.executeUpdate();
                            refreshMontant=calculMontant(prenom, nom, classe);
                            if (rowsInserted > 0) {
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Insertion réussi");
                                alert.setHeaderText("");
                                alert.setContentText("L'ajout du payement a bien été effectué");
                                alert.showAndWait();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(getEcheance());

                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH) + 1; // Les mois commencent à 0, donc on ajoute 1
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                LocalDate localdate=LocalDate.of(year, month, day);
                                
                                if(localdate.isBefore(datePayement)) {
                                	echeanceStatus(prenom,nom);
                                }else {
                                	updateStatus(prenom,nom);
                                }
                                new TableView();
                                
                            }
                            resetPaiement();
                            this.byDayLabel.setText(String.valueOf(showDays()));
                            this.byHebdoLabel.setText(String.valueOf(showHebdo()));
                            this.byMonthLabel.setText(String.valueOf(showMonth()));
                            this.byYearLabel.setText(String.valueOf(showYear()));
                            
                            
                            //calculMontant(prenom,nom,classe);
                            
                            refreshTablePaiement();
                            connection.close();
                        }else {
                        	Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Paiement effectué");
                            alert.setHeaderText("Le montant total a ete paye par l'eleve "+this.prenom.getText()+" "+this.nom.getText()+" sinon indiquez qu'il/elle doit payer le montant de  "+totalAPayer(classe)+" Fcfa");
                            alert.setContentText("le montant restant est :"+String.valueOf(Integer.valueOf(montant)+calculMontant(prenom, nom, classe)-totalAPayer(classe))+" Fcfa");
                            resetPaiement();
                            alert.showAndWait();
                            new TableView();
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(getEcheance());

                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH) + 1; // Les mois commencent à 0, donc on ajoute 1
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            LocalDate localdate=LocalDate.of(year, month, day);
                            
                            if(localdate.isBefore(datePayement)) {
                            	echeanceStatus(prenom,nom);
                            }
                        }
                    	
                    	
                    	
                    }else {
                    	resetPaiement();
                    	Alert alert =new Alert(Alert.AlertType.WARNING);
                    	alert.setTitle("not found error");
                    	alert.setContentText("Veuillez inserer le paiement d'un eleve qui existe");
                    	alert.setHeaderText("Eleve introuvable");
                    	alert.showAndWait();
                    }
                 
                } catch (SQLException var16) {
                    var16.printStackTrace();
                }
            }
	
        }else {
        	Alert alert =new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setContentText("Veuillez remplir les champs svp ou montant incorrect :)");
        	alert.setHeaderText("Erreur d'insertion");
        	alert.showAndWait();
        	resetPaiement();
        }	
        
        	
        
    }
	
	
	public int calculMontant(String prenom,String nom,String classe) {
		
		String sql="SELECT SUM(montant) FROM payement WHERE prenom = '"+prenom+"' AND nom= '"+nom+"' AND classe LIKE '%"+classe+"%'";
		
		try {
			Connection connection = DataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Paiement> paiement=new ArrayList<>();
            
            int montant=0;

            if(resultSet.first()) { 

            	montant=resultSet.getInt(1);

                
                paiement.add(new Paiement(montant));
                
            }

            return montant;


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return 2;
		}

//		
}
	@FXML
	public void resetPaiement() {
		prenom.setText("");
		nom.setText("");
		montant.setText("");
		datePayement.setValue(null);
		classes.setValue(null);
		
	}
	public void refreshTablePaiement() throws Exception {
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
                String classe = resultSet.getString("classe");
                int montant = resultSet.getInt("montant");
                Date datePaiement = resultSet.getDate("datePayement");
                Date dateEcheance = resultSet.getDate("dateEcheance");
                String statut = resultSet.getString("statut");
                int montantPaye=resultSet.getInt("montantPaye");
                
                paiements.add(new Paiement(idPaiement,prenom,nom,classe,montant,datePaiement,dateEcheance,statut,montantPaye));
            }

            this.payementTable.setItems(paiements);
            this.idPayementColumn.setCellValueFactory(new PropertyValueFactory<>("idPaiement"));
            this.prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            this.nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            this.classeColumn.setCellValueFactory(new PropertyValueFactory<>("classe"));
            this.montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
            this.datePayementColumn.setCellValueFactory(new PropertyValueFactory<>("datePayement"));
            this.echeanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateEcheance"));
            this.statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
            this.montantPayeColumn.setCellValueFactory(new PropertyValueFactory<>("montantPaye"));
            

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@FXML
	public void editPaiement() throws Exception {
		String sql1="UPDATE payement SET prenom = ?, nom = ?, classe = ?, montant = ?, datePayement = ?,dateEcheance = ?,montantPaye=? "
				+ "WHERE idPayement= ?";
    	
   		
		
		try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql1);
            Paiement selectedPaiement = (Paiement) payementTable.getSelectionModel().getSelectedItem();
            
            if(selectedPaiement == null) {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
            	alert.setTitle("aucun payement selectionné");
            	alert.setContentText("Veuillez selectionner un payement");
            	alert.setHeaderText("Aucun payement selectionné ");
            	alert.showAndWait();
            	resetPaiement();
            	
            }else if(!isNumeric(montant.getText())) {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
            	alert.setTitle("Error");
            	alert.setContentText("montant incorrect");
            	alert.setHeaderText("Erreur de modification");
            	alert.showAndWait();
            	resetPaiement();
            }else if(!isString(montant.getText())) {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
            	alert.setTitle("Error");
            	alert.setContentText("montant incorrect");
            	alert.setHeaderText("Erreur de modification");
            	alert.showAndWait();
            	resetPaiement();
            }
            else {
      	      statement.setString(1, prenom.getText());
              statement.setString(2, nom.getText());
              statement.setString(3, classes.getValue());
              statement.setInt(4, Integer.valueOf(montant.getText()));
              statement.setDate(5, java.sql.Date.valueOf(datePayement.getValue()));
              statement.setDate(6, (java.sql.Date) selectedPaiement.getDateEcheance());
              statement.setInt(7, calculMontant(prenom.getText(), nom.getText(), classes.getValue())-tmp+Integer.valueOf(montant.getText()) );
              statement.setInt(8, selectedPaiement.getIdPaiement());
              
              Alert alert = new Alert(AlertType.CONFIRMATION);
    	      alert.setTitle("modification");
    	      alert.setHeaderText("Etes vous sur de vouloir modifier l'utilisateur ?");
    	      Stage stage=(Stage) editButton.getScene().getWindow();
              
    	      Optional<ButtonType> result =alert.showAndWait();
              if(result.isPresent() && result.get() == ButtonType.OK) {
            	  if(totalAPayer(classes.getValue()) > calculMontant(prenom.getText(), nom.getText(), classes.getValue())-tmp+Integer.valueOf(montant.getText())) {
                  	statement.execute();
               
                  	int rowsInserted = statement.executeUpdate();
                      //refreshMontant=calculMontant(prenom, nom, classe);
                      if (rowsInserted > 0) {
                          Alert alert4 = new Alert(AlertType.INFORMATION);
                          alert4.setTitle("Modification Reussie");
                          alert4.setHeaderText("Modification Reussie");
                          alert4.setContentText("La modification du payement est un succès");
                          alert4.showAndWait();
                          new TableView();
                          Calendar calendar = Calendar.getInstance();
                          calendar.setTime(getEcheance());

                          int year = calendar.get(Calendar.YEAR);
                          int month = calendar.get(Calendar.MONTH) + 1; // Les mois commencent à 0, donc on ajoute 1
                          int day = calendar.get(Calendar.DAY_OF_MONTH);
                          LocalDate localdate=LocalDate.of(year, month, day);
                          
                          if(localdate.isBefore(datePayement.getValue())) {
                          	echeanceStatus(prenom.getText(),nom.getText());
                          }else {
                        	  removeStatus(prenom.getText(),nom.getText());
                          }
                          
                      }
                      resetPaiement();
                      this.byDayLabel.setText(String.valueOf(showDays()));
                      this.byHebdoLabel.setText(String.valueOf(showHebdo()));
                      this.byMonthLabel.setText(String.valueOf(showMonth()));
                      this.byYearLabel.setText(String.valueOf(showYear()));
                      
                     
                      refreshTablePaiement();
         
                      connection.close();
                  }else if(totalAPayer(classes.getValue())==calculMontant(prenom.getText(), nom.getText(), classes.getValue())-tmp+Integer.valueOf(montant.getText())) {
                  	statement.execute();
                  	int rowsInserted = statement.executeUpdate();
                      //refreshMontant=calculMontant(prenom, nom, classe);
                      if (rowsInserted > 0) {
                          Alert alert3 = new Alert(AlertType.INFORMATION);
                          alert3.setTitle("modification réussi");
                          alert3.setHeaderText("modification réussi");
                          alert3.setContentText("La modification du payement est un succès");
                          alert3.showAndWait();
                          new TableView();
                          Calendar calendar = Calendar.getInstance();
                          calendar.setTime(getEcheance());

                          int year = calendar.get(Calendar.YEAR);
                          int month = calendar.get(Calendar.MONTH) + 1; // Les mois commencent à 0, donc on ajoute 1
                          int day = calendar.get(Calendar.DAY_OF_MONTH);
                          LocalDate localdate=LocalDate.of(year, month, day);
                         
                          if(localdate.isBefore(datePayement.getValue())) {
                          	echeanceStatus(prenom.getText(),nom.getText());
                          }else {
                        	  updateStatus(prenom.getText(),nom.getText());
                          }
                          
                      }
                      resetPaiement();
                      this.byDayLabel.setText(String.valueOf(showDays()));
                      this.byHebdoLabel.setText(String.valueOf(showHebdo()));
                      this.byMonthLabel.setText(String.valueOf(showMonth()));
                      this.byYearLabel.setText(String.valueOf(showYear()));
                      removeStatus(prenom.getText(),nom.getText());
                      
                      
                      refreshTablePaiement();
                      removeStatus(prenom.getText(),nom.getText());
                      connection.close();
                  }else {
                  	Alert alert2 = new Alert(AlertType.INFORMATION);
                      alert2.setTitle("Paiement effectué");
                      alert2.setHeaderText("Le montant total a ete paye par l'eleve "+this.prenom.getText()+" "+this.nom.getText()+" sinon indiquez qu'il/elle doit payer le montant de  "+totalAPayer(classes.getValue())+" Fcfa");
                      alert2.setContentText("le montant restant est :"+String.valueOf(calculMontant(prenom.getText(), nom.getText(), classes.getValue())-tmp+Integer.valueOf(montant.getText())-totalAPayer(classes.getValue()))+" Fcfa");
                      resetPaiement();
                      alert2.showAndWait();
                      new TableView();
                      Calendar calendar = Calendar.getInstance();
                      calendar.setTime(getEcheance());

                      int year = calendar.get(Calendar.YEAR);
                      int month = calendar.get(Calendar.MONTH) + 1; // Les mois commencent à 0, donc on ajoute 1
                      int day = calendar.get(Calendar.DAY_OF_MONTH);
                      LocalDate localdate=LocalDate.of(year, month, day);
                      
                      if(localdate.isBefore(datePayement.getValue())) {
                      	echeanceStatus(prenom.getText(),nom.getText());
                      }else {
                    	  removeStatus(prenom.getText(),nom.getText());
                      }
                      
                  }
                  
            	  //removeStatus(prenom.getText(),nom.getText());
                  refreshTablePaiement();
                  //removeStatus(prenom.getText(),nom.getText());
              }
            }
            
                     
        } catch (SQLException var16) {
            var16.printStackTrace();
        }

	}
	@FXML
    public void rowSelection() {
    	Paiement selectedRubriques = (Paiement) payementTable.getSelectionModel().getSelectedItem();
    	
    	if(selectedRubriques==null) {
    		Alert alert =new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("aucun utilisateur selectionné");
        	alert.setContentText("Veuillez selectionner un utilisateur");
        	alert.setHeaderText("Aucun utilisateur selectionné ");
        	alert.showAndWait();
        	resetPaiement();
    	}else {
    		prenom.setText(String.valueOf(selectedRubriques.getPrenom()));
        	nom.setText(String.valueOf(selectedRubriques.getNom()));
        	classes.setValue(String.valueOf(selectedRubriques.getClasse()));
        	montant.setText(String.valueOf(selectedRubriques.getMontant()));
        	tmp=selectedRubriques.getMontant() ;
        	datePayement.setValue(LocalDate.parse(String.valueOf(selectedRubriques.getDatePayement())));
    	}
    	
    }

}