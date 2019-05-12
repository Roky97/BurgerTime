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
	
	public boolean getAllPiecePressed() {
		checkIfAllPressed();
		return allPiecesPressed;
	}

}
