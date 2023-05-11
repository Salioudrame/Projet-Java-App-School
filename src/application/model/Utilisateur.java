package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.StringProperty;

public class Utilisateur {
	public int idUser;
	public String username;
	public String password;
	public String role;
	
	
	
	
	
	public Utilisateur(int idUser, String username, String password, String role) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
