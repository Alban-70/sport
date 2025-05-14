package model;

public class UtilisateurConnexionModel {

    private static final String TITLE_CONNEXION = "Connexion";
    private static final String HEADER_CONNEXION = "Entrez vos informations";
    private static final String TITLE_INSCRIPTION = "Inscription";
    private static final String HEADER_INSCRIPTION = "Entrez vos informations pour vous inscrire";
    private static final String LABEL_NOM = "Nom ";
    private static final String LABEL_PRENOM = "Prénom ";
    private static final String LABEL_EMAIL = "Email ";
    private static final String ERREUR_EMAIL_UTILISE = "Cet email est déjà utilisé.";
    private static final String BOUTON_INSCRIRE = "S'inscrire";
    private static final String BOUTON_SE_CONNECTER = "Se connecter";



    // Getters
    public String getTitleConnexion() {
        return TITLE_CONNEXION;
    }

    public String getHeaderConnexion() {
        return HEADER_CONNEXION;
    }

    public String getTitleInscription() {
        return TITLE_INSCRIPTION;
    }

    public String getHeaderInscription() {
        return HEADER_INSCRIPTION;
    }

    public String getLabelNom() {
        return LABEL_NOM;
    }

    public String getLabelPrenom() {
        return LABEL_PRENOM;
    }

    public String getLabelEmail() {
        return LABEL_EMAIL;
    }

    public String getErreurEmailUtilise() {
        return ERREUR_EMAIL_UTILISE;
    }

    public String getBoutonInscrire() {
        return BOUTON_INSCRIRE;
    }

    public String getBoutonSeConnecter() {
        return BOUTON_SE_CONNECTER;
    }



}
