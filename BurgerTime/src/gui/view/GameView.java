package gui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import gui.model.GameSubScene;
import gui.model.GameButton;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import logic.GameManager;
import logic.ai.Path;
import logic.ai.PathGenerator;
import logic.model.BurgerComponent;
import logic.model.Map;
import logic.model.PieceOfComponent;
import logic.model.Player;
import logic.model.Position;
import logic.model.TypeComponent;

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
	private ArrayList<BurgerComponent> burgerComponents;
	private ArrayList<Position> stairsPositions;

	
	private AnimationTimer moveTimer;
	private GameSubScene endGameSubScene;
	
	private ArrayList<Path> path;
	
	
	
	private String tileUrl= "/gui/resources/game/tile.png";
	private String stairUrl = "/gui/resources/game/stair.png";
	private String tileStairUrl = "/gui/resources/game/tileStair.png";
	private String lifeUrl = "/gui/resources/game/life.png";
	private String chefUrl = "/gui/resources/game/chef.png";
	
	private PieceOfComponent destination;

	GameView() {
		super(WIDTH,HEIGHT);
//		mainStage.setResizable(false);
		manager = new GameManager();
		player=manager.getPlayer();
		map = manager.getLevels().get(manager.getCurrentLevel()-1);
		burgerComponents=map.getBurgerComponents();
		path=new ArrayList<Path>();
		stairsPositions=map.getStairsPositions();
		
		

		destination=new PieceOfComponent(player.getPosX(),player.getPosY(),map);

		lifesImage = new ArrayList<ImageView>();
		pieces = new HashMap<>();
		
		createBackground();
		loadElements();
		setPlayer();
		drawLifes();
		
		createSubScenes();

		
		mainStage.show();	
		gameLoop();
	}
	

	private void gameLoop() {
		
		moveTimer = new AnimationTimer() {
			
            private long lastUpdate = 0 ;
			@Override
			public void handle(long now) {
            	

            	if (now - lastUpdate >= 120_000_000) {

            		
            		if(destination.getPosX()==player.getPosX() && destination.getPosY()==player.getPosY()) { //SE LA POS DEL PLAYER È UGUALE ALLA DESTINAZIONE DA RAGGIUNGERE, CALCOLIAMO UN'ALTRA DESTINAZIONE
            			findNextDestination(player.getPosX(),player.getPosY()); 			
            		}
            		movePlayer();
        			checkVictory();
	            	lastUpdate=now;
            	}
            	

    		
			}	
		};
		moveTimer.start();
	}
	
	private void findPath() {		
		if(destination.getPosX()!=player.getPosX() || destination.getPosY()!=player.getPosY() ) {
			PathGenerator pathGenerator=new PathGenerator(map);
			pathGenerator.setFacts(player, destination);
			path=pathGenerator.findSolution();
		}
	}
	
	private void movePlayer() {
			
		if(path.size()>0) {

			Path nextPos=path.get(0);
			path.remove(0);

			if(nextPos.getRow()>player.getPosX() && nextPos.getColumn()==player.getPosY()) {
				if(player.moveDown()) {
					while(((chefImage.getY()-65)/imageSizeY)!=player.getPosX()) {	
						chefImage.setY(chefImage.getY() + 1);
					}	
				}
			}
			else if(nextPos.getRow()<player.getPosX() && nextPos.getColumn()==player.getPosY()) {
				if(player.moveUp()) {
					while(((chefImage.getY()-65)/imageSizeY)!=player.getPosX()) {
						chefImage.setY(chefImage.getY() - 1);
					}	
				}
			}
			else if(nextPos.getRow()==player.getPosX() && nextPos.getColumn()>player.getPosY()) {
				
				if(player.moveRight()) {
					while((chefImage.getX()/imageSizeX)!=player.getPosY()) {
						chefImage.setX(chefImage.getX() + 1);
					}	
				}
			}
			else if(nextPos.getRow()==player.getPosX() && nextPos.getColumn()<player.getPosY()) {
	
				if(player.moveLeft()) {	
					while((chefImage.getX()/imageSizeX)!=player.getPosY()) {
						chefImage.setX(chefImage.getX() - 1);
					}		
				}
			}
			
			checkIfPlayerOnPiece();
		}
		
		
	}
	
	
	
	
	private void checkIfPlayerOnPiece() { //CONTROLLIAMO SE IL PLAYER STA CALPESTANDO QUALCHE PEZZO DI HAMBURGER/INS
		
					
					for (Entry<ImageView, PieceOfComponent> entry : pieces.entrySet()) { //CONTROLLIAMO SE IL PLAYER SI SOPRA UN PEZZO DI COMPONENTE. SE IMPOSTIAMO IL SUO VALORE "pressato" A TRUE
						if((entry.getValue().getPosX()-1==player.getPosX() && entry.getValue().getPosY()==player.getPosY()) && (entry.getValue().getPressed()==false))
					     {
							 
							 entry.getValue().setPressed(true);
							 entry.getKey().setY(entry.getKey().getY()+10);
					     }
					}
					
					ArrayList<ImageView> componentImages=new ArrayList<ImageView>();
					
					for(BurgerComponent bc: burgerComponents) {
						componentImages=new ArrayList<ImageView>();
						
						if(bc.AllPiecePressed()) {  //SE VI È UN BURGER COMPONENT TOTALMENTE PRESSATO FISSIAMO LE POSIZIONI DEI SUOI PEZZI PARI ALLA POSIZIONE DEL PAVIMENTO SOTTOSTANTE
													//E SALVIAMO LE IMMAGINI RELATIVE AI SUOI PEZZI NELL'ARRAY componentImages
							
							for(PieceOfComponent p: bc.getPieces()) {
								for(Entry<ImageView, PieceOfComponent> entry : pieces.entrySet()) {
									
									if(p.getPosX()==entry.getValue().getPosX() && p.getPosY()==entry.getValue().getPosY() && entry.getValue().getPressed() && p.getPosX()!=21) { //GESTIAMO IL CASO IN CUI IL COMPONENTE CADE
										entry.getValue().fallTillNextFloor();
										entry.getValue().setPressed(false);
										componentImages.add(entry.getKey());
									}
									else if(p.getPosX()==entry.getValue().getPosX() && p.getPosY()==entry.getValue().getPosY() && entry.getValue().getPressed()){
										
										 
										entry.getValue().fallToCompleteBurger(howManyComponentCompleted(p.getPosY()));
										entry.getValue().setPressed(false);
										componentImages.add(entry.getKey());
										bc.setCompleted(true);
									}
								}
							}
							
							
							bc.setAllPiecePressed(false);
							
							for(BurgerComponent bc2: burgerComponents ) {
								if(bc2.getPieces().get(0).getPosX()==bc.getPieces().get(0).getPosX() && bc2.getPieces().get(0).getPosY()==bc.getPieces().get(0).getPosY() && bc2.getType()!=bc.getType()){
									bc2.setAllPiecePressed(true);
								}
							}

							while(((componentImages.get(componentImages.size()-1).getY()-OFFSET_Y)/imageSizeY)!=bc.getPieces().get(0).getPosX()) {
						
								for(int i=0;i<componentImages.size();i++) {
									componentImages.get(i).setY(componentImages.get(i).getY()+0.5);
								}
							
							}
						}
					}
	}	
	
	
	
	private int howManyComponentCompleted(int posY) {
		
		int cont=0;
		for(BurgerComponent bc: burgerComponents) {
			
			if(bc.getPieces().get(0).getPosX()>21 && bc.getPieces().get(0).getPosY()==posY) {
				cont++;
			}
		}
		
		return cont;
	}
	
	
	private void checkVictory() {
		
		for(BurgerComponent bc: burgerComponents) { //SE TUTTI I COMPONENT SONO PRESSATI 
			if(bc.getCompleted()==false) {
				return;
			}
		}
		moveTimer.stop();
		endGameSubScene.moveSubScene();
		
	}
	
	
	private int findComponentInSameRow(int row,int col,PieceOfComponent dest) {  //QUESTA FUNZIONE RESTITUISCE LA DISTANZA MINIMA DA ROW/COL AL PEZZO DI COMPONENTE PIÙ VICINO NELLA STESSA RIGA DI ROW.
																				 //E SALVA IL COMPONENTE IN DEST. RESTITUISCE 0 SE NON VI È NESSUN PEZZO DI COMP NELLA STESSA RIGA.
		
		ArrayList<PieceOfComponent> destinations=new ArrayList<PieceOfComponent>();
		for(BurgerComponent b: burgerComponents) {
			for(PieceOfComponent piece: b.getPieces()) {
				if(piece.getPosX()-1 == row && !piece.getPressed()) {
					destinations.add(piece);
				}
			}
		}
		
		int max=20;
		if(destinations.size()>0) {
			
			for(PieceOfComponent piece: destinations) {
				if(col>piece.getPosY() && col-piece.getPosY()<max) {
					dest.setPosX(piece.getPosX());
					dest.setPosY(piece.getPosY());
					max=col-piece.getPosY();
				}
				else if(col<piece.getPosY()&& piece.getPosY()-col<max) {
					dest.setPosX(piece.getPosX());
					dest.setPosY(piece.getPosY());
					max=piece.getPosY()-col;
				}
			}
			
			dest.setPosX(dest.getPosX()-1);
			System.out.println("destination: " + dest.getPosX()+" "+dest.getPosY());
			
			return max;
		}
		else {
			return 0;
		}
	}
	
	
	private void findNextDestination(int row,int col) { //FUNZIONE PER TROVARE IL PEZZO DI COMPONENTE PIÙ VICINO 
		
		int distance=findComponentInSameRow(row, col,destination);  //TROVIAMO IL PEZZO DI COMPONENTE PIÙ VICINO SULLA STESSA RIGA PARTENDO DALLA POS DEL PLAYER
		
		
		ArrayList<Position> availableStairs=new ArrayList<Position>(); //SUCCESSIVAMENTE CONSIDERIAMO TUTTE LE SCALE PIÙ VICINE DEL PEZZO DI COMP TROVATO NELLA RIGA PRECEDENTE
		
		if(distance!=0) { //SE DISTANCE È DIVERSO DA 0 VUOL DIRE CHE C'È UN PEZZO DI COMP SULLA STESSA RIGA E QUINDI PRENDIAMO LE SCALE CON DISTANZA INFERIORE A QUELLA DEL PEZZO.
			for(Position p: stairsPositions) {
				if(p.getPosX()==row) {
					
					if(col>p.getPosY() && col-p.getPosY()<distance) {
						p.setDistanceFromPlayer(col-p.getPosY());
						availableStairs.add(p);
					}
					else if(col<p.getPosY()&& p.getPosY()-col<distance) {
						p.setDistanceFromPlayer(p.getPosY()-col);
						availableStairs.add(p);
					}					
				}
			}
		}
		else {			//SE DISTANCE==0 NON VI È UN PEZZO DI COMP SULLA STESSA RIGA DEL PLAYER E QUINDI CONSIDERIAMO TUTTE LE SCALE SULLA STESSA RIGA DEL PLAYER
			for(Position p: stairsPositions) {
				if(p.getPosX()==row) {
					
					if(col>p.getPosY()) {
						p.setDistanceFromPlayer(col-p.getPosY());
					}
					else if(col<p.getPosY()) {
						p.setDistanceFromPlayer(p.getPosY()-col);
					}
					
					availableStairs.add(p);
				}
			}	
		}
		
		for(Position p: availableStairs) { 
			p.findUpFloor(); //PER CIASCUNA SCALA TROVIAMO PIANO SUPERIORE E INFERIORE
			System.out.println("ScalaX: "+ p.getPosX() + ", ScalaY: "+p.getPosY()+". UpFloor: "+ p.getUpFloor());
			p.findDownFloor();
			
			p.setDistanceUpFloorUpPiece(findComponentInSameRow(p.getUpFloor(), p.getPosY(), p.getUpPiece())); //SETTIAMO LA DISTANZA DAL PIANO SUPERIORE DELLA SCALA AL PEZZO DI COMP PIÙ VICINO AD ESSO
			p.setDistanceDownFloorDownPiece(findComponentInSameRow(p.getDownFloor(), p.getPosY(), p.getDownPiece())); //SETTIAMO LA DISTANZA DAL PIANO INFERIORE DELLA SCALA AL PEZZO DI COMP PIÙ VICINO AD ESSO
			p.findNearestPiece(); //CON QUESTA FUNZIONE DETERMINIAMO PER CIASCUNA SCALA QUAL'È IL PEZZO PIÙ VICINO A PARTIRE DA QUELLA SCALA. (DETERMINIAMO SE IL PEZZO SI TROVA AL PIANO SUP O INFERIORE)
		}
		
		
		if(distance!=0) { //DOPO AVER DETERMINATO IL PEZZO PIÙ VICINO PER CIASCUNA SCALA VEDIAMO QUAL È IL PEZZO PIÙ VICINO PER TUTTE LE SCALE. DOPO AVERLO INDIVIDUATO VEDIAMO SE È PIÙ VICINO DEL PEZZO SULLA NOSTRA STESSA RIGA.
			for(Position p: availableStairs) {
				if(p.getDestination().getPosX()!=50 && p.getTotalDistance()<distance) {
					destination.setPosX(p.getDestination().getPosX());
					destination.setPosY(p.getDestination().getPosY());
					distance=p.getTotalDistance();
				}
			}
		}
		else {
			int max=100;
			for(Position p: availableStairs) {
				if(p.getDestination().getPosX()!=50 && p.getTotalDistance()<max) {
					destination.setPosX(p.getDestination().getPosX());
					destination.setPosY(p.getDestination().getPosY());
					max=p.getTotalDistance();
				}
			}
			
			if(max==100) {
				for(Position p: availableStairs) {
					if(p.getTotalDistance()<max) {
						destination.setPosX(p.getDestination().getPosX());
						destination.setPosY(p.getDestination().getPosY());
						max=p.getTotalDistance();
					}
				}
			}
		}
		
		findPath(); //DOPO AVER DETERMINATO LA DESTINAZIONE CALCOLIAMO IL PERCORSO PER RAGGIUNGERLA
		
		
		
		
		
//		if(destination.getPosX()==player.getPosX() && destination.getPosY()==player.getPosY()) {
//			ArrayList<PieceOfComponent> destinations=new ArrayList<PieceOfComponent>();
//			for(BurgerComponent b: burgerComponents) {
//				for(PieceOfComponent piece: b.getPieces()) {
//					if(piece.getPosX()-1 == player.getPosX() && !piece.getPressed()) {
//						destinations.add(piece);
//					}
//				}
//			}
//			
//			
//			if(destinations.size()>0) {
//				int max=20;
//				for(PieceOfComponent piece: destinations) {
//					if(player.getPosY()>piece.getPosY() && player.getPosY()-piece.getPosY()<max) {
//						destination.setPosX(piece.getPosX());
//						destination.setPosY(piece.getPosY());
//						max=player.getPosY()-piece.getPosY();
//					}
//					else if(player.getPosY()<piece.getPosY()&& piece.getPosY()-player.getPosY()<max) {
//						destination.setPosX(piece.getPosX());
//						destination.setPosY(piece.getPosY());
//						max=piece.getPosY()-player.getPosY();
//					}
//				}
//				
//				destination.setPosX(destination.getPosX()-1);
//				System.out.println(destination.getPosX()+" "+destination.getPosY());
//			}
//
//		}
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

		
		
		for(BurgerComponent bc : burgerComponents) {
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
	
	public void createSubScenes() {
		endGameSubScene = new GameSubScene();
		endGameSubScene.setLabel("YOU WIN");
		endGameSubScene.getLabel().setStyle("-fx-text-fill : Gold;");

		ArrayList<GameButton> buttons = new ArrayList<GameButton>();
		final GameButton restart = new GameButton("RESTART");
		
//		restart.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent event) {
//				playSudoku(easy);
//			}
//
//		});
		buttons.add(restart);
		final GameButton menù = new GameButton("MENÙ");
		
		menù.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				hiddenStage.show();
				mainStage.close();
			}
		});
		
		buttons.add(menù);

		endGameSubScene.addButtons(buttons);

		pane.getChildren().add(endGameSubScene);

		VBox buttonsBox = new VBox();
		buttonsBox.setSpacing(20);
		buttonsBox.setAlignment(Pos.CENTER_RIGHT);
		buttonsBox.getChildren().addAll(endGameSubScene.getButtons());
		buttonsBox.setLayoutX(130);
		buttonsBox.setLayoutY(100);

		endGameSubScene.getPane().getChildren().add(endGameSubScene.getLabel());
		endGameSubScene.getPane().getChildren().add(buttonsBox);
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
