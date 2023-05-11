package application.view;

import java.awt.event.MouseEvent;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BulletinsController {

	@FXML
	private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button1;
    
    @FXML
    private AnchorPane ap;
    
    @FXML
    void Certifier(ActionEvent event) 
    {
    	try 
    	{
    		Parent root = FXMLLoader.load(getClass().getResource("certificat.fxml"));
    		Scene scene = new Scene(root); 
    		Stage stage = new Stage();
    		stage.setScene(scene); 
    		stage.show(); 
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }


    @FXML
    void AfficherBulletinsElementaire(ActionEvent event) 
    {
    	
    	//loadPage("BullettinsElementaires");
    	try 
    	{
    		Parent root = FXMLLoader.load(getClass().getResource("BullettinsElementaires.fxml"));
    		Scene scene = new Scene(root); 
    		Stage stage = new Stage();
    		stage.setScene(scene); 
    		stage.show(); 
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void AfficherBulletinsCollege(ActionEvent event) 
    {
    	try 
    	{
    		Parent root = FXMLLoader.load(getClass().getResource("Bulletinscolleges.fxml"));
    		Scene scene = new Scene(root); 
    		Stage stage = new Stage();
    		stage.setScene(scene); 
    		stage.show(); 
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    @FXML
    void AfficherBulletinsCAPBEP(ActionEvent event) 
    {
    	try 
    	{
    		Parent root = FXMLLoader.load(getClass().getResource("Bullettins_CAP_BEP.fxml"));
    		Scene scene = new Scene(root); 
    		Stage stage = new Stage();
    		stage.setScene(scene); 
    		stage.show(); 
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }


}
