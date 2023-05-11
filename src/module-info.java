module ProjetJavaFinal {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires mysql.connector.java;
	requires jakarta.xml.bind;
	requires itextpdf;
	
	
	opens application to javafx.graphics, javafx.fxml;
	exports application.view to javafx.fxml;
	opens application.view to  javafx.fxml;
	opens application.model to javafx.base;
}
