import controller.UtilisateurPathToInscription;
import javafx.application.Application;
import javafx.stage.Stage;
import model.UtilisateurDAO;
import view.UtilisateurConnexionView;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        UtilisateurPathToInscription utilisateur = new UtilisateurPathToInscription();
        utilisateur.initConnexionView();

        UtilisateurConnexionView utilisateurConnexionView = new UtilisateurConnexionView(utilisateur);
    }

    public static void main(String[] args) {
        launch(args);
    }

}