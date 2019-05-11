package test;

import java.util.ArrayList;

import logic.model.BurgerComponent;
import logic.model.Enemy;
import logic.model.Map;
import logic.model.PieceOfComponent;

public class Main {
	
	public static void main(String[] args) {
		
		Map m=new Map(1);
		
		for(int i=0;i<m.getRowLen();i++) {
			for(int j=0;j<m.getColLen();j++) {
				System.out.print(m.getMatrixValue(i, j)+" ");
			}
			System.out.println();
		}
		
		ArrayList<BurgerComponent> array=m.getBurgerComponents();
		ArrayList<Enemy> arrayEn=m.getEnemies();
		
		for(int i=0;i<array.size();i++)
		{
			ArrayList<PieceOfComponent> pezzi=array.get(i).getPieces();
			System.out.println("Posizione pezzi componente:");
			for(int j=0;j<4;j++) {
				System.out.println(pezzi.get(j).getPosX()+" "+pezzi.get(j).getPosY());
			}
		}
		
		for(int i=0;i<arrayEn.size();i++) {
			System.out.println("Posizione enemy:");
			System.out.println(arrayEn.get(i).getPosX()+" "+arrayEn.get(i).getPosY());
		}
		

		
		
	}
}


