package logic.model;

import java.util.ArrayList;

import logic.ai.Path;

public class PathSupport {

	private PieceOfComponent piece;
	private ArrayList<Path> path;
	private ArrayList<Enemy> enemies;
	private int dangerousness;
	
	
	public PathSupport() {
		
	}
	public PathSupport(PieceOfComponent p, ArrayList<Path> path,ArrayList<Enemy> enemies) {
		
		piece=p;
		this.path=path;
		this.enemies=enemies;
		dangerousness=0;
	}
	
	
	public PieceOfComponent getPiece() {
		return piece;
	}
	
	public ArrayList<Path> getPath(){
		return path;
	}
	
	public int getDangerousness() {
		return dangerousness;
	}
	
	public void setPiece(PieceOfComponent p) {
		piece=p;
	}
	
	public void setPath(ArrayList<Path> path) {
		this.path=path;
	}
	
	public void computeDangerousness() {
		
	}
}
