package view;

import controller.ChangeScene;
import controller.Session;
import controller.UtilisateurPathToInscription;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.UtilisateurDAO;
import model.homeModel;
import model.initImage;

public class homeView extends Stage {

    private Scene scene;
    private BorderPane borderPane;
    private ImageView imageView;
    private StackPane ivHome;
    private StackPane ivStat;
    private StackPane ivProfil;
    private StackPane ivSettings;
    private StackPane ivChallenge;
    private GridPane gpLeftButton, gpCenter, gpStatistics;
    private VBox vbCenter, vbChallenge, vbTrain, vbMin;
    private HBox hbChallenge1, hbChallenge2, hbChallenge3;
    private Button btnHome, btnStat, btnChallenge, btnProfil, btnSettings;
    private Label lStat, lChallenge;
    private ScrollPane scrollPane;

    private UtilisateurDAO utilisateurDAO;
    private UtilisateurPathToInscription utilisateurPathToInscription;
    private homeModel mainModel;
    private ChangeScene changeScene;
    private initImage initImage;


    public homeView(UtilisateurPathToInscription utilisateurPathToInscription) {
        this.utilisateurPathToInscription = utilisateurPathToInscription;
        utilisateurDAO = new UtilisateurDAO(utilisateurPathToInscription);
        mainModel = new homeModel();
        changeScene = new ChangeScene(this);
        initImage = new initImage();

        assembleComponent();
        display();
    }

    public void display() {
        this.setScene(scene);
        this.show();
    }

    public void assembleComponent() {
        this.setTitle("Fitness App");
        utilisateurDAO.connexionProfil(Session.getMail(),
                Session.getPrenom());

        initView();
        setBackground(gpLeftButton, "#1C1C1E", 0);

        initBorderPane();
        setBackground(borderPane, "#2C2C2C", 0);

        scene = new Scene(borderPane, 2000, 1000);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }

    public void initView() {
        initImageView();
        initButton();
        initLabel();
        initVBoxStat();
        initGridPaneStatistics();
        initHBox();
        initVbox();
        initGridPane();
    }


    public void setBackground(Region region, String color, double radius) {
        BackgroundFill backgroundFill = new BackgroundFill(
                Color.web(color),             // Convertit la couleur en objet Color
                new CornerRadii(radius),      // Rayon pour les coins arrondis
                Insets.EMPTY
        );
        Background background = new Background(backgroundFill);
        region.setBackground(background);
    }


    public void initBorderPane() {
        borderPane = new BorderPane();
        borderPane.setLeft(gpLeftButton);
        borderPane.setCenter(gpCenter);
        borderPane.setRight(null);
    }

    public void initGridPane() {
        gpLeftButton = new GridPane();
        gpLeftButton.setPadding(new Insets(10, 0, 10, 0));
        gpLeftButton.setVgap(10);

        gpLeftButton.add(btnHome, 0, 0);
        gpLeftButton.add(btnStat, 0, 1);
        gpLeftButton.add(btnChallenge, 0, 2);
        gpLeftButton.add(btnProfil, 0, 3);
        gpLeftButton.add(btnSettings, 0, 4);

        gpCenter = new GridPane();
        gpCenter.setMaxWidth(Double.MAX_VALUE);
        gpCenter.setPadding(new Insets(10, 0, 10, 10));
        gpCenter.setHgap(20);
        gpCenter.setVgap(20);

        gpCenter.add(vbCenter, 0, 0);
    }

    public void initGridPaneStatistics() {
        gpStatistics = new GridPane();
        gpStatistics.setPadding(new Insets(10, 10, 10, 10));
        gpStatistics.setHgap(10);
        gpStatistics.setVgap(10);

        gpStatistics.add(lStat, 0, 0);
        gpStatistics.add(vbTrain, 0, 1);
        gpStatistics.add(vbMin, 1, 1);
    }

    public void initVbox() {
        vbCenter = new VBox();
        vbCenter.setPadding(new Insets(15, 15, 15, 15));
        vbCenter.setMinWidth(500);

        vbChallenge = new VBox();
        vbChallenge.setSpacing(15);
        vbChallenge.setPadding(new Insets(15, 15, 15, 15));

        vbChallenge.getChildren().addAll(hbChallenge1, hbChallenge2, hbChallenge3);
        vbCenter.getChildren().addAll(gpStatistics, lChallenge, vbChallenge);
    }

    public void initVBoxStat() {
        vbTrain = new VBox();
        vbTrain.setSpacing(15);
        vbTrain.setPadding(new Insets(15, 15, 15, 15));

        vbMin = new VBox();
        vbMin.setSpacing(15);
        vbMin.setPadding(new Insets(15, 15, 15, 15));


        vbTrain.getStyleClass().add("vbInStat");
        vbMin.getStyleClass().add("vbInStat");
    }

    public void initHBox() {
        hbChallenge1 = new HBox();
        hbChallenge2 = new HBox();
        hbChallenge3 = new HBox();

        hbChallenge1.getChildren().add(new Label(mainModel.getHome()));
        hbChallenge2.getChildren().add(new Label(mainModel.getHome()));
        hbChallenge3.getChildren().add(new Label(mainModel.getHome()));

        hbChallenge1.getStyleClass().add("hbChall");
        hbChallenge2.getStyleClass().add("hbChall");
        hbChallenge3.getStyleClass().add("hbChall");
    }

    public void initButton() {
        btnHome = new Button(mainModel.getHome(), ivHome);
        btnStat = new Button(mainModel.getStatistiques(), ivStat);
        btnChallenge = new Button(mainModel.getChallenges(), ivChallenge);
        btnProfil = new Button(mainModel.getMonProfile(), ivProfil);
        btnSettings = new Button(mainModel.getParameters(), ivSettings);

        configButton(btnHome);
        configButton(btnStat);
        configButton(btnChallenge);
        configButton(btnProfil);
        configButton(btnSettings);

        btnHome.getStyleClass().add("btn");
        btnStat.getStyleClass().add("btn");
        btnChallenge.getStyleClass().add("btn");
        btnProfil.getStyleClass().add("btn");
        btnSettings.getStyleClass().add("btn");

        btnHome.setOnAction(changeScene);
        btnStat.setOnAction(changeScene);
        btnChallenge.setOnAction(changeScene);
        btnProfil.setOnAction(changeScene);
        btnSettings.setOnAction(changeScene);
    }

    public void initLabel() {
        lStat = new Label(mainModel.getStatistiques());
        lChallenge = new Label(mainModel.getDailyChallenges());

        lStat.getStyleClass().add("lab");
        lChallenge.getStyleClass().add("lab");
    }

    public void initImageView() {
        ivHome = initImage.getImageView30("home", Color.TRANSPARENT);
        ivStat = initImage.getImageView30("statistics", Color.TRANSPARENT);
        ivChallenge = initImage.getImageView30("challenges", Color.TRANSPARENT);
        ivProfil = initImage.getImageView30("profile", Color.TRANSPARENT);
        ivSettings = initImage.getImageView30("settings", Color.TRANSPARENT);
    }

    public void configButton(Button button) {
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
    }

    public GridPane getGpCenter() {
        return gpCenter;
    }

    public Button getBtnHome() {
        return btnHome;
    }

    public Button getBtnStat() {
        return btnStat;
    }

    public Button getBtnChallenge() {
        return btnChallenge;
    }

    public Button getBtnProfil() {
        return btnProfil;
    }

    public Button getBtnSettings() {
        return btnSettings;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }


}
