package model;

import controller.UtilisateurPathToInscription;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    private String challenges, categorie;
    private int idChallenges;

    private Connection conn;

    private DatabaseConnection databaseConnection;
    private UtilisateurPathToInscription utilisateurPathToInscription;

    public UtilisateurDAO(UtilisateurPathToInscription utilisateurPathToInscription) {
        this.utilisateurPathToInscription = utilisateurPathToInscription;
        try{
            databaseConnection = new DatabaseConnection();
            conn = databaseConnection.getConnection();
        } catch (SQLException e){
            System.out.println("Erreur de connexion à la base de données, " + e.getMessage());
        }
    }

    public UtilisateurDAO() {
        try{
            databaseConnection = new DatabaseConnection();
            conn = databaseConnection.getConnection();
        } catch (SQLException e){
            System.out.println("Erreur de connexion à la base de données, " + e.getMessage());
        }
    }

    public boolean profilExiste(String mail) {
        String sql = "SELECT COUNT(*) FROM profil WHERE mail = ?";  // Requête SQL

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);  // Récupère le résultat du COUNT
                    return count > 0;  // Si count > 0, cela signifie que l'utilisateur existe
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;  // Retourne false si une erreur se produit ou l'utilisateur n'existe pas
    }



    public void insertProfil(String nom, String prenom, String mail, int age, String sexe, double taille, double poids, String objectif, int nb_jours) {
        if (!profilExiste(mail)) {
            String sql = "INSERT INTO profil (nom, prenom, mail, age, sexe, taille, poids, objectif, nb_jour) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, mail);
                stmt.setInt(4, age);
                stmt.setString(5, sexe);
                stmt.setDouble(6, taille);
                stmt.setDouble(7, poids);
                stmt.setString(8, objectif);
                stmt.setInt(9, nb_jours);
                stmt.executeUpdate();
                System.out.println("Insertion faite");
            } catch (SQLException e) {
                System.out.println("Erreur d'insertion, " + e.getMessage());
            }
        }
        else {
            utilisateurPathToInscription.handleSignUp(false);
        }
    }

    public boolean connexionProfil(String mail, String prenom) {
        String sql = "SELECT COUNT(*) FROM profil WHERE mail = ? AND prenom = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, mail);
            stmt.setString(2, prenom);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);

                    if (count > 0) {
                        getUserID(mail, prenom);
                        return true;
                    }
                }
            }
        } catch (SQLException e){
            System.out.println("Erreur de connexion au profil, " + e.getMessage());
        }
        return false;
    }

    public int getUserID(String mail, String prenom) {
        String sql = "SELECT id_profil FROM profil WHERE mail = ? AND prenom = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, mail);
            stmt.setString(2, prenom);

            ResultSet rs = stmt.executeQuery(); // Exécute la requête

            if (rs.next()) {
                return rs.getInt("id_profil"); // Récupère l'ID si trouvé
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("pas trouvé");
        return -1;
    }

    public List<challengeModel> getChallenges() {
        List<challengeModel> result = new ArrayList<>();
        String sql = "SELECT challenges.id_challenges, challenges.nom_challenges, categorie.nom_categorie " +
                "FROM challenges " +
                "JOIN categorie ON challenges.categorie_id = categorie.id_categorie";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                idChallenges = rs.getInt("id_challenges");
                challenges = rs.getString("nom_challenges");
                categorie = rs.getString("nom_categorie");
                result.add(new challengeModel(idChallenges, challenges, categorie));
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void insertDailyChallenge(int idChallenge, String nomChallenge, String mail, String prenom) {
        int userId = getUserID(mail, prenom);

        if (userId == -1) {
            System.out.println("Erreur : l'utilisateur n'existe pas dans la base de données.");
            return; // Ne pas insérer le challenge si l'utilisateur n'existe pas
        }

        String sql = "INSERT INTO dailyChallenge (id_daily, nom_challenge, id_challenge, profil_id, etat)" +
                "VALUES (NULL, ?, ?, ?, 2);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, nomChallenge);
            stmt.setInt(2, idChallenge);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }
    }

    public int countInDailyChallenge(int etat) {
        String where = "";
        if (etat == 1) {
            where = "AND etat = 1";
        } else if (etat == 2) {
            where = "AND etat = 2";
        } else if (etat == 3) {
            where = "AND etat = 3";
        }

        String sql = "SELECT COUNT(*) FROM dailyChallenge " +
                " WHERE profil_id = 1 " + where;

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public List<dailyChallengeModel> selectDailyChallenge(int id, int etat) {
        List<dailyChallengeModel> list = new ArrayList<>();
        String sql = "SELECT nom_challenge, challenges.duree FROM dailyChallenge " +
                "JOIN challenges ON dailyChallenge.id_challenge = challenges.id_challenges " +
                "WHERE profil_id = ? AND etat = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.setInt(2, etat);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nomChallenge = rs.getString("nom_challenge");
                int duree = rs.getInt("duree");
                list.add(new dailyChallengeModel(nomChallenge, duree));
            }
        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void deleteInDailyChallenge(int id) {
        String sql = "DELETE FROM dailyChallenge WHERE profil_id = ? AND etat = 2";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public String selectImageName(String nom_challenges) {
        String sql = "SELECT nom_categorie FROM challenges " +
                "JOIN categorie ON challenges.categorie_id = categorie.id_categorie " +
                "WHERE nom_challenges = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, nom_challenges);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom_categorie");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String selectcolorName(String nom_challenges) {
        String sql = "SELECT nom_color FROM challenges " +
                "JOIN categorie ON challenges.categorie_id = categorie.id_categorie " +
                "JOIN color ON categorie.id_color = color.id_color " +
                "WHERE nom_challenges = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, nom_challenges);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom_color");
            }
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public void updateToCompletes(int id_profil, String nomChallenge) {
        String sql = "UPDATE dailyChallenge SET etat = 3 WHERE profil_id = ? AND nom_challenge = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id_profil);
            stmt.setString(2, nomChallenge);
            stmt.executeUpdate();

        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    public boolean besoinMinuteur(int id_profil, String nom_challenge) {
        String sql = "SELECT minuteur FROM dailyChallenge " +
                "JOIN challenges ON dailyChallenge.id_challenge = challenges.id_challenges " +
                "WHERE profil_id = ? AND nom_challenge = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id_profil);
            stmt.setString(2, nom_challenge);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("minuteur") == 1;
            }
        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }

        return false;
    }


}
