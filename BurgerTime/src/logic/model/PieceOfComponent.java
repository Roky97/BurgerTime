package logic.model;

public class PieceOfComponent {

	private int posX;
	private int posY;
	private boolean pressed;
	
	public PieceOfComponent(int x,int y) {
		posX=x;
		posY=y;
		pressed=false;
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
	
	public boolean getPressed() {
		return pressed;
	}
	
	public void setPressed(boolean b) {
		pressed=b;
	}
	
}
