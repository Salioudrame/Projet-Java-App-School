package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import application.model.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class utilisateurController implements Initializable{
    @FXML
    private TableView<Utilisateur> userTable;
    @FXML
    private TableColumn<Utilisateur, Integer> idUserColumn;
    @FXML
    private TableColumn<Utilisateur, String> usernameColumn;
    @FXML
    private TableColumn<Utilisateur, String> passwordColumn;
    @FXML
    private TableColumn<Utilisateur, String> roleColumn;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private ComboBox<String> roles;
    @FXML
    private Button edit;
    private String [] role = new String[] {"admin", "secretaire","caissier"};
    
    

    @FXML
    void role(ActionEvent event) 
    {
    	 String role = ((String)this.roles.getSelectionModel().getSelectedItem()).toString();
    }

    // Reference to the main application.
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	try {
    		
    		this.roles.getItems().addAll(this.role);
            this.showUser();
            
        } catch (Exception var4) {
            var4.printStackTrace();
        }
	}

   
    public utilisateurController() {
		// TODO Auto-generated constructor stub
	}
    private void showUser() throws Exception 
	{
    	 
		 String sql1 = "SELECT * from users";
	            
				try {
					Connection connection = DataBaseConnection.getConnection();
					Statement statement = connection.createStatement();
		            ResultSet resultSet = statement.executeQuery(sql1);
		            ObservableList<Utilisateur> users = FXCollections.observableArrayList();

		            while(resultSet.next()) { 
		            	int idUser=resultSet.getInt("idUser");
		                String username = resultSet.getString("username");
		                String password = resultSet.getString("password"); 
		                String role = resultSet.getString("role");
		                
		                users.add(new Utilisateur(idUser,username, password, role));
		            }

		            this.userTable.setItems(users);
		            this.idUserColumn.setCellValueFactory(new PropertyValueFactory("idUser"));
		            this.usernameColumn.setCellValueFactory(new PropertyValueFactory("username"));
		            this.passwordColumn.setCellValueFactory(new PropertyValueFactory("password"));
		            this.roleColumn.setCellValueFactory(new PropertyValueFactory("role"));
		            
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            	        
	}
    
    public boolean isSignedUp(String username,String password) {
		String sql="SELECT * FROM users WHERE username = '"+username+"' AND password= '"+password+"'";
		
		try {
			Connection connection = DataBaseConnection.getConnection();
			PreparedStatement statement=connection.prepareStatement(sql);
			
			
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.first()) {
				return true;
			}
			return false;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
  
    @FXML
    public void addUser() throws Exception {
    	
        
        	try {
                
                
                if(this.username.getText()!="" && this.password.getText()!="" && this.roles.getValue()!=null) {
                	
                	String username = this.username.getText();
              	  	String password = this.password.getText();
              	  	String role = ((String)this.roles.getSelectionModel().getSelectedItem()).toString();
                    
                    String sql = "INSERT INTO users (username, password, role) values(?, ?, ?)";
                    
                    Connection connection = DataBaseConnection.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.setString(3, role);
                	
                	if(isSignedUp(username, password)==false) {
                		int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Insertion réussi");
                            alert.setHeaderText("");
                            alert.setContentText("L'ajout de l'utilisateur a été tres bien éffectué");
                            alert.showAndWait();
                            new TableView();
                            resetField();
                            
                        }
                        refreshTableApprenant();
                        
            
                	}else{
                		Alert alert =new Alert(Alert.AlertType.ERROR);
                    	alert.setTitle("existe");
                    	alert.setContentText("cet utilisateur existe deja");
                    	alert.setHeaderText("Utilisateur existe deja");
                    	alert.showAndWait();
                    	resetField();
                	}
                }else {
                	Alert alert =new Alert(Alert.AlertType.ERROR);
                	alert.setTitle("champs vides");
                	alert.setContentText("Veuillez remplir les champs svp");
                	alert.setHeaderText("champs vides");
                	alert.showAndWait();
                	resetField();
                }
            } catch (SQLException var16) {
                var16.printStackTrace();
            }
		
        
    }
    public void resetField() {
    	this.username.setText("");
    	this.password.setText("");
    	this.roles.setValue(null);
    }
    
    @FXML
    public void editUser(MouseEvent event) throws Exception{
    	String sql1="UPDATE users SET username = ?, password = ? , role = ? WHERE idUser= ?";
    	
    	try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql1);
            Utilisateur selectedUser = userTable.getSelectionModel().getSelectedItem();
            
            
            if(selectedUser == null) {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
            	alert.setTitle("aucun utilisateur selectionné");
            	alert.setContentText("Veuillez selectionner un utilisateur");
            	alert.setHeaderText("Aucun utilisateur selectionné ");
            	alert.showAndWait();
            	resetField();
            }else {
            	
            	statement.setString(1, username.getText());
            	statement.setString(2, password.getText());
            	statement.setString(3, roles.getValue());
            	statement.setInt(4, selectedUser.idUser);
            	
            	Alert alert = new Alert(AlertType.CONFIRMATION);
      	      	alert.setTitle("modification");
      	      	alert.setHeaderText("Etes vous sur de vouloir modifier l'utilisateur ?");
      	      	Stage stage=(Stage) edit.getScene().getWindow();
      		
      		
      	      	Optional<ButtonType> result =alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                
                statement.execute();
                
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    Alert alerte = new Alert(AlertType.INFORMATION);
                    alerte.setTitle("Insertion réussi");
                    alerte.setHeaderText("");
                    alerte.setContentText("La suppression a bien ete effectue");
                    alerte.showAndWait();
                    new TableView();
                    
                }
                refreshTableApprenant();
               }
            }     
        } catch (SQLException var16) {
            var16.printStackTrace();
        }
	
   	 	//role.setText(selectedUser.getRole());
    }
    @SuppressWarnings("unused")
	@FXML
    public void deleteUser(MouseEvent event) throws Exception {
    	  
          
          String sql = "DELETE FROM users WHERE idUser= ?";
          
          
          
            
  			try {
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                Utilisateur selectedUser = userTable.getSelectionModel().getSelectedItem();
                
                
                if(selectedUser == null) {
                	Alert alert =new Alert(Alert.AlertType.ERROR);
                	alert.setTitle("aucun utilisateur selectionné");
                	alert.setContentText("Veuillez selectionner un utilisateur");
                	alert.setHeaderText("Aucun utilisateur selectionné ");
                	alert.showAndWait();
                	resetField();
                }else {
                	statement.setInt(1, selectedUser.idUser);
                	Alert alert = new Alert(AlertType.CONFIRMATION);
          	      	alert.setTitle("suppression");
          	      	alert.setHeaderText("Etes vous sur de vouloir supprimer l'utilisateur ?");
          	      	Stage stage=(Stage) edit.getScene().getWindow();
          		
          		
          	      	Optional<ButtonType> result =alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK) {
                    
                    statement.execute();
                    
                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        Alert alerte = new Alert(AlertType.INFORMATION);
                        alerte.setTitle("Insertion réussi");
                        alerte.setHeaderText("");
                        alerte.setContentText("La suppression a bien ete effectue");
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
    
    
    public void refreshTableApprenant() throws Exception {
    	String sql1 = "SELECT * from users";
        
		try {
			Connection connection = DataBaseConnection.getConnection();
			Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            ObservableList<Utilisateur> users = FXCollections.observableArrayList();

            while(resultSet.next()) { 
            	int idUser=resultSet.getInt("idUser");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password"); 
                String role = resultSet.getString("role");
                
                users.add(new Utilisateur(idUser,username, password, role));
            }

            this.userTable.setItems(users);
            this.idUserColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
            this.usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            this.passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
            this.roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
            resetField();
            //this.refreshTableApprenant();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
    @FXML
    public void rowSelection() {
    	Utilisateur selectedUser = userTable.getSelectionModel().getSelectedItem();
    	if(selectedUser==null) {
    		Alert alert =new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("aucun utilisateur selectionné");
        	alert.setContentText("Veuillez selectionner un utilisateur");
        	alert.setHeaderText("Aucun utilisateur selectionné ");
        	alert.showAndWait();
        	resetField();
    	}else {
    		username.setText(selectedUser.getUsername());
    	 	password.setText(selectedUser.getPassword());
    	 	roles.setValue(selectedUser.getRole());
    	}
    }



	
    
}