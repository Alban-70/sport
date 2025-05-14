package view;

import controller.UtilisateurPathToInscription;
import model.UtilisateurConnexionModel;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class UtilisateurConnexionView {

    private Dialog<ButtonType> userDetails;
    private Optional<ButtonType> result;
    private TextField tfMail, tfPrenom;
    private ButtonType login, signUp;

    private final UtilisateurConnexionModel utilisateur = new UtilisateurConnexionModel();
    private final UtilisateurPathToInscription utilisateurPathToInscription;

    /**
     * Constructeur de la classe qui instancie aussi UtilisateurPathToInscription
     */
    public UtilisateurConnexionView() {
        utilisateurPathToInscription = new UtilisateurPathToInscription(); // Lien avec le contrôleur
        utilisateurPathToInscription.initConnexionView();
    }

    /**
     * Autre constructeur qui prends en paramètre un objet UtilisateurPathToInscription
     * @param utilisateurPathToInscription
     */
    public UtilisateurConnexionView(UtilisateurPathToInscription utilisateurPathToInscription) {
        this.utilisateurPathToInscription = utilisateurPathToInscription;
    }

    /**
     * Initialise la vue de la page de dialogue de connexion et attend la réponse de l'utilisateur pour savoir
     * s'il faut se connecter ou s'inscrire
     */
    public void display() {
        initUtilisateurView();
        handleUserResponse(); // Gérer la réponse utilisateur après le showAndWait
    }

    /**
     * Initialise la vue de la page de dialogue de connexion
     */
    private void initUtilisateurView() {
        userDetails = new Dialog<>();
        userDetails.setTitle(utilisateur.getTitleConnexion());
        userDetails.setHeaderText(utilisateur.getHeaderConnexion());
        userDetails.setWidth(600);
        userDetails.setHeight(300);

        GridPane grid = new GridPane();
        tfMail = new TextField();
        tfPrenom = new TextField();

        signUp = new ButtonType(utilisateur.getBoutonInscrire());
        login = new ButtonType(utilisateur.getBoutonSeConnecter());

        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label(utilisateur.getLabelEmail()), 0, 0);
        grid.add(tfMail, 1, 0);
        grid.add(new Label(utilisateur.getLabelPrenom()), 0, 1);
        grid.add(tfPrenom, 1, 1);
        grid.setAlignment(Pos.CENTER);

        userDetails.getDialogPane().setContent(grid);
        userDetails.getDialogPane().getButtonTypes().addAll(login, signUp);

        result = userDetails.showAndWait(); // Attente de l'input de l'utilisateur
    }

    /**
     * Attend la réponse de l'utilisateur (connexion ou inscription)
     */
    public void handleUserResponse() {
        if (result != null && result.isPresent()) {
            if (result.get() == signUp) {
                utilisateurPathToInscription.handleSignUp(true); //Le contrôller gère l'inscription
            } else if (result.get() == login) {
                utilisateurPathToInscription.handleLogin(); //Le contrôller gère la connexion
            } else {
                System.out.println("Action inconnue ou non prise en charge.");
            }
        } else {
            System.out.println("La boîte de dialogue a été fermée sans sélection.");
        }
    }

    // Getters
    public Dialog<ButtonType> getUserDetails() {
        return userDetails;
    }

    public Optional<ButtonType> getResult() {
        return result;
    }

    public TextField getTfMail() {
        return tfMail;
    }

    public TextField getTfPrenom() {
        return tfPrenom;
    }

    public ButtonType getLogin() {
        return login;
    }

    public ButtonType getSignUp() {
        return signUp;
    }
}
