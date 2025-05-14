package model;

public class homeModel extends UtilisateurInscriptionModel{

    private static final String HOME = " Home";
    private static final String STATISTIQUES = " Statistiques";
    private static final String CHALLENGES = " Challenges";
    private static final String MON_PROFILE = " Mon profile";
    private static final String PARAMETRES = " Paramètres";
    private static final String DAILY_CHALLENGES = "Challenges journaliers";
    private static final int secondsTimer = 30 * 60; // Timer de 30 minutes


    public String getHome() {
        return HOME;
    }

    public String getStatistiques() {
        return STATISTIQUES;
    }

    public String getChallenges() {
        return CHALLENGES;
    }

    public String getMonProfile() {
        return MON_PROFILE;
    }

    public String getParameters() {
        return PARAMETRES;
    }

    public String getDailyChallenges() {
        return DAILY_CHALLENGES;
    }

    public int getSecondsTimer() {
        return secondsTimer;
    }
}
