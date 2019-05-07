package gui.view;

import java.util.ArrayList;

import gui.model.GameButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import logic.GameManager;

public class MenuView extends ViewManager implements IView{

	private static final int MENU_BUTTON_X = 50;
	private static final int MENU_BUTTON_Y = 150;

	private ArrayList<GameButton> menuButtons;
	
	public MenuView() {
		super();
		mainStage.setResizable(false);
		menuButtons = new ArrayList<GameButton>();
		createBackground();
//		createSubScenes();
		createButtons();
	}
	
	@Override
	public void createBackground() {
		Image backgroundImg = new Image("/gui/resources/texture.png");
		BackgroundImage background = new BackgroundImage(backgroundImg, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		pane.setBackground(new Background(background));

		ImageView logo = new ImageView("gui/resources/logo.png");
		logo.setLayoutX(WIDTH / 2 - 100);
		logo.setLayoutY(170);
		logo.setScaleX(0.9);
		logo.setScaleY(0.9);
		pane.getChildren().add(logo);
		
	}

	@Override
	public void createButtons() {
		GameButton playBtn = new GameButton("PLAY");
		playBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				for (GameButton btn : menuButtons) {
					if (btn != playBtn) {
						btn.setDisable(!btn.isDisable());
					}
				}
				mainStage.close();
			}
		});
		menuButtons.add(playBtn);
		
		GameButton exitBtn = new GameButton("EXIT");
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				mainStage.close();
			}
		});
		menuButtons.add(exitBtn);

		addButtons();
		
	}
	
	private void addButtons() {
		for (GameButton button : menuButtons) {
			button.setLayoutX(MENU_BUTTON_X);
			button.setLayoutY(MENU_BUTTON_Y + menuButtons.indexOf(button) * 100);
			pane.getChildren().add(button); // DA PROBLEMI
		}
	}
	
	private void playSudoku() {
//		GameView game = new GameView();
//		game.hideStage(stage);
	}

	@Override
	public Stage getStage() {
		return mainStage;
	}

	@Override
	public void setGameManager(GameManager gameManager) {
		
		
	}

}
