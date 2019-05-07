package gui.model;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class GameSubScene extends SubScene{
	private final static String FONT_PATH = "file:resources/Burgertime.otf";
	private final static String BACKGROUND_IMAGE = "resources/yellow_panel.png";
	private boolean isHidden;
	public GameSubScene() {
		super(new AnchorPane(), 600, 400);
		prefHeight(400);
		prefWidth(600);
		BackgroundImage background = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 400,false,true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(background));
		isHidden = true;
		setLayoutX(1024);
		setLayoutY(160);
		
		
		
		
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}
	
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		if(isHidden)
		{
			transition.setToX(-676);
			isHidden = false;
		}
		else
		{
			transition.setToX(0);
			isHidden = true;
		}
		
		transition.play();
	}
	

}
