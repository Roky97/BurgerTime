package gui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import logic.GameManager;
import logic.model.BurgerComponent;
import logic.model.Map;
import logic.model.PieceOfComponent;
import logic.model.Player;

public class GameView extends ViewManager implements IView {

	final int OFFSET_Y = 90;
	
	final int imageSizeX = 35;
	final int imageSizeY = 30;
	final int imageChefSizeX = 67;
	final int imageChefSizeY = 77;
	
	final static int WIDTH = 735;
	final static int HEIGHT = 1000;
	
	private ArrayList<ImageView> lifesImage;
	private ImageView chefImage;
	private HashMap<ImageView,PieceOfComponent> pieces;
	
	private GameManager manager;
	private Player player;
	private Map map;

	
	private AnimationTimer gameTimer;
	
	private boolean isLeftKeyPressed = false;
	private boolean isRightKeyPressed = false;
	private boolean isUpKeyPressed = false;
	private boolean isDownKeyPressed = false;
	
	private int stepPlayer = 1;
	
	private String tileUrl= "/gui/resources/game/tile.png";
	private String stairUrl = "/gui/resources/game/stair.png";
	private String tileStairUrl = "/gui/resources/game/tileStair.png";
	private String lifeUrl = "/gui/resources/game/life.png";
	private String chefUrl = "/gui/resources/game/chef.png";
	
	public GameView() {
		super(WIDTH,HEIGHT);
//		mainStage.setResizable(false);
		
		manager = new GameManager();
		player=manager.getPlayer();
		map = manager.getLevels().get(manager.getCurrentLevel()-1);
		
		lifesImage = new ArrayList<ImageView>();
		pieces = new HashMap<>();
		
		createBackground();
		loadElements();
		setPlayer();
//		setEnemies();
		drawLifes();
		
		createKeyListeners();
		gameLoop();
		
		mainStage.show();
	}
	
	private void gameLoop() {
		gameTimer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				movePlayer();
				checkIfPlayerOnPiece();
			}	
		};
		
		gameTimer.start();
	}
	
	
	private void movePlayer() {
		if(isLeftKeyPressed && !isRightKeyPressed) {
			if(player.moveLeft()) {	
				while((chefImage.getX()/imageSizeX)!=player.getPosY()) {
					chefImage.setX(chefImage.getX() - 1);
				}		
			}
			
			isLeftKeyPressed=false;
		}
		
		if(!isLeftKeyPressed && isRightKeyPressed) {
			if(player.moveRight()) {
				while((chefImage.getX()/imageSizeX)!=player.getPosY()) {
					chefImage.setX(chefImage.getX() + 1);
				}	
			}
			
			isRightKeyPressed=false;
		}
		
		if(isUpKeyPressed && !isDownKeyPressed) {
			if(player.moveUp()) {
				
				while(((chefImage.getY()-65)/imageSizeY)!=player.getPosX()) {
					chefImage.setY(chefImage.getY() - 1);
				}	
			}
			
			isUpKeyPressed=false;
		}
		
		if(!isUpKeyPressed && isDownKeyPressed) {
			if(player.moveDown()) {
				
				while(((chefImage.getY()-65)/imageSizeY)!=player.getPosX()) {	
					chefImage.setY(chefImage.getY() + 1);
				}	
			}
			
			isDownKeyPressed=false;
		}		
		
	}
	
	private void checkIfPlayerOnPiece() {
		
		for(BurgerComponent bc : map.getBurgerComponents()) {
			for(int i = 0; i< bc.getPieces().size(); i++) {
				if( (bc.getPieces().get(i).getPosX()-1==player.getPosX()) && (bc.getPieces().get(i).getPosY()==player.getPosY())) {
					for (Entry<ImageView, PieceOfComponent> entry : pieces.entrySet()) {
						 if((entry.getValue().getPosX()==bc.getPieces().get(i).getPosX() && entry.getValue().getPosY()==bc.getPieces().get(i).getPosY()) && (entry.getValue().getPressed()==false))
					     {
							 
							 entry.getValue().setPressed(true);
							 entry.getKey().setY(entry.getKey().getY()+10);
							 return;
					     }
					}
				}	
			}
		}
		
	}
	
	
	
