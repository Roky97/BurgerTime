package gui.view;

import javafx.stage.Stage;
import logic.GameManager;

public interface IView
{
	void createBackground();
	void createButtons();
	Stage getStage();
	void setGameManager(GameManager gameManager);
}
