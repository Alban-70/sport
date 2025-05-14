package controller;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import model.UtilisateurDAO;
import view.UtilisateurConnexionView;
import view.UtilisateurInscriptionView;
import view.homeView;

import java.util.Optional;


public class UtilisateurPathToInscription {

    private String mail;
    private String prenom;

    private UtilisateurConnexionView utilisateurView;
    private UtilisateurInscriptionView utilisateurInscriptionView;
    private homeView view;
    private UtilisateurDAO utilisateurDAO;

    /**
     * Constructeur de la classe qui instancie aussi la vue de connexion UtilisateurConnexionView et qui affiche la vue
     */
    public UtilisateurPathToInscription() {}

    public void initConnexionView() {
        utilisateurView = new UtilisateurConnexionView(this);
        utilisateurView.display();
    }


    /**
     * Instancie la vue d'inscription, le modèle pour la base de données. Initialise la vue d'inscription et appelle la méthode d'inscription
     */
    public void goToInscription() {
        utilisateurInscriptionView = new UtilisateurInscriptionView();
        utilisateurDAO = new UtilisateurDAO(this);
        utilisateurInscriptionView.initUtilisateurView(); // Initialiser la vue d'inscription
    }


    /**
     * Passer de la page de connexion à la page d'inscription et vérifier si le profil existe déjà
     * @param fermerUtilisateurView
     */
    public void handleSignUp(boolean fermerUtilisateurView) {
        if (utilisateurView != null) {
            if (fermerUtilisateurView) {
                utilisateurView.getUserDetails().close();
            }
        }
        goToInscription();
        utilisateurInscriptionView.getUserSignUp().close(); // Ferme la vue d'inscription
    }

    /**
     * Passer de la page de connexion à la page principale
     */
    public void handleLogin() {
        mail = utilisateurView.getTfMail().getText().trim();
        prenom = utilisateurView.getTfPrenom().getText().trim();

        Session.setUser(mail, prenom);

        Optional<ButtonType> result = utilisateurView.getResult();
        if (result.isPresent() && result.get() == utilisateurView.getLogin()) {
            utilisateurView.getUserDetails().close();
            view = new homeView(this);
        }
    }

    //Getters
    public Label getErrorLabel() {
        return utilisateurInscriptionView.getErrorLabel();
    }

    public String getTfEmail() {
        return mail;
    }

    public String getTfPrenom() {
        return prenom;
    }
}