//	/////////////////////////////////
//	//// CHECK MOVEMENT SECTION /////
//	/////////////////////////////////
//	
//	private boolean canMoveDown() {
//		int x = (int) ((chefImage.getX() + imageSizeX));
//		int y = (int) chefImage.getY() - OFFSET_Y + imageChefSizeX;
//		if(x/imageSizeX >= manager.getCurrentMapLevel().getRowLen()) {
//			System.out.println(y + " " + manager.getCurrentMapLevel().getRowLen());
//			return false;
//		}
//		
////		Rectangle rect = new Rectangle(5, 5, Color.WHITE);
////		rect.setLayoutX(x);
////		rect.setLayoutY(y);
////		pane.getChildren().add(rect);
//		
////		System.out.println((y/imageSizeY) + " " + ( x/imageSizeX) + ": " + manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX) + " attuale");
////		System.out.println(((y/imageSizeY) + 1) + " " + ( x/imageSizeX) + ": " + manager.getCurrentMapLevel().getMatrixValue(((y/imageSizeY) + 1), x/imageSizeX) + " sotto");
////		System.out.println();
//		
//		if(manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX) == '|' || (manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX) == '-' && manager.getCurrentMapLevel().getMatrixValue((y/imageSizeY)+1, x/imageSizeX)=='|'))
//			return true;
//		return false;
//	}
//
//	private boolean canMoveUp() {
//		int x = (int) ((chefImage.getX() + imageSizeX));
//		int y = (int) chefImage.getY() - OFFSET_Y + imageChefSizeX;
//		
//		if((y/imageSizeY)-1 < 0) {
//			return false;
//		}
////		Rectangle rect = new Rectangle(5, 5, Color.WHITE);
////		rect.setLayoutX(x);
////		rect.setLayoutY(y);
////		pane.getChildren().add(rect);
//		
////		System.out.println((y/imageSizeY) + " " + ( x/imageSizeX) + ": " + manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX) + " attuale");
////		System.out.println(((y/imageSizeY) - 1) + " " + ( x/imageSizeX) + ": " + manager.getCurrentMapLevel().getMatrixValue(((y/imageSizeY) - 1), x/imageSizeX) + " sopra");
////		System.out.println();
//		
//		if(manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX) == '|' || (manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX) == '-' && manager.getCurrentMapLevel().getMatrixValue((y/imageSizeY)-1, x/imageSizeX)=='|'))
//			return true;
//		return false;
//	}
//
//	private boolean canMoveLeft() {
//		int x = (int) ((chefImage.getX() + imageSizeX) - stepPlayer);
//		int y = (int) chefImage.getY() - OFFSET_Y + imageChefSizeX;
//		if(x-1<0) {
//			return false;
//		}
////		Rectangle rect = new Rectangle(5, 5, Color.WHITE);
////		rect.setLayoutX(x);
////		rect.setLayoutY(y);
////		pane.getChildren().add(rect);
//		
//		System.out.println((y/imageSizeY) + " " + ( x/imageSizeX) + ": " + manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX));
//		switch (manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX)) {
//		case 'p':
//			return true;
//			
//		case 'd':
//			return true;
//			
//		case 'h':
//			return true;
//		case 's':
//			return true;
//		case 'u':
//			return true;
//			
//		case '-':
//			return true;
//
//		default:
//			break;
//		}
//		
//		return false;
//	}
//	
//	private boolean canMoveRight() {
//		
//		int x = (int) ((chefImage.getX() + imageSizeX) + stepPlayer);
//		int y = (int) chefImage.getY() - OFFSET_Y + imageChefSizeX;
//		
//		if(x/imageSizeX>= manager.getCurrentMapLevel().getColLen()) {
//			return false;
//		}
//		
////		Rectangle rect = new Rectangle(5, 5, Color.WHITE);
////		rect.setLayoutX(x);
////		rect.setLayoutY(y);
////		pane.getChildren().add(rect);
//		
//		System.out.println((y/imageSizeY) + " " + ( x/imageSizeX) + ": " + manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX));
//		switch (manager.getCurrentMapLevel().getMatrixValue(y/imageSizeY, x/imageSizeX)) {
//		case 'p':
//			return true;
//			
//		case 'd':
//			return true;
//			
//		case 'h':
//			return true;
//		case 's':
//			return true;
//		case 'u':
//			return true;
//			
//		case '-':
//			return true;
//
//		default:
//			break;
//		}
//		
//
//		
//		return false;
//	}
	
	
	////////////////////////////////////
	////////  LISTENER SECTION /////////
	////////////////////////////////////

	private void createKeyListeners() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.LEFT)
				{
					isLeftKeyPressed=true;

				}
				else if(event.getCode() == KeyCode.RIGHT)
				{
					isRightKeyPressed=true;
				}
				else if(event.getCode() == KeyCode.UP)
				{
					isUpKeyPressed=true;
				}
				else if(event.getCode() == KeyCode.DOWN)
				{
					isDownKeyPressed=true;
				}	
			}
		});
		
