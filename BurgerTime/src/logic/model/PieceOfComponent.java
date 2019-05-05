package logic.model;

public class PieceOfComponent {

	private double posX;
	private double posY;
	private boolean pressed;
	
	public PieceOfComponent(double x,double y) {
		posX=x;
		posY=y;
		pressed=false;
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
	
	public boolean getPressed() {
		return pressed;
	}
	
	public void setPressed(boolean b) {
		pressed=b;
	}
	
}
