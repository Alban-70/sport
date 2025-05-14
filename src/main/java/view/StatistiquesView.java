package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.StatistiquesModel;
import model.UtilisateurDAO;

public class StatistiquesView {

    private GridPane grid;
    private VBox vBox, vbSeance, vbCalories, vbTrain;
    private Label lActivite, lChallenges, lTrain, lTitleStat;
    private LineChart<String, Number> lineChart;
    private XYChart.Series<String, Number> cardioData;
    private PieChart pieChart;
    private BarChart barChart;

    //Instance des classes
    private StatistiquesModel model;
    private UtilisateurDAO utilisateurDAO;

    public StatistiquesView() {
        this.model = new StatistiquesModel();
        this.utilisateurDAO = new UtilisateurDAO();
        initView();
    }

    public void initView() {
        initLabel();
        initVBoxInGridPane();
        initGridPane();
        initVBox();
    }

    public void initGridPane() {
        grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setPadding(new Insets(20, 50, 20, 50));

        grid.add(lTitleStat, 0, 0, 2, 1);
        grid.add(vbCalories, 0, 1);
        grid.add(vbSeance, 1, 1);
        grid.add(vbTrain, 0, 2, 2, 1);
    }

    public void initVBox() {
        vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        vBox.getChildren().addAll(grid);
    }

    public void initVBoxInGridPane() {
        vbSeance = new VBox();
        vbSeance.setSpacing(10);
        vbSeance.setPadding(new Insets(10, 20, 10, 20));
        vbSeance.setAlignment(Pos.CENTER);

        vbCalories = new VBox();
        vbCalories.setSpacing(10);
        vbCalories.setPadding(new Insets(10, 20, 10, 20));
        vbCalories.setAlignment(Pos.CENTER);

        vbTrain = new VBox();
        vbTrain.setSpacing(10);
        vbTrain.setPadding(new Insets(10, 20, 10, 20));
        vbTrain.setAlignment(Pos.CENTER);


        initLineChart();
        vbSeance.getChildren().addAll(lActivite, lineChart);
        initPieChart();
        vbCalories.getChildren().addAll(lChallenges, pieChart);
        initBarChart();
        vbTrain.getChildren().addAll(lTrain, barChart);

        vbSeance.getStyleClass().add("vbInGP");
        vbCalories.getStyleClass().add("vbInGP");
        vbTrain.getStyleClass().add("vbInGP");
    }

    public void initLabel() {
        lActivite = new Label(model.getActivites());
        lChallenges = new Label(model.getChallenges());
        lTrain = new Label(model.getEntrainement());
        lTitleStat = new Label(model.getStatistiques());
        lTitleStat.setPrefWidth(Double.MAX_VALUE);
        lTitleStat.setAlignment(Pos.CENTER);

        lTitleStat.getStyleClass().add("labelStat");
        lActivite.getStyleClass().add("labelStat");
        lChallenges.getStyleClass().add("labelStat");
        lTrain.getStyleClass().add("labelStat");
    }

    public void initLineChart() {
        CategoryAxis xAxisLine = new CategoryAxis();
        NumberAxis yAxisLine = new NumberAxis();

        // Désactiver l'auto-échelle de l'axe Y pour pouvoir contrôler les graduations
        yAxisLine.setAutoRanging(false);

        // Définir les bornes de l'axe Y
        yAxisLine.setLowerBound(0); // Valeur minimum de l'axe Y
        yAxisLine.setUpperBound(30); // Valeur maximum de l'axe Y

        // Définir l'intervalle entre les ticks (graduations) sur l'axe Y
        yAxisLine.setTickUnit(10); // Intervalle de 10 pour l'axe Y

        // Désactiver les ticks mineurs de l'axe Y
        yAxisLine.setMinorTickVisible(false);

        // Supprimer les ticks des axes
        xAxisLine.setTickMarkVisible(false);  // Masquer les ticks de l'axe X
        yAxisLine.setTickMarkVisible(false);  // Masquer les ticks de l'axe Y

        lineChart = new LineChart<>(xAxisLine, yAxisLine);

        lineChart.getStyleClass().add("linechart");

        // Ajouter les données à une seule série
        cardioData = new XYChart.Series<>();
        cardioData.setName("Cardio");

        // Ajouter les données pour chaque jour de la semaine
        addCardioData(model.getLun(), 0);
        addCardioData(model.getMar(), 5);
        addCardioData(model.getMercredi(), 15);
        addCardioData(model.getJeu(), 12);
        addCardioData(model.getVendredi(), 18);
        addCardioData(model.getSamedi(), 24);
        addCardioData(model.getDimanche(), 28);

        // Ajouter la série au graphique
        lineChart.getData().add(cardioData);
    }

    public void addCardioData(String jour, int min) {
        cardioData.getData().add(new XYChart.Data<>(jour, min));
    }

    public void initPieChart() {
        pieChart = new PieChart();
        PieChart.Data data1 = new PieChart.Data(model.getNonCompletes(), utilisateurDAO.countInDailyChallenge(1));
        PieChart.Data data2 = new PieChart.Data(model.getCompletes(), utilisateurDAO.countInDailyChallenge(3));
        PieChart.Data data3 = new PieChart.Data(model.getEncours(), utilisateurDAO.countInDailyChallenge(2));

        pieChart.getData().add(data1);
        pieChart.getData().add(data2);
        pieChart.getData().add(data3);

        pieChart.getStyleClass().add("pieChart");
    }

    public void initBarChart() {
        // BarChart (Calories par jour)
        CategoryAxis xAxisBar = new CategoryAxis();
        NumberAxis yAxisBar = new NumberAxis();
        barChart = new BarChart<>(xAxisBar, yAxisBar);

        // Désactiver l'auto-échelle de l'axe Y pour pouvoir contrôler les graduations
        yAxisBar.setAutoRanging(false);

        // Définir les bornes de l'axe Y
        yAxisBar.setLowerBound(0); // Valeur minimum de l'axe Y
        yAxisBar.setUpperBound(500); // Valeur maximum de l'axe Y

        // Définir l'intervalle entre les ticks (graduations) sur l'axe Y
        yAxisBar.setTickUnit(50); // Intervalle de 10 pour l'axe Y

        // Désactiver les ticks mineurs de l'axe Y
        yAxisBar.setMinorTickVisible(false);

        XYChart.Series<String, Number> calorieData = new XYChart.Series<>();
        calorieData.setName("Calories");
        calorieData.getData().add(new XYChart.Data<>(model.getLun(), 300));
        calorieData.getData().add(new XYChart.Data<>(model.getMar(), 200));
        calorieData.getData().add(new XYChart.Data<>(model.getMercredi(), 150));
        calorieData.getData().add(new XYChart.Data<>(model.getJeu(), 450));
        calorieData.getData().add(new XYChart.Data<>(model.getVendredi(), 320));
        calorieData.getData().add(new XYChart.Data<>(model.getSamedi(), 250));
        calorieData.getData().add(new XYChart.Data<>(model.getDimanche(), 400));
        barChart.getData().add(calorieData);

        barChart.getStyleClass().add("barChart");
    }

    // Getters
    public VBox getvBox() {
        return vBox;
    }

}