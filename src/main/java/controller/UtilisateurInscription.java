package controller;

import javafx.scene.control.ButtonType;
import model.UtilisateurDAO;
import view.UtilisateurInscriptionView;

import java.util.Optional;

public class UtilisateurInscription {

    private UtilisateurInscriptionView view;
    private UtilisateurDAO utilisateurDAO;
    private UtilisateurPathToInscription utilisateurPathToInscription;

    public UtilisateurInscription(UtilisateurInscriptionView view) {
        this.view = view;
        utilisateurDAO = new UtilisateurDAO(new UtilisateurPathToInscription());
    }

    public void inscription() {
        Optional<ButtonType> result = view.getResult();
        if (result.isPresent() && result.get() == view.getSignUp()) {
            utilisateurDAO.insertProfil(
                    view.getNom(),
                    view.getPrenom(),
                    view.getMail(),
                    view.getAge(),
                    view.getSexe(),
                    view.getTaille(),
                    view.getPoids(),
                    view.getObjectif(),
                    view.getNbJours()
            );
        }
    }

}

