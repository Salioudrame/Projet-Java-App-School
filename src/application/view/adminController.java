package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class adminController implements Initializable {
	@FXML
	private BorderPane bp;
	@FXML
	private AnchorPane ap;
	@FXML
	private Button deconnexion;
	@FXML
	private BorderPane loginLayout;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	// Event Listener on Button.onMouseClicked
	@FXML
	public void utilisateur(MouseEvent event) {
		loadPage("utilisateur");
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void echeancier(MouseEvent event) {
		loadPage("echeancier");
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void rubriques(MouseEvent event) {
		loadPage("rubriques");
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void etats(MouseEvent event) {
		loadPage("etats");
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void operations(MouseEvent event) {
		loadPage("operations");
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void reductions(MouseEvent event) {
		loadPage("reductions");
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void certificat(MouseEvent event) {
		loadPage("certificat");
	}
	@FXML
	public void statistiques(MouseEvent event) {
		loadPage("statistiques");
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void deconnexion(MouseEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Deconnexion");
	    alert.setHeaderText("Etes vous sur de vouloir vous deconnecter ?");
		Stage stage=(Stage) deconnexion.getScene().getWindow();
		
		
		Optional<ButtonType> result =alert.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK) {
			stage.close();
			 //Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class
	                .getResource("view/login.fxml"));
	        try {
				loginLayout = (BorderPane) loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	        //Show the scene containing the root layout.
	        Scene scene = new Scene(loginLayout);
	        stage.setScene(scene);
	        
			stage.show();}
	}
	private void loadPage(String page) {
		Parent root=null;
		try {
			root=FXMLLoader.load(getClass().getResource(page+".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bp.setCenter(root);
		
	}
	
}
