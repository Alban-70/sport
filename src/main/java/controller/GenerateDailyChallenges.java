package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.challengeModel;
import model.dailyChallengeModel;
import model.initImage;
import model.UtilisateurDAO;
import view.challengesView;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class GenerateDailyChallenges {

    private static final String DATE_FILE = "last_execution_date.txt"; // Fichier pour stocker la date de la dernière exécution
    private List<Integer> listRandom;
    private Map<String, HBox> mapChallenges = new HashMap<>();// Permet de mémoriser chaque HBox

    private List<challengeModel> challengeList;
    private initImage initImage;
    private UtilisateurDAO utilisateurDAO;
    private challengesView challengesView;
    private challengeModel challengeModel;
    private dailyChallengeModel dailyChallengeModel;

    /**
     * Création du constructeur qui va générer 3 challenges aléatoires par jour et
     * les afficher dans le bon vbox
     * @param challengesView
     */
    public GenerateDailyChallenges(challengesView challengesView) {
        // Initialiser les instances des classes
        this.challengesView = challengesView;
        utilisateurDAO = new UtilisateurDAO();
        challengeList = utilisateurDAO.getChallenges();
        initImage = new initImage();
        challengeModel = new challengeModel();
        dailyChallengeModel = new dailyChallengeModel();

        // Vérifier si la tâche a déjà été effectuée aujourd'hui
        LocalDate lastExecutionDate = readLastExecutionDate();
        LocalDate today = LocalDate.now();

        if (lastExecutionDate == null || !lastExecutionDate.isEqual(today)) {
            // Si la dernière date d'exécution n'est pas aujourd'hui, exécuter le bloc
            generateRandomChallenges();

            // Mettre à jour la date de la dernière exécution dans le fichier
            writeLastExecutionDate(today);
        }
        afficheChallenges(1, "");
        afficheChallenges(2, "");
        afficherChallengesCompletes(3);
    }

    /**
     * Génère les challenges aléatoirement
     */
    public void generateRandomChallenges() {
        listRandom = new ArrayList<>();
        Random indiceRandom = new Random();

        int userId = utilisateurDAO.getUserID(Session.getMail(), Session.getPrenom());

        if (utilisateurDAO.countInDailyChallenge(0) >= 3 ){
            utilisateurDAO.deleteInDailyChallenge(userId);
        }

        while (listRandom.size() < 3) {
            int randomNumber = indiceRandom.nextInt(challengeList.size());

            if (!randomIsAlreadyChoose(randomNumber)){
                listRandom.add(randomNumber);
                // Ajouter l'élément aléatoire à hbEnCours (par exemple à un VBox ou HBox, selon ton code)
                challengeModel challenge = challengeList.get(randomNumber);
                utilisateurDAO.insertDailyChallenge(
                        challenge.getIdChallenges(),
                        challenge.getNom_challenge(),
                        Session.getMail(),
                        Session.getPrenom()
                );
            }
        }

    }

    /**
     * Vérifie si le challenge est déjà choisi (pour ne pas avoir de doublon)
     * @param newNumber
     * @return isChoose (true or flase)
     */
    public boolean randomIsAlreadyChoose(int newNumber) {
        return listRandom.contains(newNumber); // Vérifie si le nombre est déjà dans la liste
    }

    /**
     * Lis la date de la dernière modification du fichier pour vérifier s'il faut générer de
     * nouveau =des challenges (changement de jour)
     * @return la date
     */
    public LocalDate readLastExecutionDate() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATE_FILE))) {
            String dateString = reader.readLine();
            if (dateString != null) {
                return LocalDate.parse(dateString);
            }
        } catch (IOException e) {
            // Si le fichier n'existe pas ou est vide, une exception est lancée et attrapée ici
            System.out.println("Pas de fichier trouvé, première exécution.");
        }
        return null; // Si le fichier n'existe pas ou est vide
    }

    /**
     * Écrit la date quand on génère les nouveaux challenges
     * @param date
     */
    public void writeLastExecutionDate(LocalDate date) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATE_FILE))) {
            writer.write(date.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appelle les fonctions qui vont afficher les elements dans leur vbox respectifs
     * pour les challenges
     * @param etat
     * @param nomChallenge
     */
    public void afficheChallenges(int etat, String nomChallenge) {
        int userId = utilisateurDAO.getUserID(Session.getMail(), Session.getPrenom());

        if (etat == 1){         // État 1 = Non Complétés
            afficherChallengesNonCompletes(userId);
        }
        else if (etat == 2) {        // État 2 = En Cours
            afficherChallengesEnCours(userId);
        } else if (etat == 3) {         // État 3 == Complétés
            validerChallenge(userId, nomChallenge);
        }
    }

    /**
     * Initialise et affiche les éléments du vbox En Cours
     * @param userId
     */
    public void afficherChallengesEnCours(int userId) {
        List<dailyChallengeModel> dailyChallenges = utilisateurDAO.selectDailyChallenge(userId, 2);

        for (dailyChallengeModel daily : dailyChallenges) {
            String nomChallenge = daily.getNomChallenge();

            StackPane icon = initImage.getImageView30(
                    utilisateurDAO.selectImageName(nomChallenge),
                    Color.valueOf(utilisateurDAO.selectcolorName(nomChallenge))
            );
            Label label = challengesView.createLabelWithIcon(icon, nomChallenge);
            label.setWrapText(true);
            label.setMaxWidth(200);


            Button btnValider = challengesView.createButton(challengeModel.getValider());

            if (utilisateurDAO.besoinMinuteur(userId, nomChallenge)) {
                btnValider.setText(challengeModel.getCommencer());
            }
            HBox hbox = challengesView.createHbox(label, btnValider);

            mapChallenges.put(nomChallenge, hbox);
            challengesView.getVbEnCours().getChildren().add(hbox);

            btnValider.setOnAction(e -> afficheChallenges(3, nomChallenge));
        }
    }

    /**
     * Permet de faire passer un élément du vbox En Cours au VBox Complétés quand
     * on appuie sur le bouton Valider
     * @param userId
     * @param nomChallenge
     */
    public void validerChallenge(int userId, String nomChallenge) {
        HBox hbox = mapChallenges.get(nomChallenge);
        if (hbox != null && hbox.getParent() != challengesView.getVbCompletes()) {
            if (utilisateurDAO.besoinMinuteur(userId, nomChallenge)) {
                if (hbox.getChildren().size() > 2) {
                    hbox.getChildren().set(2, createLabelTimer(getDureeByNomChallenge(nomChallenge)));
                } else {
                    hbox.getChildren().add(createLabelTimer(getDureeByNomChallenge(nomChallenge)));
                }
            } else {
                hbox.getChildren().set(2, initImage.getImageView30("valid", Color.GREEN));

                challengesView.getVbCompletes().getChildren().add(hbox);
            }
            utilisateurDAO.updateToCompletes(userId, nomChallenge);
        }
    }

    /**
     * Initialise et affiche les éléments du vbox Complétés
     * @param etat
     */
    public void afficherChallengesCompletes(int etat) {
        ObservableList<Node> children = challengesView.getVbCompletes().getChildren();
        if (children.size() > 1) {
            children.remove(1, children.size()); // Supprime de l'indice 1 jusqu'à la fin (exclu)
        }

        int userId = utilisateurDAO.getUserID(Session.getMail(), Session.getPrenom());
        List<dailyChallengeModel> dailyChallenges = utilisateurDAO.selectDailyChallenge(userId, etat);

        for (dailyChallengeModel daily : dailyChallenges) {
            String nomChallenge = daily.getNomChallenge();

            StackPane icon = initImage.getImageView30(
                    utilisateurDAO.selectImageName(nomChallenge),
                    Color.valueOf(utilisateurDAO.selectcolorName(nomChallenge))
            );
            Label label = challengesView.createLabelWithIcon(icon, nomChallenge);
            label.setWrapText(true);
            label.setMaxWidth(200);

            HBox hbox = challengesView.createHbox(label, initImage.getImageView30("valid", Color.TRANSPARENT));

            challengesView.getVbCompletes().getChildren().add(hbox);

        }
    }

    public void afficherChallengesNonCompletes(int userId) {
        List<dailyChallengeModel> dailyChallenges = utilisateurDAO.selectDailyChallenge(userId, 1);

        for (dailyChallengeModel daily : dailyChallenges) {
            String nomChallenge = daily.getNomChallenge();

            StackPane icon = initImage.getImageView30(
                    utilisateurDAO.selectImageName(nomChallenge),
                    Color.valueOf(utilisateurDAO.selectcolorName(nomChallenge))
            );
            Label label = challengesView.createLabelWithIcon(icon, nomChallenge);
            label.setWrapText(true);
            label.setMaxWidth(200);

            HBox hBox = challengesView.createHbox(label, initImage.getImageView30("close", Color.TRANSPARENT));

            challengesView.getVbNonCompletes().getChildren().add(hBox);
        }
    }

    /**
     * Initialise un timer qui sera de initialSeconds et qui décrémente de 1 toutes les secondes
     * @param initialSeconds
     * @return le label initialisé
     */
    public Label createLabelTimer(int initialSeconds) {
        Label label = new Label();
        label.getStyleClass().add("label-timer");
        // Utilisation d'un tableau pour permettre la modification
        final int[] totalSeconds = {initialSeconds};
        // Création du Timeline avant le KeyFrame
        Timeline timeline = new Timeline();
        // Création du KeyFrame pour chaque seconde
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            totalSeconds[0]--;  // Décrémentation du temps restant
            // Mise à jour du label avec le temps formaté
            label.setText(formatTime(totalSeconds[0]));

            // Ajout de la classe "warning" si le temps restant est inférieur ou égal à 10 secondes
            if (totalSeconds[0] <= 10) {
                label.getStyleClass().add("warning"); // Appliquer la classe warning
            }
            // Lorsque le timer atteint 0, afficher "Temps écoulé!" et arrêter le timeline
            if (totalSeconds[0] <= 0) {
                label.setText("Temps écoulé !");
                timeline.stop(); // Arrêter proprement le Timeline ici
            }
        });
        // Ajouter le KeyFrame à la timeline
        timeline.getKeyFrames().add(keyFrame);
        // Répéter le KeyFrame à l'infini
        timeline.setCycleCount(Timeline.INDEFINITE);
        // Démarrer le timer
        timeline.play();
        return label;
    }

    /**
     * Permet de formater le temps des secondes en "00:00"
     * @param seconds
     * @return le nouveau format
     */
    public String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }


    public int getDureeByNomChallenge(String nomRecherche) {
        int userId = utilisateurDAO.getUserID(Session.getMail(), Session.getPrenom());
        List<dailyChallengeModel> dailyChallenges = utilisateurDAO.selectDailyChallenge(userId, 2); // ou 3 selon l'état

        for (dailyChallengeModel daily : dailyChallenges) {
            if (daily.getNomChallenge().equals(nomRecherche)) {
                return daily.getDuree() * 60;
            }
        }
        return -1; // ou une autre valeur par défaut si non trouvé
    }


}
