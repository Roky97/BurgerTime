package logic.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("pieceOfComponent")
public class PieceOfComponent {

	@Param(0)
	private int posX;
	@Param(1)
	private int posY;
	private Map map;
	private boolean pressed;
	
	public PieceOfComponent() {
		
	}
	public PieceOfComponent(int x,int y,Map m) {
		posX=x;
		posY=y;
		map=m;
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
	
	public void fallTillNextFloor() {
		
		for(int i=posX+1;i<map.getRowLen();i++) {
			
			if((map.getMatrixValue(i, posY)!='1') && (map.getMatrixValue(i, posY)!='0')) {
				posX=i;
				return;
			}
		}
		
	}
	
	
	public void fallToCompleteBurger(int howManyComponent) {
		
		for(int i=posX+1;i<map.getRowLen();i++) {
			if((map.getMatrixValue(i, posY)!='1') && (map.getMatrixValue(i, posY)!='0')) {
				posX=i-howManyComponent;
				return;
			}
		}
	}
	

	
}
