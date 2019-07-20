package logic.model;

import java.util.ArrayList;

public class BurgerComponent {
	
	ArrayList<PieceOfComponent> pieces;
	boolean allPiecesPressed;
	boolean completed;
	boolean falling;
	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	TypeComponent type;
	Map map;
	
	public TypeComponent getType() {
		return type;
	}

	public void setType(TypeComponent type) {
		this.type = type;
	}
	
	public BurgerComponent(Map m) {
		map=m;
		allPiecesPressed=false;
		pieces=new ArrayList<PieceOfComponent>();
		completed=false;
	}
	
	public void addPiece(PieceOfComponent p) {
		pieces.add(p);
	}
	
	public void checkIfAllPressed() {
		for(int i=0;i<pieces.size();i++) {
			if(pieces.get(i).getPressed()==false) {
				return;
			}
		}
		allPiecesPressed=true;
//		falling  = true;
	}
	
	public ArrayList<PieceOfComponent> getPieces(){
		return pieces;
	}
	
	public boolean AllPiecePressed() {
		checkIfAllPressed();
		return allPiecesPressed;
	}
	
	
	public int containPiece(PieceOfComponent piece) {
		
		int index=4;
		for(int i=0;i<pieces.size();i++) {
			if(pieces.get(i).getPosX()==piece.getPosX() && pieces.get(i).getPosY()==piece.getPosY())
			{
				return i;
			}
		}
		return index;
	}
	
	public void setAllPiecePressed(boolean b) {
		for(PieceOfComponent p: pieces) {
			p.setPressed(b);
		}
		allPiecesPressed=b;
	}
	
	public boolean getCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean b) {
		completed=b;
	}
	
	public boolean lastPieceBeforeFall(PieceOfComponent p) {
		
		for(PieceOfComponent piece: pieces) {
			if(piece.getPosY()!=p.getPosY() && !piece.getPressed()) {
				return false;
			}
		}
		
		return true;
	}

}
