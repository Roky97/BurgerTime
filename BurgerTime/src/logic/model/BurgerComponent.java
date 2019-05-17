package logic.model;

import java.util.ArrayList;

public class BurgerComponent {
	
	ArrayList<PieceOfComponent> pieces;
	boolean allPiecesPressed;
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
	}
	
	public ArrayList<PieceOfComponent> getPieces(){
		return pieces;
	}
	
	public boolean AllPiecePressed() {
		checkIfAllPressed();
		return allPiecesPressed;
	}
	
	public void fallDown() {
		for(PieceOfComponent p: pieces) {
			p.setPosX(p.getPosX()+4);
		}
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

}
