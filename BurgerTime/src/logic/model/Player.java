package logic.model;

public class Player {
	private int posX;
	private int posY;
	private Map map;
	
	
	
	public Player(Map m) {
		map=m;
	}
	
	public Player(int x, int y) {
		posX = x;
		posY = y;
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
	
}
