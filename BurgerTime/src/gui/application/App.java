package gui.application;
	
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.GameManager;

import java.util.Scanner;

import gui.view.IView;
import gui.view.MenuView;

public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GameManager gameManager = new GameManager();
			IView view = new MenuView();
			view.setGameManager(gameManager);
			primaryStage = view.getStage();
			primaryStage.show();
			
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
