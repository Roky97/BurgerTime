package logic.model;

import java.util.ArrayList;

import logic.ai.Path;

public class EnemySupport {

	private Enemy e;
	private ArrayList<Path> pathToDo;
	
	public EnemySupport() {
		e=new Enemy();
		pathToDo=new ArrayList<Path>();
	}
	
	public EnemySupport(Enemy e,ArrayList<Path> a) {
		this.e=e;
		pathToDo=a;
	}
	
	public Enemy getEnemy() {
		return e;
	}
	
	public ArrayList<Path> getPath(){
		return pathToDo;
	}
	
	public void setEnemy(Enemy e) {
		this.e=e;
	}
	
	public void setPath(ArrayList<Path> path) {
		pathToDo=path;
	}
}
