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
	private char[][] matrix;
	
	
	public Map(int i) {
		rowLen=26;
		colLen=21;
		matrix = new char[rowLen][colLen];
		
		fillMatrixFromTextFile(i);
		
	}
	
	public void fillMatrixFromTextFile(int i) {
		FileReader f1 = null;
	    try {
	        f1 = new FileReader("FileMap/map" + i +".txt");
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
//	    		int a=Character.getNumericValue(ch);
	    		matrix[row][col]=ch;
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
	
	public char[][] getMatrix(){
		return matrix;
	}
	
	public char getMatrixValue(int i, int j) {
		return matrix[i][j];
	}
	
	public void setMatrixValue(int i,int j,char v) {
		matrix[i][j]=v;
	}
	
	public Player getPlayer() {
		for(int i=0;i<rowLen;i++) {
			for(int j=0;j<colLen;j++) {
				if(matrix[i][j]=='p') {
					matrix[i][j]='0';
					return new Player(i,j,this);
				}
			}
		}
		return null;
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
				if(matrix[i][j]=='d') {
					b.setType(TypeComponent.DOWN_BREAD);
					for(int k=j;k<j+4;k++) {
//						PieceOfComponent p=new PieceOfComponent(screenWeight/(i+1),screenHeight/(k+1));
						PieceOfComponent p=new PieceOfComponent(i,k,this);
						b.addPiece(p);
					}
					burgerComponents.add(b);
					j+=3; // prima era 3
				}
				
				if(matrix[i][j]=='h') {
					b.setType(TypeComponent.HAMBUGER);
					for(int k=j;k<j+4;k++) {
//						PieceOfComponent p=new PieceOfComponent(screenWeight/(i+1),screenHeight/(k+1));
						PieceOfComponent p=new PieceOfComponent(i,k,this);
						b.addPiece(p);
					}
					burgerComponents.add(b);
					j+=3;
				}
				
				if(matrix[i][j]=='s') {
					b.setType(TypeComponent.SALAD);
					for(int k=j;k<j+4;k++) {
//						PieceOfComponent p=new PieceOfComponent(screenWeight/(i+1),screenHeight/(k+1));
						PieceOfComponent p=new PieceOfComponent(i,k,this);
						b.addPiece(p);
					}
					burgerComponents.add(b);
					j+=3;
				}
				
				if(matrix[i][j]=='u') {
					b.setType(TypeComponent.UP_BREAD);
					for(int k=j;k<j+4;k++) {
//						PieceOfComponent p=new PieceOfComponent(screenWeight/(i+1),screenHeight/(k+1));
						PieceOfComponent p=new PieceOfComponent(i,k,this);
						b.addPiece(p);
					}
					burgerComponents.add(b);
					j+=3;
				}
			}
		}
		
		return burgerComponents;
	}
	
	@Override
	public String toString() {
		String mstring = "";
		for(int i=0;i<getRowLen();i++) {
			for(int j=0;j<getColLen();j++) {
				mstring +=getMatrixValue(i, j)+" ";
			}
			mstring+="\n";
		}
		return mstring;
	}
	
}
