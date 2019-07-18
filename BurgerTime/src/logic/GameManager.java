package logic;

import java.util.ArrayList;

import javax.swing.text.AbstractDocument.LeafElement;

import logic.model.Enemy;
import logic.model.Map;
import logic.model.Player;

public class GameManager {

	private ArrayList<Map> levels; //gamemanager?
	private int totLevels = 1; //gamemanger
	private int currLevel = 1; //gamemanger
	private int lifes = 2; //gamemanger
	
	Player player;
	ArrayList<Enemy> enemies;
	
	public GameManager() {
		levels = new ArrayList<Map>();
		loadLevels();
		
		player = levels.get(currLevel-1).getPlayer();
		enemies= levels.get(currLevel-1).getEnemies();
	}
	
	public Map getCurrentMapLevel() {
		return levels.get(currLevel-1);
	}
	
	private void loadLevels() {
		for(int i = 0; i < totLevels; i++)
		{
			levels.add(new Map(i+1));
		}
	}

	public ArrayList<Map> getLevels() {
		return levels;
	}

//	public void setLevels(ArrayList<Map> levels) {
//		this.levels = levels;
//	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}

	public int getNumbersOfLevels() {
		return totLevels;
	}

	public void setNumbersOfLevels(int totLevels) {
		this.totLevels = totLevels;
	}

	public int getCurrentLevel() {
		return currLevel;
	}

	public void setCurrentLevel(int currLevel) {
		this.currLevel = currLevel;
	}

	public int getLifes() {
		return lifes;
	}
	
	public void removeLife() {
		this.lifes--;
	}
	
	public void setLifes(int lifes) {
		this.lifes = lifes;
	}
}
