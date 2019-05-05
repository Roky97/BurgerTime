package logic.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
	private int screenWeight=1000;
	private int screenHeight=1000;
	private int rowLen;
	private int colLen;  
	private Integer[][] matrix;
	
	
	public Map() {
		rowLen=20;
		colLen=20;
		matrix = new Integer[rowLen][colLen];
		
		fillMatrixFromTextFile();
		
	}
	
	public void fillMatrixFromTextFile() {
		FileReader f1 = null;
	    try {
	        f1 = new FileReader("FileMap/map1.txt");
	    } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    @SuppressWarnings("resource")
	    Scanner s1 = new Scanner(f1);
	    
	    
	    
	    int row=0;
	    while(s1.hasNext()) {
	    	
	    	String words = s1.next();
	    	
	    	for(int col=0;col<colLen;col++) {
	    		char ch = words.charAt(col);
	    		
	    		int a=Character.getNumericValue(ch);
	    		matrix[row][col]=a;
	    	}
	    	row++;
	    }

	}
	

	
	public int getRowLen() {
		return rowLen;
	}
	
	public int getColLen() {
		return colLen;
	}
	
	public Integer[][] getMatrix(){
		return matrix;
	}
	
	public int getMatrixValue(int i, int j) {
		return matrix[i][j];
	}
	
	public void setMatrixValue(int i,int j,int v) {
		matrix[i][j]=v;
	}
	
	
	public ArrayList<Enemy> getEnemies(){
		ArrayList<Enemy> enemies=new ArrayList<Enemy>();
		for(int i=0;i<rowLen;i++) {
			for(int j=0;j<colLen;j++) {
				Enemy e;
				if(matrix[i][j]==2) {
					e=new Enemy(this);
					e.setPosX(screenWeight/(i+1));
					e.setPosY(screenHeight/(j+1));
					enemies.add(e);
				}
			}
		}
		
		return enemies;
	}
	
	public ArrayList<BurgerComponent> getBurgerComponents(){
		
		ArrayList<BurgerComponent> burgerComponents=new ArrayList<BurgerComponent>();
		for(int i=0;i<rowLen;i++) {
			for(int j=0;j<colLen;j++) {
				BurgerComponent b=new BurgerComponent(this);
				if(matrix[i][j]==3) {
					for(int k=j;k<j+4;k++) {
						PieceOfComponent p=new PieceOfComponent(screenWeight/(i+1),screenHeight/(k+1));
						b.addPiece(p);
					}
					burgerComponents.add(b);
					j+=3;
				}
			}
		}
		
		return burgerComponents;
	}
	
}
