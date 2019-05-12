package gui.view;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import logic.GameManager;
import logic.model.BurgerComponent;
import logic.model.Map;
import logic.model.PieceOfComponent;

public class GameView extends ViewManager implements IView {

	final int OFFSET_Y = 90;
	
	final int imageSizeX = 35;
	final int imageSizeY = 30;
	
	final static int WIDTH = 735;
	final static int HEIGHT = 1000;
	
	private ArrayList<ImageView> lifesImage;
	
	private GameManager manager;
	
	private String tileUrl= "/gui/resources/game/tile.png";
	private String stairUrl = "/gui/resources/game/stair.png";
	private String tileStairUrl = "/gui/resources/game/tileStair.png";
	private String lifeUrl = "/gui/resources/game/life.png";
	
	public GameView() {
		super(WIDTH,HEIGHT);
//		mainStage.setResizable(false);
		
		manager = new GameManager();
		
		lifesImage = new ArrayList<ImageView>();
		
		createBackground();
		loadElements();
		drawLifes();
		
		mainStage.show();
	}
	
	private void loadElements() {
		Map map = manager.getLevels().get(manager.getCurrentLevel()-1);
		HashMap<ImageView,PieceOfComponent> pieces = new HashMap<>();
		
		
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
