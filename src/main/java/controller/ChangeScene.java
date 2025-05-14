package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import view.StatistiquesView;
import view.challengesView;
import view.homeView;

public class ChangeScene implements EventHandler<ActionEvent> {

    private homeView view;
    private StatistiquesView statistiquesView;
    private challengesView challengesView;

    public ChangeScene(homeView view) {
        this.view = view;
        this.statistiquesView = new StatistiquesView();
        this.challengesView = new challengesView();
    }


    @Override
    public void handle(ActionEvent event) {
        resetButtonStyles();
        Object source = event.getSource();

        if (source == view.getBtnStat()) {
            view.getBorderPane().setCenter(statistiquesView.getvBox());
            view.getBtnStat().getStyleClass().add("active-btn");
        }
        else if (source == view.getBtnHome()) {
            view.getBorderPane().setCenter(view.getGpCenter());
            view.getBtnHome().getStyleClass().add("active-btn");
        }
        else if (source == view.getBtnChallenge()) {
            view.getBorderPane().setCenter(challengesView.getVBox());
            view.getBtnChallenge().getStyleClass().add("active-btn");
        }
    }

    private void resetButtonStyles() {
        for (Button btn : new Button[]{
                view.getBtnHome(),
                view.getBtnStat(),
                view.getBtnChallenge(),
                view.getBtnProfil(),
                view.getBtnSettings()
        }) {
            btn.getStyleClass().remove("active-btn");
        }
    }

}
