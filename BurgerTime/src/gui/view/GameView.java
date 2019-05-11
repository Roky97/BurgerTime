package gui.view;

import java.awt.Image;
import java.util.ArrayList;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import logic.GameManager;
import logic.model.Map;

public class GameView extends ViewManager implements IView {

	final int OFFSET_Y = 90;
	
	final int imageSizeX = 35;
	final int imageSizeY = 30;
	
	private ArrayList<ImageView> lifesImage;
	
	private ArrayList<Map> levels;
	private int totLevels = 1;
	private int currLevel = 1;
	private int lifes = 4;
	
	
	private String tileUrl= "/gui/resources/game/tile.png";
	private String stairUrl = "/gui/resources/game/stair.png";
	private String tileStairUrl = "/gui/resources/game/tileStair.png";
	private String lifeUrl = "/gui/resources/game/life.png";
	
	public GameView() {
		super(735,1000);
//		mainStage.setResizable(false);
		
		levels = new ArrayList<Map>();
		lifesImage = new ArrayList<ImageView>();
		
		loadLevels();
		createBackground();
		
		mainStage.show();
	}
	
	private void loadLevels() {
		for(int i = 0; i < totLevels; i++)
		{
			levels.add(new Map(i+1));
//			System.out.println(levels.get(i));
		}
		drawLifes();
	}
	
	
	
	private void drawLifes() {
		for(int i=0; i<lifes; i++) {
			ImageView lifeImage = new ImageView(lifeUrl);
			lifeImage.setX(i*35);
			lifeImage.setY(0);
			lifesImage.add(lifeImage);
			
			pane.getChildren().add(lifeImage);
//			pane.getChildren().remove(lifesImage.get(lifesImage.size()-1));
		}
		
	}

	private void drawTile(int i, int j) {
		ImageView tileImage = new ImageView(tileUrl);
		tileImage.setX(j*35 );
		tileImage.setY((i*30) + OFFSET_Y );
		
		pane.getChildren().add(tileImage);
	}
	
	private void drawStair(int i, int j) {
		ImageView stairImage = new ImageView(stairUrl);
//	System.out.println(stairImage.getFitHeight());
		stairImage.setX(j*35 );
		stairImage.setY((i*30) + OFFSET_Y );
		
		pane.getChildren().add(stairImage);
	}
	
	
	private void drawTileStair(int i, int j) {
		ImageView tileStairImage = new ImageView(tileStairUrl);
		tileStairImage.setX(j*35 );
		tileStairImage.setY((i*30) + OFFSET_Y );
		
		pane.getChildren().add(tileStairImage);
	}

	@Override
	public void createBackground() {
		pane.setStyle("-fx-background-color: black");
		for(int i = 0; i < levels.get(currLevel - 1).getRowLen(); i++) {
			for( int j = 0; j < levels.get(currLevel - 1).getColLen(); j++) {
				
				switch (levels.get(currLevel - 1).getMatrixValue(i, j)) {
				case '-':
					
					drawTile(i, j);
					
					if(i>0 && levels.get(currLevel - 1).getMatrixValue(i - 1, j) == '|') {
						drawTileStair(i, j);
						drawStair(i, j);
						
					}
					if(i == 0 && levels.get(currLevel - 1).getMatrixValue(i + 1, j) == '|')
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGameManager(GameManager gameManager) {
		// TODO Auto-generated method stub
		
	}
	
}
