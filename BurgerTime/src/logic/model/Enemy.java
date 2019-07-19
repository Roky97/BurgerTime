package logic.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("enemy")
public class Enemy {
	
	@Param(0)
	private int posX;
	@Param(1)
	private int posY;
	private int initalPosX;
	private int initalPosY;
	private Map map;
	TypeEnemy type;
	boolean alive;
	
	public Enemy() {
		alive = true;
	}
	public Enemy(Map m) {
		map=m;
		alive = true;
	}
	
	public Enemy(int x, int y,Map m) {
		initalPosX = x;
		initalPosY = y;
		posX = x;
		posY = y;
		map= m;
		alive = true;
	}
	
	public boolean isAlive() {
		return alive;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alive ? 1231 : 1237);
		result = prime * result + initalPosX;
		result = prime * result + initalPosY;
		result = prime * result + posX;
		result = prime * result + posY;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enemy other = (Enemy) obj;
		if (alive != other.alive)
			return false;
		if (initalPosX != other.initalPosX)
			return false;
		if (initalPosY != other.initalPosY)
			return false;
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public int getInitalPosX() {
		return initalPosX;
	}
	
	public int getInitalPosY() {
		return initalPosY;
	}
	
	public void resetPosition() {
		this.posX = initalPosX;
		this.posY = initalPosY;
	}
	
	public void setType(TypeEnemy type) {
		this.type=type;
	}
	
	public TypeEnemy getType() {
		return type;
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
	
	public boolean moveRight() {	
		if(posY+1<map.getColLen()){
			if((map.getMatrixValue(posX, (posY+1))=='0' || map.getMatrixValue(posX, posY+1)=='|') && posY+1<map.getColLen()) {
				posY+=1;
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean moveLeft() {
		
		if(posY-1>=0) {
			if((map.getMatrixValue(posX, (posY-1))=='0' || map.getMatrixValue(posX, posY-1)=='|') && posY-1>=0) {
				posY-=1;
				return true;
			}
		}

		return false;
	}

	public boolean moveDown() {
		if(posX+1<21) {
			if(map.getMatrixValue(posX+1, posY)=='|') {
				posX+=1;
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean moveUp() {	
		if(posX-1>=0) {
			if(map.getMatrixValue(posX-1, posY)=='|') {
				posX-=1;
				return true;
			}
		}
		return false;
	}
	
}
