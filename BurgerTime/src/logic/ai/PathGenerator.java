package logic.ai;

import java.util.ArrayList;
import java.util.Random;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import logic.model.Cell;
import logic.model.Map;
import logic.model.PieceOfComponent;
import logic.model.Player;

public class PathGenerator {
	

	private static String encodingResource = "encodings/findPath";
	private static Handler handler;
	
	private Map map;
	private ArrayList<Cell> cells;
	private PieceOfComponent piece;
	private Player player;
	private InputProgram facts;
	
	public PathGenerator(Map m) {
		
		
		setCell(m);
	}
	
	public void setCell(Map m) {
		map=m;
		cells=map.getAccessibleCell();	
	}
	
	public void setFacts(Player p, PieceOfComponent piece) {
		player=p;
		this.piece=piece;
	}
	
//	public void fillFacts() {
//		//INIZIALIZZO I FATTI
//		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
//		
//		facts= new ASPInputProgram();
//		
//		try {
//			
//			//AGGIUNGO LE CELLE PERCORRIBILI AI FATTI
//			for(Cell cell : cells) {
//				facts.addObjectInput(new Cell(cell.getRow(),cell.getColumn()));
//				System.out.println("cell("+cell.getRow()+","+cell.getColumn()+").");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			//AGGIUNGO I PEZZI DEI COMPONENTI AI FATTI
//			facts.addObjectInput(new PieceOfComponent(piece.getPosX(),piece.getPosY(),map));
//			System.out.println("pieceOfComponent("+piece.getPosX()+","+piece.getPosY()+").");
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//			//AGGIUNGO IL PLAYER AI FATTI
//		try {
//			facts.addObjectInput(new Player(player.getPosX(),player.getPosY(),map));
//			System.out.println("player("+player.getPosX()+","+player.getPosY()+").");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Random rand = new Random(); 
//		int value = rand.nextInt(100)+50;
//		try {
//			facts.addObjectInput(new Path(value,value));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		handler.addProgram(facts);
//		InputProgram encoding= new ASPInputProgram();
//		encoding.addFilesPath(encodingResource);
//		handler.addProgram(encoding);
//	}
	
	public ArrayList<Path> findSolution(){
		
		//INIZIALIZZO I FATTI
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		
		facts= new ASPInputProgram();
		
		try {
			
			//AGGIUNGO LE CELLE PERCORRIBILI AI FATTI
			for(Cell cell : cells) {
				facts.addObjectInput(new Cell(cell.getRow(),cell.getColumn()));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//AGGIUNGO I PEZZI DEI COMPONENTI AI FATTI
			facts.addObjectInput(new PieceOfComponent(piece.getPosX(),piece.getPosY(),map));
			System.out.println("pieceOfComponent("+piece.getPosX()+","+piece.getPosY()+").");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
			//AGGIUNGO IL PLAYER AI FATTI
		try {
			facts.addObjectInput(new Player(player.getPosX(),player.getPosY(),map));
			System.out.println("player("+player.getPosX()+","+player.getPosY()+").");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Random rand = new Random(); 
		int value = rand.nextInt(100)+50;
		try {
			facts.addObjectInput(new Path(value,50));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		handler.addProgram(facts);
		InputProgram encoding= new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		handler.addProgram(encoding);
		
		Output o =  handler.startSync();
		
		ArrayList<Path> pathToDo= new ArrayList<Path>();
		AnswerSets answers = (AnswerSets) o;
		for(AnswerSet a:answers.getAnswersets()){
			try {
				for(Object obj:a.getAtoms()){
					if(! (obj instanceof Path))continue;
					Path path= (Path) obj;
					pathToDo.add(path);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		

		
		for(int i=0;i<pathToDo.size();i++) {
			if(pathToDo.get(i).getRow()==player.getPosX() && pathToDo.get(i).getColumn()==player.getPosY()){
				pathToDo.remove(i);
			}
		}
		
		for(int i=0;i<pathToDo.size();i++) {
			if((pathToDo.get(i)).getColumn()==50){
				pathToDo.remove(i);
			}
		}
		
		
		System.out.println("Percorso per raggiungere la destinazione:");
		for(Path p : pathToDo) {
			System.out.println(p.getRow()+" "+p.getColumn());
		}
		
		if(pathToDo.size()>1) {
			pathToDo=sortPath(pathToDo);
		}
		

		
		return pathToDo;
	}
	
	public ArrayList<Path> sortPath(ArrayList<Path> pathToDo) {
		
		ArrayList<Path> sorted=new ArrayList<Path>();
		
		while(sorted.size()!= pathToDo.size()) {
			for(Path p: pathToDo) {
				
				if(sorted.size()==0) {
					if(p.nextTo(new Path(player.getPosX(),player.getPosY()))  &&   (p.getColumn()!=player.getPosY() || p.getRow()!=player.getPosX()) ) {
						sorted.add(p);
					}
				}
				else if(p.nextTo(sorted.get(sorted.size()-1)) && sorted.lastIndexOf(p)==-1){
					sorted.add(p);
				}
			}
		}
		

		return sorted;
		
	}
		
	
	
	
	
	

}
