package gui.view;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.GameManager;

@SuppressWarnings("restriction")
public class ViewManager {

	protected int HEIGHT = 600;
	protected int WIDTH = 800;

	protected Stage mainStage;
	protected AnchorPane pane;
	protected Scene scene;
	
	public Stage hiddenStage;
	
	protected GameManager gameManager;
	
	public ViewManager() 
	{
		mainStage = new Stage();
		pane = new AnchorPane();
		scene = new Scene(pane, WIDTH, HEIGHT);
		mainStage.setScene(scene);
	}
	
	public ViewManager(int with, int heigh) 
	{
		mainStage = new Stage();
		pane = new AnchorPane();
		HEIGHT = heigh;
		WIDTH = with;
		scene = new Scene(pane, WIDTH, HEIGHT);
		mainStage.setScene(scene);
	}

	public void setView(IView view) {
		mainStage = view.getStage();
	}

	public void hideStage(Stage stage) {
		hiddenStage = stage;
		hiddenStage.hide();
	}

}
