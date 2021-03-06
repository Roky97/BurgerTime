package logic.model;

import java.util.ArrayList;

public class Position {

	private int posX; //POSIZIONE SCALA X,Y
	private int posY; 
	private int upFloor; // RIGA DEL PIANO ALTO ALLA SCALA
	private int downFloor; //RIGA DEL PIANO BASSO ALLA SCALA
	private PieceOfComponent upPiece; //PEZZO PIÙ VICINO SITUATO AL PIANO SUP DELLA SCALA
	private PieceOfComponent downPiece; //PEZZO PIÙ LONTANO SITUATO AL PIANO INFERIORE DELLA SCALA
	private int distanceFromPlayer; //DISTANZA DELLA SCALA X,Y DAL PLAYER. (QUANTE CASELLE TRA PLAYER E LA SCALA IN QUESTIONE)
	private Map map;	
	private int distanceUpFloorUpPiece; //DISTANZA TRA LA SCALA DEL PIANO SUPERIORE E IL PEZZO DEL PIANO SUPERIORE
	private int distanceDownFloorDownPiece; //DISTANZA TRA LA SCALA DEL PIANO INFERIORE E IL PEZZO DEL PIANO INFERIORE
	
	private PieceOfComponent dest; //DESTINAZIONE PIÙ VICINA DA RAGGIUNGERE A PARTIRE DALLA SCALA X,Y CALCOLATA IN BASE ALLE DISTANZE SOPRA CITATE
	private int totalDistance;  //DISTANZA TOTALE TRA PLAYER E DEST
	private ArrayList<BurgerComponent> components;
	private boolean towardUpFloor;
	
	public Position() {
		
	}
	public Position(int x, int y,Map m) {
		posX=x;
		posY=y;
		map=m;
		upPiece=new PieceOfComponent(50,50,m);
		downPiece=new PieceOfComponent(50,50,m);
		dest=new PieceOfComponent(50,50,m);
		totalDistance=0;
		upFloor=50;
		downFloor=50;
	}
	
	public void setComponents(ArrayList<BurgerComponent> components) {
		this.components=components;
	}
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void setPosX(int x) {
		posX=x;
	}
	
	public void setPosY(int y) {
		posY=y;
	}	
	public void setDistanceFromPlayer(int d) {
		distanceFromPlayer=d;
	}
	public int getDistanceFromPlayer() {
		return distanceFromPlayer;
	}
	
	
	public void findUpFloor() {
		
		for(int i=posX-1;i>=0;i--) {
			
			if( map.getMatrixValue(i, posY+1)=='0'  || map.getMatrixValue(i, posY-1)=='0'){
				upFloor=i;		
				break;
			}
		}
	}
	
	public void findDownFloor() {
		
		for(int i=posX+1;i<21;i++) {
			if(map.getMatrixValue(i, posY+1)=='0' || map.getMatrixValue(i, posY-1)=='0') {
				downFloor=i;
				break;
			}
		}	
	}
	
	
	public int getUpFloor() {
		return upFloor;
	}
	
	public int getDownFloor() {
		return downFloor;
	}
	
	public PieceOfComponent getUpPiece() {
		return upPiece;
	}
	
	public PieceOfComponent getDownPiece() {
		return downPiece;
	}
	
	public PieceOfComponent getDestination() {
		return dest;
	}
	
	public int getTotalDistance() {
		return totalDistance;
	}
	public int getDistanceUpFloorUpPiece() {
		return distanceUpFloorUpPiece;
	}
	public int getDistanceDownFloorDownPiece() {
		return distanceDownFloorDownPiece;
	}
	public void setDistanceUpFloorUpPiece(int d) {
		distanceUpFloorUpPiece=d;
	}
	
	public void setDistanceDownFloorDownPiece(int d) {
		distanceDownFloorDownPiece=d;
	}
	
	public boolean getTowardUpFloor() {
		return towardUpFloor;
	}
	
	public void findNearestPiece() {
		
		if(upFloor!=50 && downFloor==50) {
			
			if(distanceUpFloorUpPiece!=0) {
				dest=upPiece;
				towardUpFloor=true;
				totalDistance=distanceFromPlayer+(posX-upFloor)+distanceUpFloorUpPiece;
			}
			else {
				dest.setPosX(upFloor);
				dest.setPosY(posY);
				towardUpFloor=true;
				totalDistance=distanceFromPlayer+(posX-upFloor);
			}
		}
		else if(upFloor==50 && downFloor!=50){
			if(distanceDownFloorDownPiece!=0) {
				dest=downPiece;
				towardUpFloor=false;
				totalDistance=distanceFromPlayer+(downFloor-posX)+distanceDownFloorDownPiece;
			}
			else {
				dest.setPosX(downFloor);
				dest.setPosY(posY);
				towardUpFloor=false;
				totalDistance=distanceFromPlayer+(downFloor-posX);
			}	
		}
		else {
			if(distanceUpFloorUpPiece!=0 && distanceDownFloorDownPiece==0) {
				dest=upPiece;
				towardUpFloor=true;
				totalDistance=distanceFromPlayer+distanceUpFloorUpPiece+(posX-upFloor);
			}
			else if(distanceDownFloorDownPiece!=0 && distanceUpFloorUpPiece==0) {
				dest=downPiece;
				towardUpFloor=false;
				totalDistance=distanceFromPlayer+distanceDownFloorDownPiece+(downFloor-posX);
			}
			else if(distanceUpFloorUpPiece==0 && distanceDownFloorDownPiece==0) {
			
				boolean somethingUpstairs=false;
				for(BurgerComponent b:components) {
					for(PieceOfComponent p: b.getPieces()) {
						if(p.getPosX()<posX) {
							somethingUpstairs=true;
						}
					}
				}
				
				if(somethingUpstairs) {
					dest.setPosX(upFloor);
					dest.setPosY(posY);
					towardUpFloor=true;
					totalDistance=distanceFromPlayer+(posX-upFloor);
				}else {
					dest.setPosX(downFloor);
					dest.setPosY(posY);
					towardUpFloor=false;
					totalDistance=distanceFromPlayer+(downFloor-posX);
				}
			}
			else {
				
				if(posX-upFloor+distanceUpFloorUpPiece == downFloor-posX + distanceDownFloorDownPiece) {
					dest=upPiece;
					towardUpFloor=true;
					totalDistance=distanceUpFloorUpPiece+(posX-upFloor)+distanceFromPlayer;
				}

				else if((posX-upFloor)+distanceUpFloorUpPiece < (downFloor-posX)+distanceDownFloorDownPiece) {
						dest=upPiece;
						towardUpFloor=true;
						totalDistance=distanceFromPlayer+distanceUpFloorUpPiece+(posX-upFloor);
					}
				else if((posX-upFloor)+distanceUpFloorUpPiece > (downFloor-posX)+distanceDownFloorDownPiece) {
						dest=downPiece;
						towardUpFloor=false;
						totalDistance=distanceFromPlayer+distanceDownFloorDownPiece+(downFloor-posX);
				}
			}
		}
	}
					
}
