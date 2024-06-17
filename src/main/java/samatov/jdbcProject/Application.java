package samatov.jdbcProject;

import samatov.jdbcProject.view.initializer.MainIntializator;
import samatov.jdbcProject.view.MainView;

public class Application {

    public static void main(String[] args) {
        MainView mainView = MainIntializator.initializeApp();
        mainView.displayMainMenu();
    }
}
