package controller;

public class Session {
    private static String mail;
    private static String prenom;

    public static void setUser(String m, String p) {
        mail = m;
        prenom = p;
    }

    public static String getMail() { return mail; }
    public static String getPrenom() { return prenom; }
}
