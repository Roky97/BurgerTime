package logic;

import java.util.ArrayList;

import logic.model.Map;

public class GameManager {

	private ArrayList<Map> levels; //gamemanager?
	private int totLevels = 1; //gamemanger
	private int currLevel = 1; //gamemanger
	private int lifes = 4; //gamemanger
	
	public GameManager() {
		levels = new ArrayList<Map>();
		loadLevels();
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

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}
}
