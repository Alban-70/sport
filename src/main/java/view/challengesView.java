package view;

import controller.GenerateDailyChallenges;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;
import model.UtilisateurDAO;
import model.challengeModel;
import model.initImage;
import java.util.List;

public class challengesView {

    private VBox vbox, vbEnCours, vbCompletes, vbNonCompletes;
    private HBox hbox, hbEnCours, hbCompletes, hbNonCompletes;
    private Label lChallenge, lEnCours, lCompletes, lNonCompletes;

    //Instance des classes
    private List<challengeModel> challengeList;
    private challengeModel model;
    private initImage initImage;
    private UtilisateurDAO utilisateurDAO;
    private GenerateDailyChallenges generateDailyChallenges;

    public challengesView() {
        model = new challengeModel();
        utilisateurDAO = new UtilisateurDAO();
        challengeList = utilisateurDAO.getChallenges();
        initImage = new initImage();

        initView();
        generateDailyChallenges = new GenerateDailyChallenges(this);
    }


    public void initView() {
        initLabel();
        initVBoxInHBox();
        initHBox();
        initVBox();
    }

    public void initVBox() {
        vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        vbox.getChildren().addAll(lChallenge, hbox);
    }


    public void initHBox() {
        hbox = new HBox();
        hbox.setSpacing(30);
        hbox.setPadding(new Insets(20, 50, 20, 50));


        // Lier la largeur de chaque VBox à 33% de la largeur de l'HBox
        vbEnCours.prefWidthProperty().bind(hbox.widthProperty().multiply(0.33));
        vbCompletes.prefWidthProperty().bind(hbox.widthProperty().multiply(0.33));
        vbNonCompletes.prefWidthProperty().bind(hbox.widthProperty().multiply(0.33));

        vbEnCours.setMaxWidth(Double.MAX_VALUE);
        vbCompletes.setMaxWidth(Double.MAX_VALUE);
        vbNonCompletes.setMaxWidth(Double.MAX_VALUE);

        hbox.getChildren().addAll(vbEnCours, vbCompletes, vbNonCompletes);
    }


    public void initVBoxInHBox() {
        vbEnCours = new VBox();
        vbEnCours.setSpacing(10);
        vbEnCours.setPadding(new Insets(10));

        vbCompletes = new VBox();
        vbCompletes.setSpacing(10);
        vbCompletes.setPadding(new Insets(10));

        vbNonCompletes = new VBox();
        vbNonCompletes.setSpacing(30);
        vbNonCompletes.setPadding(new Insets(10, 20, 10, 20));


        vbEnCours.getChildren().addAll(lEnCours);
        vbCompletes.getChildren().addAll(lCompletes);
        vbNonCompletes.getChildren().addAll(lNonCompletes);

        vbEnCours.getStyleClass().add("vbInGP");
        vbCompletes.getStyleClass().add("vbInGP");
        vbNonCompletes.getStyleClass().add("vbInGP");
    }

    public HBox createHbox(Label label, Region region) {
        HBox newHbox = new HBox();
        newHbox.setSpacing(20);
        newHbox.setPadding(new Insets(15));
        newHbox.getStyleClass().add("hbInVBox");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        newHbox.getChildren().addAll(label, spacer, region);
        return newHbox;
    }


    public void initLabel() {
        lEnCours = new Label(model.getEncours());
        lEnCours.setPrefWidth(Double.MAX_VALUE);
        lEnCours.setAlignment(Pos.CENTER);
        lCompletes = new Label(model.getCompletes());
        lCompletes.setPrefWidth(Double.MAX_VALUE);
        lCompletes.setAlignment(Pos.CENTER);
        lNonCompletes = new Label(model.getNonCompletes());
        lNonCompletes.setPrefWidth(Double.MAX_VALUE);
        lNonCompletes.setAlignment(Pos.CENTER);
        lChallenge = new Label(model.getChallenges());
        lChallenge.setPrefWidth(Double.MAX_VALUE);
        lChallenge.setAlignment(Pos.CENTER);

        lEnCours.getStyleClass().add("labelStat");
        lCompletes.getStyleClass().add("labelStat");
        lNonCompletes.getStyleClass().add("labelStat");
        lChallenge.getStyleClass().add("labelStat");
    }

    public Button createButton(String nomButton) {
        Button newButton = new Button(nomButton);
        newButton.getStyleClass().add("btn");

        return newButton;
    }


    public Label createLabelWithIcon(StackPane stackPane, String nom) {
        Label label = new Label(nom, stackPane);
        label.setGraphicTextGap(20);
        label.getStyleClass().add("label15");
        return label;
    }


    //Getters
    public VBox getVBox() {
        return vbox;
    }

    public VBox getVbNonCompletes() {
        return vbNonCompletes;
    }

    public VBox getVbEnCours() {
        return vbEnCours;
    }

    public VBox getVbCompletes() {
        return vbCompletes;
    }

}
