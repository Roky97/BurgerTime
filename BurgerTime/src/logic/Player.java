package logic;

public class Player {
	private int screenWeight=1000;
	private int screenHeight=1000;
	private double posX;
	private double posY;
	private Map map;
	
	public Player(Map m) {
		map=m;
	}
	
	public double getPosX() {
		return posX;
	}
	
	public void setPosX(double newPosX) {
		posX=newPosX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public void setPosY(double newPosY) {
		posY=newPosY;
	}
	
}
