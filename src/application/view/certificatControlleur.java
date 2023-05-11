package application.view;

import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import application.model.Classe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class certificatControlleur implements Initializable{

    @FXML
    private TextField txt_search;

    @FXML
    private TextField txt_prenom;

    @FXML
    private TextField nom;

    @FXML
    private TextField nomsClasse;

    @FXML
    private TextField nomSection;

    @FXML
    private TextField genre;

    @FXML
    private TextField dateString;
    
    @FXML
    private TextField txt_lieu;

    @FXML
    
    private boolean rechercheEffectuee = false;
    
   public void searchApprenant() throws Exception {
        String sql = "SELECT nom, prenom, dateNaissance, lieuNaissance,genre, classe, section from apprenantprofil WHERE ID ='"
                + txt_search.getText() + "'";
        try {
            Connection connection = DataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                nom.setText(resultSet.getString("nom"));
                txt_prenom.setText(resultSet.getString("prenom"));
                java.sql.Date date = resultSet.getDate("dateNaissance");
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dateStringValue = formatter.format(date);
                dateString.setText(dateStringValue);
                txt_lieu.setText(resultSet.getString("lieuNaissance"));
                genre.setText(resultSet.getString("genre"));
                nomsClasse.setText(resultSet.getString("classe"));
                nomSection.setText(resultSet.getString("section"));
            }
        
                else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur de recherche");
                    alert.setHeaderText("Aucun résultat");
                    alert.setContentText("Impossible de certifier cet apprenant");
                    alert.show();
                }
                
            }catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

    @FXML
    private Button certifierApprenant;
    @FXML
    void certifierApprenantOnAction(ActionEvent event) {
        try {
            String filename = nom.getText() +""+ txt_prenom.getText()+"certificat_de_scolarite.pdf";
            Document document = new Document();

             
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD | Font.UNDERLINE);
            Paragraph title = new Paragraph("CERTIFICAT DE SCOLARITÉ\n\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Paragraph dateParagraph = new Paragraph("Fait à Dakar, le " + formatter.format(date) + "\n\n\n\n");
            dateParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(dateParagraph);

            Font corpsFont = new Font(Font.FontFamily.HELVETICA, 12);
            Paragraph corps = new Paragraph("Je, soussigné(e), Directeur/Directrice de l'établissement scolaire XYZ, certifie que ", corpsFont);
            corps.add(new Chunk(nom.getText() + "  " + txt_prenom.getText(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            corps.add(" est bien inscrit(e) au sein de mon établissement: \n\n");
            corps.setAlignment(Element.ALIGN_LEFT);
            document.add(corps);

            String nomEleve = nom.getText() + " " + txt_prenom.getText();
            String classeEleve = nomsClasse.getText() + " à la section  " + nomSection.getText();
            String lieuNaissance = txt_lieu.getText();
            String formattedDate = dateString.getText();
            Paragraph informationsEleve = new Paragraph("Nom et prénom : " + nomEleve + "\nClasse : " + classeEleve + "\nDate et lieu de naissance : " + formattedDate + " à " + lieuNaissance + "\nAnnée scolaire : 2022-2023\n\n\n");
            informationsEleve.setAlignment(Element.ALIGN_LEFT);
            document.add(informationsEleve);
            
            Paragraph signature = new Paragraph("Signature :");
            signature.setAlignment(Element.ALIGN_RIGHT);
            document.add(signature);

            document.close();
         
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setHeaderText("Certificat créé");
            successAlert.setContentText("Le certificat de scolarité a été créé avec succès.");
            successAlert.showAndWait();
            resetField();
        } catch (Exception e) {
            System.err.println(e);
        }  
    }
    public void resetField() {
    	this.txt_search.setText("");
    	this.txt_prenom.setText("");
    	this.txt_lieu.setText("");
    	this.nom.setText("");
    	this.nomSection.setText("");
    	this.nomsClasse.setText("");
    	this.genre.setText("");
    	this.dateString.setText("");
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		 // Désactiver l'édition des champs au démarrage
        txt_prenom.setEditable(false);
        nom.setEditable(false);
        nomsClasse.setEditable(false);
        nomSection.setEditable(false);
        genre.setEditable(false);
        dateString.setEditable(false);
        txt_lieu.setEditable(false);
	}
}
