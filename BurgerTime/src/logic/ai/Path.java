package logic.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("path")
public class Path {
	
	@Param(0)
	private int row;
	@Param(1)
	private int column;
	
	public Path(int r,int c){
		this.row=r;
		this.column=c;
	}
	
	public Path() {
		
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public boolean nextTo(Path p) {
		
		if(p.getRow()==row+1 && p.getColumn()==column) {
			return true;
		}
		
		if(p.getRow()==row-1 && p.getColumn()==column) {
			return true;
		}
		
		if(p.getRow()==row && p.getColumn()==column+1) {
			return true;
		}
		
		if(p.getRow()==row && p.getColumn()==column-1) {
			return true;
		}
		
		return false;
	}
}