//		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				
//				if(event.getCode() == KeyCode.LEFT)
//				{
//					isLeftKeyPressed=false;
//				}
//				else if(event.getCode() == KeyCode.RIGHT)
//				{
//					isRightKeyPressed=false;
//				}
//				else if(event.getCode() == KeyCode.UP)
//				{
//					isUpKeyPressed=false;
//				}
//				else if(event.getCode() == KeyCode.DOWN)
//				{
//					isDownKeyPressed=false;
//				}
//			}
//		});
		
	}
	
	
	/////////////////////////////////
	///////// DRAW SECTION //////////
	/////////////////////////////////
	
	
	private void setPlayer() {
		chefImage = new ImageView(chefUrl);
//		System.out.println(manager.getPlayer().getPosY() + " " + (manager.getPlayer().getPosX()));
		chefImage.setX((player.getPosY() * imageSizeX)-(imageSizeX/2));
		chefImage.setY(((player.getPosX() * imageSizeY) + 65) );
		
		pane.getChildren().add(chefImage);
		
	}

	private void loadElements() {

		
		
		for(BurgerComponent bc : map.getBurgerComponents()) {
			for(int i = 0; i< bc.getPieces().size(); i++) {

				ImageView temp = new ImageView(bc.getType().getUrl()+ (i+1) + ".png");
				temp.setX(bc.getPieces().get(i).getPosY() * imageSizeX);
				temp.setY((bc.getPieces().get(i).getPosX() * imageSizeY) + OFFSET_Y);
				
				drawTile(bc.getPieces().get(i).getPosX(), bc.getPieces().get(i).getPosY());
				pane.getChildren().add(temp);
				pieces.put(temp, bc.getPieces().get(i));
				
				
			}
		}
	}

	private void drawLifes() {
		for(int i=0; i<manager.getLifes(); i++) {
			ImageView lifeImage = new ImageView(lifeUrl);
			lifeImage.setX(i*imageSizeX);
			lifeImage.setY(0);
			lifesImage.add(lifeImage);
			lifeImage.setScaleX(0.7);
			lifeImage.setScaleY(0.7);
			
			pane.getChildren().add(lifeImage);
//			pane.getChildren().remove(lifesImage.get(lifesImage.size()-1));
		}
		
	}

	private void drawTile(int i, int j) {
		ImageView tileImage = new ImageView(tileUrl);
		tileImage.setX(j*imageSizeX );
		tileImage.setY((i*imageSizeY) + OFFSET_Y );
		
		pane.getChildren().add(tileImage);
	}
	
	private void drawStair(int i, int j) {
		ImageView stairImage = new ImageView(stairUrl);
//	System.out.println(stairImage.getFitHeight());
		stairImage.setX(j*imageSizeX );
		stairImage.setY((i*imageSizeY) + OFFSET_Y );
		
		pane.getChildren().add(stairImage);
	}
	
	
	private void drawTileStair(int i, int j) {
		ImageView tileStairImage = new ImageView(tileStairUrl);
		tileStairImage.setX(j*imageSizeX );
		tileStairImage.setY((i*imageSizeY) + OFFSET_Y );
		
		pane.getChildren().add(tileStairImage);
	}

	@Override
	public void createBackground() {
		pane.setStyle("-fx-background-color: black");
		for(int i = 0; i < manager.getLevels().get(manager.getCurrentLevel() - 1).getRowLen(); i++) {
			for( int j = 0; j < manager.getLevels().get(manager.getCurrentLevel() - 1).getColLen(); j++) {
				
				switch (manager.getLevels().get(manager.getCurrentLevel() - 1).getMatrixValue(i, j)) {
				case '-':
					
					drawTile(i, j);
					
					if(i>0 && manager.getLevels().get(manager.getCurrentLevel() - 1).getMatrixValue(i - 1, j) == '|') {
						drawTileStair(i, j);
						drawStair(i, j);
						
					}
					if(i == 0 && manager.getLevels().get(manager.getCurrentLevel() - 1).getMatrixValue(i + 1, j) == '|')
						drawTileStair(i, j);
					break;
				
				case '|':
					
					drawStair(i, j);
					
					break;

				default:
					break;
				}
				
				
				
			}
		}
		
		
		
		
	}

	@Override
	public void createButtons() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stage getStage() {
		return mainStage;
	}

	@Override
	public void setGameManager(GameManager gameManager) {
		// TODO Auto-generated method stub
		
	}
	
}
