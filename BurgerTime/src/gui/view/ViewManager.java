package gui.view;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.GameManager;

@SuppressWarnings("restriction")
public class ViewManager {

	final int HEIGHT = 600;
	final int WIDTH = 800;
	
	protected Stage mainStage;
	protected AnchorPane pane;
	protected Scene scene;
	
	protected Stage hiddenStage;
	
	protected GameManager gameManager;
	
	public ViewManager() 
	{
		mainStage = new Stage();
		pane = new AnchorPane();
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
