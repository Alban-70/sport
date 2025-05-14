package model;

public class dailyChallengeModel {

    private String nomChallenge;
    private int duree;

    public dailyChallengeModel(String nomChallenge, int duree) {
        this.nomChallenge = nomChallenge;
        this.duree = duree;
    }

    public dailyChallengeModel() {}

    public String getNomChallenge() {
        return nomChallenge;
    }

    public int getDuree() {
        return duree;
    }


}
