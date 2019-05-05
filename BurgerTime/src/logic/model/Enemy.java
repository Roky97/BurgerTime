package logic.model;

public class Enemy {
	
	private int screenWeight=1000;
	private int screenHeight=1000;
	private double posX;
	private double posY;
	private Map map;
	
	public Enemy(Map m) {
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
