package application.view;


import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;

import java.sql.ResultSet;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	private Main main;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField mdpField;
	@FXML
	private Label loginMessageLabel;
	@FXML
	private Button connectButton;
	@FXML
	private BorderPane dashboardSecretaireLayout;
	@FXML
	private BorderPane dashboardAdminLayout;
	@FXML
	private BorderPane dashboardCaissierLayout;
		
	@FXML
	private void loginButtonOnAction(ActionEvent e) {
		if(usernameField.getText()=="" && mdpField.getText()=="") {
			loginMessageLabel.setText("username ou mot de passe vide");
		}else {
			//loginMessageLabel.setText("Vous etes connecté");
			validate();
		}
		
	}
	@FXML
	public void cancelButtonOnAction(ActionEvent e){
		Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Fermeture");
	    alert.setHeaderText("Etes vous sur de vouloir quitter ?");
		Stage stage=(Stage) cancelButton.getScene().getWindow();
		
		
		Optional<ButtonType> result =alert.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK) {
			stage.close();
		}
		
	}
	@FXML
	public void validate() {
		DataBaseConnection connectNow=new DataBaseConnection();
		Connection connectDB=connectNow.getConnection();
		
		String verifyLogin="SELECT count(1), role FROM users WHERE username='"+usernameField.getText()+"' AND password='"+mdpField.getText()+"'";
		try {
			Statement statement=connectDB.createStatement();
			ResultSet queryResult=statement.executeQuery(verifyLogin);
			
			
			while(queryResult.next()) {
				if(queryResult.getInt(1)==1) {
					
					if(queryResult.getString("role").equals("secretaire")) {
						dashboardSecretaire();
					}else if(queryResult.getString("role").equals("admin")){
						dashboardAdmin();
					}else {
						dashboardCaissier();
					}
					
					
					usernameField.setText("");
					mdpField.setText("");
				}else {
					loginMessageLabel.setText("Invalid username ou mot de passe");
					usernameField.setText("");
					mdpField.setText("");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dashboardSecretaire() throws IOException{
		Stage stage=(Stage) connectButton.getScene().getWindow();
		stage.close();
		
		  //Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class
                .getResource("view/secretaire.fxml"));
        dashboardSecretaireLayout = (BorderPane) loader.load();
       
        //Show the scene containing the root layout.
        Scene scene = new Scene(dashboardSecretaireLayout);
        stage.setScene(scene);
        
		stage.show();
	}
	
	public void dashboardAdmin() throws IOException{
		Stage stage=(Stage) connectButton.getScene().getWindow();
		stage.close();
		
		  //Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class
                .getResource("view/admin.fxml"));
        dashboardAdminLayout = (BorderPane) loader.load();
       
        //Show the scene containing the root layout.
        Scene scene = new Scene(dashboardAdminLayout);
        stage.setScene(scene);
        
        
		stage.show();
	}
	
	public void dashboardCaissier() throws IOException{
		Stage stage=(Stage) connectButton.getScene().getWindow();
		stage.close();
		
		  //Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class
                .getResource("view/caissier.fxml"));
        dashboardCaissierLayout = (BorderPane) loader.load();
       
        //Show the scene containing the root layout.
        Scene scene = new Scene(dashboardCaissierLayout);
        stage.setScene(scene);
        
		stage.show();
	}
	
	
	
	
}
