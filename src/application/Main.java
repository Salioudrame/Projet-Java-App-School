package application;
	
import java.io.IOException;

import application.model.Utilisateur;
import application.view.LoginController;
import application.view.utilisateurController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane loginLayout;
	private ObservableList<Utilisateur> userData= FXCollections.observableArrayList();
	
	@Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("S'cool");
       

        showLoginLayout();

    }
	public Main() {
		//userData.add(new Utilisateur("seydou", "password1", "admin"));
//		utilisateurController controller = loader.getController();
//        controller.setMain(this);
	}
	public ObservableList<Utilisateur> getUserData() {
		return userData;
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	public void showLoginLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("view/login.fxml"));
            loginLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(loginLayout);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            //primaryStage.resizableProperty().setValue(false);
            primaryStage.centerOnScreen();
            // Set the application icon.
            this.primaryStage.getIcons().add(new Image("file:ressources/image/logo.png"));
            
//            LoginController controller = loader.getController();
//            controller.setMain(this);
            
            
            
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
