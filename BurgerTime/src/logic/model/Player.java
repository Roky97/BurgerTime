package logic.model;

public class Player {
	private int posX;
	private int posY;
	private Map map;
	
	
	
	public Player(Map m) {
		map=m;
	}
	
	public Player(int x, int y,Map m) {
		posX = x;
		posY = y;
		map= m;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public void setPosX(int newPosX) {
		posX=newPosX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void setPosY(int newPosY) {
		posY=newPosY;
	}
	
	public boolean moveRight() {	
		if(posY+1<map.getColLen()){
			if((map.getMatrixValue(posX, (posY+1))=='0' || map.getMatrixValue(posX, posY+1)=='|') && posY+1<map.getColLen()) {
				posY+=1;
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean moveLeft() {
		
		if(posY-1>=0) {
			if((map.getMatrixValue(posX, (posY-1))=='0' || map.getMatrixValue(posX, posY-1)=='|') && posY-1>=0) {
				posY-=1;
				return true;
			}
		}

		return false;
	}

	public boolean moveDown() {
		if(posX+1<20) {
			if(map.getMatrixValue(posX+1, posY)=='|') {
				posX+=1;
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean moveUp() {	
		if(posX-1>=0) {
			if(map.getMatrixValue(posX-1, posY)=='|') {
				posX-=1;
				return true;
			}
		}
		
		return false;
	}
}
