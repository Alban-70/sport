package model;

public class challengeModel extends StatistiquesModel{
    private String nom_challenge;
    private String categorie;
    private int idChallenges ;

    private static final String VALIDER = "Valider";
    private static final String COMMENCER = "Commencer";

    public challengeModel(int idChallenges, String nom, String categorie) {
        this.idChallenges = idChallenges;
        this.nom_challenge = nom;
        this.categorie = categorie;
    }

    public challengeModel() {}

    public int getIdChallenges() {
        return idChallenges;
    }

    public String getNom_challenge() {
        return nom_challenge;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getValider() {
        return VALIDER;
    }

    public String getCommencer() {
        return COMMENCER;
    }
}
