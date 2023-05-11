package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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

public class ListeClasseController implements Initializable 
{
	@FXML
	private TableColumn<Classe, String> tab2;

    @FXML
    private TableView<Classe> classeSectionListe;

    @FXML
    private ComboBox<String> listeclasse;

    @FXML
    void SelectSection(ActionEvent event) throws Exception 
    {
        String selectedSection = listeclasse.getValue();
        if (selectedSection == null) {
            return;
        }

        String sql = "SELECT nom_classe FROM sections WHERE nom_section = ?";
        try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, selectedSection);
            ResultSet resultSet = statement.executeQuery();
            ObservableList<Classe> classes = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String nomClasse = resultSet.getString("nom_classe");
                classes.add(new Classe(nomClasse));
            }

            this.classeSectionListe.setItems(classes);
            this.tab2.setCellValueFactory(new PropertyValueFactory("nomClasse"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		 this.listeclasse.getItems().addAll(Classe.getNomsSection());
	}
}
