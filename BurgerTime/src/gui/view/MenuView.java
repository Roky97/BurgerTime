package gui.view;

import java.util.ArrayList;


import gui.model.*;
import javafx.stage.Stage;
import logic.GameManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

public class MenuView extends ViewManager implements IView {

	private Stage stage;
    
	private static final int MENU_BUTTON_X = 50;
	private static final int MENU_BUTTON_Y = 150;

	private ArrayList<GameButton> menuButtons;

	public MenuView() {

		stage = new Stage();
		stage.setResizable(false);
		setMenuButtons(new ArrayList<GameButton>());
		createBackground();
//		createSubScenes();
		createButtons();
		stage.setScene(scene);
	}

	public void createBackground() {
		Image backgroundImg = new Image("/gui/resources/texture.png");
		BackgroundImage background = new BackgroundImage(backgroundImg, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		pane.setBackground(new Background(background));

		ImageView logo = new ImageView("gui/resources/logo.png");
		logo.setLayoutX(WIDTH / 2 - 100);
		logo.setLayoutY(150);
		logo.setScaleX(0.9);
		logo.setScaleY(0.9);
		pane.getChildren().add(logo);
	}

	public void createButtons() {
		GameButton playBtn = new GameButton("PLAY");
		playBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				for (GameButton btn : menuButtons) {
					if (btn != playBtn) {
						btn.setDisable(!btn.isDisable());
					}
				}
//				difficultySubScene.moveSubScene(); // QUA METTERE IL GIOCO
			}
		});
		menuButtons.add(playBtn);

		GameButton exitBtn = new GameButton("EXIT");
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				stage.close();
			}
		});
		menuButtons.add(exitBtn);

		addButtons();
	}

	private void addButtons() {
		for (GameButton button : menuButtons) {
			button.setLayoutX(MENU_BUTTON_X);
			button.setLayoutY(MENU_BUTTON_Y + menuButtons.indexOf(button) * 100);
			pane.getChildren().add(button);
		}
	}

	@SuppressWarnings("unused")
	private void createSubScenes() {
		System.out.println("ciao");
//		difficultySubScene = new GameSubScene();
//		difficultySubScene.setLabel("CHOOSE DIFFICULTY");
//		difficultySubScene.getLabel().setStyle("-fx-text-fill : Gold;");
//
//		ArrayList<GameButton> buttons = new ArrayList<GameButton>();
//		final GameButton easy = new GameButton("EASY");
//		easy.setDifficulty(DIFFICULTY.EASY);
//		easy.setDifficultyStyle();
//		easy.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent event) {
//				playGame(easy);
//			}
//
//		});
//		buttons.add(easy);
//		final GameButton medium = new GameButton("MEDIUM");
//		medium.setDifficulty(DIFFICULTY.NORMAL);
//		medium.setDifficultyStyle();
//		medium.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent event) {
//				playGame(medium);
//			}
//		});
//		buttons.add(medium);
//		final GameButton hard = new GameButton("HARD");
//		hard.setDifficulty(DIFFICULTY.HARD);
//		hard.setDifficultyStyle();
//		hard.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent event) {
//				playGame(hard);
//			}
//		});
//		buttons.add(hard);
//
//		difficultySubScene.addButtons(buttons);
//
//		pane.getChildren().add(difficultySubScene);
//
//		VBox buttonsBox = new VBox();
//		buttonsBox.setSpacing(20);
//		buttonsBox.setAlignment(Pos.CENTER_RIGHT);
//		buttonsBox.getChildren().addAll(difficultySubScene.getButtons());
//		buttonsBox.setLayoutX(130);
//		buttonsBox.setLayoutY(100);
//
//		difficultySubScene.getPane().getChildren().add(difficultySubScene.getLabel());
//		difficultySubScene.getPane().getChildren().add(buttonsBox);
//
//		// load
//		loadSubScene = new GameSubScene();
//		loadSubScene.setLabel("NESSUNA PARTITA SALVATA");
//		loadSubScene.setLabelLayout(20, 35);
//		loadSubScene.setLayout(1500, 300);
//		loadSubScene.getLabel().setStyle("-fx-text-fill : Gold;");
//		loadSubScene.backgroundSettings(350, 100);
//		loadSubScene.setTransitionCoordinate(-1110, 0);
//		loadSubScene.getPane().getChildren().add(loadSubScene.getLabel());
//
//		pane.getChildren().add(loadSubScene);
//
//		// scanner
//		scannerSubScene = new GameSubScene();
//		scannerSubScene.setLabel("SCANSIONE SUDOKU DA IMMAGINE");
//		scannerSubScene.getLabel().setStyle("-fx-text-fill : Gold;");
//
//		ArrayList<GameButton> buttons2 = new ArrayList<GameButton>();
//		GameButton cameraBtn = new GameButton("CAMERA");
//		cameraBtn.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent event) {
//				Scanner scanner = new Scanner();
//				if (scanner.enableCamera()) {
//					scanner.manageDisplayTransition(stage);
//					scanner.startScanning();
//				}
//			}
//		});
//		buttons2.add(cameraBtn);
//
//		GameButton galleryBtn = new GameButton("GALLERIA");
//		galleryBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//			public void handle(ActionEvent event) 
//			{
//				FileChooser fileChooser = new FileChooser();
//				fileChooser.setTitle("Open Resource File");
//				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
//				File selectedFile = fileChooser.showOpenDialog(null);
//				if(selectedFile != null) {
//					Mat colorimg = OpenCVUtilsJava.loadOrExit(selectedFile);
//					Scanner scanner = new Scanner();
//				    scanner.initGallery();
//					scanner.manageDisplayTransition(stage);
//					scanner.startScanning(colorimg);
//				}
//			}
//		});
//		buttons2.add(galleryBtn);
//		scannerSubScene.addButtons(buttons2);
//
//		VBox buttonsBox2 = new VBox();
//		buttonsBox2.setSpacing(20);
//		buttonsBox2.setAlignment(Pos.CENTER_RIGHT);
//		buttonsBox2.getChildren().addAll(scannerSubScene.getButtons());
//		buttonsBox2.setLayoutX(130);
//		buttonsBox2.setLayoutY(130);
//		scannerSubScene.setLabelLayout(30, 30);
//		scannerSubScene.getPane().getChildren().add(scannerSubScene.getLabel());
//		scannerSubScene.getPane().getChildren().add(buttonsBox2);
//
//		pane.getChildren().add(scannerSubScene);
	}
	
	public Stage getStage() {
		return stage;
	}

	public ArrayList<GameButton> getMenuButtons() {
		return menuButtons;
	}

	public void setMenuButtons(ArrayList<GameButton> menuButtons) {
		this.menuButtons = menuButtons;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

//	public GameSubScene getSubscene() {
//		return difficultySubScene;
//	}

}
