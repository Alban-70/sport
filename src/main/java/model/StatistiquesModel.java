package model;

public class StatistiquesModel extends homeModel {


    private static final String ACTIVITES = "Durée d'activités physiques par jour";
    private static final String ENTRAINEMENT = "Entraînement";
    private static final String NON_COMPLETES = "Non complétés";
    private static final String COMPLETES = "Complétés";
    private static final String EN_COURS = "En cours";
    private static final String LUNDI = "Lun";
    private static final String MARDI = "Mar";
    private static final String MERCREDI = "Mer";
    private static final String JEUDI = "Jeu";
    private static final String VENDREDI = "Ven";
    private static final String SAMEDI = "Sam";
    private static final String DIMANCHE = "Dim";


    //Getters
    public String getActivites() {
        return ACTIVITES;
    }

    public String getEntrainement() {
        return ENTRAINEMENT;
    }

    public String getNonCompletes() {
        return NON_COMPLETES;
    }

    public String getCompletes() {
        return COMPLETES;
    }

    public String getEncours() {
        return EN_COURS;
    }

    public String getLun() {
        return LUNDI;
    }

    public String getMar() {
        return MARDI;
    }

    public String getMercredi() {
        return MERCREDI;
    }

    public String getJeu() {
        return JEUDI;
    }

    public String getVendredi() {
        return VENDREDI;
    }

    public String getSamedi() {
        return SAMEDI;
    }

    public String getDimanche() {
        return DIMANCHE;
    }

}