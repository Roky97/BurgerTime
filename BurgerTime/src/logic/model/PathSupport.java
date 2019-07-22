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
		
		for(Path p : path) {
			
			for(Enemy e : enemies) {
				if(e.isAlive()) {
					if(e.getPosX()==p.getRow() && e.getPosY()==p.getColumn()) {
						dangerousness+=2;
					}
					else if(Math.abs(e.getPosX()-p.getRow())<2 && e.getPosY()==p.getColumn()) {
						dangerousness+=1;
					}
					else if(e.getPosX()==p.getRow() && Math.abs(e.getPosY()-p.getColumn())<2) {
						dangerousness+=1;
					}
				}
			}
		}
	}
}
