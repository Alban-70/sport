package view;

import controller.UtilisateurInscription;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.UtilisateurInscriptionModel;

import java.util.Optional;

public class UtilisateurInscriptionView {

    private Dialog<ButtonType> userSignUp;
    private Optional<ButtonType> result;
    private TextField tfNom, tfPrenom, tfAge, tfMail, tfTaille, tfPoids, tfObjectif, tfNbJours;
    private ButtonType signUp;
    private GridPane grid;
    private Label errorLabel;
    private ComboBox<String> cbSexe;

    // Instance de UtilisateurInscriptionModel
    private UtilisateurInscriptionModel utilisateurModel;
    private UtilisateurInscription inscription;

    // Pour afficher la page de connexion à nouveau après s'être inscrit
    private UtilisateurConnexionView utilisateurConnexionView;

    /**
     * Constructeur de la classe qui instancie le model qui correspond
     */
    public UtilisateurInscriptionView() {
        utilisateurModel = new UtilisateurInscriptionModel();
    }

    /**
     * Initialise la vue
     */
    public void initUtilisateurView() {
        userSignUp = new Dialog<>();
        userSignUp.setTitle(utilisateurModel.getTitleInscription());
        userSignUp.setHeaderText(utilisateurModel.getHeaderText());
        userSignUp.setWidth(600);
        userSignUp.setHeight(400);

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);

        initTextField();

        cbSexe = new ComboBox<>();
        cbSexe.getItems().addAll(utilisateurModel.getHomme(), utilisateurModel.getFemme());
        cbSexe.setPromptText(utilisateurModel.getSexe());

        initGridPane();

        signUp = new ButtonType(utilisateurModel.getButtonSignUp());

        userSignUp.getDialogPane().setContent(grid);
        userSignUp.getDialogPane().getButtonTypes().add(signUp);

        result = userSignUp.showAndWait();

        handleSignUp();
    }


    /**
     * Initialise les textField
     */
    public void initTextField() {
        tfNom = new TextField();
        tfPrenom = new TextField();
        tfAge = new TextField();
        tfMail = new TextField();
        tfTaille = new TextField();
        tfPoids = new TextField();
        tfObjectif = new TextField();
        tfNbJours = new TextField();
    }

    /**
     * Initialise le gridPane
     */
    public void initGridPane() {
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label(utilisateurModel.getLabelNom()), 0, 0);
        grid.add(tfNom, 1, 0);
        grid.add(new Label(utilisateurModel.getLabelPrenom()), 0, 1);
        grid.add(tfPrenom, 1, 1);
        grid.add(new Label(utilisateurModel.getLabelMail()), 0, 2);
        grid.add(tfMail, 1, 2);
        grid.add(new Label(utilisateurModel.getLabelAge()), 0, 3);
        grid.add(tfAge, 1, 3);
        grid.add(new Label(utilisateurModel.getSexe()), 0, 4);
        grid.add(cbSexe, 1, 4);
        grid.add(new Label(utilisateurModel.getLabelTaille()), 0, 5);
        grid.add(tfTaille, 1, 5);
        grid.add(new Label(utilisateurModel.getLabelPoids()), 0, 6);
        grid.add(tfPoids, 1, 6);
        grid.add(new Label(utilisateurModel.getLabelObjectif()), 0, 7);
        grid.add(tfObjectif, 1, 7);
        grid.add(new Label(utilisateurModel.getLabelNbJours()), 0, 8);
        grid.add(tfNbJours, 1, 8);
        grid.add(errorLabel, 1, 9);
        grid.setAlignment(Pos.CENTER);
    }

    /**
     * Inscrit l'utilisateur et renvoie sur la page de connexion
     */
    public void handleSignUp() {
        if (result != null && result.isPresent() && result.get() == signUp) {
            inscription();
            getUserSignUp().close();
            redirectToLogin();
        } else {
            errorLabel.setText("Veuillez remplir tous les champs.");
            errorLabel.setVisible(true);
        }
    }


    /**
     * Ferme la fenêtre d'inscription et renvoie sur la page de connexion
     */
    public void redirectToLogin() {
        if (utilisateurConnexionView == null) {
            utilisateurConnexionView = new UtilisateurConnexionView();
        }
    }

    // Getters
    public Dialog<ButtonType> getUserSignUp() {
        return userSignUp;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Optional<ButtonType> getResult() {
        return result;
    }

    public ButtonType getSignUp() {
        return signUp;
    }

    public String getNom() {
        return tfNom.getText().trim();
    }

    public String getPrenom() {
        return tfPrenom.getText().trim();
    }

    public String getMail() {
        return tfMail.getText().trim();
    }

    public int getAge() {
        try {
            return Integer.parseInt(tfAge.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getSexe() {
        return cbSexe.getValue();
    }

    public double getTaille() {
        try {
            return Double.parseDouble(tfTaille.getText().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public double getPoids() {
        try {
            return Double.parseDouble(tfPoids.getText().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public String getObjectif() {
        return tfObjectif.getText().trim();
    }

    public int getNbJours() {
        try {
            return Integer.parseInt(tfNbJours.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    /**
     * ...
     */
    public void canSetVisible() {
        errorLabel.setText("Profil déjà existant. Réessayez !");
        errorLabel.setVisible(true);
    }

    /**
     * Inscrit l'utilisateur
     */
    public void inscription() {
        inscription = new UtilisateurInscription(this);
        inscription.inscription();
    }
}
