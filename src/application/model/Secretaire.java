package application.model;

import java.util.List;

public class Secretaire extends Utilisateur {

    public Secretaire(int idUser, String username, String password, String role) {
		super(idUser, username, password, role);
		// TODO Auto-generated constructor stub
	}

	private List<Classe> classes;
    private List<Apprenant> apprenants;
     

    public List<Classe> getClasses() {
        return classes;
    }

    public void setClasses(List<Classe> classes) {
        this.classes = classes;
    }

    public List<Apprenant> getApprenants() {
        return apprenants;
    }

    public void setApprenants(List<Apprenant> apprenants) {
        this.apprenants = apprenants;
    }

    

    // Ajoutez d'autres méthodes spécifiques de la classe Secretaire ici si nécessaire

}
