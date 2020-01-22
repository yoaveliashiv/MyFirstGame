package gameClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import MyGameGUI.Window;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.Nodedata;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;

public class AutomaticPlay3 {
	public static boolean start;
	public static void main(String[] a) {

		WindowMange window = new WindowMange();
		window.setVisible(true);
		AutomaticPlay3 s= new AutomaticPlay3();
		s.test3(window);}
	public  void test3(WindowMange window) {
		

		while(window.getId()==-1) {
			System.out.println();;
		}
	
		int scenario_num=window.getPlay();
		Game_Server.login(window.getId());
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games

		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		ArrayList<Nodedata> roobet = new ArrayList<Nodedata>();
		ArrayList<Fruit> fruit = new ArrayList<Fruit>();
		AlgoGameRooboteStart A=new AlgoGameRooboteStart(game,scenario_num);
		Edgedata []E=A.getEdgedataMaxVal();

		String info = game.toString();

		ArrayList<Integer> srcR = new ArrayList<Integer>();
		for (int j = 0; j < E.length; j++) {
			game.addRobot(E[j].getSrc());
			roobet.add(new Nodedata(gg.getNode(E[j].getSrc())));
			 srcR.add(E[j].getSrc());
			//System.out.println("rstart"+E[j].getSrc());
		}




window.setG0Graph(gg);
window.setGame(game);
window.setRoobet(roobet);
window.setGrafh();
window.setList( game);
window.repaint();

		int dests[]=new int[E.length];
		for (int i = 0; i < dests.length; i++) {
			dests[i]=E[i].getDest();
		//	System.out.println("dest"+dests[i]);
		}
		start=true;
		// should be a Thread!!!
		game.startGame();
		
		trehd2(game);

		while(game.isRunning()) {
			moveRobots(game, gg,roobet ,window,dests,srcR,scenario_num);
		}
		String results ="lll"+ game.toString();
		System.out.println(results);
	}
	/** 
	 * Moves each of the robots along the edge, 
	 * in case the robot is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param roobet 
	 * @param window 
	 * @param window 
	 * @param src 
	 * @param scenario_num 
	 * @param log
	 */
	private static void moveRobots(game_service game, DGraph gg, ArrayList<Nodedata> roobet, WindowMange window,int []dests, ArrayList<Integer> src, int scenario_num) {

		  ArrayList<List<node_data>> dest1 = new ArrayList<List<node_data>>();		
		int dest;
		

				for (int j = 0; j < dests.length; j++) {
					while(game.isRunning() &&game.getRobots().get(j).toString().lastIndexOf("-")<0) {
					}

					if(dest1.size()==0 ) {
						AlgoGameWhereTheRoobotsGo R=new AlgoGameWhereTheRoobotsGo(game,src,scenario_num);
						dest1=R.getDest1();
						//System.out.println(dest1.size()+"size");
					}
					//System.out.println(j+"j");
					//System.out.println(dest1.size()+"dest1");
					dest=dest1.get(j).get(0).getKey();
					src.set(j, dest);
					dest1.get(j).remove(0);
					System.out.println(dest+"dest");
					game.chooseNextEdge(j, dest);
			//	game.move();
					roobet.get(j).copy(new Nodedata(gg.getNode(dest)));
				}
			System.out.println("end");
				
				window.repaint();
				WindowMange.setList(game);
				window.repaint();
				

			
		
	
	

}
/**
 * a very simple random walk implementation!
 * @param g
 * @param src
 * @return
 */


public static  void trehd2(game_service game) {
	Thread a=new  Thread(new Runnable() {
		@Override
		public void run() {
			while(game.isRunning()) {
				String c=game.getRobots().get(0).toString();
				String z=game.getRobots().get(1).toString();
				String t=game.getRobots().get(2).toString();

				
			//	System.out.println(game.getRobots());
				if((c.lastIndexOf("-")<0 ||z.lastIndexOf("-")<0 ||t.lastIndexOf("-")<0) &&game.timeToEnd()>72) {
					
					game.move();
				}
				try {
					Thread.sleep(55);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
		}
	});
	a.start();
}


}