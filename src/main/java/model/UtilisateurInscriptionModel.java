package model;

public class UtilisateurInscriptionModel extends UtilisateurConnexionModel {

    private static final String HEADER_TEXT = "Veuillez remplir le formulaire d'inscription";
    private static final String BUTTON_SIGN_UP = "S'inscrire";

    private static final String LABEL_AGE = "Age";
    private static final String LABEL_MAIL = "Mail";
    private static final String LABEL_TAILLE = "Taille";
    private static final String LABEL_POIDS = "Poids";
    private static final String LABEL_OBJECTIF = "Objectif";
    private static final String LABEL_NB_JOURS = "NbJours";

    private static final String LABEL_ERROR = "Erreur, le mail est déjà utilisé";

    private static final String HOMME = "Homme";
    private static final String FEMME = "Femme";
    private static final String SEXE = "Sexe";

    // Getters spécifiques à l'inscription
    public String getHeaderText() {
        return HEADER_TEXT;
    }

    public String getButtonSignUp() {
        return BUTTON_SIGN_UP;
    }

    public String getLabelAge() {
        return LABEL_AGE;
    }

    public String getLabelMail() {
        return LABEL_MAIL;
    }

    public String getLabelTaille() {
        return LABEL_TAILLE;
    }

    public String getLabelPoids() {
        return LABEL_POIDS;
    }

    public String getLabelObjectif() {
        return LABEL_OBJECTIF;
    }

    public String getLabelNbJours() {
        return LABEL_NB_JOURS;
    }

    public String getLabelError() {
        return LABEL_ERROR;
    }

    public String getHomme() {
        return HOMME;
    }

    public String getFemme() {
        return FEMME;
    }

    public String getSexe() {
        return SEXE;
    }
}
